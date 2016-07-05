package tests.database_tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import database.DatabaseConnectionHandler;
import database.DatabaseGrabber;
import database.DefaultDatabaseGrabber;
import factory.DatabaseFactory;
import factory.DefaultQuestionFactory;
import factory.DefaultQuizFactory;
import factory.DefaultUserFactory;
import quiz.Challenge;
import quiz.FriendList;
import quiz.Quiz;
import quiz.QuizProperty;
import quiz.QuizQuestions;
import quiz.TextMessage;
import quiz.User;
import quiz.UserMessageList;


/**
 * @author dav23r
 * This test suit is concerned with correctness of user relations
 * in database; methods of database grabber such as addFriendRequest,
 * sendMessage, etc are under examination.
 */
public class UserRelationsTest {

	private static DatabaseFactory mockDbFactory = null;
	private static Timestamp date;

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
			public DefaultDatabaseGrabber getDatabaseGrabber() {
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
		sampleUser.setName("Esteban");
		
		sampleQuiz = new Quiz();
		sampleQuiz.setCreator("Esteban");
		sampleQuiz.setDescription("bla");
		sampleQuiz.setCreationDate(new Timestamp(new Date().getTime()));
		sampleQuiz.setProperty(new QuizProperty(false, true, false));
		sampleQuiz.setSummaryStatistics(0);
		sampleQuiz.setQuestions(new QuizQuestions());
		sampleQuiz.setName("samsfirst");
		dbGrabber.close();
		
		date = new Timestamp(new Date().getTime());
	}

	
	@Test
	public void testFriendshipBasic() throws SQLException {
		DatabaseGrabber dbGrabber = mockDbFactory.getDatabaseGrabber();
		dbGrabber.connect();
		dbGrabber.registerUser(sampleQuiz.getCreator(), "00");
		dbGrabber.uploadQuiz(sampleQuiz);

		assertTrue(dbGrabber.registerUser("sam", "12"));
		assertTrue(dbGrabber.registerUser("samuel", "34"));
		
		assertTrue (dbGrabber.addFriendRequest("sam", "samuel", date));
		// Sam's urge should not affect the system
		assertFalse(dbGrabber.addFriendRequest("sam", "samuel", date));
		// Samuel accepts
		assertTrue (dbGrabber.acceptFriendRequest("samuel", "sam"));
		// Samuel is kinda forgetful and sends request to sam even though 
		// the are already friends
		assertFalse(dbGrabber.addFriendRequest("samuel", "sam", date));
		// Everything ended as Sam discovered Samuel being Trump supporter
		assertTrue(dbGrabber.removeFriend("sam", "samuel"));
	}


	@Test
	public void voidTestMessagingBasic() throws SQLException {
		DatabaseGrabber dbGrabber = mockDbFactory.getDatabaseGrabber();
		dbGrabber.connect();
		// Emm, let's elaborate the same story
		dbGrabber.registerUser(sampleQuiz.getCreator(), "12");
		dbGrabber.uploadQuiz(sampleQuiz);

		dbGrabber.registerUser("sam", "12");
		dbGrabber.registerUser("samuel", "34");
		
		Timestamp someTime = new Timestamp(new Date().getTime());
		// Samuel can't cope why Sam unfriended him, so he send request again
		assertTrue(dbGrabber.addFriendRequest("samuel", "sam", date));
		// Sam decides to be clear and say the reason
		assertTrue(dbGrabber.acceptFriendRequest("sam", "samuel"));
		assertTrue(dbGrabber.sendMessage("sam", "samuel", 
				"I cant be friends with trump supporter", someTime));
		// Samuel reads inbox
		User samuel = dbGrabber.loadUser("samuel");
		UserMessageList samuelsMessages = 
				samuel.getMessages();
		assertEquals(1, samuelsMessages.size());
		TextMessage mes = (TextMessage) samuelsMessages.get(0);
		assertEquals("I cant be friends with trump supporter", mes.getMessage());
		assertEquals("<p>" + mes.getMessage() + "</p>", mes.displayMessage());
		String someNonsense =
				"Hillay and Ted Cruz will make 2 + 2 = 4" + 
				" Im not saying its 5 maybe Jeb is saying that..";
		assertTrue(dbGrabber.sendMessage("samuel", "sam", 
				someNonsense, someTime));
		User sam = dbGrabber.loadUser("sam");
		// Lets see sams friends
		FriendList samsFriends = sam.getFriends();
		assertEquals(1, samsFriends.size());
		assertEquals("samuel", samsFriends.get(0));
		// Sam reading inbox
		UserMessageList samsMessages = 
				sam.getMessages();
		assertEquals(1, samsMessages.size());
		assertEquals(someNonsense, ((TextMessage) samsMessages.get(0)).getMessage());
		// Angry sam deletes samuel twice!
		assertTrue(dbGrabber.removeFriend("sam", "samuel"));
		assertFalse(dbGrabber.removeFriend("samuel", "sam"));
	}
	
