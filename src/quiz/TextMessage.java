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
		return message;
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isRead() {
		// TODO Auto-generated method stub
		return false;
	}


	public void setMessage(String message){
		this.message = message;
	}

	@Override
	public void setRead() {
		// TODO Auto-generated method stub
		
	}

}