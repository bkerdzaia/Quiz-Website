package questions;

import java.util.Map;
import quiz.QuizQuestions;

public class QuestionAdd {

	/***
	 * Add all question create by creator to the questions from map keys that
	 * starts startKey
	 * 
	 * @param map 
	 * @param startKey
	 * @param creator
	 * @param questions
	 */
	public void addQuestionType(Map<String, String[]> map, String startKey, QuestionCreator creator,
			QuizQuestions questions) {
		map.keySet().stream()
			.filter(key -> key.startsWith(startKey))
				.forEach(elem -> questions.add(creator.create(map.get(elem))));
	}

}
