package tests.questioncreator_tests;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;
import factory.*;
import questions.*;

public class FillBlankCreatorTest {

	private FillBlankCreator blank;
	
	@Before
	public void setUp() throws Exception {
		blank = DefaultQuestionFactory.getFactoryInstance().getFillBlankCreator();
	}

	/* Test fill the blank having two answers */
	@Test
	public void test() {
		String questionText = "__ is USA first president";
		String posAnsw1 = "Washington";
		String posAnsw2 = "George Washington";
		Set<String> posAnsw = new HashSet<>();
		posAnsw.add(posAnsw1);
		posAnsw.add(posAnsw2);
		FillBlank question = (FillBlank) blank.create(new String[] {questionText, posAnsw1, posAnsw2});
		assertEquals(questionText, question.getQuestionText());
	    assertEquals(posAnsw, question.getCorrectAnswers());
	}

	/* Test fill the blank having one answer */
	@Test
	public void test2() {
		String questionText = "__ is USA first president";
		String posAnsw1 = "Washington";
		Set<String> posAnsw = new HashSet<>();
		posAnsw.add(posAnsw1);
		FillBlank question = (FillBlank) blank.create(new String[] { questionText, posAnsw1 });
		assertEquals(questionText, question.getQuestionText());
		assertEquals(posAnsw, question.getCorrectAnswers());
	}

	/* Test fill the blank having more than three answers */
	@Test
	public void test3() {
		String questionText = "__ is USA first president";
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
		FillBlank question = (FillBlank) blank
				.create(new String[] { questionText, posAnsw1, posAnsw2, posAnsw3, posAnsw4, posAnsw5 });
		assertEquals(questionText, question.getQuestionText());
		assertEquals(posAnsw, question.getCorrectAnswers());
	}
}
