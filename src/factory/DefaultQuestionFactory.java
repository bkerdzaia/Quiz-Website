package factory;

import questions.FillBlank;
import questions.FillBlankCreator;
import questions.FillBlankView;
import questions.MultipleChoise;
import questions.MultipleChoiseCreator;
import questions.MultipleChoiseView;
import questions.PictureResponse;
import questions.PictureResponseCreator;
import questions.PictureResponseView;
import questions.QuestionAdd;
import questions.QuestionResponce;
import questions.QuestionResponseCreator;
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
	
	public static QuestionFactory getFactoryInstance(){
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

	@Override
	public QuestionAdd getQuestionAdd() {
		return new QuestionAdd();
	}

	@Override
	public FillBlankCreator getFillBlankCreator() {
		return new FillBlankCreator();
	}

	@Override
	public PictureResponseCreator getPictureResponseCreator() {
		return new PictureResponseCreator();
	}

	@Override
	public MultipleChoiseCreator getMultipleChoiseCreator() {
		return new MultipleChoiseCreator();
	}

	@Override
	public QuestionResponseCreator getQuestionResponceCreator() {
		return new QuestionResponseCreator();
	}

}
