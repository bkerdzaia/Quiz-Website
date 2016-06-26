package quiz;

import java.util.ArrayList;
import factory.DefaultQuizFactory;

@SuppressWarnings("serial")
public class History extends ArrayList<QuizPerformance> {
	
	
	public History() {
	}
	
	public QuizCollection getMadeQuizzes() {
		return DefaultQuizFactory.getFactoryInstance().getQuizCollection();
	}
	

}
