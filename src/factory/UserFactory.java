package factory;

import application.Encryption;
import quiz.FriendList;
import quiz.History;
import quiz.User;
import quiz.UserMessageList;

public class UserFactory {

	public static Encryption getEncryption() {
		return new Encryption();
	}
	
	public static User getUser() {
		return new User();
	}
	
	public static History getHistory() {
		return new History();
	}
	
	public static FriendList getFriendList() {
		return new FriendList();
	}
	
	public static UserMessageList getMessageList() {
		return new UserMessageList();
	}
}
