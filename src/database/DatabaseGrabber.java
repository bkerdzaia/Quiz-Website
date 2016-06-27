package database;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import quiz.*;

/**
 * @author dav23r
 * States overall interface for various implementations of DatabaseGrabber.
 * Both DefaultDatabaseGrabber and other mock implementations should obey
 * following rules of interaction. As observed, database delegates handling
 * of exceptions to client, believing that it's client responsibility and 
 * client can handle in a very specific way that suits particular needs.
 */
public interface DatabaseGrabber {
	
	/**
	 * Initialize connection with database, make possible subsequent
	 * interactions with MYSQL database using credentials from DatabaseParameters.
	 * @throws SQLException 
	 */
	public void connect() throws SQLException;
	

	/**
	 * Registers user inside database. If the userName already
	 * exists in database, registration will fail, returning
	 * value of 'false'; 'true' signifies successful registration.
	 * @param userName - system login of the user.
	 * @param password - secret string for proving identity.
	 * @return registration status (boolean)
	 * @throws SQLException 
	 */
	public boolean registerUser(String userName, String passwHash) 
			throws SQLException;
	

	/**
	 * Authenticates user in database. Ensure that right credentials
	 * are supplied, and if that is the case returns corresponding
	 * 'user' object. If user doesn't exist or password hashes do not
	 * match, null object is handed back
	 * @param userName
	 * @param passwHash
	 * @return corresponding 'user' or null
	 * @throws SQLException
	 */
	public User authenticateUser(String userName, String passwHash) 
			throws SQLException;
	

	/**
	 * Methods truncates all tables in  database, so all the data
	 * is lost after method completion, be extremely 
	 * !!!!!!!    CAREFUL !!!!!!!! If no extreme condition,
	 * client should *** NEVER *** call this method.
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public void truncateDatabase() throws FileNotFoundException, IOException, SQLException;
	

	/**
	 * Constructs 'user' object after retrieving data about 
	 * system user with provided userName from database. If
	 * such userName doesn't exist in database, null object
	 * is handed back.
	 * @param userName
	 * @return 'user' object or null if doesn't exist
	 * @throws SQLException
	 */
	public User loadUser(String userName) throws SQLException;


	/**
	 * Provided with 'quiz' object, method breaks it down
	 * into pieces of information that should be store in
	 * database, as an entry associated with the new quiz.
	 * If quiz with current quizz'es name already exists,
	 * 'false' is returned as indication of failure.
	 * @param quiz
	 * @return upload status (boolean value)
	 * @throws SQLException
	 */
	public boolean uploadQuiz (Quiz quiz) throws SQLException; 

	
	/**
	 * Provided with new quiz object, that represents alteration
	 * of previously stored quiz in database (may have completely
	 * different questions), corresponding changes are made in the 
	 * database copy of the quiz. If the quiz, identified with it's
	 * name is not contained in database, 'false' is handed back 
	 * as a sign of failure.
	 * @param quiz - new version of the quiz
	 * @return edit status (boolean true - if success, false otherwise)
	 * @throws SQLException
	 */
	public boolean editQuiz (Quiz quiz) throws SQLException;
	
	/**
	 * Method deletes quiz named 'quizName' from database storage;
	 * provides means of deleting quiz for quizz's creator. After
	 * successfull execution of the method, quiz is permanently 
	 * deleted from database.
	 * @param quizName
	 * @return delete status (boolean true - if success, false otherwise)
	 * @throws SQLException
	 */
	public boolean deleteQuiz(String quizName) throws SQLException;
	
	/**
	 * Retrieves data from database associated with quiz named
	 * 'quizName' and composes 'quiz' object which is handed back.
	 * @param quizName
	 * @return 'quiz' object or null if not exists.
	 * @throws SQLException
	 */
	public Quiz loadQuiz(String quizName) throws SQLException;
	

	/**
	 * Method returns list of quiz names that are created
	 * by user with provided userName. It is assumed that
	 * userName is a valid name that is contained in database.
	 * Quizzes are in decreasing order of date (recent - first)
	 * @param userName
	 * @return collection of quzzes created by user with 'userName'
	 * @throws SQLException
	 */
	public QuizCollection getCreatedQuizzesByUserName(String userName) 
			throws SQLException;

	/**
	 * Given performance summary of user's attempt on particular
	 * quiz stores that record in database. It is assumed that
	 * 'perf' has valid quiz and user names.
	 */
	public void storeAttempt(QuizPerformance perf) throws SQLException;

	/**
	 * Returns 'QuizCollection' object filled with most popular 
	 * quizzes for time being. Threshold for amount of quizzes 
	 * is specified in 'UIParameters' interface. Quizzes are 
	 * in order of decreasing popularity.
	 * @return 'QuizCollection' object of popular quizzes.
	 * @throws SQLException
	 */
	public QuizCollection getPopularQuizzes() throws SQLException;
	
	/**
	 * Returns 'QuizCollection' object populated with most recently
	 * created quizzes. Threshold for amount of quizzes of contained
	 * in collection is specified in 'UIParameters' interface. Quizzes
	 * are in order of decreasing date (newest first)
	 * @return 'QuizCollection' object of recent quizzes. 
	 * @throws SQLException
	 */
	public QuizCollection getRecentlyCreatedQuizzes() 
			throws SQLException;
	
	/**
	 * Method retrieves stats of most recent quiz takers.
	 * Threshold for amount of user is specified in 
	 * 'UIParameters' interface. Users are in order of decreasing
	 * date of taking (most recent taker first)
	 * @param quizName
	 * @return UserList of recent takers of the quiz.
	 * @throws SQLException
	 */
	public History getRecentTakersStats(Timestamp date) 
			throws SQLException; 
	
	/**
	 * For particular quizName, retrieves users who performed
	 * best on the quiz. Threshold for amount of users is in 
	 * 'UIParameters'. Users in list are arranged in decreasing
	 * order of score (best performer - first).
	 * @param quizName
	 * @param date - min date of attempt
	 * @return UserList of best performers.
	 * @throws SQLException
	 */
	public UserList highestPerformers(String quizName, Timestamp date) 
			throws SQLException;
	
	/**
	 * Frees up resources associated with current connection.
	 */
	public void close();
}
