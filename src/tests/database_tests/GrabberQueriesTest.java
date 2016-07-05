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
import java.util.Iterator;
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
import quiz.Performance;
import quiz.PerformanceOnQuiz;
import quiz.Quiz;
import quiz.QuizCollection;
import quiz.QuizHistory;
import quiz.QuizProperty;
import quiz.QuizQuestions;
import quiz.User;
import quiz.UserList;

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
	private static Quiz sampleQuiz = null;
	private static User sampleUser = null;
	
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
		
		// Populate sample quiz and sample user with values
		sampleUser = new User();
		sampleUser.setAboutMe("");
		sampleUser.setName("sam");
		
		sampleQuiz = new Quiz();
		sampleQuiz.setCreator("sam");
		sampleQuiz.setDescription("bla");
		sampleQuiz.setCreationDate(new Timestamp(new Date().getTime()));
		sampleQuiz.setProperty(new QuizProperty(false, true, false));
		sampleQuiz.setSummaryStatistics(0);
		sampleQuiz.setQuestions(new QuizQuestions());
		sampleQuiz.setName("samsfirst");
		dbGrabber.close();
	}

  
	// Tests empty database not to contain particular entry.
	@Test
	public void emptyDatabase() throws SQLException, IOException {
		DatabaseGrabber dbGrabber = mockDbFactory.getDatabaseGrabber();
		dbGrabber.connect();
		// Surely, there should not be such entry
		assertFalse(dbGrabber.authenticateUser("Esteban", "asdf"));
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
		assertTrue(dbGrabber.authenticateUser("Armando", "1234"));
		User armando = dbGrabber.loadUser("armando");
		assertNotNull(armando);
		// pass wrong credentials
		assertFalse(dbGrabber.authenticateUser("Armando", "12345"));
		assertFalse(dbGrabber.authenticateUser("Sam", "1234"));
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
		QuizProperty prop = new QuizProperty(false, false, true);
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
		assertEquals(false, sameQuizProp.isRandomSeq());
		assertEquals(false, sameQuizProp.isOnePage());
		assertEquals(true, sameQuizProp.isInstantlyMarked());
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
	
		QuizQuestions sameQuestions = sameQuiz.getQuestions();
		assertEquals(3, sameQuestions.size());

		// Test question-response
		QuestionResponce sameQr = (QuestionResponce) sameQuestions.get(0);
		assertEquals(qr.getQuestionText(), sameQr.getQuestionText());
		assertTrue(setsEqual(qr.getCorrectAnswers(), sameQr.getCorrectAnswers()));
		
		// Test multiple-choise
		MultipleChoise sameMch = (MultipleChoise) sameQuestions.get(2);
		assertEquals(mch.getQuestionText(), sameMch.getQuestionText());
		assertEquals(mch.getPossibleChoises(), sameMch.getPossibleChoises());
		assertTrue(sameMch.isCorrectAnswer("b"));
		
		// Test quiz deletion
		dbGrabber.deleteQuiz("name");
		assertNull(dbGrabber.loadQuiz("name"));
	}

	// Helper method for determining whether two sets contain same elements 
	private boolean setsEqual(Set<String> firstSet, Set<String> secondSet) {
		if (firstSet.size() != secondSet.size())
			return false;
		Iterator<String> it = firstSet.iterator();
		while (it.hasNext()){
			if (!secondSet.contains(it.next()))
				return false;
		}
		return true;
	}
	
	@Test
	public void testStoreAttempt() throws SQLException {
		DatabaseGrabber dbGrabber = mockDbFactory.getDatabaseGrabber();
		dbGrabber.connect();
		dbGrabber.registerUser("sam", "12");
		dbGrabber.uploadQuiz(sampleQuiz);
		Performance perf = new Performance();
		perf.setAmountTime(1);
		perf.setDate(new Timestamp(new Date().getTime()));
		perf.setPercentCorrect(100);

		int milisecondsOffset1 = 1000;
		int milisecondsOffset2 = 2000;
		dbGrabber.storeAttempt(perf, sampleQuiz.getCreator(), sampleQuiz.getName());
		perf.setDate(new Timestamp(perf.getDate().getTime() - milisecondsOffset2)); 
		dbGrabber.storeAttempt(perf, sampleQuiz.getCreator(), sampleQuiz.getName());
		QuizHistory  recentStats = 
				dbGrabber.getRecentTakersStats(sampleQuiz.getName(), new Timestamp
				(new Date().getTime() - milisecondsOffset1));
		assertEquals(1, recentStats.size());
		PerformanceOnQuiz samePerf = recentStats.get(0);
		assertEquals(sampleQuiz.getCreator(), samePerf.getUser());
	}

	@Test
	public void testGetPopularQuizzes() throws SQLException {
		DatabaseGrabber dbGrabber = mockDbFactory.getDatabaseGrabber();
		dbGrabber.connect();
		assertEquals(0, dbGrabber.getPopularQuizzes().size());
		dbGrabber.registerUser("sam", "12");
		assertTrue(dbGrabber.uploadQuiz(sampleQuiz));
		
		String oldName = sampleQuiz.getName(), newName = "newSample";
		sampleQuiz.setName(newName);
		assertTrue(dbGrabber.uploadQuiz(sampleQuiz));
		
		Performance perf = new Performance();
		perf.setAmountTime(1);
		perf.setDate(new Timestamp(new Date().getTime()));
		perf.setPercentCorrect(100);

		dbGrabber.storeAttempt(perf, sampleQuiz.getCreator(), oldName);
		dbGrabber.storeAttempt(perf, sampleQuiz.getCreator(), oldName);
		
		dbGrabber.storeAttempt(perf, sampleQuiz.getCreator(), newName);
		QuizCollection popular = dbGrabber.getPopularQuizzes();
		assertEquals(2, popular.size());
		// Ensure that first quiz is the one with two attemtps
		assertEquals(oldName, popular.get(0));
		dbGrabber.close();
	}
	
	@Test
	public void testHighestPerformers() throws SQLException {
		DatabaseGrabber dbGrabber = mockDbFactory.getDatabaseGrabber();
		dbGrabber.connect();
		Timestamp now = new Timestamp(new Date().getTime());
		assertEquals(0, 
				dbGrabber.getHighestPerformers(sampleQuiz.getName(), now).size());
		dbGrabber.registerUser(sampleQuiz.getCreator(), "1");
		dbGrabber.registerUser("sam", "12");
		dbGrabber.registerUser("samuel", "34");
		dbGrabber.uploadQuiz(sampleQuiz);

		Performance lowPerf = new Performance();
		lowPerf.setAmountTime(23);
		lowPerf.setDate(now);
		lowPerf.setPercentCorrect(10);
		
		Performance highPerf = new Performance();
		highPerf.setAmountTime(67);
		highPerf.setDate(now);
		highPerf.setPercentCorrect(99);
		
		dbGrabber.storeAttempt(lowPerf, "sam", sampleQuiz.getName());
		dbGrabber.storeAttempt(highPerf, "samuel", sampleQuiz.getName());
		
		Timestamp past = new Timestamp(now.getTime() - 3000);
		UserList users = 
				dbGrabber.getHighestPerformers(sampleQuiz.getName(), past);
		assertEquals(2, users.size());
		assertEquals("samuel", users.get(0));
		assertEquals("sam", users.get(1));
		
		Timestamp future = new Timestamp(now.getTime() + 1000);
		assertEquals(0, 
			dbGrabber.getHighestPerformers(sampleQuiz.getName(), future).size());
	}
	

	@Test
	public void testRecentlyCreatedQuizzes() throws SQLException {
		DatabaseGrabber dbGrabber = mockDbFactory.getDatabaseGrabber();
		dbGrabber.connect();
		dbGrabber.registerUser("sam", "123");
		dbGrabber.uploadQuiz(sampleQuiz);
		String oldName = sampleQuiz.getName();
		sampleQuiz.setName("newQuiz");
		long oldDateSecs = sampleQuiz.getCreationDate().getTime();
		sampleQuiz.setCreationDate(new Timestamp(oldDateSecs + 1000));
		dbGrabber.uploadQuiz(sampleQuiz);
		QuizCollection recentQuizzes = dbGrabber.getRecentlyCreatedQuizzes();
		assertEquals(2, recentQuizzes.size());
		assertEquals("newQuiz", recentQuizzes.get(0));
		assertEquals(oldName, recentQuizzes.get(1));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testGetRecentTakersStats() throws SQLException {
		DatabaseGrabber dbGrabber = mockDbFactory.getDatabaseGrabber();
		dbGrabber.connect();
		dbGrabber.registerUser(sampleQuiz.getCreator(), "12");
		dbGrabber.uploadQuiz(sampleQuiz);
		
		dbGrabber.registerUser("sam", "12");
		dbGrabber.registerUser("samuel", "34");
	
		Timestamp now = new Timestamp(new Date().getTime());
		Timestamp past = new Timestamp(now.getTime() - 5000);
		Timestamp future = new Timestamp(now.getTime() + 5000);

		Performance recentPerf = new Performance();
		recentPerf.setAmountTime(12);
		recentPerf.setDate(now);
		recentPerf.setPercentCorrect(89);
		
		Performance futurePerf = new Performance();
		futurePerf.setAmountTime(11);
		futurePerf.setDate(future);
		futurePerf.setPercentCorrect(34);
		
		dbGrabber.storeAttempt(recentPerf, "sam", sampleQuiz.getName());
		dbGrabber.storeAttempt(futurePerf, "samuel", sampleQuiz.getName());

		QuizHistory recentStats = 
				dbGrabber.getRecentTakersStats(sampleQuiz.getName(),past);
		assertEquals(2, recentStats.size());
		assertEquals(11, recentStats.get(0).getAmountTime());
		assertEquals(future.getMinutes(), recentStats.get(0).getDate().getMinutes());
		assertEquals((int)89, (int)recentStats.get(1).getPercentCorrect());
	}
	
	@Test
	public void editQuiz() throws SQLException {
		DatabaseGrabber dbGrabber = mockDbFactory.getDatabaseGrabber();
		dbGrabber.connect();
		assertTrue(dbGrabber.registerUser("sam", "samsky"));
		dbGrabber.registerUser(sampleQuiz.getCreator(), "creatorsky");
		dbGrabber.uploadQuiz(sampleQuiz);
		sampleQuiz.setDescription("new description");
		assertTrue(dbGrabber.editQuiz(sampleQuiz));
		assertEquals("new description", 
				dbGrabber.loadQuiz(sampleQuiz.getName()).getDescription());
	}
	
	@Test 
	public void editUser() throws SQLException {
		DatabaseGrabber dbGrabber = mockDbFactory.getDatabaseGrabber();
		dbGrabber.connect();
		User nonexistent = new User();
		nonexistent.setName("i really dont exist");
		assertFalse(dbGrabber.editUser(nonexistent));

		dbGrabber.registerUser("user", "12");
		User user = dbGrabber.loadUser("user");
		user.setAboutMe("Im changed fully");
		assertTrue(dbGrabber.editUser(user));
		User changedUser = dbGrabber.loadUser("user");
		assertEquals("Im changed fully", changedUser.getAboutMe());
	}
	
	@Test
	public void loadUserFullInfo() throws SQLException{
		DatabaseGrabber dbGrabber = mockDbFactory.getDatabaseGrabber();
		dbGrabber.connect();
		assertTrue(dbGrabber.registerUser("sam", "as"));
		User sam = dbGrabber.loadUser("sam");
		assertNotNull(sam);
		assertNotNull(sam.getCreatedQuizzes());
		assertNotNull(sam.getFriends());
		assertNotNull(sam.getHistory());
		assertNotNull(sam.getMessages());
		assertNotNull(sam.getName());
		// Now these fields should be null initially
		assertNull(sam.getAboutMe());
		assertNull(sam.getPictureUrl());
	}

}
