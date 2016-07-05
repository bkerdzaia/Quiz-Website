package quiz;

import java.sql.Timestamp;

/**
 * @author dav23r
 * Encapsulates ordinary text message that users 
 * send to interact with each other. Implements 
 * abstract methods of 'Message' interface.
 */
public class TextMessage implements Message {
	
	private String message;
	private String sender;
	private Timestamp sentDate;
	
	/** Empty constructor */
	public TextMessage(){}

	@Override
	public String displayMessage() {
		return "<p>" + message + "</p>";
	}

	/**
	 * Sets message text.
	 * @param message - text to be set
	 */
	public void setMessage(String message){
		this.message = message;
	}
	
	/**
	 * Getter for message text.
	 * @return bare message withot html
	 */
	public String getMessage(){
		return message;
	}
	
	@Override
	public void setSenderName(String name){
		this.sender = name;
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