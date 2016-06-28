package quiz;

import java.util.ArrayList;
import java.util.Iterator;

public class FriendList implements Iterable<User> {
	
	private ArrayList<User> friends;
	
	public FriendList() {
		friends = new ArrayList<User>();
	}
	
	public void addFriend(User friend) {
		friends.add(friend);
	}
	
	public void removeFreind(User friend) {
		friends.remove(friend);
	}

	@Override
	public Iterator<User> iterator() {
		return friends.iterator();
	}

}
