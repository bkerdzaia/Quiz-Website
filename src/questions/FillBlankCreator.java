package questions;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import factory.DefaultQuestionFactory;

public class FillBlankCreator implements QuestionCreator {

	/**
	 * Create a blank question 
	 */
	@Override
	public Question create(String[] questions) {
		FillBlank blank = DefaultQuestionFactory.getFactoryInstance().getFillBlankQuestion();
		blank.setQuestionText(questions[QUESTION_ID]);
		String[] answerArray = Arrays.copyOfRange(questions, QUESTION_ID+1, questions.length);
		Set<String> answers = new HashSet<>();
		Collections.addAll(answers, answerArray);
		blank.setCorrectAnswers(answers);
		return blank;
	}

}
