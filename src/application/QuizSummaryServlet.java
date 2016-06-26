package application;

import java.io.*;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import database.DatabaseGrabber;
import database.DatabaseListener;
import quiz.*;

/**
 * Servlet implementation class QuizSummaryServlet
 */
@WebServlet("/QuizSummaryServlet")
public class QuizSummaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DatabaseGrabber db = (DatabaseGrabber) request.getServletContext().getAttribute(DatabaseListener.ATTRIBUTE_NAME);
		HttpSession session = request.getSession();
		try {
			db.connect();
			String quizName = request.getParameter("name");
			Quiz quiz = db.loadQuiz(quizName);
			session.setAttribute("quizName", quiz);
			UserList highestPerformers = db.highestPerformers(quizName, null);
			UserList topPerformersLastDay = db.highestPerformers(quizName, null);
			UserList recentPerformers = db.getRecentTestTakers(quizName, null);
			session.setAttribute("highestPerformers", highestPerformers);
			session.setAttribute("topPerformers", topPerformersLastDay);
			session.setAttribute("recentPerformers", recentPerformers);
			db.close();
			RequestDispatcher dispatcher = request.getRequestDispatcher("quiz-summary-page.jsp?name=" + quizName);
			dispatcher.forward(request, response);
		} catch (SQLException e) {}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
