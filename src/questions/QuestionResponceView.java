package questions;

public class QuestionResponceView implements QuestionView {

	@Override
	public String displayQuestion(Question question) {
		QuestionResponce curQuestion = (QuestionResponce) question;
		// TODO It's stub
		return "<br> Question responce <br>";
	}

}
