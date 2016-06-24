package tests.database_tests;

import database.DatabaseConnectionHandler;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import database.DatabaseGrabber;
import database.DefaultDatabaseGrabber;
import factory.DatabaseFactory;
import factory.DefaultQuizFactory;
import factory.DefaultUserFactory;
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
				return new DefaultDatabaseGrabber(mockDbFactory,
								DefaultUserFactory.getFactoryInstance(),
								DefaultQuizFactory.getFactoryInstance()){
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
		// Assert other parameters to be 'null'
		assertNull(david.getAboutMe());
		assertNull(sandro.getHistory());
		assertNull(baduri.getTakenQuizzes());
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

}