	@Test
	public void testFriendshipIntermediate() throws SQLException{
		DatabaseGrabber dbGrabber = mockDbFactory.getDatabaseGrabber();
		dbGrabber.connect();
		dbGrabber.registerUser("ann", "123");
		dbGrabber.registerUser("mery", "11");
		User ann = dbGrabber.loadUser("ann");
		assertNotNull(ann);
		assertEquals(0, ann.getFriends().size());
		assertTrue(dbGrabber.addFriendRequest("ann", "mery", date));
		assertTrue(dbGrabber.acceptFriendRequest("mery", "ann"));
		assertFalse(dbGrabber.addFriendRequest("ann", "mery", date));
	}
	
	@Test
	public void testTrickyCases() throws SQLException {
		DatabaseGrabber dbGrabber = mockDbFactory.getDatabaseGrabber();
		dbGrabber.connect();
		assertTrue(dbGrabber.registerUser(sampleQuiz.getCreator(), "afd"));
		assertTrue(dbGrabber.uploadQuiz(sampleQuiz));
		assertTrue(dbGrabber.registerUser("amantha", "asdf"));
		dbGrabber.registerUser("miranda", "iop");
		assertFalse(dbGrabber.addFriendRequest("amantha", "amantha", date));
		
		Timestamp date = new Timestamp(new Date().getTime());
		// They are not friends yet
		assertFalse(dbGrabber.sendChallenge("amantha", "miranda", sampleQuiz.getName(), date));
		// They became friends
		dbGrabber.addFriendRequest("amantha", "miranda", date);
		assertFalse(dbGrabber.acceptFriendRequest("amanda", "miranda"));
		assertTrue(dbGrabber.acceptFriendRequest("miranda", "amantha"));
		
		assertTrue(dbGrabber.sendChallenge("amantha", "miranda", sampleQuiz.getName(), date));

		User miranda = dbGrabber.loadUser("miranda");
		UserMessageList messages = miranda.getMessages();
		assertEquals(1, messages.size());
		@SuppressWarnings("unused")
		Challenge amandaToMiranda = (Challenge) messages.get(0);
		
		User amanda = dbGrabber.loadUser("amantha");
		assertEquals(0, amanda.getMessages().size());
	}

	@Test
	public void testRejectFriendship() throws SQLException{
		DatabaseGrabber dbGrabber = mockDbFactory.getDatabaseGrabber();
		dbGrabber.connect();
		Timestamp date = new Timestamp(new Date().getTime());

		dbGrabber.registerUser("sam", "qfsasd");
		dbGrabber.registerUser("samuel", "asdfas");
		
		assertTrue(dbGrabber.addFriendRequest("sam", "samuel", date));
		assertTrue(dbGrabber.rejectFriendship("samuel", "sam"));
		
		User sam = dbGrabber.loadUser("sam");
		assertEquals(0, sam.getFriends().size());
		
		assertFalse(dbGrabber.acceptFriendRequest("samuel", "sam"));
		assertFalse(dbGrabber.acceptFriendRequest("sam", "samuel"));
	}
}
