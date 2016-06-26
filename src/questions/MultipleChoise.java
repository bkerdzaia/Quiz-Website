package questions;

import java.util.ArrayList;
import java.util.Set;

import factory.QuestionFactory;

public class MultipleChoise extends Question {

	ArrayList<String> possibleChoises = null;
	int correctAnswerIndex;
	
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

}
