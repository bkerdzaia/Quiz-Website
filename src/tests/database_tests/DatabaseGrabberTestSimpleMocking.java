package tests.database_tests;


import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import database.DatabaseConnectionHandler;
import database.DatabaseGrabber;
import database.DefaultDatabaseGrabber;
import factory.DatabaseFactory;
import factory.DefaultQuestionFactory;
import factory.DefaultQuizFactory;
import factory.DefaultUserFactory;

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
		DatabaseGrabber dbGrabber = new DefaultDatabaseGrabber(corruptedFactory,
											DefaultUserFactory.getFactoryInstance(),
											DefaultQuizFactory.getFactoryInstance(),
											DefaultQuestionFactory.getFactoryInstance());
		dbGrabber.authenticateUser("Sam", "asfd");
		dbGrabber.connect();
	}

}
