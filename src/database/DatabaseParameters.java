package database;

import PrivateSection.*;
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
	public static final int PASSWORD_HASH_MAX_LEN = 160;
	
	
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
		INSTANT_CORRECTION 		(5),
		ONE_MULTIPLE_PAGE_MODE 	(6),
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
		
	// 'Question_response' columns enumeration
	public enum QUESTION_RESPONSE{
		PROBLEM_ID    		(1),         
		QUESTION            (2),   
		QUIZ_NAME           (3),   
		REL_POSITION       	(4);
		
		private int index;
		private QUESTION_RESPONSE(int num) { index = num; }
		public int num() { return index; }
	}
	
	// 'Fill in blank' columns enumeration
	public enum FILL_BLANK{
		PROBLEM_ID    		(1),         
		QUESTION            (2),   
		QUIZ_NAME           (3),
		FIELD_POSITION 		(4),
		REL_POSITION       	(5);
		
		private int index;
		private FILL_BLANK(int num) { index = num; }
		public int num() { return index; }
	}
	
	// 'Picture response' columns enumeration
	public enum PICTURE_RESPONSE{
		PROBLEM_ID    		(1),         
		QUIZ_NAME           (2),
		QUESTION            (3),   
		IMAGE_URL			(4),
		REL_POSITION       	(5);
		
		private int index;
		private PICTURE_RESPONSE(int num) { index = num; }
		public int num() { return index; }
	}

	// 'Multiple choise' columns enumeration
	public enum MULTIPLE_CHOISE{
		PROBLEM_ID    		(1),         
		QUIZ_NAME           (2),
		QUESTION            (3),   
		REL_POSITION       	(4);
		
		private int index;
		private MULTIPLE_CHOISE(int num) { index = num; }
		public int num() { return index; }
	}
	
}
