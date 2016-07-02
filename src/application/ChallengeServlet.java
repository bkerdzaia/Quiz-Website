package application;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DefaultDatabaseGrabber;
import factory.DefaultUserFactory;
import quiz.Challenge;
import quiz.Quiz;

/**
 * Servlet implementation class ChallengeServlet
 */
@WebServlet("/ChallengeServlet")
public class ChallengeServlet extends HttpServlet implements ServletConstants {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("challenge servlet");
		String user = (String) request.getSession().getAttribute(USER_NAME_PARAM);
		String friendName = request.getParameter("friendName");
		Quiz quiz = (Quiz) request.getSession().getAttribute(ServletConstants.QUIZ_NAME_PARAM);
		String addressToRedirect;
		try {
			DefaultDatabaseGrabber db = (DefaultDatabaseGrabber) request.getServletContext().getAttribute(DATABASE_ATTRIBUTE);
			db.connect();
			System.out.println("user: " + user + " friend: " + friendName + " quizName: " + quiz.getName());
			Challenge chal = DefaultUserFactory.getFactoryInstance().getChallenge();
			chal.setSenderName(user);
			chal.setQuizUrl("QuizSummaryServlet?" + ServletConstants.QUIZ_NAME_PARAM + "=" + quiz.getName());
			System.out.println("sending message: " + db.sendMessage(user, friendName, chal.displayMessage(), new Timestamp(System.currentTimeMillis())));
			db.close();
			addressToRedirect = HOMEPAGE_ADDRESS + "?" + USER_NAME_PARAM + "=" + user;
		} catch (Exception e) {
			e.printStackTrace();
			addressToRedirect = ERROR_PAGE_ADDRESS;
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(addressToRedirect);
		dispatcher.forward(request, response);
	}
}
