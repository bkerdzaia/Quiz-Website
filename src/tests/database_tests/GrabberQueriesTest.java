package tests.database_tests;

import database.DatabaseConnectionHandler;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import database.DatabaseGrabber;
import database.DefaultDatabaseGrabber;
import factory.DatabaseFactory;
import factory.DefaultQuestionFactory;
import factory.DefaultQuizFactory;
import factory.DefaultUserFactory;
import factory.QuestionFactory;
import questions.FillBlank;
import questions.MultipleChoise;
import questions.PictureResponse;
import questions.QuestionResponce;
import quiz.Quiz;
import quiz.QuizPerformance;
import quiz.QuizProperty;
import quiz.QuizQuestions;
import quiz.User;

/**
 * @author dav23r
 * The test suite is concerned with correctness of the databaseGrabber
 * methods; connection to mock database is initialized, which can be
 * manually filled with various sample entries; afterwards results of 
 * methods (internally queries) are examined.
 * 
 * !! Note, it may take a while to complete, since for each test 
 *    tables are truncated, with code read from file, so be patient !!
 */
public class GrabberQueriesTest {

	private static DatabaseFactory mockDbFactory = null;
	
	// Create mock factory
	@BeforeClass
	public static void init(){
		mockDbFactory = new DatabaseFactory(){
			String pathToScripts =  new File("").getAbsolutePath() + 
									"/src/tests/database_tests/" +
									"mock_database_scripts/";
			@Override
			public DatabaseGrabber getDatabaseGrabber() {
				// Surprisingly this kind of recursive definition works
				return new DefaultDatabaseGrabber(
								mockDbFactory,
								DefaultUserFactory.getFactoryInstance(),
								DefaultQuizFactory.getFactoryInstance(),
								DefaultQuestionFactory.getFactoryInstance()){
					@Override
					public void truncateDatabase() throws IOException, SQLException{
						// Change path variable, else is the same, so delegate via super.
						truncateScriptPath = pathToScripts + "truncate_mock_db.sql";
						super.truncateDatabase();
					}
				}; 
			}
			@Override
			public DatabaseConnectionHandler getDatabaseConnectionHandler() {
				// Redirect to test database instead of real
				return new MockDatabaseConnectionHandler();
			}
		};
	}

	
	// Empty all tables to make clear state for each test.
	@Before
	public void setUp() throws Exception {
		DatabaseGrabber dbGrabber = mockDbFactory.getDatabaseGrabber();
		dbGrabber.connect();
		dbGrabber.truncateDatabase();
		dbGrabber.close();
	}

	// Tests empty database not to contain particular entry.
	@Test
	public void emptyDatabase() throws SQLException, IOException {
		DatabaseGrabber dbGrabber = mockDbFactory.getDatabaseGrabber();
		dbGrabber.connect();
		// Surely, there should not be such entry
		assertNull(dbGrabber.authenticateUser("Esteban", "asdf"));
		dbGrabber.close();
	}
	

	// Tests registration of user.
	@Test
	public void registrationTest() throws SQLException{
		DatabaseGrabber dbGrabber = mockDbFactory.getDatabaseGrabber();
		dbGrabber.connect();
		dbGrabber.registerUser("David", "bla1");
		dbGrabber.registerUser("Sandro", "bla2");
		dbGrabber.registerUser("Baduri", "bla3");
		User david = dbGrabber.loadUser("David");
		User sandro = dbGrabber.loadUser("Sandro");
		User baduri = dbGrabber.loadUser("Baduri");
		// Ensure none of the users is null
		assertNotNull(david);
		assertNotNull(sandro);
		assertNotNull(baduri);
		// Ensure valid passwords
		assertEquals("bla1", david.getPasswordHash());
		assertEquals("bla2", sandro.getPasswordHash());
		assertEquals("bla3", baduri.getPasswordHash());
		// Assert other parameters to be empty
		assertNull(david.getAboutMe());
		assertFalse(sandro.getHistory().iterator().hasNext());
		assertFalse(baduri.getHistory().iterator().hasNext());
		assertFalse(david.getCreatedQuizzes().iterator().hasNext());
		// Now try to register again same users
		assertFalse(dbGrabber.registerUser("David", "bla1"));
		assertFalse(dbGrabber.registerUser("Sandro", "different"));
		assertFalse(dbGrabber.registerUser("Baduri", "asdf"));
		dbGrabber.close();
	}
	
	
	// Tests authentication of user
	@Test
	public void authenticationTest() throws SQLException{
		DatabaseGrabber dbGrabber = mockDbFactory.getDatabaseGrabber();
		dbGrabber.connect();
		dbGrabber.registerUser("Armando", "1234");
		User armando = dbGrabber.authenticateUser("Armando", "1234");
		assertNotNull(armando);
		assertEquals("1234", armando.getPasswordHash());
		// pass wrong credentials
		assertNull(dbGrabber.authenticateUser("Armando", "12345"));
		assertNull(dbGrabber.authenticateUser("Sam", "1234"));
		dbGrabber.close();
	}
	
