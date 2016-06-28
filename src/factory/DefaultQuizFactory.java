package factory;

import quiz.Quiz;
import quiz.QuizCollection;
import quiz.QuizPerformance;
import quiz.QuizProperty;
import quiz.QuizQuestions;

/**
 * 
 * @author dav23r
 * Default implementation of QuizFactory interface. Should be 
 * used in real application. Singleton pattern suits well to 
 * this circumstance.
 */
public class DefaultQuizFactory implements QuizFactory {

	public static DefaultQuizFactory defQuizFactory = null;
	
	// Hide constructor
	private DefaultQuizFactory(){};
	
	/**
	 * Instance of DefaultQuizFactory is handed back.
	 * @return DefaultQuizFactory
	 */
	public static QuizFactory getFactoryInstance(){
		if (defQuizFactory == null)
			defQuizFactory = new DefaultQuizFactory();
		
		return defQuizFactory;
	}
	
	@Override
	public QuizCollection getQuizCollection() {
		return new QuizCollection();
	}

	@Override
	public Quiz getQuiz() {
		return new Quiz();
	}

	@Override
	public QuizQuestions getQuizQuestions() {
		return new QuizQuestions();
	}

	@Override
	public QuizPerformance getQuizPerformance() {
		return new QuizPerformance();
	}

	@Override
	public QuizProperty getQuizProperty() {
		return new QuizProperty();
	}

}
