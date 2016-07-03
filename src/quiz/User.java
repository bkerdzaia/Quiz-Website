package quiz;

/**
 * @author dav23r
 * The class encapsulates information about system's user.
 * Created quizzes, history, inbox messages are stored alongside
 * with trivial identity information in the bean.
 */
public class User {
	
	private String name;
	private FriendList friends;
	private UserMessageList messages;
	private QuizCollection createdQuizzes;
	private String aboutMe;
	private String pictureUrl;
	private History<UsersPerformance> history;

	/** Empty constructor */
	public User() {}

	/**
	 * Setter for user's name
	 * @param name to be set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for user's name
	 * @return user's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter for 'aboutMe' user's description of him/herself 
	 * @param aboutMe - the description to be set
	 */
	public void setAboutMe(String aboutMe){
		this.aboutMe = aboutMe;
	}
	
	/**
	 * Gettef for 'aboutMe' 
	 * @return user's description of him/herself
	 */
	public String getAboutMe(){
		return aboutMe;
	}
	
	/**
	 * Setter for profile picture's url
	 * @param pictureUrl - url of picture to be used as profile
	 */
	public void setPictureUrl(String pictureUrl){
		this.pictureUrl = pictureUrl;
	}
	
	/**
	 * Getter for profile picture's url
	 * @return url of user's current profile picture
	 */
	public String getPictureUrl(){
		return pictureUrl;
	}

	public History<UsersPerformance> getHistory() {
		return history;
	}

	public void setHistory(History<UsersPerformance> history) {
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
