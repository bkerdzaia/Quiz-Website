package quiz;

public class FriendRequest implements Message {

	private String senderName, recipientName;
	
	@Override
	public String displayMessage() {
		return "<div class='freindRequestClass'><form action='AddFriend' method='post'>" +
				"<p>" + senderName + " want's to become friends</p>" +
				"<input type='hidden' name='senderName' value='" + senderName + "'>" +
				"<input type='hidden' name='recipientName' value='" + recipientName + "'>" +
				"what is your answer <input type='button' value='no'>" +
				"<input type='submit' value='yes'>" +
				"</form></div>";
	}
	
	public void setSender(String senderName){
		this.senderName = senderName;
	}
	
	public void setRecipient(String recipientName){
		this.recipientName = recipientName;
	}

}
