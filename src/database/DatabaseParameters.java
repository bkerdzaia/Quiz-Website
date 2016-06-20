package database;

/**
 * @author dav23r
 * Stores various parameters such as MYSQL login,
 * password, schema name etc. All the database classes
 * should implement this interface to gain access to 
 * shared constants instead of using "magic" values.
 */

public interface DatabaseParameters {

	/* Server-related constants */
	public static final String SERVER_ADDRESS = "localhost";
	public static final String SCHEMA_NAME = "QuizWebsite";
	public static final String LOGIN = "root";
	public static final String PASSWORD = "xp4m4dav"; 
	
	/* wrapped SQL statements */
	
}