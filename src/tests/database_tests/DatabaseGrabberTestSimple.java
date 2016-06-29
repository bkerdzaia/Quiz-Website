package tests.database_tests;

import database.DatabaseGrabber;
import factory.DatabaseFactory;
import factory.DefaultDatabaseFactory;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

/**
 * @author dav23r
 * Trivial test for DatabaseGrabber. Ensure that object 
 * connects to MYSQL database and closes connection properly.
 */
public class DatabaseGrabberTestSimple {

	DatabaseGrabber dbGrabber = null;
	
	@Before
	public void setUp() {
		DatabaseFactory dbFactory = 
				DefaultDatabaseFactory.getFactoryInstance();
		
		dbGrabber = dbFactory.getDatabaseGrabber();
	}

	@Test
	public void test() throws SQLException,
			FileNotFoundException, IOException {
		dbGrabber.connect();
		dbGrabber.truncateDatabase();
		assertTrue (dbGrabber.registerUser("ab", "ab"));
		assertNotNull(dbGrabber.loadUser("ab"));
		dbGrabber.truncateDatabase();
		dbGrabber.close();
	
		for (int i = 0; i < 10; i++ ){
			dbGrabber.connect();
			dbGrabber.close();
		}
		
	}

}
