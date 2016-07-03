package quiz;

/**
 * @author dav23r
 * Encapsulates ordinary text message that users 
 * send to interact with each other. Implements 
 * abstract methods of 'Message' interface.
 */
public class TextMessage implements Message {
	
	String message;
	
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

}