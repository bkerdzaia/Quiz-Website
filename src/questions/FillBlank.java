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
	Set<String> correctAnswers = null;
	// Stores index where text field should 'start'
	int fieldPositionIndex;
	
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

}
