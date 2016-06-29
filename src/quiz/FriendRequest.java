package quiz;

public class FriendRequest implements Message {

	String senderName, recipientName;
	
	@Override
	public String displayMessage() {
		return "Hey " + recipientName + ", " + senderName + 
				" wants to be friends with you";
	}
	
	public void setSender(String senderName){
		this.senderName = senderName;
	}
	
	public void setRecipient(String recipientName){
		this.recipientName = recipientName;
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


	@Override
	public void setRead() {
		// TODO Auto-generated method stub
		
	}

}
