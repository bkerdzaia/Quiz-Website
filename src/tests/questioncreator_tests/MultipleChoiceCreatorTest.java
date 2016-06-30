package tests.questioncreator_tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import factory.DefaultQuestionFactory;
import questions.MultipleChoise;
import questions.MultipleChoiseCreator;


public class MultipleChoiceCreatorTest {

	private MultipleChoiseCreator multChoice;

	@Before
	public void setUp() throws Exception {
		multChoice = DefaultQuestionFactory.getFactoryInstance().getMultipleChoiseCreator();
	}

	/* Test multiple choice question and isCorrectAnswer */
	@Test
	public void test() {
		String questionText = "Who is USA first president?";
		String posAnsw1 = "George Washington";
		String posAnsw2 = "John Adams";
		String posAnsw3 = "Thomas Jefferson";
		String posAnsw4 = "Abraham Lincoln";
		ArrayList<String> posAnsw = new ArrayList<>();
		posAnsw.add(posAnsw1);
		posAnsw.add(posAnsw2);
		posAnsw.add(posAnsw3);
		posAnsw.add(posAnsw4);
		MultipleChoise question = (MultipleChoise) multChoice
				.create(new String[] { questionText, posAnsw1, posAnsw2, posAnsw3, posAnsw4 });
		assertEquals(questionText, question.getQuestionText());
		assertTrue(posAnsw.containsAll(question.getPossibleChoises()));
		assertTrue(question.isCorrectAnswer(posAnsw1));
		assertFalse(question.isCorrectAnswer(posAnsw2));
		assertFalse(question.isCorrectAnswer(posAnsw3));
		assertFalse(question.isCorrectAnswer(posAnsw4));
	}
	
}
