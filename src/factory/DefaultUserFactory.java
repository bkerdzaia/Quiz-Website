package factory;

import quiz.FriendList;
import quiz.History;
import quiz.User;
import quiz.UserList;
import quiz.UserMessageList;

/**
 * @author dav23r
 * Default factory for creating user-related objects, the one
 * that should be used in production code. Uses singleton pattern.
 */
public class DefaultUserFactory implements UserFactory{

	// Lazy evaluation
	private static DefaultUserFactory defUserFactoryInstance = null;
	
	// Hide constructor
	DefaultUserFactory(){}
	
	/**
	 * Static method which returns factory instance that is 
	 * shared across different clients.
	 * @return DefaultUserFactory
	 */
	public static UserFactory getFactoryInstance(){
		if (defUserFactoryInstance == null)
			defUserFactoryInstance = new DefaultUserFactory();

		return defUserFactoryInstance;
	}
	
	@Override
	public User getUser() {
		return new User();
	}

	@Override
	public History getHistory() {
		return new History();
	}

	@Override
	public FriendList getFriendList() {
		return new FriendList();
	}

	@Override
	public UserMessageList getMessageList() {
		return new UserMessageList();
	}

	@Override
	public UserList getUserList() {
		return new UserList();
	}

	
}
