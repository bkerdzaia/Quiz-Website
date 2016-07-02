package questions;

/**
 * @author dav23r
 * Class defines skeleton for different question types.
 * All the abstract methods will be implemented in 
 * subclasses. Only fields that are common to all possible
 * subclasses are included in abstract 'Question', the rest
 * is up to subclasses to decide how to store various data.
 * Attempt is made to make class proper java 'bean'.
 */
public abstract class Question {
	
	/** Stores question, and view object responsible
	 *  for displaying in form of html markup */
	private String questionText = null;
	/**
	 * Injection of view object in the class as field, "Strategy Pattern".
	 */
	private QuestionView view = null;
	
	private String usersAns = null;
	
	/**
	 * Sets new view. May be changed dynamically.
	 * @param view - new view for question object
	 */
	public void setView(QuestionView view){
		this.view = view;
	}
	
	
	/**
	 * Getter for view. 
	 * @return current view object of question.
	 */
	public QuestionView getView(){
		return this.view;
	}
	
	/**
	 * Sets questionText field to the provided string argument
	 * @param questionText
	 */
	public void setQuestionText(String questionText){
		this.questionText = questionText;
	}
	
	
	/**
	 * Getter for 'questionText' field. Use in particular by
	 * view object to generate html code.
	 */
	public String getQuestionText(){
		return questionText;
	}
	
	
	public void setUsersChoice(String ans){
		this.usersAns = ans;
	}
	
	public String getUsersChoice(){
		return usersAns;
	}
	
	public boolean isUsersAnswerCorrect(){
		return isCorrectAnswer(usersAns);
	}
	/**
	 * Checks whether provided answer is correct for this 
	 * question. The actual logic is up to subclasses.
	 * @param answer - possible guess for correct answer.
	 * @return true, if correct, otherwise false
	 */
	public abstract boolean isCorrectAnswer(String answer);
	
	
	/**
	 * Displays question in html format. Returns html text.
	 */
	public String displayQuestion(){
		return view.displayQuestion(this);
	}
	
}
