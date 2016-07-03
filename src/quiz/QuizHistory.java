package quiz;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class QuizHistory extends History<PerformanceOnQuiz> {

	/**
	 * Sorts the PerformanceOnQuiz by comparator for the user's whose name equal
	 * to userName.
	 * 
	 * @param userName
	 *            - the name of user whose performance should be sorted.
	 * @param comparator
	 *            - the comparator of Performance for sorting
	 * @return list of sorted PerformanceOnQuiz of given user's name
	 */
	public List<PerformanceOnQuiz> sortByUser(String userName, Comparator<Performance> comparator) {
		return this.stream()
				.filter(quizPerformance -> quizPerformance.getUser().equals(userName))
					.sorted(comparator)
						.collect(Collectors.toList());
	}

}
