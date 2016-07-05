package application;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.DefaultDatabaseGrabber;
import quiz.User;

/**
 * Servlet implementation class FriendSearchServlet
 */
@WebServlet("/FriendSearchServlet")
public class FriendSearchServlet extends HttpServlet implements ServletConstants {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			response.setContentType("text/plain");
			String friendPrefix = request.getParameter("friendNamePrefix");
			String user = (String) request.getSession().getAttribute(USER_NAME_PARAM);
			PrintWriter out = response.getWriter();
			DefaultDatabaseGrabber db = (DefaultDatabaseGrabber) request.getServletContext().getAttribute(DATABASE_ATTRIBUTE);
			db.connect();
			User currentUser = db.loadUser(user);
			out.println("<table id='friendsStatus' border='1'>");
			out.println("<thead><tr>");
			out.println("<th>friend name</th>"
					+ "<th>quiz name</th>"
					+ "<th>action</th>"
					+ "<th>date</th>"
					+ "<th>percent correct</th>"
					+ "<th>time amount</th>");
			out.println("</tr></thead><tbody>");
			for(String friendName : currentUser.getFriends()) {
				if (friendName.startsWith(friendPrefix)) {
					User friend = db.loadUser(friendName);
					out.println(createTableRowFriendsCreatedQuizzes(friend));
					out.println(createTableRowFriendsHistory(friend));
				}
			}
			db.close();
			out.print("</tbody></table>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * Returns the link to the userName's user page/
	 */
	private String getUserLink(String userName) {
		return "<a href='" + USER_PAGE_ADDRESS + "?" + USER_NAME_PARAM + "=" + userName + "'>" + userName + "</a>";
	}
	
	/*
	 * Returns the link to the quizName's quiz page/
	 */
	private String getQuizLink(String quizName) {
		return "<a href='" + QUIZ_SUMMARY_SERVLET + "?" + QUIZ_NAME_PARAM + "=" + quizName + "'>" + quizName + "</a>";
	}
	
	/*
	 * Returns a html table row string of data friends name, created quiz name
	 * and status it has created quiz.
	 */
	private String createTableRowFriendsCreatedQuizzes(User friend) {
		return friend.getCreatedQuizzes()
				.stream()
					.reduce("", (result, quizName) -> {
					return result + "<tr><td>" + getUserLink(friend.getName()) + "</td>" + "<td>"
							+ getQuizLink(quizName) + "</td><td>created</td><td></td><td></td><td></td></tr>";
				});
	}

	/*
	 * Returns a html table row string of data friends name, taken quiz name,
	 * status it has taken quiz and performance of taken quiz with order of
	 * date, percent correct and time amount.
	 */
	private String createTableRowFriendsHistory(User friend) {
		return friend.getHistory()
				.parallelStream()
					.reduce("", (result, performance) -> {
						return result + "<tr><td>" + getUserLink(friend.getName()) + "</td>" + 
								"<td>" + getQuizLink(performance.getQuiz()) + "</td><td>taken</td>" +
								"<td>" + performance.getDate() + "</td>" + 
								"<td>" + performance.getPercentCorrect() + "</td>" + 
								"<td>" + performance.getAmountTime() +"</td></tr>";
					}, (accumulated1, accumulated2) -> {
						return accumulated1 + accumulated2; 
					});
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
