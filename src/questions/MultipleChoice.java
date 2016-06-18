package questions;

public class MultipleChoice implements QuestionType {
	
	public MultipleChoice() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getQuestion() {
		return "what is usa first president?";
	}

	@Override
	public String[] getCorrectAnswers() {
		// TODO Auto-generated method stub
		return new String[] {"washington"};
	}

	@Override
	public String[] getPossibleAnswers() {
		// TODO Auto-generated method stub
		return new String[] {"washington", "jeperson", "kenedy", "lincoln"};
	}

}
