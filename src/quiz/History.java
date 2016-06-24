package quiz;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class History implements Iterable<QuizPerformance> {
	
	private ArrayList<QuizPerformance> perfomance;
	
	public History() {
		perfomance = new ArrayList<QuizPerformance>();
	}
	
	public void addPerformance(QuizPerformance quiz) {
		perfomance.add(quiz);
	}
	
	public void orderBy(Comparator<QuizPerformance> comparator) {
		perfomance.sort(comparator);
	}

	@Override
	public Iterator<QuizPerformance> iterator() {
		return perfomance.iterator();
	}

}
