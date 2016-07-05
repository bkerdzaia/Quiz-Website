package application;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
import questions.Question;
import quiz.Quiz;
import quiz.User;
import quiz.UsersPerformance;

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
			
			String userName = (String) session.getAttribute(ServletConstants.USER_NAME_PARAM);
			
			ArrayList<String> a= new ArrayList<String>();
			Enumeration<String> x = request.getParameterNames();
			while (x.hasMoreElements()) {
				a.add(x.nextElement());
			}
			
			int k=0;
			for(int j=0; j<a.size()-1; j++){
				String[] str = request.getParameterValues(a.get(j));
				for(int i=0; i<str.length; i++){
					quiz.getQuestions().get(k).setUsersChoice(str[i]);
					k++;
				}
			}
			
			
			String timeSpentString = (String) session.getAttribute("time");
			long timeSpent = Long.parseLong(timeSpentString);
			long seconds = (timeSpent / 1000) % 60 ;
			session.setAttribute("time", seconds);
			int score = 0;
			for(Question q: quiz.getQuestions()){
				if(q.isUsersAnswerCorrect())score++;
			}
			User user = db.loadUser(userName);
			session.setAttribute("realUser", user);
			UsersPerformance e = new UsersPerformance();
			e.setAmountTime(seconds);
			Date date = new Date();
			e.setDate(new Timestamp(date.getTime()));
			e.setPercentCorrect(score/(quiz.getQuestions().size())*100);
			e.setQuiz(quiz.getName());
			user.getHistory().add(e);
			
			quiz.setSummaryStatistics(score/(quiz.getQuestions().size())*100);
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
