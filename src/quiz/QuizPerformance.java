package quiz;

import java.util.Date;

/**
 * Encapsulates user's particular attemp, it's performance
 * on some quiz. The bean provides getters/setters for 
 * data manipualtion.
 */
public class QuizPerformance {
	
	private String quiz;
	private Date date;
	private int amountTimeSecs;
	private int percentCorrect;
	
	/** Empty constructor **/
	public QuizPerformance() {}

	public String getQuiz() {
		return quiz;
	}

	public void setQuiz(String quiz) {
		this.quiz = quiz;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getAmountTime() {
		return amountTimeSecs;
	}

	public void setAmountTime(int amountTimeSecs) {
		this.amountTimeSecs = amountTimeSecs;
	}

	public int getPercentCorrect() {
		return percentCorrect;
	}

	public void setPercentCorrect(int percentCorrect) {
		this.percentCorrect = percentCorrect;
	}

}
