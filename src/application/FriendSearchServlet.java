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
import quiz.UsersPerformance;

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
		System.out.println("friend search servlet");
		try {
			response.setContentType("text/plain");
			String friendPrefix = request.getParameter("friendNamePrefix");
			String user = (String) request.getSession().getAttribute(USER_NAME_PARAM);
			System.out.println("search: " + friendPrefix);
			PrintWriter out = response.getWriter();
			DefaultDatabaseGrabber db = (DefaultDatabaseGrabber) request.getServletContext().getAttribute(DATABASE_ATTRIBUTE);
			db.connect();
			User currentUser = db.loadUser(user);
			out.println("<table border='1'>");
			out.println("<thead><tr>");
			out.println("<th>friend name</th>"
					+ "<th>quiz name</th>"
					+ "<th>action</th>"
					+ "<th>date</th>"
					+ "<th>percent correct</th>"
					+ "<th>time amount</th></thead>");
			out.println("</tr>");
			for(String friendName : currentUser.getFriends()) {
				if (friendName.startsWith(friendPrefix)) {
					System.out.println("friend: " + friendName);
					User friend = db.loadUser(friendName);
					System.out.println("from base: " + friend.getName());
					out.println(createTableRowFriendsCreatedQuizzes(friend));
					out.println(createTableRowFriendsHistory(friend));
				}
			}
			db.close();
			out.print("</table>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	private String createTableRowFriendsCreatedQuizzes(User friend) {
		String result = "";
		for(String quizName : friend.getCreatedQuizzes()) {
			result += "<tr><td>" + friend.getName() + "</td>" + "<td>" + quizName
					+ "</td><td>created</td><td></td><td></td><td></td></tr>";
		}
		return result;
	}


	private String createTableRowFriendsHistory(User friend) {
		String result = "";
		for(UsersPerformance performance : friend.getHistory()) {
			result += "<tr><td>" + friend.getName() + "</td>" + 
					"<td>" + performance.getQuiz() + "</td><td>taken</td>" +
					"<td>" + performance.getDate() + "</td>" + 
					"<td>" + performance.getPercentCorrect() + "</td>" + 
					"<td>" + performance.getAmountTime() +"</td></tr>";
		}
		return  result;
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
