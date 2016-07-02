package quiz;

import java.util.Comparator;

public class OrderByPercentCorrect implements Comparator<Performance> {

	@Override
	public int compare(Performance o1, Performance o2) {
		return Double.compare(o1.getPercentCorrect(), o2.getPercentCorrect());
	}

}
