package factory;
import quiz.*;

/**
 * @author dav23r
 * Interface of factory for creating user-related object.
 * All the userFactory implementations should share this
 * particular interface.
 */
public interface UserFactory {

	/**
	 * Constructs 'user' object
	 * @return user
	 */
	public User getUser();
	
	/**
	 * Constructs 'History' object
	 * @return History
	 */
	public History getHistory();
	
	/**
	 * Constructs 'FriendsList' object
	 * @return FriendsList
	 */
	public FriendList getFriendList();
	
	/**
	 * Constructs 'MessageList' object
	 * @return MessageList
	 */
	public UserMessageList getMessageList();
	
	/**
	 * Constructs 'UserList' object
	 * @return UserList
	 */
	public UserList getUserList();
}
