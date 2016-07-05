package quiz;

import java.sql.Timestamp;

/**
 * @author dav23r
 * Stores one particular attempt of taking quiz. Is completely unaware 
 * who took the quiz and which quiz in particular. Classes UsersPerformance
 * and PerformanceOnQuiz extend this class to add aforementioned functionality.
 */
public class Performance {

	private Timestamp date;
	private long amountTimeSecs;
	private double percentCorrect;
	
	/** Empty constructor **/
	public Performance() {}


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
	public long getAmountTime() {
		return amountTimeSecs;
	}

	/**
	 * Setter for time interval the user took for
	 * the particular attempt.
	 * @param seconds - number of seconds taken
	 */
	public void setAmountTime(long seconds) {
		this.amountTimeSecs = seconds;
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
