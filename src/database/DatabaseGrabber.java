package database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import factory.DatabaseFactory;
import quiz.*;
import questions.*;

/** 
 * @author dav23r
 * The class encapsulates all the interactions with database.
 */
public class DatabaseGrabber implements DatabaseParameters{

	// Factory is used to acquire connection handler.
	private DatabaseFactory dbFactory = null;
	private DatabaseConnectionHandler conHandler = null;
	
	/**
	 * DatabaseGrabber constructor accepts factory instance,
	 * which will determine what objects will be handed back
	 * when say connection to database is issued.
	 * @param dbFactory
	 */
	public DatabaseGrabber(DatabaseFactory dbFactory) {
		this.dbFactory = dbFactory;
	}
	
	/**
	 * Initialize connection with database, make possible subsequent
	 * interactions with MYSQL database using credentials from DatabaseParameters.
	 * @throws SQLException 
	 */
	public void connect() throws SQLException {
		this.conHandler = dbFactory.getDatabaseConnectionHandler();

		Statement stmt = conHandler.getConnection().createStatement();
		stmt.executeQuery("USE " + conHandler.getDatabaseName() + " ;");
		stmt.close();
	}
	
	/**
	 * Registers user inside database. If the userName already
	 * exists in database, registration will fail, returning
	 * value of 'false'; 'true' signifies successful registration.
	 * @param userName - system login of the user.
	 * @param password - secret string for proving identity.
	 * @return registration status
	 * @throws SQLException 
	 */
	public boolean registerUser(String userName, String password) 
			throws SQLException{
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlNumberEntriesSameName = 
				"SELECT COUNT(*) "
				+ "FROM users "
				+ "WHERE username = " + "'" + userName + "'"+ ";";
		
		ResultSet rs = stmt.executeQuery(sqlNumberEntriesSameName);
		// If database already contains entry with same name terminate.
		if (!rs.next())
			return false;
		// Otherwise add new user row to database
		String sqlAddNewUser = 
				"INSERT INTO users (username, passw_hash) " +
				"VALUES "+ "(" + "'" + userName + "'" + "," + " " + 
						"'" + password + "'" + ");";
				
		stmt.executeUpdate(sqlAddNewUser);
		stmt.close();
		return true;
	}
	
	
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
			throws SQLException{
		Statement stmt = conHandler.getConnection().createStatement();
		String queryUser = 
				"SELECT * FROM users "
				+ "WHERE username = " + "'" + userName + "';";
		ResultSet rs = stmt.executeQuery(queryUser);
		// User with provided userName doesn't exist in database
		if (!rs.next())
			return null;
		// Otherwise check if stored hash and provided one are equal
		if (rs.getString(PASSW_HASH).equals(passwHash))
			return null;
		// If valid credentials are provided, return corresponding user.
		return loadUser(userName);
	}
	
	/**
	 * Methods drops all tables in database, so all the data
	 * is lost after method completion, be extremely 
	 * !!!!!!!    CAREFUL !!!!!!!! If no extreme condition,
	 * client should *** NEVER *** call this method.
	 * @throws FileNotFoundException 
	 */
	public void dropDatabase() throws FileNotFoundException{
		BufferedReader br = new BufferedReader(new FileReader(DROP_DB_SCRIPT));

	}
	
	public void uploadQuiz (Quiz quiz) 
			throws SQLException  {}

	
	public User loadUser(String userName) {return null;}
	
	public Quiz loadQuiz(String quizName) {return null;}
	
	public QuizCollection getPopularQuizzes() {return null;}
	
	public QuizCollection getRecentlyCreatedQuizzes() {return null;}
	
	public UserList getRecentTestTakers(String quizName, Date date) {return null;}
	
	public UserList highestPerformers(String quizName, Date date) {return null;}
	
	/**
	 * Frees up resources associated with current connection.
	 */
	public void close() {
		conHandler.close();
	}
}
