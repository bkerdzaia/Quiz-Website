package database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import questions.*;
import application.UIParameters;
import factory.DatabaseFactory;
import factory.QuestionFactory;
import factory.QuizFactory;
import factory.UserFactory;
import quiz.History;
import quiz.Quiz;
import quiz.QuizCollection;
import quiz.QuizPerformance;
import quiz.QuizProperty;
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
				"SELECT username "
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
				getCreatedQuizzesByUserName(rs.getString(USER.USERNAME.num())));
		// Now, history of performance
		History userHistory = fillHistoryByUserName(rs.getString(USER.USERNAME.num()));
		retrievedUser.setHistory(userHistory);
		return retrievedUser;
	}


	/* Given user id, constructs record of user's performance,
	 * and hands back corresponding 'history' object. */
	private History fillHistoryByUserName(String userName) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		History userHistory = userFactory.getHistory();
		String sqlTakenQuizzes = 
				"SELECT * FROM quizzes_taken " +
				"WHERE username = " + "'" + userName + "' " +
				"ORDER BY attempt_date DESC " +
				"LIMIT " + MAX_HISTORY_ENTRIES + ";";
		ResultSet rs = stmt.executeQuery(sqlTakenQuizzes);
		// Iterate over result set and collect performance objects.
		while (rs.next()){
			// Create new performance object
			QuizPerformance curPerformance = quizFactory.getQuizPerformance();
			// And fill with corresponding values
			curPerformance.setDate(rs.getDate(QUIZ_TAKEN.ATTEMPT_DATE.num()));
			curPerformance.setAmountTime(rs.getTime(QUIZ_TAKEN.AMOUNT_TIME.num()));
			curPerformance.setPercentCorrect(rs.getInt(QUIZ_TAKEN.PERCENT_CORRECT.num()));
			curPerformance.setQuiz(loadQuiz(rs.getString(QUIZ_TAKEN.QUIZ_NAME.num())));
			// Add performance entry to history
			userHistory.add(curPerformance);
		}
		return userHistory;
	}


	// Returns collection of user's recently created quizzes
	private QuizCollection getCreatedQuizzesByUserName(String userName) 
			throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlQuizNameByUserName = 
				"SELECT quiz_name FROM quizzes " +
				"WHERE quiz_creator = " + "'" + userName + "' " + 
				"ORDER BY creation_date " + 
				"LIMIT " + MAX_RECENTLY_CREATED_BY_USER + " ;"; 
		ResultSet rs = stmt.executeQuery(sqlQuizNameByUserName);
		// Acquire new QuizCollection and start filling
		QuizCollection createdQuizzes = quizFactory.getQuizCollection();
		while (rs.next())
			createdQuizzes.add(loadQuiz(rs.getString(1))); //there's only one column
		return createdQuizzes;
	}


	@Override
	public boolean uploadQuiz(Quiz quiz) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		// Won't upload, if the name is not unique.
		if (quizAlredyInDatabase(quiz.getName())) return false;
		// Upload quiz fields
		QuizProperty prop = quiz.getProperty();
		String creator = quiz.getCreator().getName();
		String sqlNewQuiz = 
				"INSERT INTO quizzes " + 
				"VALUES ('" + quiz.getName() + "'," + quiz.getCreationDate() + "," +
						 "'" + creator + "'," + prop.isRandomSeq() + "," + 
						 prop.isInstantlyMarked() + "," + prop.isOnePage() + "," +
						 "'" + quiz.getDescription() + "'";
		stmt.executeUpdate(sqlNewQuiz);
		// Now populate question tables
		int position = 0; // to keep track of question's position in quiz
		String quizName = quiz.getName();
		for (Question question : quiz.getQuestions()){
			if (question instanceof FillBlank)
				addFillBlank(question, position, quizName);
			else if (question instanceof QuestionResponce)
				addQuestionResponce(question, position, quizName);
			else if (question instanceof PictureResponse)
				addPictureResponse(question, position, quizName);
			else 
				addMultipleChoise(question, position, quizName);
			position += 1;
		}
		stmt.close();
		return true;
	}


	private void addMultipleChoise(Question question, int position, String quizName) 
			throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		MultipleChoise curQuestion = (MultipleChoise) question;
		String sqlAddMultChoiseQuestion = 
				"INSERT INTO multiple_choise " + 
					"VALUES(NULL," + "'" + quizName + "','" + 
						curQuestion.getQuestionText() + "'," + position + ";";
		stmt.executeUpdate(sqlAddMultChoiseQuestion);
		// Now add answers to corresponding table
		int questionId = getLastAutoIncrement();
		for (String possibleAnswer : curQuestion.getPossibleChoises()){
			String sqlAddChoise = 
					"INSERT INTO multiple_choise_answers " + 
						"VALUES(" + questionId + ",'" + possibleAnswer + "'," + 
							curQuestion.isCorrectAnswer(possibleAnswer);
			stmt.executeUpdate(sqlAddChoise);
		}
		stmt.close();
	}


	private int getLastAutoIncrement() throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		return stmt.executeQuery("SELECT LAST_INSERT_ID();").getInt(1);
	}


	private void addPictureResponse(Question question, int position, String quizName) 
			throws SQLException{
		Statement stmt = conHandler.getConnection().createStatement();
		PictureResponse curQuestion = (PictureResponse) question;
		String sqlAddPictureResponseQuestion = 
				"INSERT INTO picture_response " +
					"VALUES(NULL," + "'" + quizName + "','" + curQuestion.getQuestionText() +
					"','" + curQuestion.getPictureUrl() + "," + position;
		stmt.executeUpdate(sqlAddPictureResponseQuestion);
		// Add answers
		int questionId = getLastAutoIncrement();
		for (String correctAnswer : curQuestion.getCorrectAnswers()){
			String sqlAddCorrect = 
					"INSERT INTO picture_response_correct_answers " + 
						"VALUES(" + questionId + ",'" + correctAnswer + "';";
			stmt.executeUpdate(sqlAddCorrect);
		}
		stmt.close();
	}


	private void addQuestionResponce(Question question, int position, String quizName) throws SQLException{
		// TODO Auto-generated method stub
		
	}


	private void addFillBlank(Question question, int position, String quizName) 
			throws SQLException {
		// TODO Auto-generated method stub
		
	}


	// Checks if quiz with provided name already exists in database.
	private boolean quizAlredyInDatabase(String quizName) 
			throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlQuizWithSameName = 
				"SELECT * FROM quizzes " +
				"WHERE quiz_name = " + "'" + quizName + "';";
		return (!stmt.executeQuery(sqlQuizWithSameName).next());
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
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlQuizJoinTaken = 
				"SELECT quiz_name, COUNT(*) AS popularity FROM quizzes " +
					"JOIN quiz_taken " +
						"ON quiz_taken.quiz_id = quizzes.quiz_id " + 
				"GROUP_BY quizzes.quiz_id " + 
				"ORDER BY popularity DESC " +
				"LIMIT " + MAX_POPULAR_QUIZZES + ";";
		ResultSet rs = stmt.executeQuery(sqlQuizJoinTaken);
		// Create quiz collection and starting filling
		QuizCollection popularQuizzes = quizFactory.getQuizCollection();
		while (rs.next())
			popularQuizzes.add(loadQuiz(rs.getString(1))); // name column
		return popularQuizzes;
	}


	@Override
	public QuizCollection getRecentlyCreatedQuizzes() throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlRecentCreatedQuizzes = 
				"SELECT * FROM quizzes " +
				"SORT BY creation_date DESC " + 
				"LIMIT " + MAX_RECENTRY_CREATED_QUIZZES + ";";
		ResultSet rs = stmt.executeQuery(sqlRecentCreatedQuizzes);
		QuizCollection recentQuizzes = quizFactory.getQuizCollection();
		while (rs.next())
			recentQuizzes.add(loadQuiz(rs.getString(QUIZ.QUIZ_NAME.num())));
		return recentQuizzes;
	}


	@Override
	public UserList getRecentTestTakers(String quizName, Date date) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlUserJoinQuizzes = 
				"SELECT username FROM users " + 
					"JOIN quizzes_taken ON " + 
						"quiz_name = " + quizName +
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
		String sqlUserJoinQuizzes = 
				"SELECT users.username FROM users " +
					"JOIN quizzes_taken ON " +
						"quiz_name = " + quizName +
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


	@Override
	public void close() {
		conHandler.close();
	}

}
