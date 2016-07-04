package questions;


/**
 * @author dav23r
 * View object for FillBlank type of question. Method 
 * 'displayQuestion' is fed with current question; based
 * on the question data it returns corresponding html.
 */
public class FillBlankView implements QuestionView {

	@Override
	public String displayQuestion(Question question) {
		FillBlank curQuestion = (FillBlank) question;
		String html = "<br><h3>"+ curQuestion.getQuestionText() +"</h3>";
		html+="<input type=\"text\" id=\"qiu\" name=\"possibleAnswer\" placeholder=\"Answer\"><br>";
		return html;
	}

}
