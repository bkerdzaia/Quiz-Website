package application;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import factory.DefaultQuestionFactory;
import factory.QuestionFactory;
import questions.FillBlank;
import questions.MultipleChoise;
import questions.PictureResponse;
import questions.QuestionResponce;
import quiz.Quiz;
import quiz.QuizProperty;
import quiz.QuizQuestions;
import quiz.UserList;

/**
 * Servlet implementation class DisplayQuizServlet
 */
@WebServlet("/DisplayQuizServlet")
public class DisplayQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String address;
		try {
			System.out.println("Quiz is being displayed");
			DatabaseGrabber db = (DatabaseGrabber) request.getServletContext().getAttribute(LoginServlet.DATABASE_ATTRIBUTE);
			if (db == null) {
				db = DefaultDatabaseFactory.getFactoryInstance().getDatabaseGrabber();
				request.getServletContext().setAttribute(LoginServlet.DATABASE_ATTRIBUTE, db);
			}
			HttpSession session = request.getSession();
			db.connect();
//			Enumeration<String> x = request.getParameterNames();
//			while (x.hasMoreElements()) {
//				System.out.println("param names: " + x.nextElement());
//			}
			String quizName = request.getParameter("quizName");
//			String quizName = "ab";
//			Quiz quiz = db.loadQuiz(quizName);
			Quiz quiz = null;
			if (quiz == null) {
				quiz = new Quiz();
				quiz.setCreator("Sandro");
				quiz.setDescription("description");
				quiz.setName(quizName);
				quiz.setSummaryStatistics(0);
				QuizQuestions q = new QuizQuestions();
				FillBlank q1 = DefaultQuestionFactory.getFactoryInstance().getFillBlankQuestion();
				q1.setQuestionText("Where are you?");
				q1.setCorrectAnswers(null);
				q.add(q1);
				PictureResponse q2 = DefaultQuestionFactory.getFactoryInstance().getPictureResponseQuestion();
				q2.setQuestionText("Who is this?");
				q2.setPictureUrl("https://fbcdn-sphotos-c-a.akamaihd.net/hphotos-ak-xft1/v/t1.0-9/10464363_900142266748629_3826821890834226325_n.jpg?oh=f042d94c493974fd10d922926e9942b0&oe=58027C5B&__gda__=1475986641_7428a87f5a19f4fc26e8c27e10b4e6bf");
				q2.setCorrectAnswers(null);
				q.add(q2);
				MultipleChoise q3 = DefaultQuestionFactory.getFactoryInstance().getMultipleChoiseQuestion();
				q3.setQuestionText("Who wrote this?");
				ArrayList<String> s = new ArrayList<String>();
				s.add("Sandro");
				s.add("Baduri");
				s.add("Daviti");
				q3.setPossibleChoises(s);
				q3.setCorrectAnswerIndex(0);
				q.add(q3);
				QuizProperty qp = new QuizProperty(true,true,true);
				quiz.setProperty(qp);
				quiz.setQuestions(q);
			}
			session.setAttribute("quizName", quiz);
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
