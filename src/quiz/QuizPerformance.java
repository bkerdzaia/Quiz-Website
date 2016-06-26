package quiz;

import java.sql.Time;
import java.util.Date;

public class QuizPerformance {
	
	private Quiz quiz;
	private Date date;
	private Time amountTimeSecs;
	private int percentCorrect;
	
	public QuizPerformance() {
		// TODO Auto-generated constructor stub
	}

	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getAmountTime() {
		return amountTimeSecs;
	}

	public void setAmountTime(Time amountTimeSecs) {
		this.amountTimeSecs = amountTimeSecs;
	}

	public int getPercentCorrect() {
		return percentCorrect;
	}

	public void setPercentCorrect(int percentCorrect) {
		this.percentCorrect = percentCorrect;
	}

}
