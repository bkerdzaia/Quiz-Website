package questions;

/**
 * Interface that creates Questions.
 * 
 */
public interface QuestionCreator {

	/**
	 * This method creates question object from given questions.
	 * 
	 * @param questions
	 *            array of questions, where first element is correct answer
	 * @return question object
	 */
	Question create(String[] questions);

}
