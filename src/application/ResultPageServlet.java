package application;

import java.io.IOException;
import java.util.Enumeration;

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
 * Servlet implementation class ResultPageServlet
 */
@WebServlet("/ResultPageServlet")
public class ResultPageServlet extends HttpServlet implements ServletConstants{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String address = "quiz-result-page.jsp";
		try {
			System.out.println("Quiz Result Page");
			DatabaseGrabber db = (DatabaseGrabber) request.getServletContext().getAttribute(DATABASE_ATTRIBUTE);
			if (db == null) {
				db = DefaultDatabaseFactory.getFactoryInstance().getDatabaseGrabber();
				request.getServletContext().setAttribute(DATABASE_ATTRIBUTE, db);
			}
			HttpSession session = request.getSession();
			db.connect();
			
			Quiz quiz = (Quiz) session.getAttribute("quizName");
			System.out.println("Quiz name = " +quiz.getName());
			
			System.out.println("User name = "+session.getAttribute(USER_NAME_PARAM));
			
//			Enumeration<String> x = request.getParameterNames();
//			while (x.hasMoreElements()) {
//				System.out.println("param names: " + x.nextElement());
//			}
			
			String[] str = request.getParameterValues("possibleAnswer");
			
			for(int i=0; i<str.length; i++){
				quiz.getQuestions().get(i).setUsersChoice(str[i]);
				System.out.println(str[i]);
			}
			
			
			db.close();
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
