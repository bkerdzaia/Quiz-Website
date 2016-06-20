package database;

import java.sql.*;

/**
 * @author dav23r
 * The class provides means of connectivity to MYSQL database.
 * Constructor is hidden as usual for singleton pattern; instead
 * clients should call static 'getConnection' method to retrieve
 * connection object which is shared across different clients.
 */
public class DatabaseConnection implements DatabaseParameters{
	
	// Initially empty, will be created 'lazily'.
	private static Connection dbConnection = null;
	
	// Hide constructor 
	private DatabaseConnection(){}
	
	/**
	 * Returns database connection object needed to carry
	 * out various queries from MYSQL database.
	 * @return object of type 'connection'
	 */
	public static Connection getConnection(){
		// If connection is already initialized, just hand it back.
		if (dbConnection != null) 
			return dbConnection;
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
		DatabaseConnection.dbConnection = currentConnection;
		return currentConnection;
	}
	
	/**
	 * Method closes existing connection to database.
	 * May create additional work if used improperly; client
	 * should close connection if it is not needed anymore.
	 */
	public static void close(){
		try{
			dbConnection.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		dbConnection = null;
	}
}
