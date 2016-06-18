package quiz;

public class FriendRequest implements Message {

	@Override
	public void displayMessage() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof FriendRequest))
			return false;
		FriendRequest other = (FriendRequest) obj;
		return false;
	}

	@Override
	public boolean isRead() {
		// TODO Auto-generated method stub
		return false;
	}

}
