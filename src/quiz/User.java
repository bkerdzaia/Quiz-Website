package quiz;

public class User {
	
	private String name;
	private String passwordHash;
	private FriendList friends;
	private UserMessageList messages;
	private QuizCollection createdQuizzes;
	private QuizCollection takenQuizzes;
	private String aboutMe;
	private String pictureUrl;
	private History history;

	public User() {}

	public String getName() {
		return name;
	}
	
	public void setAboutMe(String aboutMe){
		this.aboutMe = aboutMe;
	}
	
	public String getAboutMe(){
		return aboutMe;
	}
	
	public void setPictureUrl(String pictureUrl){
		this.pictureUrl = pictureUrl;
	}
	
	public String getPictureUrl(){
		return pictureUrl;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
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

	public QuizCollection getCreatedQuizzes() {
		return createdQuizzes;
	}

	public void setCreatedQuizzes(QuizCollection createdQuizzes) {
		this.createdQuizzes = createdQuizzes;
	}

	public QuizCollection getTakenQuizzes() {
		return takenQuizzes;
	}

	public void setTakenQuizzes(QuizCollection takenQuizzes) {
		this.takenQuizzes = takenQuizzes;
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
