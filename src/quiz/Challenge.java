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
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Challenge))
			return false;
		Challenge other = (Challenge) obj;
		return false;
	}

	@Override
	public boolean isRead() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void setRead() {
		// TODO Auto-generated method stub
	}
	
}
