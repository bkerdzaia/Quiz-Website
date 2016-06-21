package tests.database_tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import database.DatabaseConnectionHandler;
import database.DatabaseGrabber;
import factory.DatabaseFactory;

/**
 * @author dav23r
 * Tests DatabaseGrabber's behavior when fed with 
 * corrupted factory, one that always returns nulls 
 * for any request.
 */
public class DatabaseGrabberTestSimpleMocking {
	
	DatabaseFactory corruptedFactory = null;
	
	@Before
	public void setUp() throws Exception {

		corruptedFactory = new DatabaseFactory(){
			@Override
			public DatabaseGrabber getDatabaseGrabber() {
				return null;
			}
			@Override
			public DatabaseConnectionHandler getDatabaseConnectionHandler() {
				return null;
			}
		};
	}

	@Test(expected=NullPointerException.class)
	public void test() {
		DatabaseGrabber dbGrabber = new DatabaseGrabber(corruptedFactory);
		dbGrabber.connect();
	}

}
