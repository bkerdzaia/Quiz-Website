package quiz;

import java.util.ArrayList;
import java.util.Iterator;

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

}
