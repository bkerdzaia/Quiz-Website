package questions;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import factory.DefaultQuestionFactory;

public class QuestionResponseCreator implements QuestionCreator {

	@Override
	public Question create(String[] questions) {
		QuestionResponce questionResponse = DefaultQuestionFactory.getFactoryInstance().getQuestionResponceQuestion();
		questionResponse.setQuestionText(questions[QUESTION_ID]);
		String[] answer = Arrays.copyOfRange(questions, QUESTION_ID+1, questions.length);
		Set<String> list = new HashSet<>();
		Collections.addAll(list, answer);
		questionResponse.setCorrectAnswers(list);
		return questionResponse;
	}

}
