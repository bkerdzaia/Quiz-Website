package application;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import factory.DefaultQuestionFactory;
import factory.DefaultQuizFactory;
import factory.QuestionFactory;
import factory.QuizFactory;
import questions.Question;
import questions.QuestionAdd;
import quiz.QuizProperty;
import quiz.QuizQuestions;

/**
 * Servlet implementation class EditQuizServlet
 */
@WebServlet("/EditQuizServlet")
public class EditQuizServlet extends HttpServlet implements ServletConstants {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		QuestionFactory questionFactory = DefaultQuestionFactory.getFactoryInstance();
		QuizFactory quizFactory = DefaultQuizFactory.getFactoryInstance();
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

		System.out.println("quizName: " + quizName);
		System.out.println("desc: " + description);
		System.out.println("properties(is one page, isRand, isInstantlyMarked): " + property.isOnePage() + " "
				+ property.isRandomSeq() + " " + property.isInstantlyMarked());
		for (Question q: questions) {
			System.out.println("qestions: " + q.displayQuestion());
		}
		
	}

}
