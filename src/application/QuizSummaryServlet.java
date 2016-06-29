package application;

import java.io.*;
import java.sql.Timestamp;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import database.*;
import factory.*;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String address;
		try {
			System.out.println("quiz summary page");
			DatabaseGrabber db = (DatabaseGrabber) request.getServletContext().getAttribute(LoginServlet.DATABASE_ATTRIBUTE);
			if (db == null) {
				db = DefaultDatabaseFactory.getFactoryInstance().getDatabaseGrabber();
				request.getServletContext().setAttribute(LoginServlet.DATABASE_ATTRIBUTE, db);
			}
			HttpSession session = request.getSession();
			db.connect();
			String quizName = request.getParameter("name");
			Quiz quiz = null; //db.loadQuiz(quizName);
			if (quiz == null) {
				quiz = new Quiz();
				quiz.setCreator("go");
				quiz.setDescription("description");
				quiz.setName(quizName);
				quiz.setSummaryStatistics(0);
			}
			session.setAttribute("quizName", quiz);
			UserList highestPerformers = db.getHighestPerformers(quizName, null);
			Timestamp thisTimeYesterday = new Timestamp(System.currentTimeMillis()-24*60*60*1000);
			UserList topPerformersLastDay = db.getHighestPerformers(quizName, thisTimeYesterday);
			//UserList recentPerformers = db.getRecentTestTakers(quizName, null); // Method changed
			session.setAttribute("highestPerformers", highestPerformers);  // see database interface
			session.setAttribute("topPerformers", topPerformersLastDay);
			//session.setAttribute("recentPerformers", recentPerformers);
			db.close();
			address = "quiz-summary-page.jsp?name=" + quizName;
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

}
