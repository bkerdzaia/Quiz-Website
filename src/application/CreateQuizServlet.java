package application;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import factory.*;
import questions.*;
import quiz.*;

/**
 * Servlet implementation class CreateQuizServlet
 */
@WebServlet("/CreateQuizServlet")
public class CreateQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.err.println("create quiz servlet");
		HttpSession session = request.getSession();
		String user = (String) session.getAttribute("userName");
		System.out.println("creator user is: " + user);
		
		String quizName = request.getParameter("quizNameText");
		String description = request.getParameter("quizDescriptionText");
		String[] quizProperty = request.getParameterValues("QuizProperties");
		Enumeration<String> x = request.getParameterNames();
		while (x.hasMoreElements()) {
			System.out.println("param names: " + x.nextElement());
		}
		
		Map<String, String[]> map = request.getParameterMap();
		Set<String> paramKeySet = map.keySet();
		Stream<String> blankQuestions = paramKeySet.stream().filter(key -> key.startsWith("fillInTheBlankQuestion"));
		Stream<String> multipleChoiceQuestions = paramKeySet.stream().filter(key -> key.startsWith("multipleChoiceQuestion"));
		Stream<String> questionResponseQuestions = paramKeySet.stream().filter(key -> key.startsWith("questionResponseQuestion"));
		Stream<String> pictureResponseQuestions = paramKeySet.stream().filter(key -> key.startsWith("pictureResponseQuestion"));
		
		QuizQuestions questions = DefaultQuizFactory.getFactoryInstance().getQuizQuestions();
		blankQuestions.forEach(elem -> { 
			questions.add(createFillBlank(map.get(elem)));
			System.out.println("blank elem: " + elem);
		});
		
		
		System.out.println("quizName: " + quizName);
		System.out.println("description: " + description);
		if (quizProperty != null) {
			for (String prop: quizProperty) {
				System.out.println("property: " + prop);
			}
		} else {
			System.out.println("quiz property null");
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("homepage.jsp?name="+user);
		dispatcher.forward(request, response);
	}
	
	private Question createFillBlank(String[] blankQuestion) {
		FillBlank blank = DefaultQuestionFactory.getFactoryInstance().getFillBlankQuestion();
		blank.setQuestionText(blankQuestion[0]);
		System.out.println("question: " + blankQuestion[0]);
		Set<String> answers = new HashSet<>();
		for (int i=1; i<blankQuestion.length; i++) {
			System.out.println("answers: " + blankQuestion[i]);
			answers.add(blankQuestion[i]);
		}
		blank.setCorrectAnswers(answers);
		return blank;
	}

}
