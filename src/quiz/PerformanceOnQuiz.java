package quiz;

/**
 * @author dav23r
 * The class extends 'Performance' to provide functionality
 * to store the user who took quiz. Quiz name is not stored,
 * it is assumed that client knows it, in particular client
 * has a collection of objects associated with same quiz.
 */
public class PerformanceOnQuiz extends Performance {
	
	// Field for user name
	private String userName;
	
	/**
	 * Setter for user's name
	 * @param userName to be set
	 */
	public void setUser(String userName){
		this.userName = userName;
	}
	
	/**
	 * Getter for user's name
	 * @return name of the user associated with the attempt
	 */
	public String getUser(){
		return userName;
	}

}
