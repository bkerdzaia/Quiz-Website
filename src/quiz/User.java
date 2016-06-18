package quiz;

public class User {
	
	private String name;
	private String password;
	private FriendList friends;
	private UserMessageList messages;
	private QuizCollection createdQuiz;
	private QuizCollection madeQuiz;
	private History history;

	public User() {}

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
	
	public FriendList getFriends() {
		return friends;
	}

	public void setFriends(FriendList friends) {
		this.friends = friends;
	}
	
	public UserMessageList getMessages() {
		return messages;
	}

	public void setMessages(UserMessageList messages) {
		this.messages = messages;
	}

	public QuizCollection getCreatedQuiz() {
		return createdQuiz;
	}

	public void setCreatedQuiz(QuizCollection createdQuiz) {
		this.createdQuiz = createdQuiz;
	}

	public QuizCollection getMadeQuiz() {
		return madeQuiz;
	}

	public void setMadeQuiz(QuizCollection madeQuiz) {
		this.madeQuiz = madeQuiz;
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
	
	@Override
	public String toString() {
		return name;
	}
	
}
