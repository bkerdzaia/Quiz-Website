package application;

import java.io.*;
import java.sql.Timestamp;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import database.*;
import quiz.*;

/**
 * Servlet implementation class QuizSummaryServlet
 */
@WebServlet("/QuizSummaryServlet")
public class QuizSummaryServlet extends HttpServlet implements ServletConstants{
	private static final long serialVersionUID = 1L;
	
	private static final Timestamp START_TIME = new Timestamp(0);
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String address;
		try {
			System.out.println("quiz summary page");
			DatabaseGrabber db = (DatabaseGrabber) request.getServletContext().getAttribute(DATABASE_ATTRIBUTE);
			HttpSession session = request.getSession();
			db.connect();
			String quizName = request.getParameter(QUIZ_NAME_PARAM);
			Quiz quiz = db.loadQuiz(quizName);
			session.setAttribute(QUIZ_NAME_PARAM, quiz);
			UserList highestPerformers = db.getHighestPerformers(quizName, START_TIME);
			Timestamp thisTimeYesterday = new Timestamp(System.currentTimeMillis()-24*60*60*1000);
			UserList topPerformersLastDay = db.getHighestPerformers(quizName, thisTimeYesterday);
			History performance = db.getRecentTakersStats(quizName, START_TIME);
			session.setAttribute(HIGHEST_PERFORMANCE_ATTRIBUTE, highestPerformers);  
			session.setAttribute(TOP_PERFORMANCE_ATTRIBUTE, topPerformersLastDay);
			session.setAttribute(PERFORMANCE_ATTRIBUTE, performance);
			db.close();
			address = QUIZ_SUMMARY_PAGE_ADDRESS + "?" + QUIZ_NAME_PARAM + "=" + quizName;
		} catch (Exception e) {
			e.printStackTrace();
			address = ERROR_PAGE_ADDRESS;
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
