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
	 * @return QuizCollection
	 */
	public QuizCollection getQuizCollection();
	
	/**
	 * Encapsulates creation of single 'Quiz' object.
	 * @return Quiz
	 */
	public Quiz getQuiz();
	
	/**
	 * Encapsulates creation of 'QuizQuestions'
	 * @return QuizQuestions
	 */
	public QuizQuestions getQuizQuestions();

}
