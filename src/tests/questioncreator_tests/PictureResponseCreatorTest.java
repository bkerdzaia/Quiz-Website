package tests.questioncreator_tests;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import factory.*;
import questions.*;

public class PictureResponseCreatorTest {

	private PictureResponseCreator pictureResp;

	@Before
	public void setUp() throws Exception {
		pictureResp = DefaultQuestionFactory.getFactoryInstance().getPictureResponseCreator();
	}

	/* Test picture response having two answers */
	@Test
	public void test() {
		String questionText = "Who is this species?";
		String pictUrl = "https://www.howrse.com/media/equideo/image/chevaux/adulte/americain/normal/300/bai_v1828806360.png";
		String posAnsw1 = "horse";
		String posAnsw2 = "Azteca horse";
		Set<String> posAnsw = new HashSet<>();
		posAnsw.add(posAnsw1);
		posAnsw.add(posAnsw2);
		PictureResponse question = (PictureResponse) pictureResp
				.create(new String[] { questionText, pictUrl, posAnsw1, posAnsw2 });
		assertEquals(questionText, question.getQuestionText());
		assertEquals(pictUrl, question.getPictureUrl());
		assertEquals(posAnsw, question.getCorrectAnswers());
	}

	/* Test picture response having one answers */
	@Test
	public void test2() {
		String questionText = "Who is this species?";
		String pictUrl = "https://www.howrse.com/media/equideo/image/chevaux/adulte/americain/normal/300/bai_v1828806360.png";
		String posAnsw1 = "horse";
		Set<String> posAnsw = new HashSet<>();
		posAnsw.add(posAnsw1);
		PictureResponse question = (PictureResponse) pictureResp
				.create(new String[] { questionText, pictUrl, posAnsw1 });
		assertEquals(questionText, question.getQuestionText());
		assertEquals(pictUrl, question.getPictureUrl());
		assertEquals(posAnsw, question.getCorrectAnswers());
	}

	/* Test picture response having more than three answers */
	@Test
	public void test3() {
		String questionText = "Who is this species?";
		String pictUrl = "https://www.howrse.com/media/equideo/image/chevaux/adulte/americain/normal/300/bai_v1828806360.png";
		String posAnsw1 = "horse";
		String posAnsw2 = "Azteca horse";
		String posAnsw3 = "horse2";
		String posAnsw4 = "horse3";
		String posAnsw5 = "horse5";
		Set<String> posAnsw = new HashSet<>();
		posAnsw.add(posAnsw1);
		posAnsw.add(posAnsw2);
		posAnsw.add(posAnsw3);
		posAnsw.add(posAnsw4);
		posAnsw.add(posAnsw5);
		PictureResponse question = (PictureResponse) pictureResp.
				create(new String[] {questionText, pictUrl, posAnsw1, posAnsw2, posAnsw3, posAnsw4, posAnsw5});
		assertEquals(questionText, question.getQuestionText());
		assertEquals(pictUrl, question.getPictureUrl());
	    assertEquals(posAnsw, question.getCorrectAnswers());	
	}

}
