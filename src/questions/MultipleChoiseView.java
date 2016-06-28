package questions;

public class MultipleChoiseView implements QuestionView {

	@Override
	public String displayQuestion(Question question) {
		MultipleChoise curQuestion = (MultipleChoise) question;
		String html = "<br><h3>"+ curQuestion.getQuestionText() +"</h3>";
		html+="<div><p>";
		for(String possAns : curQuestion.getPossibleChoises())
			html+="<input type=\"radio\" name=\"possibleAnswer\" value=\"ans1\">"+ possAns +"<br>";
		html+="</p></div>";
		return html;
	}

}
