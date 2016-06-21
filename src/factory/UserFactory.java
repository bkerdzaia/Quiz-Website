package factory;
import quiz.*;

public interface UserFactory {
	
	public User getUser();
	
	public History getHistory();
	
	public FriendList getFriendList();
	
	public UserMessageList getMessageList();
}
