package quiz;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class QuizHistory extends History<PerformanceOnQuiz> {

	public ArrayList<PerformanceOnQuiz> sortByUser(String userName, Comparator<Performance> comparator) {
		return (ArrayList<PerformanceOnQuiz>) this.stream()
				.filter(quizPerformance -> quizPerformance.getUser().equals(userName))
					.sorted(comparator)
						.collect(Collectors.toList());
	}

}
