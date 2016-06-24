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
	public static final String NO_SSL = "?useSSL=no";
	
	/* Scripts-related constants */
	public static final String DB_INIT_SCRIPT = "init_db.sql";
	public static final String DB_DROP_SCRIPT = "drop_db.sql";
	public static final String DB_TRUNCATE_SCRIPT = "truncate_db.sql";
	

	/* SQL related constants */
	
	// 'User' columns enumeration
	public enum USER{
		USER_ID 				(1),
		USERNAME 				(2),
		PASSW_HASH 				(3),
		PROFILE_PICTURE_URL 	(4),
		ABOUT_ME 				(5);
	
		private int index;
		private USER(int num){ index = num; }
		public int num(){ return index; }
	}

	
	// 'Quiz' columns enumeration
	public enum QUIZ{
		QUIZ_ID 				(1),
		QUIZ_NAME  				(2),
		DATE_CREATION  			(3),
		CATEGORY_ID  			(4),
		CREATOR_ID  			(5),
		RANDOM_ORDER  			(6),
		INSTANT_CORRECTION 		(7),
		ONE_MULTIPLE_PAGE_MODE 	(8),
		QUIZ_DESCRIPTION  		(9);
		
		private int index;
		private QUIZ(int num) { index = num; }
		public int num() { return index; }
	}

	
	// 'Quizzes_taken' columns enumeration 
	public enum QUIZ_TAKEN{
		QUIZ_ID  				(1),
		ATTEMPT_ID 				(2),
		USER_IDd  				(3),
		PERCENT_CORRECT  		(4), 
		ATTEMPT_DATE  			(5),
		AMOUNT_TIME 			(6);
		
		private int index;
		private QUIZ_TAKEN(int num) { index = num; }
		public int num() { return index; }

	}
		
	
}
