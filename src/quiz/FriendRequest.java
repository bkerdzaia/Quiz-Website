package quiz;

import java.sql.Timestamp;

public class FriendRequest implements Message {

	private String senderName, recipientName;
	private Timestamp sentDate;
	
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
	
	public void setSenderName(String senderName){
		this.senderName = senderName;
	}
	
	public void setRecipient(String recipientName){
		this.recipientName = recipientName;
	}

	@Override
	public void setDate(Timestamp sentDate) {
		this.sentDate = sentDate;
	}

	@Override
	public Timestamp getDate() {
		return sentDate;
	}

}
