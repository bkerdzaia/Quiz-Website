package questions;

import java.util.Set;

import factory.QuestionFactory;

/**
 * @author dav23r
 * Implementation of 'QuestionResponce' type questions.
 *
 */
public class QuestionResponce extends Question {

	// Stores correct answers for question
	private Set<String> correctAnswers = null;


	/**
	 * Convenience constructor. Provides default 
	 * view (QuestionResponceView). 
	 */
	public QuestionResponce(QuestionFactory questionFactory){
		this(questionFactory.getQuestionResponceView());
	}
	

	/**
	 * Constructor for QuestionResponce question, 
	 * accepts view object.
	 * @param view
	 */
	public QuestionResponce(QuestionView view){
		setView(view);
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
	
	
	@Override
	public boolean isCorrectAnswer(String answer) {
		return (correctAnswers.contains(answer));
	}


	@Override
	public String getQuestionHtmlForm(int id) {
		String result = "<div class='questionResponseQuestion'><p><b>QuestionResponseType</b></p>" +
					"Enter Question: <input type='text' name='questionResponseQuestion" + id + "' value='" + getQuestionText() + "' required>";
		for (String answer : correctAnswers) {
			result += "Enter Possible Answer: <input type='text' name='questionResponseQuestion" + id + "' value='" + answer + "' required>";
		}
		result += "</div>";
		return result;
	}

}
