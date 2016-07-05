package quiz;

import java.sql.Timestamp;

/**
 * Interface that defines overall look of messages
 */
public interface Message {
	
	/**
	 * Returns message text as it should be displayed
	 * on the screen. Naturally different implementations
	 * of message will have completely different logic on
	 * the method.
	 * @return message as string
	 */
	public String displayMessage();
	
	/**
	 * Sets name of the user who sent the message
	 * @return - sender name
	 */
	public void setSenderName(String name);
	
	/**
	 * Sets date of sending message
	 */
	public void setDate(Timestamp sentDate);
	
	/**
	 * Return date when message was sent
	 * @return
	 */
	public Timestamp getDate();
	
}
