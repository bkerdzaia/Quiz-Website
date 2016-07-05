package questions;

import factory.QuestionFactory;

import java.util.Set;

/**
 * @author dav23r
 * Implementation of FillBlank question type. 
 * All the abstract method of Question object are
 * overridden + additional field for 'FillBlank' 
 * specific data. 
 */
public class FillBlank extends Question {

	// Stores possible correct answers 
	private Set<String> correctAnswers = null;
	// Stores index where text field should 'start'
	private int fieldPositionIndex;
	
	/**
	 * Convenience constructor. Provides default 
	 * view (FillBlankView). 
	 */
	public FillBlank(QuestionFactory questionFactory){
		this(questionFactory.getFillBlankView());
	}
	
	/**
	 * Constructor for FillBlank question, 
	 * accepts view object.
	 * @param view
	 */
	public FillBlank(QuestionView view){
		setView(view);
	}
	
	/**
	 * Setter for fieldPositionIndex.
	 * @param index - where should question field 
	 * 			      be placed in questionText
	 */
	public void setFieldPositionIndex(int index){
		this.fieldPositionIndex = index;
	}
	

	/**
	 * Getter for fieldPositionIndex
	 * @return position where question field 
	 * 		   should be placed in question
	 */
	public int getFieldPositionIndex(){
		return fieldPositionIndex;
	}
	
	
	/**
	 * Setter for corrects answers. 
	 * @param correctAnswers - set of string qualified as 
	 *                         correct answers
	 */
	public void setCorrectAnswers(Set<String> correctAnswers){
		this.correctAnswers = correctAnswers;
	}
	
	
	/**
	 * Getter for correct answers
	 * @return set of string qualified as 
	 *         correct answers
	 */
	public Set<String> getCorrectAnswers(){
		return correctAnswers;
	}
	
	
	/**
	 * Decides whether provided answer is correct.
	 * @return true if correct, otherwise false
	 */
	@Override
	public boolean isCorrectAnswer(String answer) {
		return (correctAnswers.contains(answer));
	}

	@Override
	public String getQuestionHtmlForm(int id) {
		String result = "<div class='fillInTheBlankQuestion'><p><b>FillInTheBlankType</b></p>"
				+ "Enter Question: <input type='text' name='fillInTheBlankQuestion" + id + "' value='"
				+ getQuestionText() + "' oninvalid='alert(\"question should have one blank ___ (three _) !\");' "
				+ "pattern='([^_]*(___){1}[^_]+)|([^_]+(___){1}[^_]*)' required><br>";
		for (String answers : correctAnswers) {
			result += "Enter Possible Answer: <input type='text' name='fillInTheBlankQuestion" + id + "' value='"
					+ answers + "' required>";
		}
		result += "</div>";
		return result;
	}

}
