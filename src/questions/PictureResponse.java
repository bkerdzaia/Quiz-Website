package questions;

import java.util.Set;

import factory.QuestionFactory;

/**
 * @author dav23r
 * Implementation of PictureResponse type of Question.
 */
public class PictureResponse extends Question {
	
	// Stores correct answers for the question
	private Set<String> correctAnswers;
	
	// Stores url of picture
	private String pictureUrl;

	
	/**
	 * Convenience constructor. Sets view to
	 * default one of PictureResponse, uses factory
	 * to acquire latter.
	 */
	public PictureResponse(QuestionFactory questionFactory){
		this(questionFactory.getPictureResponseView());
	}
	
	/**
	 * Constructor for PictureResponse question.
	 * Accepts view object that will determine
	 * it's subsequent html layout on display.
	 * @param view 
	 */
	public PictureResponse(QuestionView view){
		setView(view);
	}
	
	/**
	 * Setter for correctAnswers field.
	 * @param correctAnswers - answers that should be considered 'correct'
	 */
	public void setCorrectAnswers(Set<String> correctAnswers){
		this.correctAnswers = correctAnswers;
	}

	/**
	 * Getter for correctAnswers field.
	 * @return set of answers that are correct for the question.
	 */
	public Set<String> getCorrectAnswers(){
		return correctAnswers;
	}
	
	/**
	 * Setter for picture url field.
	 * @param - url of the picture that will be displayed
	 */
	public void setPictureUrl(String pictureUrl){
		this.pictureUrl = pictureUrl;
	}
	
	/**
	 * Getter for picture url.
	 * @return url of the picture that would be diplayed as part of question.
	 */
	public String getPictureUrl(){
		return pictureUrl;
	}
	
	@Override
	public boolean isCorrectAnswer(String answer) {
		return (correctAnswers.contains(answer));
	}
	
	@Override
	public String getQuestionHtmlForm(int id) {
		String result = "<div class='pictureResponseQuestion'><p><b>PictureResponseType</b></p>"
				+ "Enter Question: <input type='text' name='pictureResponseQuestion" + id + "' value='" + getQuestionText() + "' required>"
				+ "Enter URL: <input type='text' name='pictureResponseQuestion" + id + "' value='" + getPictureUrl() + "' required>";
		for (String answer : correctAnswers) {
			result += "Enter Possible Answer: <input type='text' name='pictureResponseQuestion" + id + "' value='"
					+ answer + "' required>";
		}
		result += "</div>";
		return result;
	}

}
