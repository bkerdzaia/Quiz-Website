package questions;

/**
 * Interface that creates Questions.
 * 
 */
public interface QuestionCreator {
	
	public static final int QUESTION_ID = 0;
	public static final int PICTURE_URL_ID = 1;
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