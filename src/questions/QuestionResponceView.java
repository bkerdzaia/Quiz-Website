package questions;

public class QuestionResponceView implements QuestionView {

	@Override
	public String displayQuestion(Question question) {
		QuestionResponce curQuestion = (QuestionResponce) question;
		String html = "<br><h3>"+ curQuestion.getQuestionText() +"</h3>";
		html+="<input type=\"text\" name=\"possibleAnswer\" placeholder=\"Answer\"><br>";
		return html;
	}

}
