package quiz;

import java.util.ArrayList;

public class User {
	
	private String name;
	private String password;
	private ArrayList<User> friends;
	private ArrayList<Message> messages;
	private History history;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public History getHistory() {
		return history;
	}

	public void setHistory(History history) {
		this.history = history;
	}
	
	public void addFriend(User friend) {
		friends.add(friend);
	}
	
	public void removeFreind(User friend) {
		friends.remove(friend);
	}
	
	public void addMessage(Message message) {
		messages.add(message);
	}

	public void removeMessage(Message message) {
		messages.remove(message);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (name == null) {
			return other.name == null;
		} 
		return name.equals(other.name);
	}
	
}
