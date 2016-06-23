package database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import com.mysql.jdbc.Connection;

import factory.DatabaseFactory;
import quiz.Quiz;
import quiz.QuizCollection;
import quiz.User;
import quiz.UserList;

/**
 * @author dav23r
 * Default implementation of DatabaseGrabber interface. Should be 
 * used to interact with database in production code. 
 */
public class DefaultDatabaseGrabber implements 
				DatabaseGrabber, DatabaseParameters {
	
	// Factory is used to acquire connection handler.
	private DatabaseFactory dbFactory = null;
	private DatabaseConnectionHandler conHandler = null;
	
	/* Following to are paths to database scripts (initialize and drop).
	 * 'protected' to make possible for subclasses to override. */
	protected String truncateScriptPath = 
			new File("").getAbsolutePath() +
			"/src/db_scripts/" + 
			DatabaseParameters.DB_TRUNCATE_SCRIPT;

	// Accept and store factory, to compose objects later.
	public DefaultDatabaseGrabber(DatabaseFactory dbFactory) {
		this.dbFactory = dbFactory;
	}


	// Initialize connection via connection handler.
	@Override
	public void connect() throws SQLException {
		this.conHandler = dbFactory.getDatabaseConnectionHandler();
	}

	// Register user in database
	@Override
	public boolean registerUser(String userName, String password) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlNumberEntriesSameName = 
				"SELECT COUNT(*) "
				+ "FROM users "
				+ "WHERE username = " + "'" + userName + "'"+ ";";
		
		ResultSet rs = stmt.executeQuery(sqlNumberEntriesSameName);
		// If database already contains entry with same name terminate.
		if (!rs.next()){
			stmt.close();
			return false;
		}
		// Otherwise add new user row to database
		String sqlAddNewUser = 
				"INSERT INTO users (username, passw_hash) " +
				"VALUES "+ "(" + "'" + userName + "'" + "," + " " + 
						"'" + password + "'" + ");";
				
		stmt.executeUpdate(sqlAddNewUser);
		stmt.close();
		return true;
	}


	// Authenticate user based on login and hash of password
	@Override
	public User authenticateUser(String userName, String passwHash) 
			throws SQLException {
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


	// Truncates all tables in database
	@Override
	public void truncateDatabase() throws IOException, SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		BufferedReader br = new BufferedReader(
				new FileReader(truncateScriptPath));
		String line;
		while ((line = br.readLine()) != null){
			stmt.executeUpdate(line);
		}
		br.close();
		stmt.close();
	}


	@Override
	public User loadUser(String userName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean uploadQuiz(Quiz quiz) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Quiz loadQuiz(String quizName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QuizCollection getPopularQuizzes() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QuizCollection getRecentlyCreatedQuizzes() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserList getRecentTestTakers(String quizName, Date date) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserList highestPerformers(String quizName, Date date) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
