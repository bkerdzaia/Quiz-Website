package quiz;

public class Challenge implements Message {

	private String quizUrl, senderName;
	
	/** Empty constructor */
	public Challenge(){}

	public void setQuizUrl(String quizUrl){
		this.quizUrl = quizUrl;
	}
	
	public void setSenderName(String senderName){
		this.senderName = senderName;
	}
	
	@Override
	public String displayMessage() {
		return "Come on, " + senderName + 
				" challenged you on the quiz " + 
				quizUrl;
	}
	
}
