package quiz;

import java.util.Comparator;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class QuizHistory extends History<PerformanceOnQuiz> {

	public QuizHistory sortByUser(String userName, Comparator<Performance> comparator) {
		return (QuizHistory) this.stream()
				.filter(quizPerformance -> quizPerformance.getUser().equals(userName))
					.sorted(comparator)
						.collect(Collectors.toList());
	}

}
