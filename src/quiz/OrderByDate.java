package quiz;

import java.util.Comparator;

public class OrderByDate implements Comparator<QuizPerformance> {

	@Override
	public int compare(QuizPerformance o1, QuizPerformance o2) {
		return o1.getDate().compareTo(o2.getDate());
	}

}
