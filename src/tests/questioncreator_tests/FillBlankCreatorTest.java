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
		question.getCorrectAnswers();
		
	}

}
