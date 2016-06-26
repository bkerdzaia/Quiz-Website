package database;

import PrivateSection.DBPassword;

/**
 * @author dav23r
 * Stores various parameters such as MYSQL login,
 * password, schema name etc. All the database classes
 * should implement this interface to gain access to 
 * shared constants instead of using "magic" values.
 */

public interface DatabaseParameters {

	/* Server-related constants */
	public static final String SERVER_ADDRESS = "localhost:3306";
	public static final String SCHEMA_NAME = "QuizWebsite";
	public static final String LOGIN = "root";
	public static final String PASSWORD = DBPassword.PASSWORD; 
	public static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	public static final String NO_SSL = "?useSSL=no";
	
	/* Scripts-related constants */
	public static final String DB_INIT_SCRIPT = "init_db.sql";
	public static final String DB_DROP_SCRIPT = "drop_db.sql";
	public static final String DB_TRUNCATE_SCRIPT = "truncate_db.sql";
	
	/* Constraints on stored data */
	public static final int QUIZ_NAME_MAX_LEN = 45;
	public static final int USER_NAME_MAX_LEN = 45;
	
	
	// 'User' columns enumeration
	public enum USER{
		USERNAME 				(1),
		PASSW_HASH 				(2),
		PROFILE_PICTURE_URL 	(3),
		ABOUT_ME 				(4);
	
		private int index;
		private USER(int num){ index = num; }
		public int num(){ return index; }
	}

	
	// 'Quiz' columns enumeration
	public enum QUIZ{
		QUIZ_NAME  				(1),
		DATE_CREATION  			(2),
		CREATOR_NAME 			(3),
		RANDOM_ORDER  			(4),
		INSTANT_CORRECTION 		(4),
		ONE_MULTIPLE_PAGE_MODE 	(5),
		QUIZ_DESCRIPTION  		(7);
		
		private int index;
		private QUIZ(int num) { index = num; }
		public int num() { return index; }
	}

	
	// 'Quizzes_taken' columns enumeration 
	public enum QUIZ_TAKEN{
		QUIZ_NAME  				(1),
		ATTEMPT_ID 				(2),
		USER_NAME  				(3),
		PERCENT_CORRECT  		(4), 
		ATTEMPT_DATE  			(5),
		AMOUNT_TIME 			(6);
		
		private int index;
		private QUIZ_TAKEN(int num) { index = num; }
		public int num() { return index; }

	}
		
	
}
