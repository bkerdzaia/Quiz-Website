package database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import application.UIParameters;
import factory.DatabaseFactory;
import factory.QuestionFactory;
import factory.QuizFactory;
import factory.UserFactory;
import quiz.History;
import quiz.Quiz;
import quiz.QuizCollection;
import quiz.QuizPerformance;
import quiz.User;
import quiz.UserList;

/**
 * @author dav23r
 * Default implementation of DatabaseGrabber interface. Should be 
 * used to interact with database in production code. 
 */
public class DefaultDatabaseGrabber implements 
				DatabaseGrabber, DatabaseParameters, UIParameters {
	
	// Factory is used to acquire connection handler.
	private DatabaseFactory dbFactory = null;
	private UserFactory userFactory = null;
	private QuizFactory quizFactory = null;
	private QuestionFactory questionFactory = null;
	private DatabaseConnectionHandler conHandler = null;
	
	/* Following to are paths to database scripts (initialize and drop).
	 * 'protected' to make possible for subclasses to override. */
	protected String truncateScriptPath = 
			new File("").getAbsolutePath() +
			"/src/db_scripts/" + 
			DatabaseParameters.DB_TRUNCATE_SCRIPT;

	// Accept and store factories, to compose objects later.
	public DefaultDatabaseGrabber(DatabaseFactory dbFactory,
									UserFactory userFactory,
									QuizFactory quizFactory,
									QuestionFactory questionFactory) {
		this.dbFactory = dbFactory;
		this.userFactory = userFactory;
		this.quizFactory = quizFactory;
		this.questionFactory = questionFactory;
	}


	// Initialize connection via connection handler.
	@Override
	public void connect() throws SQLException {
		this.conHandler = dbFactory.getDatabaseConnectionHandler();
	}


	// Register user in database
	@Override
	public boolean registerUser(String userName, String passwHash) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlNumberEntriesSameName = 
				"SELECT user_id "
				+ "FROM users "
				+ "WHERE username = " + "'" + userName + "'"+ ";";
		
		ResultSet rs = stmt.executeQuery(sqlNumberEntriesSameName);
		// If database already contains entry with same name terminate.
		if (rs.next()){
			stmt.close();
			return false;
		}
		// Otherwise add new user row to database
		String sqlAddNewUser = 
				"INSERT INTO users (username, passw_hash) " +
				"VALUES "+ "(" + "'" + userName + "'" + "," + " " + 
						"'" + passwHash + "'" + ");";
				
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
		if (!rs.next()){
			stmt.close();
			return null;
		}
		// Otherwise check if stored hash and provided one are equal
		if (!rs.getString(USER.PASSW_HASH.num()).equals(passwHash)){
			stmt.close();
			return null;
		}
		// If valid credentials are provided, return corresponding user.
		stmt.close();
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


	// Return user object based on name.
	@Override
	public User loadUser(String userName) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String queryUser = 
				"SELECT * FROM users " +
				"WHERE username = " + "'" + userName + "';";
		ResultSet rs = stmt.executeQuery(queryUser);
		if (!rs.next())
			return null;
		User retrievedUser = userFactory.getUser();
		// Fill user bean
		retrievedUser.setName(rs.getString(USER.USERNAME.num()));
		retrievedUser.setPictureUrl(rs.getString(USER.PROFILE_PICTURE_URL.num()));
		retrievedUser.setAboutMe(rs.getString(USER.ABOUT_ME.num()));
		retrievedUser.setPasswordHash(rs.getString(USER.PASSW_HASH.num()));
		// Fill more complex fields, first list of created quizzes
		retrievedUser.setCreatedQuizzes(
				getCreatedQuizzesByUserId(rs.getInt(USER.USER_ID.num())));
		// Now, history of performance
		History userHistory = fillHistoryByUserId(rs.getInt(USER.USER_ID.num()));
		retrievedUser.setHistory(userHistory);
		return retrievedUser;
	}


	/* Given user id, constructs record of user's performance,
	 * and hands back corresponding 'history' object.
	 */
	private History fillHistoryByUserId(int userId) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		History userHistory = userFactory.getHistory();
		String sqlTakenQuizzes = 
				"SELECT * FROM quizzes_taken " +
				"WHERE quiz_id = " + "'" + userId + "' " +
				"ORDER BY attempt_date DESC;";
		ResultSet rs = stmt.executeQuery(sqlTakenQuizzes);
		// Iterate over result set and collect performance objects.
		while (rs.next() && rs.getRow() < MAX_HISTORY_ENTRIES_FOR_USER){
			// Create new performance object
			QuizPerformance curPerformance = quizFactory.getQuizPerformance();
			// And fill with corresponding values
			curPerformance.setDate(rs.getDate(QUIZ_TAKEN.ATTEMPT_DATE.num()));
			curPerformance.setAmountTime(rs.getTime(QUIZ_TAKEN.AMOUNT_TIME.num()));
			curPerformance.setPercentCorrect(rs.getInt(QUIZ_TAKEN.AMOUNT_TIME.num()));
			// Add performance entry to history
			userHistory.addPerformance(curPerformance);
		}
		return userHistory;
	}


	private QuizCollection getCreatedQuizzesByUserId(int userId) 
			throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlQuizIdByUserId = 
				"SELECT quiz_name FROM quizzes " +
				"WHERE quiz_creator_id = " + "'" + userId + "';";
		ResultSet rs = stmt.executeQuery(sqlQuizIdByUserId);
		// Acquire new QuizCollection and start filling
		QuizCollection createdQuizzes = quizFactory.getQuizCollection();
		while (rs.next())
			createdQuizzes.add(loadQuiz(rs.getString(1))); // only one column
		return null;
	}


	@Override
	public boolean uploadQuiz(Quiz quiz) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}


	// Return quiz from database with provided name.
	@Override
	public Quiz loadQuiz(String quizName) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlGetQuiz =
				"SELECT * FROM quizzes " +
				"wHERE quizname = " + "'" + quizName + "';";
		ResultSet rs = stmt.executeQuery(sqlGetQuiz);
		// If doesn't exist, return null
		if (!rs.next())
			return null;
		Quiz retrievedQuiz = quizFactory.getQuiz();
		// Fill quiz bean
		retrievedQuiz.setName(rs.getString(QUIZ.QUIZ_NAME.num()));
		retrievedQuiz.setDescription(rs.getString(QUIZ.QUIZ_DESCRIPTION.num()));
		// TODO Complex fields are empty !!
		return retrievedQuiz;
	}


	@Override
	public QuizCollection getPopularQuizzes() throws SQLException {
		// TODO everything
		Statement stmt = conHandler.getConnection().createStatement();
		return null;
	}


	@Override
	public QuizCollection getRecentlyCreatedQuizzes() throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlRecentCreatedQuizzes = 
				"SELECT * FROM quizzes " +
				"SORT BY creation_date ASC " + 
				"LIMIT " + UIParameters.MAX_RECENTRY_CREATED_QUIZZES_NUM + ";";
		ResultSet rs = stmt.executeQuery(sqlRecentCreatedQuizzes);
		QuizCollection recentQuizzes = quizFactory.getQuizCollection();
		while (rs.next())
			recentQuizzes.add(loadQuiz(rs.getString(QUIZ.QUIZ_NAME.num())));
		return recentQuizzes;
	}


	@Override
	public UserList getRecentTestTakers(String quizName, Date date) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		int quizID = getQuizIDByName(quizName);
		String sqlUserJoinQuizzes = 
				"SELECT username FROM users " + 
					"JOIN quizzes_taken ON " + 
						"quiz_id = " + quizID +
						" AND " +
						"users.user_id = quizzes_taken.user_id " +
						" AND " +
						"attempt_date > " + date + " " +
				"ORDER BY attempt_date DESC";
		ResultSet rs = stmt.executeQuery(sqlUserJoinQuizzes);
		UserList recentTakers = userFactory.getUserList();
		while (rs.next())
			recentTakers.add(loadUser(rs.getString(1)));
		return recentTakers;
	}


	// Returns list of highest performer user for particular quiz, starting from given date
	@Override
	public UserList highestPerformers(String quizName, Date date) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		int quizID = getQuizIDByName(quizName);
		String sqlUserJoinQuizzes = 
				"SELECT username FROM users " +
					"JOIN quizzes_taken ON " +
						"quiz_id = " + quizID +
						" AND " +
						"users.user_id = quizzes_taken.user_id " +
						" AND " +
						"attempt_date > " + date + " " +
				"ORDER BY score DESC";
		ResultSet rs = stmt.executeQuery(sqlUserJoinQuizzes);
		UserList highestPerformers = userFactory.getUserList();
		while (rs.next())
			highestPerformers.add(loadUser(rs.getString(USER.USERNAME.num())));
		return highestPerformers;
	}


	/* Provided with quiz name, determines which unique 
	 * id is associated with that quiz in database and returns it.
	 */
	private int getQuizIDByName(String quizName) throws SQLException{
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlQuizNameToID = 
				"SELECT quiz_id FROM quizzes " + 
				"WHERE quiz_name = " + "'" + quizName + "';";
		ResultSet rs = stmt.executeQuery(sqlQuizNameToID);
		stmt.close();
		int quizID = rs.getInt(0);
		return quizID;
	}
	

	@Override
	public void close() {
		conHandler.close();
	}

}
