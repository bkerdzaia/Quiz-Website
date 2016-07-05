package application;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.DatabaseGrabber;
import quiz.QuizCollection;
import quiz.User;

/**
 * Servlet implementation class HomepageServlet
 */
@WebServlet("/HomepageServlet")
public class HomepageServlet extends HttpServlet implements ServletConstants {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String redirectAdress;
		try {
			DatabaseGrabber db = (DatabaseGrabber) request.getServletContext()
					.getAttribute(ServletConstants.DATABASE_ATTRIBUTE);
			db.connect();
			HttpSession session = request.getSession();
			String userName = (String) session.getAttribute(ServletConstants.USER_NAME_PARAM);
			User user = db.loadUser(userName);
			QuizCollection popularQuizzes = db.getPopularQuizzes();
			QuizCollection recentlyCreatedQuiz = db.getRecentlyCreatedQuizzes();
			session.setAttribute(USER_OBJECT_ATTRIBUTE, user);
			session.setAttribute(POPULAR_QUIZZES_ATTRIBUTE, popularQuizzes);
			session.setAttribute(RECENTLY_CREATED_QUIZZES_ATTRIBUTE, recentlyCreatedQuiz); 
			db.close();
			redirectAdress = "homepage.jsp";
		} catch (SQLException e) {
			e.printStackTrace();
			redirectAdress = ERROR_PAGE_ADDRESS;
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(redirectAdress);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
