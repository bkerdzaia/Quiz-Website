package tests.database_tests;

import database.DatabaseGrabber;
import factory.DatabaseFactory;
import factory.DefaultDatabaseFactory;

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
		dbGrabber = new DatabaseGrabber(dbFactory);
	}

	@Test
	public void test() throws SQLException {
		dbGrabber.connect();
		dbGrabber.close();
	}

}
