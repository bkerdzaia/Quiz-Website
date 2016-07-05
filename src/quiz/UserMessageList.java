package quiz;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Stores collection of message objects as an iterable list
 */
@SuppressWarnings("serial")
public class UserMessageList extends ArrayList<Message> {
	
	/**
	 * Sorts messages chronologically. Most recent - first.
	 */
	public void sortByDate(){
		Collections.sort(this, (m1, m2) -> 
					m1.getDate().before(m2.getDate()) ? 1 : -1);
	}
}
