package tests.database_tests;

import database.DatabaseGrabber;
import factory.DatabaseFactory;
import factory.DefaultDatabaseFactory;

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
	public void setUp() throws Exception {
		DatabaseFactory dbFactory = DefaultDatabaseFactory.getFactoryInstance();
		dbGrabber = new DatabaseGrabber(dbFactory);
	}

	@Test
	public void test() {
		dbGrabber.connect();
		dbGrabber.close();
	}

}
