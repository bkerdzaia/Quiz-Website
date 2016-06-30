package questions;

/**
 * @author dav23r
 * Implementation of default view for PictureResponse
 * type of problem.
 */
public class PictureResponseView implements QuestionView {

	@Override
	public String displayQuestion(Question question) {
		PictureResponse curQuestion = (PictureResponse) question;
		String html = "<br><h3>"+ curQuestion.getQuestionText() +"</h3>";
		html+= "<img style=\"max-height:250;max-width:350px;\" src=\""+ curQuestion.getPictureUrl() +"\" alt=\"Question Image\"><br>";
		html+="<br><input type=\"text\" name=\"possibleAnswer\" placeholder=\"Answer\"><br>";
		return html;
	}

}
