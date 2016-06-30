package tests.questioncreator_tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import factory.DefaultQuestionFactory;
import factory.DefaultQuizFactory;
import factory.QuestionFactory;
import questions.FillBlank;
import questions.MultipleChoise;
import questions.PictureResponse;
import questions.QuestionAdd;
import questions.QuestionResponce;
import quiz.QuizQuestions;

public class QuestionAddTest {
	
	private static final String PICTURE_RESPONSE = "pictureResponse";
	private static final String QUESTION_RESPONSE = "questionResponse";
	private static final String MULTIPLE_CHOICE = "multipleChoice";
	private static final String FILL_BLANK = "fillBlank";
	
	private QuizQuestions questions;
	private Map<String, String[]> map;

	/* insert elements to map and add questions */
	@Before
	public void setUp() throws Exception {
		QuestionFactory questionFactory = DefaultQuestionFactory.getFactoryInstance();
		QuestionAdd addingQuestion = questionFactory.getQuestionAdd();
		questions = DefaultQuizFactory.getFactoryInstance().getQuizQuestions();
		map = new HashMap<>();
		map.put(PICTURE_RESPONSE, new String[] {"pict quest", "pict url", "pos answ 1", "pos answ 2"});
		map.put(QUESTION_RESPONSE, new String[] {"quest resp", "poss answer 1", "poss answer 2"});
		map.put(MULTIPLE_CHOICE, new String[] {"quest mult choice", "answ", "posansw1", "posansw2", "posansw3"});
		map.put(FILL_BLANK, new String[] {"ques fill blank", "pos answer 1", "pos answer 2"});
		addingQuestion.addQuestionType(map, PICTURE_RESPONSE, questionFactory.getPictureResponseCreator(), questions);
		addingQuestion.addQuestionType(map, QUESTION_RESPONSE, questionFactory.getQuestionResponceCreator(), questions);
		addingQuestion.addQuestionType(map, MULTIPLE_CHOICE, questionFactory.getMultipleChoiseCreator(), questions);
		addingQuestion.addQuestionType(map, FILL_BLANK, questionFactory.getFillBlankCreator(), questions);
	}
	
	/*
	 * Test if picture response is correctly added to questions. 
	 */
	@Test
	public void checkPictureResponse() {
		PictureResponse pictResp = (PictureResponse) questions.get(0);
		assertEquals(pictResp.getQuestionText(), map.get(PICTURE_RESPONSE)[0]);
		assertEquals(pictResp.getPictureUrl(), map.get(PICTURE_RESPONSE)[1]);
		Set<String> posAnsw = new HashSet<>();
		posAnsw.add(map.get(PICTURE_RESPONSE)[2]);
		posAnsw.add(map.get(PICTURE_RESPONSE)[3]);
		assertEquals(pictResp.getCorrectAnswers(), posAnsw);
		assertTrue(pictResp.isCorrectAnswer(map.get(PICTURE_RESPONSE)[2]));
		assertTrue(pictResp.isCorrectAnswer(map.get(PICTURE_RESPONSE)[3]));
		assertFalse(pictResp.isCorrectAnswer("not cor"));
	}
	
	/*
	 * Test if question response is correctly added to questions. 
	 */
	@Test
	public void checkQuestionResponse() {
		QuestionResponce questResp = (QuestionResponce) questions.get(1);
		assertEquals(questResp.getQuestionText(), map.get(QUESTION_RESPONSE)[0]);
		Set<String> posAnsw = new HashSet<>();
		posAnsw.add(map.get(QUESTION_RESPONSE)[1]);
		posAnsw.add(map.get(QUESTION_RESPONSE)[2]);
		assertEquals(questResp.getCorrectAnswers(), posAnsw);
		assertTrue(questResp.isCorrectAnswer(map.get(QUESTION_RESPONSE)[1]));
		assertTrue(questResp.isCorrectAnswer(map.get(QUESTION_RESPONSE)[2]));
		assertFalse(questResp.isCorrectAnswer("not cor"));
	}
	
	/*
	 * Test if multiple choice is correctly added to questions. 
	 */
	@Test
	public void checkMultipleChoice() {
		MultipleChoise multChoice = (MultipleChoise) questions.get(2);
		assertEquals(multChoice.getQuestionText(), map.get(MULTIPLE_CHOICE)[0]);
		ArrayList<String> posChoice = new ArrayList<>();
		posChoice.add(map.get(MULTIPLE_CHOICE)[1]);
		posChoice.add(map.get(MULTIPLE_CHOICE)[2]);
		posChoice.add(map.get(MULTIPLE_CHOICE)[3]);
		posChoice.add(map.get(MULTIPLE_CHOICE)[4]);
		assertTrue(multChoice.getPossibleChoises().containsAll(posChoice));
		assertTrue(multChoice.isCorrectAnswer(map.get(MULTIPLE_CHOICE)[1]));
		assertFalse(multChoice.isCorrectAnswer(map.get(MULTIPLE_CHOICE)[2]));
		assertFalse(multChoice.isCorrectAnswer(map.get(MULTIPLE_CHOICE)[3]));
		assertFalse(multChoice.isCorrectAnswer(map.get(MULTIPLE_CHOICE)[4]));
	}
	
	/*
	 * Test if fill the blank is correctly added to questions. 
	 */
	@Test
	public void checkFillBlank() {
		FillBlank fillBlank = (FillBlank) questions.get(3);
		assertEquals(fillBlank.getQuestionText(), map.get(FILL_BLANK)[0]);
		Set<String> posAnsw = new HashSet<>();
		posAnsw.add(map.get(FILL_BLANK)[1]);
		posAnsw.add(map.get(FILL_BLANK)[2]);
		assertEquals(fillBlank.getCorrectAnswers(), posAnsw);
		assertTrue(fillBlank.isCorrectAnswer(map.get(FILL_BLANK)[1]));
		assertTrue(fillBlank.isCorrectAnswer(map.get(FILL_BLANK)[2]));
		assertFalse(fillBlank.isCorrectAnswer("not correct"));
	}
	

}
