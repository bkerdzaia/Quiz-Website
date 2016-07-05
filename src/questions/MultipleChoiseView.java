package questions;

import java.util.ArrayList;

public class MultipleChoiseView implements QuestionView {

	@Override
	public String displayQuestion(Question question) {
		MultipleChoise curQuestion = (MultipleChoise) question;
		String html = "<br><h3>"+ curQuestion.getQuestionText() +"</h3>";
		html+="<div><p>";
		ArrayList <String> answers = curQuestion.getPossibleChoises();
			//html+="<input type=\"radio\" id=\"qiu\" name=\"possibleAnswer\" value=\""+answers.get(0)+"\">"+ answers.get(0) +"<br>";
		for(int i = 0; i < answers.size(); i++)
			html+="<input type=\"radio\" id=\"qiu\" name=\"possibleAnswer\" value=\""+answers.get(i)+"\">"+ answers.get(i) +"<br>";
		html+="</p></div>";
		return html;
	}

}
