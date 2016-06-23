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
	public static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	
	/* Scripts-related constants */
	public static final String DB_INIT_SCRIPT = "init_db.sql";
	public static final String DB_DROP_SCRIPT = "drop_db.sql";
	public static final String DB_TRUNCATE_SCRIPT = "truncate_db.sql";
	

	/* SQL related constants */
	
	// 'User' columns  
	public static final int USER_ID = 1;
	public static final int USERNAME = 2;
	public static final int PASSW_HASH = 3;
	public static final int PROFILE_PICTURE_URL = 3;
	public static final int ABOUT_ME = 4;
	
}
