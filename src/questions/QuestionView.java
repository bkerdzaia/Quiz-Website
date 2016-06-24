package questions;

/**
 * @author dav23r
 * Interface defines methods of various 'views' of different 
 * question types. All the subclasses of 'Question' will 
 * contain object 'QuestionView' which will be responsible
 * for displaying question in form of 'html'. This approach
 * is known as 'Strategy Pattern'.  
 */
public interface QuestionView {
	
	/**
	 * Hands back html code displaying particular question. (view)
	 * @return html corresponding to question.
	 */
	public String displayQuestion(Question curQuestion);


}
