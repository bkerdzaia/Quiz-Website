package database;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

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
	 * are supplied, and if that is the case returns true. If user 
	 * doesn't exist or password hashes do not match, 'false' is handed back.
	 * @param userName
	 * @param passwHash
	 * @return corresponding 'user' or null
	 * @throws SQLException
	 */
	public boolean authenticateUser(String userName, String passwHash) 
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
	 * Given performance summary of user's attempt on particular
	 * quiz stores that record in database. It is assumed that
	 * 'valid quiz and user names are provided.
	 */
	public void storeAttempt(Performance perf, String userName, String quizName) 
			throws SQLException;

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
	 * Method retrieves stats of most recent quiz takers for 
	 * specified quiz.Threshold for amount of user is specified in 
	 * 'UIParameters' interface. Users are in order of decreasing
	 * date of taking (most recent taker first)
	 * @param quizName
	 * @return UserList of recent takers of the quiz.
	 * @throws SQLException
	 */
	public QuizHistory getRecentTakersStats(String quizName, Timestamp date) 
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
	public UserList getHighestPerformers(String quizName, Timestamp date) 
			throws SQLException;
	
	/**
	 * Method adds new friend request record to database. The first argument
	 * is friendship initiator's user name, the second - the one the first 
	 * wants to be friends with. As a precondition, both string should represent
	 * valid user names contained in system. Method ensures that users are 
	 * not friends already, in which case it returns 'false'. Also method 
	 * returns 'false' if request is already sent by same user or if from == to.
	 * !! Note: if 'to' already requested friendship with 'from', new friendship
	 * entry is automatically added to database.
	 * @param from - user who wants to be friends with 'to'
	 * @param to - to whom friendship request in sent
	 * @return - add status (true - success, otherwise - false)
	 * @throws SQLException
	 */
	public boolean addFriendRequest(String from, String to) throws SQLException;
	
	/**
	 * Adds new friendship record to the database. A check is done, 
	 * that the provided users are not friends already and that friendship
	 * is really requested, if these requirements are not fulfilled 'false' 
	 * is handed back as a sign of failure.
	 * @param first user's name
	 * @param second user's name
	 * @return completion status ('true' if success, 'false' otherwise)
	 * @throws SQLException
	 */
	public boolean acceptFriendRequest(String acceptor, String initiator) 
			throws SQLException;
	
	/**
	 * Provided with two user names, removes friend relationship between
	 * them from database. If user are not friends hands back 'false'
	 * as a sign of failure. As a precondition, both names should 
	 * belong to valid registered users in database.
	 * @param firstUser
	 * @param secondUser
	 * @return completion status ('true' if success, 'false' otherwise)
	 */
	public boolean removeFriend(String firstUser, String secondUser) 
			throws SQLException;
	
	/**
	 * Provides means of sending message from one user of the system to
	 * another. The message is stored in database, and will be viewed 
	 * by recepient once he/she logs in. A check is done for users to 
	 * be friends.
	 * @param from - sender's name
	 * @param to - recepient's name
	 * @return - completion status ('true' if success, 'false' otherwise)
	 * @throws SQLException
	 */
	public boolean sendMessage(String from, String to, String message, Timestamp date) 
			throws SQLException;

	
	/**
	 * Frees up resources associated with current connection.
	 */
	public void close();
}
