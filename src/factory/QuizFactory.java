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
	 * Encapsulates creation of 'QuizPerformance' object
	 * @return new QuizPerformance
	 */
	public QuizPerformance getQuizPerformance();
	
	/**
	 * Encapsulates creation of 'QuizProperty' object
	 * @return new QuizProperty
	 */
	public QuizProperty getQuizProperty();
	
}
