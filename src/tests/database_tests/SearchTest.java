package tests.database_tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
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
import quiz.Quiz;
import quiz.QuizProperty;
import quiz.QuizQuestions;
import quiz.User;

public class SearchTest {
	
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

	@Test
	public void testUserSearchSuggestions() throws SQLException {
		DatabaseGrabber dbGrabber = mockDbFactory.getDatabaseGrabber();
		dbGrabber.connect();
		
		dbGrabber.registerUser("john", "12");
		dbGrabber.registerUser("joni", "34");
		dbGrabber.registerUser("joshua", "asdf");
		dbGrabber.registerUser("mike", "34234");

		ArrayList<String> similar = dbGrabber.searchUsers("jo");
		assertEquals(3, similar.size());
		
		ArrayList<String> expected = (ArrayList<String>) 
				new ArrayList<String>(Arrays.asList(new String[]{"john", "joni", "joshua"}));

		java.util.Collections.sort(similar);
		java.util.Collections.sort(expected);
		
		assertEquals(expected, similar);
	}

}
