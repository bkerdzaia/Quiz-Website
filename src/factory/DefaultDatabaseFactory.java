package factory;

import database.DatabaseConnectionHandler;
import database.DatabaseGrabber;
import database.DefaultDatabaseConnectionHandler;

/**
 * @author dav23r
 * Default implementation of DatabaseFactory; the one which
 * should be used in production code. Uses singleton pattern
 * to reduce memory overhead (Single factory object will be 
 * shared between different clients).
 */
public class DefaultDatabaseFactory implements DatabaseFactory{

	// Initially empty, will be composed lazily.
	private static DefaultDatabaseFactory defaultFactoryInstance = null;
	
	// Hide constructor
	private DefaultDatabaseFactory(){}
	
	// Returns one and only instance DefaultDatabaseFactory
	public static DatabaseFactory getFactoryInstance(){
		// Initialize static field if empty
		if (defaultFactoryInstance == null)
			defaultFactoryInstance = new DefaultDatabaseFactory();
		
		return defaultFactoryInstance;
	}
	
	// Constructs DatabaseGrabber, is fed with default factory
	@Override
	public DatabaseGrabber getDatabaseGrabber() {
		return new DatabaseGrabber(getFactoryInstance());
	}
	
	// Constructs DefaultDatabaseConnectionHandler object
	@Override
	public DatabaseConnectionHandler getDatabaseConnectionHandler(){
		return DefaultDatabaseConnectionHandler.getConnectionHandler();
	}

}