	// Tests uploading/dowloading quiz from database.
	@Test
	public void quizTest() throws SQLException{
		DatabaseGrabber dbGrabber = mockDbFactory.getDatabaseGrabber();
		QuestionFactory questFactory = DefaultQuestionFactory.getFactoryInstance();
		dbGrabber.connect();
		dbGrabber.registerUser("Sam", "123");
		// Create quiz with two 'FillInBlank' questions.
		Quiz sampleQuiz = new Quiz();
		sampleQuiz.setCreationDate(new Timestamp(new Date().getTime()));
		sampleQuiz.setCreator("Sam");
		sampleQuiz.setDescription("abcd");
		sampleQuiz.setName("abc");
		QuizProperty prop = new QuizProperty(true, true, true);
		sampleQuiz.setProperty(prop);
		FillBlank fb1 = questFactory.getFillBlankQuestion();
		FillBlank fb2 = fb1; // shallow copy
		fb1.setQuestionText("??");
		fb1.setFieldPositionIndex(2);
		Set<String> ans = new HashSet<String>();
		ans.add("!!");
		ans.add("ee");
		fb1.setCorrectAnswers(ans);
		QuizQuestions questions = new QuizQuestions();
		questions.add(fb1);
		questions.add(fb2);
		sampleQuiz.setQuestions(questions);
		// Testing
		assertTrue(dbGrabber.uploadQuiz(sampleQuiz));
		assertNull(dbGrabber.loadQuiz("doesntexist")); // some nonsential name
		Quiz sameQuiz = dbGrabber.loadQuiz("abc");
		assertNotNull(sameQuiz);
		assertEquals("Sam", sameQuiz.getCreator());
		assertEquals("abcd", sameQuiz.getDescription());
		QuizProperty sameQuizProp = sameQuiz.getProperty();
		assertEquals(true, sameQuizProp.isRandomSeq());
		assertEquals(true, sameQuizProp.isInstantlyMarked());
		assertEquals(true, sameQuizProp.isOnePage());
		dbGrabber.close();
	}
	
	// Test other 3 types of questions.
	@Test
	public void testAllTypesQuestions() throws SQLException{
		DatabaseGrabber dbGrabber = mockDbFactory.getDatabaseGrabber();
		QuestionFactory questFactory = DefaultQuestionFactory.getFactoryInstance();
		dbGrabber.connect();
		dbGrabber.registerUser("Sam", "123");
		QuizProperty prop = new QuizProperty(false, true, false);
		Set<String> correct1 = new HashSet<String>();
		correct1.add("correct");
		correct1.add("perfect");
		Set<String> correct2 = new HashSet<String>();
		correct2.add("wonderful");
		ArrayList<String> choises = new ArrayList<String>(
				Arrays.asList(new String[]{"a", "b", "c"}));
		
		QuestionResponce qr = questFactory.getQuestionResponceQuestion();
		qr.setQuestionText("Whats up");
		qr.setCorrectAnswers(correct1);
		
		PictureResponse pr = questFactory.getPictureResponseQuestion();
		pr.setQuestionText("How your doin");
		pr.setCorrectAnswers(correct2);
		pr.setPictureUrl("www.xel.ge");
		
		MultipleChoise mch = questFactory.getMultipleChoiseQuestion();
		mch.setQuestionText("bla");
		mch.setPossibleChoises(choises);
		mch.setCorrectAnswerIndex(1);
		
		Quiz sample = new Quiz();
		sample.setCreator("Sam");
		sample.setCreationDate(new Timestamp(new Date().getTime()));
		sample.setDescription("descr");
		sample.setName("name");
		sample.setProperty(prop);
		QuizQuestions questions = new QuizQuestions();
		questions.add(qr);
		questions.add(pr);
		questions.add(mch);
		sample.setQuestions(questions);

		// The moment of truth
		dbGrabber.uploadQuiz(sample);
		
		Quiz sameQuiz = dbGrabber.loadQuiz("name");
		assertNotNull(sameQuiz);
		assertEquals("Sam", sameQuiz.getCreator());
		assertEquals("descr", sameQuiz.getDescription());
		QuizProperty sameProp = sameQuiz.getProperty();
		assertEquals(prop.isInstantlyMarked(), sameProp.isInstantlyMarked());
		assertEquals(prop.isRandomSeq(), sameProp.isRandomSeq());
	
		// Test question-response
		QuizQuestions sameQuestions = sameQuiz.getQuestions();
		assertEquals(3, sameQuestions.size());
	}


}
