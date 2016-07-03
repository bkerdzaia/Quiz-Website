package quiz;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Collectors;

import factory.DefaultQuizFactory;

/**
 * Stores history of users performance on various quizzes.
 */
@SuppressWarnings("serial")
public class History<T extends Performance> extends ArrayList<T> {
	
	/*
	// TODO comment
	public History<PerformanceOnQuiz> sortByUser(String userName, Comparator<Performance> comparator) {
		return (ArrayList<QuizPerformance>) this.stream()
			.filter(quizPerformance -> quizPerformance.getUser().equals(userName))
				.sorted(comparator).collect(Collectors.toList());
	}
	
	*/
}
