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
		// TODO it's sub, use curQuestion to extract info
		return "<br> PictureResponse <br>";
	}

}
