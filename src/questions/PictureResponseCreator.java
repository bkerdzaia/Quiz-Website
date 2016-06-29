package questions;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import factory.DefaultQuestionFactory;

public class PictureResponseCreator implements QuestionCreator {

	@Override
	public Question create(String[] questions) {
		PictureResponse pictureResponse = DefaultQuestionFactory.getFactoryInstance().getPictureResponseQuestion();
		pictureResponse.setQuestionText(questions[QUESTION_ID]);
		pictureResponse.setPictureUrl(questions[PICTURE_URL_ID]);
		String[] answer = Arrays.copyOfRange(questions, PICTURE_URL_ID+1, questions.length);
		Set<String> list = new HashSet<>();
		Collections.addAll(list, answer);
		pictureResponse.setCorrectAnswers(list);
		return pictureResponse;
	}

}
