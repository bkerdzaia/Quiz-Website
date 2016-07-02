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
public class History extends ArrayList<QuizPerformance> {
	
	
	public History() {}
	
	public QuizCollection getMadeQuizzes() {
		// Acquire collection of quizzes from factory
		QuizCollection takenQuizzes = 
				DefaultQuizFactory.getFactoryInstance().getQuizCollection();
		// Iterate over 'performance' object and extract quiz names
		Iterator<QuizPerformance> it = this.iterator();
		while (it.hasNext())
			takenQuizzes.add(it.next().getQuiz());
		return takenQuizzes;
	}
	
	// TODO comment
	public ArrayList<QuizPerformance> sortByUser(String userName, Comparator<QuizPerformance> comparator) {
		return (ArrayList<QuizPerformance>) this.stream()
			.filter(quizPerformance -> quizPerformance.getUser().equals(userName))
				.sorted(comparator).collect(Collectors.toList());
	}

}
