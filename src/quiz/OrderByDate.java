package quiz;

import java.util.Comparator;

public class OrderByDate implements Comparator<Performance> {

	@Override
	public int compare(Performance o1, Performance o2) {
		return (-1) * o1.getDate().compareTo(o2.getDate());
	}

}
