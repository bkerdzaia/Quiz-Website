package questions;

import java.util.ArrayList;
import factory.QuestionFactory;

public class MultipleChoise extends Question {

	private ArrayList<String> possibleChoises = null;
	private int correctAnswerIndex;
	
	/**
	 * Convenience constructor, provides default 
	 * view object for MultipleChoise type problem.
	 * @param questionFactory - needed for creating view
	 */
	public MultipleChoise(QuestionFactory questionFactory){
		this(questionFactory.getMultipleChoiseView());
	}
	
	/**
	 * Constructor. Accepts view object that would be
	 * used for displaying problem in form of html.
	 * @param view
	 */
	public MultipleChoise(QuestionView view){
		setView(view);
	}
	
	/**
	 * Setter for possibleChoises field.
	 * @param possibleChoises - that would be displayed
	 */
	public void setPossibleChoises(ArrayList<String> possibleChoises){
		this.possibleChoises = possibleChoises;
	}
	
	/**
	 * Getter for possibleChoises field.
	 * @return possible choices that should be displayed
	 */
	public ArrayList<String> getPossibleChoises(){
		return possibleChoises;
	}
	
	/**
	 * Setter for correctAnswerIndex.
	 * @param index of the answer that should be 
	 * 		  considered correct in the array.
	 */
	public void setCorrectAnswerIndex(int correctAnswerIndex){
		this.correctAnswerIndex = correctAnswerIndex;
	}
	

	@Override
	public boolean isCorrectAnswer(String answer) {
		return possibleChoises.get(correctAnswerIndex).equals(answer);
	}

	@Override
	public String getQuestionHtmlForm(int id) {
		String result = " <div class='multipleChoiceQuestion'> " + "<p><b>MultipleChoiceType</b></p>"
				+ "Enter Question: <input type='text' name='multipleChoiceQuestion"+id+"' value='" + getQuestionText() +"' required>"
				+ "Enter Correct Answer: <input type='text' name='multipleChoiceQuestion"+id+"' value='" + possibleChoises.get(correctAnswerIndex) +"' required>";
		for (int i=0; i<possibleChoises.size(); i++) {
			if (i != correctAnswerIndex) {
				result += "Enter Possible Answer: <input type='text' name='multipleChoiceQuestion"+id+"' value='" + possibleChoises.get(i) + "' required>";
			}
		}
		result += "</div>";
		return result;
	}

}
