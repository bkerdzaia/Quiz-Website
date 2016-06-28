package factory;
import database.*;

/**
 * @author dav23r
 * Interface for creating database-related objects. All
 * database factory implementations including default 
 * factory and various 'mock' factories should implement
 * this interface. So testing particular module boils 
 * down to creating new object implementing factory interface
 * and feeding mock to the constructor of object under testing.
 */
public interface DatabaseFactory {
	
	/**
	 * Incorporates DatabaseGrabber creation.
	 * @return DatabaseGrabber or any of it's subclasses. 
	 */
	public DatabaseGrabber getDatabaseGrabber();
	
	/**
	 * Incorporates DatabaseConnectionHandler creation
	 * @return DatabaseConnection or any of it's subclasses.
	 */
	public DatabaseConnectionHandler getDatabaseConnectionHandler();

}
