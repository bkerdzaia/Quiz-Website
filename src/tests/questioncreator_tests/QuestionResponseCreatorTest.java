package tests.questioncreator_tests;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import factory.DefaultQuestionFactory;
import questions.*;

public class QuestionResponseCreatorTest {

	private QuestionResponseCreator questionResp;

	@Before
	public void setUp() throws Exception {
		questionResp = DefaultQuestionFactory.getFactoryInstance().getQuestionResponceCreator();
	}

	/* Test question response having two answers */
	@Test
	public void test() {
		String questionText = "Who is USA first president?";
		String posAnsw1 = "Washington";
		String posAnsw2 = "George Washington";
		Set<String> posAnsw = new HashSet<>();
		posAnsw.add(posAnsw1);
		posAnsw.add(posAnsw2);
		QuestionResponce question = (QuestionResponce) questionResp
				.create(new String[] { questionText, posAnsw1, posAnsw2 });
		assertEquals(questionText, question.getQuestionText());
		assertEquals(posAnsw, question.getCorrectAnswers());
	}

	/* Test question response having one answers */
	@Test
	public void test2() {
		String questionText = "Who was USA first president?";
		String posAnsw1 = "Washington";
		Set<String> posAnsw = new HashSet<>();
		posAnsw.add(posAnsw1);
		QuestionResponce question = (QuestionResponce) questionResp.create(new String[] { questionText, posAnsw1 });
		assertEquals(questionText, question.getQuestionText());
		assertEquals(posAnsw, question.getCorrectAnswers());
	}

	/* Test question response having more than three answers */
	@Test
	public void test3() {
		String questionText = "Who was USA first president?";
		String posAnsw1 = "Washington";
		String posAnsw2 = "Washington1";
		String posAnsw3 = "Washington2";
		String posAnsw4 = "Washington3";
		String posAnsw5 = "Washington5";
		Set<String> posAnsw = new HashSet<>();
		posAnsw.add(posAnsw1);
		posAnsw.add(posAnsw2);
		posAnsw.add(posAnsw3);
		posAnsw.add(posAnsw4);
		posAnsw.add(posAnsw5);
		QuestionResponce question = (QuestionResponce) questionResp
				.create(new String[] { questionText, posAnsw1, posAnsw2, posAnsw3, posAnsw4, posAnsw5 });
		assertEquals(questionText, question.getQuestionText());
		assertEquals(posAnsw, question.getCorrectAnswers());
	}
}
