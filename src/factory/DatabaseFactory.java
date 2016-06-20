package factory;

import database.*;

/**
 * @author dav23r
 * The class encapsulates construction of database-related
 * objects. This factory will be handed to any class that 
 * needs to created objects associated with database at any
 * point of its life-cycle; so testing particular module boils 
 * down to overriding listed methods and feeding mock factory
 * to the constructor of object under testing.
 * Singleton pattern is used.
 */
public class DatabaseFactory {

	private static DatabaseFactory factoryInstance = null;
	
	// Hide constructor
	private DatabaseFactory(){}
	
	// Returns one and only instance DatabaseFactory
	public static DatabaseFactory getFactoryInstance(){
		// Initialize static field if empty
		if (factoryInstance == null)
			factoryInstance = new DatabaseFactory();
		
		return factoryInstance;
	}

	// Constructs DatabaseGrabber, is fed with default factory
	public DatabaseGrabber getDatabaseGrabber() {
		return new DatabaseGrabber(getFactoryInstance());
	}
	
	// Constructs DatabaseConnectionHandler object
	public DatabaseConnectionHandler getDatabaseConnectionHandler(){
		return DatabaseConnectionHandler.getConnectionHandler();
	}
	
}
