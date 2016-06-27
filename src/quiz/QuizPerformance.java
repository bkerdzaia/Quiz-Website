package quiz;

import java.sql.Timestamp;

/**
 * Encapsulates user's particular attemp, it's performance
 * on some quiz. The bean provides getters/setters for 
 * data manipualtion.
 */
public class QuizPerformance {
	
	private String user;
	private String quiz;
	private Timestamp date;
	private int amountTimeSecs;
	private double percentCorrect;
	
	/** Empty constructor **/
	public QuizPerformance() {}

	public void setUser(String user){
		this.user = user;
	}
	
	public String getUser(){
		return user;
	}
	
	public String getQuiz() {
		return quiz;
	}

	public void setQuiz(String quiz) {
		this.quiz = quiz;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public int getAmountTime() {
		return amountTimeSecs;
	}

	public void setAmountTime(int amountTimeSecs) {
		this.amountTimeSecs = amountTimeSecs;
	}

	public double getPercentCorrect() {
		return percentCorrect;
	}

	public void setPercentCorrect(double percentCorrect) {
		this.percentCorrect = percentCorrect;
	}

}
