package factory;

import quiz.Quiz;
import quiz.QuizCollection;
import quiz.QuizQuestions;
import quiz.UserMessageList;

public class QuizFactory {

	public static QuizCollection getQuizCollection() {
		return new QuizCollection();
	}
	
	public static Quiz getQuiz() {
		return new Quiz();
	}
	
	public static UserMessageList getMessageList() {
		return new UserMessageList();
	}
	
	public static QuizQuestions getQuizQuestions() {
		return new QuizQuestions();
	}
}