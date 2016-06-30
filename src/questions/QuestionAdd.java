package questions;

import java.util.Map;
import quiz.QuizQuestions;

public class QuestionAdd {

	/***
	 * Add all question created by creator to the questions from map values,
	 * whose key starts with startKey.
	 * 
	 * @param map
	 *            - the map of key values, where each key corresponds some
	 *            questions and answers array
	 * @param startKey
	 *            - the map keys prefix string
	 * @param creator
	 *            - the interface that creates Question type object from map
	 *            values
	 * @param questions
	 *            - the collection of questions where to add created quizzes
	 */
	public void addQuestionType(Map<String, String[]> map, String startKey, QuestionCreator creator,
			QuizQuestions questions) {
		map.keySet().stream()
			.filter(key -> key.startsWith(startKey))
				.forEach(elem -> questions.add(creator.create(map.get(elem))));
	}

}
