package questions;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import factory.DefaultQuestionFactory;

public class FillBlankCreator implements QuestionCreator {

	/**
	 * 
	 */
	@Override
	public Question create(String[] questions) {
		FillBlank blank = DefaultQuestionFactory.getFactoryInstance().getFillBlankQuestion();
		blank.setQuestionText(questions[0]);
		String[] answer = Arrays.copyOfRange(questions, 1, questions.length);
		Set<String> answers = new HashSet<>();
		Collections.addAll(answers, answer);
		blank.setCorrectAnswers(answers);
		return blank;
	}

}
