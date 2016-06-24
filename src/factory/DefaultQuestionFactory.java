package factory;

import questions.FillBlank;
import questions.FillBlankView;
import questions.MultipleChoise;
import questions.MultipleChoiseView;
import questions.PictureResponse;
import questions.PictureResponseView;
import questions.QuestionResponce;
import questions.QuestionResponceView;

/**
 * @author dav23r
 * Default implementation of QuestionFactory interface; hands back
 * newly created, 'correct' object. Should be used in real code.
 * Implements singleton pattern.
 */
public class DefaultQuestionFactory implements QuestionFactory {

	private static DefaultQuestionFactory defFactoryInstance = null;
	
	// Hide constructor
	private DefaultQuestionFactory() {}
	
	public static QuestionFactory getDefaultQuestionFactory(){
		if (defFactoryInstance == null)
			defFactoryInstance = new DefaultQuestionFactory();
		return defFactoryInstance;
	}
	
	// Pass factory, as it's responsible for creation of views as well
	@Override
	public FillBlank getFillBlankQuestion() {
		return new FillBlank(defFactoryInstance); 
	}

	@Override
	public FillBlankView getFillBlankView() {
		return new FillBlankView();
	}

	// Pass factory, as it's responsible for creation of views as well.
	@Override
	public QuestionResponce getQuestionResponceQuestion() {
		return new QuestionResponce(defFactoryInstance);
	}

	@Override
	public QuestionResponceView getQuestionResponceView() {
		return new QuestionResponceView();
	}

	// Pass factory, as it's responsible fore creation of views as well.
	@Override
	public MultipleChoise getMultipleChoiseQuestion() {
		return new MultipleChoise(defFactoryInstance);
	}

	@Override
	public MultipleChoiseView getMultipleChoiseView() {
		return new MultipleChoiseView();
	}

	// Pass factory, as it's responsible fore creation of views as well.
	@Override
	public PictureResponse getPictureResponseQuestion() {
		return new PictureResponse(defFactoryInstance);
	}

	@Override
	public PictureResponseView getPictureResponseView() {
		return new PictureResponseView();
	}

}
