package application;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import database.DatabaseGrabber;
import factory.*;
import questions.*;
import quiz.*;

/**
 * Servlet implementation class CreateQuizServlet
 */
@WebServlet("/CreateQuizServlet")
public class CreateQuizServlet extends HttpServlet implements ServletConstants{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String address;
		try {
			DatabaseGrabber db = (DatabaseGrabber) request.getServletContext().getAttribute(DATABASE_ATTRIBUTE);
			db.connect();
			HttpSession session = request.getSession();
			QuestionFactory questionFactory = DefaultQuestionFactory.getFactoryInstance();
			QuizFactory quizFactory = DefaultQuizFactory.getFactoryInstance();
			String user = (String) session.getAttribute(USER_NAME_PARAM);
			String quizName = request.getParameter("quizNameText");
			String description = request.getParameter("quizDescriptionText");
			String[] quizProperty = request.getParameterValues("QuizProperties");
			QuizProperty property = quizFactory.getQuizProperty();
			List<String> properties = Arrays.asList(quizProperty);
			property.setOnePage(properties.contains("DisplayOnePage"));
			property.setRandomQuestion(properties.contains("RandomQuestions"));
			property.setInstantCorrection(properties.contains("ImmediateCorrection"));
			Map<String, String[]> map = request.getParameterMap();
			QuestionAdd add = questionFactory.getQuestionAdd();
			QuizQuestions questions = quizFactory.getQuizQuestions();
			add.addQuestionType(map, "fillInTheBlankQuestion", questionFactory.getFillBlankCreator(), questions);
			add.addQuestionType(map, "multipleChoiceQuestion", questionFactory.getMultipleChoiseCreator(), questions);
			add.addQuestionType(map, "questionResponseQuestion", questionFactory.getQuestionResponceCreator(), questions);
			add.addQuestionType(map, "pictureResponseQuestion", questionFactory.getPictureResponseCreator(), questions);
			Quiz quiz = quizFactory.getQuiz();
			quiz.setName(quizName);
			quiz.setDescription(description);
			quiz.setCreator(user);
			quiz.setCreationDate(new Timestamp(System.currentTimeMillis()));
			quiz.setProperty(property);
			quiz.setQuestions(questions);
			quiz.setSummaryStatistics(0);
			db.uploadQuiz(quiz);
			db.close();
			address = HOMEPAGE_ADDRESS;
		} catch (Exception e) {
			e.printStackTrace();
			address = ERROR_PAGE_ADDRESS;
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}

}
