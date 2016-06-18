package quiz;

import java.util.ArrayList;
import java.util.Iterator;

public class UserMessageList implements Iterable<Message> {
	
	private ArrayList<Message> messages;

	public UserMessageList() {
		messages = new ArrayList<Message>();
	}
	
	public void addMessage(Message message) {
		messages.add(message);
	}

	public void removeMessage(Message message) {
		messages.remove(message);
	}

	@Override
	public Iterator<Message> iterator() {
		return messages.iterator();
	}
	
}
