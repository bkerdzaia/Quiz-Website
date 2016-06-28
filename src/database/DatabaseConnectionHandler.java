package database;

import java.sql.Connection;

/**
 * @author dav23r
 * The interface states methods that should be implemented
 * by connection-related classes to provides means of creating
 * connection with database and properly terminating it.
 */
public interface DatabaseConnectionHandler {
	
	/**
	 * Method returns name of the schema on which
	 * subsequent queries should be performed.
	 * @return schema name
	 */
	public String getDatabaseName();
	
	/**
	 * Initiates connection and provides client
	 * with 'connection' object that allows to 
	 * transfer data to and from database.
	 * @return Connection object
	 */
	public Connection getConnection();
	
	/**
	 * Terminates current connection. Clients are supposed
	 * to call this method as fast as particular 'job' 
	 * regarding database is done.
	 */
	public void close();

}
