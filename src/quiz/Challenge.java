package quiz;

import java.sql.Timestamp;

import application.ServletConstants;

public class Challenge implements Message {

	private String quizName, senderName;
	private Timestamp sentDate;
	
	/** Empty constructor */
	public Challenge(){}

	public void setQuizName(String quizName){
		this.quizName = quizName;
	}
	
	public void setSenderName(String senderName){
		this.senderName = senderName;
	}
	
	@Override
	public String displayMessage() {
		return "<div class=challengeClass><p>" + senderName + " has challenged you to take"
				+ " a quiz <a href='QuizSummaryServlet?'" + ServletConstants.QUIZ_NAME_PARAM 
				+ "=" + quizName + "'>" + quizName + "</a></p></div>";
	}

	@Override
	public void setDate(Timestamp sentDate) {
		this.sentDate = sentDate;
	}

	public String getSenderName(){
		return senderName;
	}
	
	public String getQuizName(){
		return quizName;
	}

	@Override
	public Timestamp getDate() {
		return sentDate;
	}

}
