package factory;

import quiz.OrderByAmountTime;
import quiz.OrderByDate;
import quiz.OrderByPercentCorrect;
import quiz.PerformanceOnQuiz;
import quiz.Quiz;
import quiz.QuizCollection;
import quiz.QuizProperty;
import quiz.QuizQuestions;
import quiz.UsersPerformance;

/**
 * 
 * @author dav23r
 * Default implementation of QuizFactory interface. Should be 
 * used in real application. Singleton pattern suits well to 
 * this circumstance.
 */
public class DefaultQuizFactory implements QuizFactory {

	private static DefaultQuizFactory defQuizFactory = null;
	
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
	public QuizProperty getQuizProperty() {
		return new QuizProperty();
	}

	@Override
	public OrderByDate getOrderByDateInstance() {
		return new OrderByDate();
	}

	@Override
	public OrderByAmountTime getOrderByAmountTimeInstance() {
		return new OrderByAmountTime();
	}

	@Override
	public OrderByPercentCorrect getOrderByPercentCorrectInstance() {
		return new OrderByPercentCorrect();
	}

	@Override
	public PerformanceOnQuiz getPerformanceOnQuiz() {
		return new PerformanceOnQuiz();
	}

	@Override
	public UsersPerformance getUsersPerformance() {
		return new UsersPerformance();
	}

}
