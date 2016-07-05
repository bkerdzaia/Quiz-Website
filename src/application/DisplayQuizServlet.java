package application;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.DatabaseGrabber;
import factory.DefaultDatabaseFactory;
import quiz.Quiz;

/**
 * Servlet implementation class DisplayQuizServlet
 */
@WebServlet("/DisplayQuizServlet")
public class DisplayQuizServlet extends HttpServlet implements ServletConstants{
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String address;
		try {
			// If session does not contain user attribute then go to login page
			if (request.getSession().getAttribute(USER_NAME_PARAM) == null){
				response.sendRedirect(LOGIN_ADDRESS);
				return;
			}
			DatabaseGrabber db = (DatabaseGrabber) 
					request.getServletContext().getAttribute(DATABASE_ATTRIBUTE);
			HttpSession session = request.getSession();
			db.connect();
			
			String quizName = request.getParameter("quizName");
			Quiz quiz = db.loadQuiz(quizName);
			session.setAttribute("quizName", quiz);
			session.setAttribute("questIndex", 0);
			
			db.close();
			address = "quiz.jsp?quizName=" + quizName;
		} catch (Exception e) {
			e.printStackTrace();
			address = "error-page.jsp";
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
