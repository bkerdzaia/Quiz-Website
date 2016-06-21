package factory;
import quiz.*;

public interface QuizFactory {

	public QuizCollection getQuizCollection();
	
	public Quiz getQuiz();
	
	public UserMessageList getMessageList();
	
	public QuizQuestions getQuizQuestions();
}
