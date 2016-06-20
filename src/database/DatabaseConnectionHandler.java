package database;

import java.sql.*;

/**
 * @author dav23r
 * The class provides means of connectivity to MYSQL database.
 * Constructor is hidden as usual for singleton pattern; instead
 * clients should call static 'getConnection' method to retrieve
 * DatabaseConnection object which is shared across different clients.
 */
public class DatabaseConnectionHandler implements DatabaseParameters{
	
	// Initially empty, will be created 'lazily'.
	private Connection mysqlConnection = null;
	private static DatabaseConnectionHandler connectionHandler = null;
	
	// Hide constructor 
	private DatabaseConnectionHandler(){}
	
	/**
	 * Returns DatabaseConnection object needed to carry
	 * out various queries from MYSQL database.
	 * @return object of type 'connection'
	 */
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
	public static DatabaseConnectionHandler getConnectionHandler(){
		//If static field is empty, initialize it
		if (connectionHandler == null)
			connectionHandler = new DatabaseConnectionHandler();
		
		return connectionHandler;
	}
	
	/**
	 * Method closes existing connection to database.
	 * May create additional work if used improperly; client
	 * should close connection if it is not needed anymore.
	 */
	public void close(){
		try{
			mysqlConnection.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		mysqlConnection = null;
	}
	
}
