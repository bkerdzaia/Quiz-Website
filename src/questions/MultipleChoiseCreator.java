package questions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import factory.DefaultQuestionFactory;

public class MultipleChoiseCreator implements QuestionCreator {

	/**
	 * 
	 */
	@Override
	public Question create(String[] questions) {
		MultipleChoise multipleChoice = DefaultQuestionFactory.getFactoryInstance().getMultipleChoiseQuestion();
		multipleChoice.setQuestionText(questions[QUESTION_ID]);
		String corAnswer = questions[CORRECT_ANSWER_ID];
		String[] answer = Arrays.copyOfRange(questions, QUESTION_ID+1, questions.length);
		ArrayList<String> list = new ArrayList<>();
		Collections.addAll(list, answer);
		Collections.shuffle(list);
		multipleChoice.setPossibleChoises(list);
		multipleChoice.setCorrectAnswerIndex(list.indexOf(corAnswer));
		return multipleChoice;
	}

}
