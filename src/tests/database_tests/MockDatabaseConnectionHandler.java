package tests.database_tests;

import database.DatabaseParameters;

import java.sql.Connection;
import java.sql.DriverManager;

import database.DatabaseConnectionHandler;

/**
 * @author dav23r
 * The class is mock implementation of DatabaseConnectionHandler
 * interface, which basically connects to database used for testing
 * instead of real 'production' database. Tester is allowed to 
 * configure mock database as wanted (populate with sample values..)
 * and then use getConnection of this class and polymorphically
 * connect to mock database in place of real one.  
 */
public class MockDatabaseConnectionHandler implements 
				DatabaseParameters, DatabaseConnectionHandler{

	private static final String mockSchemaName = "MockQuizWebsite";
	
	/* Returns connection to mock database, one used for 
	 * testing purposes. In this case mock database is also
	 * on localhost.
	 * (Actually it's same server this time, with different
	 * schema)
	 * @see database.DatabaseConnectionHandler#getConnection()
	 */
	@Override
	public Connection getConnection(){
		Connection currentConnection = null;
		try{
			Class.forName(DRIVER_NAME); // ensure existence of driver
			currentConnection = DriverManager.getConnection(
					"jdbc:mysql://" + DatabaseParameters.SERVER_ADDRESS +  
					"/" + getDatabaseName() + NO_SSL,
					DatabaseParameters.LOGIN, 
					DatabaseParameters.PASSWORD);
		}catch(Exception e){}
		return currentConnection;
	}


	// Not necessary for tests
	@Override
	public void close() {}

	
	// Returns name of schema used for testing (mock database)
	@Override
	public String getDatabaseName() {
		return mockSchemaName;
	}
	
}
