package quiz;

import java.util.Comparator;

public class OrderByAmountTime implements Comparator<Performance> {

	@Override
	public int compare(Performance o1, Performance o2) {
		return Long.compare(o1.getAmountTime(), o2.getAmountTime());
	}

}
