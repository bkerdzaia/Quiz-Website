package quiz;

import java.util.ArrayList;
import java.util.Iterator;

import questions.QuizType;

public class QuizQuestions implements Iterable<QuizType> {

	private ArrayList<QuizType> quizQuestion;

	public QuizQuestions() {
		quizQuestion = new ArrayList<QuizType>();
	}
	
	public void addQuestion(QuizType question) {
		quizQuestion.add(question);
	}

	@Override
	public Iterator<QuizType> iterator() {
		return quizQuestion.iterator();
	}
	
}
