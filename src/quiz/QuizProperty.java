package quiz;

/**
 * @author dav23r
 * The class encapsulates properties of the quiz.
 * Currently three properties are supported. They
 * are: randomizing order of questions, displaying
 * one/multiple page variant, and immediately 
 * evaluating user's answers.
 */
public class QuizProperty {
	
	private boolean randomQuestions;
	private boolean onePage;
	private boolean immediateCorrection;
	
	public QuizProperty(boolean randomQuestions, 
						boolean onePage, 
						boolean immediateCorrection) {
		
		this.randomQuestions = randomQuestions;
		this.onePage = onePage;
		this.immediateCorrection = immediateCorrection;
	}
	
	public QuizProperty() {}
	
	public void setRandomQuestion(boolean mark){
		randomQuestions = mark;
	}

	public void setOnePage(boolean mark){
		onePage = mark;
	}
	
	public void setInstantCorrection(boolean mark){
		immediateCorrection = mark;
	}
	
	public boolean isRandomSeq(){
		return randomQuestions;
	}
	
	public boolean isOnePage(){
		return onePage;
	}
	
	public boolean isInstantlyMarked(){
		return immediateCorrection;
	}

}
