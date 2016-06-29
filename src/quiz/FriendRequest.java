package quiz;

public class FriendRequest implements Message {

	String senderName, recipientName;
	
	@Override
	public String displayMessage() {
		return "Hey " + recipientName + ", " + senderName + 
				" wants to be friends with you. Click here to add";
	}
	
	public void setSender(String senderName){
		this.senderName = senderName;
	}
	
	public void setRecipient(String recipientName){
		this.recipientName = recipientName;
	}

}
