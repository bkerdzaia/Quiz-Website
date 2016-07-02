package quiz;

import java.util.Comparator;

public class OrderByPercentCorrect implements Comparator<QuizPerformance> {

	@Override
	public int compare(QuizPerformance o1, QuizPerformance o2) {
		return Double.compare(o1.getPercentCorrect(), o2.getPercentCorrect());
	}

}
