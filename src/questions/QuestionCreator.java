package questions;

/**
 * Interface that creates Questions.
 * 
 */
public interface QuestionCreator {
	
	/** the question index in questions array */
	public static final int QUESTION_ID = 0;
	/** the question index in questions array for picture response questions */
	public static final int PICTURE_URL_ID = 1;
	/** the correct answer index in questions array for multiple choice questions */
	public static final int CORRECT_ANSWER_ID = 1;

	/**
	 * This method creates question object from given questions.
	 * 
	 * @param questions
	 *            array of questions, where first element is correct answer
	 * @return question object
	 */
	Question create(String[] questions);

}