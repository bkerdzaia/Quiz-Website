package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DefaultDatabaseConnectionHandler implements 
						DatabaseConnectionHandler, DatabaseParameters{
/**
 * @author dav23r
 * The class provides means of connectivity to MYSQL database.
 * Constructor is hidden as usual for singleton pattern; instead
 * clients should call static 'getConnection' method to retrieve
 * DatabaseConnection object which is shared across different clients.
 */

	
	// Initially empty, will be created 'lazily'.
	private Connection mysqlConnection = null;
	private static DefaultDatabaseConnectionHandler connectionHandler = null;
	
	// Hide constructor 
	private DefaultDatabaseConnectionHandler(){}
	
	/**
	 * Returns DatabaseConnection object needed to carry
	 * out various queries from MYSQL database.
	 * @return object of type 'connection'
	 */
	@Override
	public Connection getConnection(){
		// If connection is already initialized, just hand it back.
		if (mysqlConnection != null) 
			return mysqlConnection;
		/* Otherwise try to establish connection, save it in the 
		   corresponding field and pass back to the client. */
		String driverName = "com.mysql.jdbc.Driver";
		Connection currentConnection = null;
		try{
			Class.forName(driverName); // ensure existence of driver
			currentConnection = DriverManager.getConnection(
					"jdbc:mysql://" + DatabaseParameters.SERVER_ADDRESS,
					DatabaseParameters.LOGIN, 
					DatabaseParameters.PASSWORD);
		}catch(SQLException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		this.mysqlConnection = currentConnection;
		return currentConnection;
	}
	
	/**
	 * Returns object of current class. Way of instantiation
	 * alternative to 'new' keyword. Client should acquire 
	 * DatabaseConnection object this way and then get actual
	 * connection object from it.
	 */
	public static DefaultDatabaseConnectionHandler getConnectionHandler(){
		//If static field is empty, initialize it
		if (connectionHandler == null)
			connectionHandler = new DefaultDatabaseConnectionHandler();
		
		return connectionHandler;
	}
	
	/**
	 * Method closes existing connection to database.
	 * May create additional work if used improperly; client
	 * should close connection if it is not needed anymore.
	 */
	@Override
	public void close(){
		try{
			mysqlConnection.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		mysqlConnection = null;
	}

	/**
	 * Returns default schema name, as specified in parameters.
	 */
	@Override
	public String getDatabaseName() {
		return DatabaseParameters.SCHEMA_NAME;
	}
}
	
