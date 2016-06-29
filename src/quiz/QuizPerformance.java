package quiz;

import java.sql.Timestamp;

/**
 * Encapsulates user's particular attemp, it's performance
 * on some quiz. The bean provides getters/setters for 
 * data manipualtion.
 */
public class QuizPerformance {
	
	private String userName;
	private String quizName;
	private Timestamp date;
	private int amountTimeSecs;
	private double percentCorrect;
	
	/** Empty constructor **/
	public QuizPerformance() {}

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

	/**
	 * Getter for date of the attempt
	 * @return attempt date
	 */
	public Timestamp getDate() {
		return date;
	}

	/**
	 * Setter for  date of the attempt
	 * @param date - the date to be set
	 */
	public void setDate(Timestamp date) {
		this.date = date;
	}

	/**
	 * Setter for time period the user took for
	 * the particular attempt.
	 * @return time taken on the quiz (seconds)
	 */
	public int getAmountTime() {
		return amountTimeSecs;
	}

	/**
	 * Setter for time interval the user took for
	 * the particular attempt.
	 * @param amountTimeSecs - number of seconds taken
	 */
	public void setAmountTime(int amountTimeSecs) {
		this.amountTimeSecs = amountTimeSecs;
	}

	/**
	 * Getter for percentage of correct answers.
	 * @return percentage of correct answers (double)
	 */
	public double getPercentCorrect() {
		return percentCorrect;
	}

	/**
	 * Setter for percentage of correct answers.
	 * @param percentCorrect (double)
	 */
	public void setPercentCorrect(double percentCorrect) {
		this.percentCorrect = percentCorrect;
	}

}
