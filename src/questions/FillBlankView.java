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
		// TODO it's stub , user curQuestion to extract info
		return "<br> FillInBlankQuestion <br>";
	}

}
