package quiz;

import java.util.Comparator;

public class OrderByAmountTime implements Comparator<QuizPerformance> {

	@Override
	public int compare(QuizPerformance o1, QuizPerformance o2) {
		return Integer.compare(o1.getAmountTime(), o2.getAmountTime());
	}

}
