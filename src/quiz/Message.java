package quiz;

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
	 * Returns whether the message has been read or not.
	 * @return 'true' if read, otherwise 'false'
	 */
	public boolean isRead();
	
	/**
	 * Sets message statuts or 'read' by user.
	 */
	public void setRead();

}
