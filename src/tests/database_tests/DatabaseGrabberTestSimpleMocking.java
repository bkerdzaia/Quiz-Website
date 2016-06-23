package tests.database_tests;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import database.DatabaseConnectionHandler;
import database.DatabaseGrabber;
import database.DefaultDatabaseGrabber;
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
	public void setUp(){

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
	public void connectionTest() throws SQLException {
		DatabaseGrabber dbGrabber = new DefaultDatabaseGrabber(corruptedFactory);
		dbGrabber.authenticateUser("Sam", "asfd");
		dbGrabber.connect();
	}

}
