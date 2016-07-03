package quiz;

/**
 * @author dav23r
 * The class extends Perfomance adding field for 'quiz',
 * thus representing user's single attempt's complete data.
 * (user name is not stored, it's assumed that client knows
 * user name, in particular one user's data is needed)
 */
public class UsersPerformance extends Performance {
	
	// Field for quiz name
	private String quizName;
	
	/**
	 * Getter for the quiz name.
	 * @return name of the quiz associated with the attempt
	 */
	public String getQuiz() {
		return quizName;
	}

	/**
	 * Setter for quiz name
	 * @param quizName - name of the quiz to be set
	 */
	public void setQuiz(String quizName) {
		this.quizName = quizName;
	}

}
