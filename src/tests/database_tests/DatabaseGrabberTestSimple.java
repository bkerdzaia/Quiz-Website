package tests.database_tests;

import database.DatabaseGrabber;
import factory.DatabaseFactory;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DatabaseGrabberTestSimple {

	DatabaseGrabber dbGrabber = null;
	
	@Before
	public void setUp() throws Exception {
		DatabaseFactory dbFactory = DatabaseFactory.getFactoryInstance();
		dbGrabber = new DatabaseGrabber(dbFactory);
	}

	@Test
	public void test() {
		dbGrabber.connect();
		dbGrabber.close();
	}

}
