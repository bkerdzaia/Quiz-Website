package factory;

import questions.*;

/**
 * @author dav23r
 * Interface of Factory used for constructing question-related
 * objects. All the factories should obey following interface,
 * both default one and various mock implementations.
 */
public interface QuestionFactory {

	/**
	 * Constructs new FillInBlankQuestion.
	 * @return
	 */
	public FillBlank getFillBlankQuestion();
	
	/**
	 * Constructs new FillInBlankView.
	 * @return
	 */
	public FillBlankView getFillBlankView();
	
	/**
	 * Constructs new Question of type 'QuestionResponce'
	 * @return
	 */
	public QuestionResponce getQuestionResponceQuestion();
	
	/**
	 * Constructs new view object for 'QuestionResponce' question.
	 * @return
	 */
	public QuestionResponceView getQuestionResponceView();
	
	/**
	 * Constructs new question of type MultipleChoise
	 * @return
	 */
	public MultipleChoise getMultipleChoiseQuestion();
	
	/**
	 * Constructs new view object for MultipleChoise question
	 * @return
	 */
	public MultipleChoiseView getMultipleChoiseView();
	
	/**
	 * Constructs new problem of type PictureResponse.
	 * @return
	 */
	public PictureResponse getPictureResponseQuestion();
	
	/**
	 * Creates new view object for PictureResponse problem.
	 * @return
	 */
	public PictureResponseView getPictureResponseView();
	
	/**
	 * Constructs new problem of type QuestionAdd
	 * @return
	 */
	public QuestionAdd getQuestionAdd();
	
	/**
	 * Constructs new problem of type FillBlankCreator
	 * @return
	 */
	public FillBlankCreator getFillBlankCreator();
	
	/**
	 * Constructs new problem of type PictureResponseCreator
	 * @return
	 */
	public PictureResponseCreator getPictureResponseCreator();
	
	/**
	 * Constructs new problem of type QuestionResponceCreator
	 * @return
	 */
	public QuestionResponseCreator getQuestionResponceCreator();
	
	/**
	 * Constructs new problem of type MultipleChoiseCreator
	 * @return
	 */
	public MultipleChoiseCreator getMultipleChoiseCreator();
	
}
