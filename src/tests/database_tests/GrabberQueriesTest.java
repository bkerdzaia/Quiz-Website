package tests.database_tests;

import database.DatabaseConnectionHandler;
import factory.DefaultDatabaseFactory;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import database.DatabaseGrabber;
import factory.DatabaseFactory;

/**
 * @author dav23r
 * The test suite is concerned with correctness of the databaseGrabber
 * methods; connection to mock database is initialized, which can be
 * manually filled with various sample entries; afterwards results of 
 * methods (internally queries) are examined.
 */
public class GrabberQueriesTest {

	private DatabaseFactory mockDbFactory = null;
	
	@Before
	public void setUp() throws Exception {
		// Surprisingly this kind of recursive definition works
		mockDbFactory = new DatabaseFactory(){
			@Override
			public DatabaseGrabber getDatabaseGrabber() {
				return new DatabaseGrabber(mockDbFactory); // no, not null, but itself
			}
			@Override
			public DatabaseConnectionHandler getDatabaseConnectionHandler() {
				// Redirect to test database instead of real
				return new MockDatabaseConnectionHandler();
			}
		};
	}

	@Test
	public void test() throws SQLException, FileNotFoundException {
		DatabaseGrabber dbGrabber = mockDbFactory.getDatabaseGrabber();
		dbGrabber.connect();
		// Remove all entries
		dbGrabber.dropDatabase();
		// Surely, there should not be such entry
		assertNull(dbGrabber.authenticateUser("Esteban", "asdf"));
	}

}
