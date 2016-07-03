package factory;
import quiz.*;

/**
 * @author dav23r
 * Interface of Factory class used for creating quiz-related
 * objects. All the factories should obey following interface,
 * both default one and various mock implementations.
 */
public interface QuizFactory {

	/**
	 * Encapsulates creation of 'QuizCollection' object.
	 * @return new QuizCollection
	 */
	public QuizCollection getQuizCollection();
	
	/**
	 * Encapsulates creation of single 'Quiz' object.
	 * @return new Quiz
	 */
	public Quiz getQuiz();
	
	/**
	 * Encapsulates creation of 'QuizQuestions'
	 * @return new QuizQuestions
	 */
	public QuizQuestions getQuizQuestions();

	/**
	 * Encapsulates creation of 'UsersPerformance' object
	 * @return new UsersPerformance
	 */
	public UsersPerformance getUsersPerformance();
	
	/**
	 * Encapsulates creation of 'PerformanceOnQuiz' object
	 * @return new PerformanceOnQuiz
	 */
	public PerformanceOnQuiz getPerformanceOnQuiz();
	
	/**
	 * Encapsulates creation of 'QuizProperty' object
	 * @return new QuizProperty
	 */
	public QuizProperty getQuizProperty();
	
	/**
	 * The comparator of QuizPerformance objects by date
	 * @return new comparator OrderByDate
	 */
	public OrderByDate getOrderByDateInstance();
	
	/**
	 * The comparator of QuizPerformance objects by amount of time
	 * @return new comparator OrderByAmountTime
	 */
	public OrderByAmountTime getOrderByAmountTimeInstance();
	
	/**
	 * The comparator of QuizPerformance objects by percent correctness
	 * @return new comparator OrderByPercentCorrect
	 */
	public OrderByPercentCorrect getOrderByPercentCorrectInstance();
	
}
