package database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import quiz.QuizQuestions;
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
	
	// Little tuple for sorting questions (by position)
	class Positioner implements Comparable<Positioner>{

		Question question;
		int position;

		Positioner(Question q, int p) {question = q; position = p;}

		@Override
		public int compareTo(Positioner o) {
			return this.position - o.position;
		}
	}

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
			curPerformance.setAmountTime(rs.getInt(QUIZ_TAKEN.AMOUNT_TIME.num()));
			curPerformance.setPercentCorrect(rs.getInt(QUIZ_TAKEN.PERCENT_CORRECT.num()));
			curPerformance.setQuiz(rs.getString(QUIZ_TAKEN.QUIZ_NAME.num()));
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
			createdQuizzes.add(rs.getString(1)); //there's only one column
		return createdQuizzes;
	}


	@Override
	public boolean uploadQuiz(Quiz quiz) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		// Won't upload, if the name is not unique.
		if (quizAlredyInDatabase(quiz.getName())) return false;
		// Upload quiz fields
		QuizProperty prop = quiz.getProperty();
		String creator = quiz.getCreator();
		String sqlNewQuiz = 
				"INSERT INTO quizzes " + 
				"VALUES ('" + quiz.getName() + "','" + quiz.getCreationDate() + "'," +
						 "'" + creator + "'," + prop.isRandomSeq() + "," + 
						 prop.isInstantlyMarked() + "," + prop.isOnePage() + "," +
						 "'" + quiz.getDescription() + "');";
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
						curQuestion.getQuestionText() + "'," + position + ");";
		stmt.executeUpdate(sqlAddMultChoiseQuestion);
		// Now add answers to corresponding table
		int questionId = getLastAutoIncrement(stmt);
		for (String possibleAnswer : curQuestion.getPossibleChoises()){
			String sqlAddChoise = 
					"INSERT INTO multiple_choise_answers " + 
						"VALUES(" + questionId + ",'" + possibleAnswer + "'," + 
							curQuestion.isCorrectAnswer(possibleAnswer) + ");";
			stmt.executeUpdate(sqlAddChoise);
		}
		stmt.close();
	}


	private int getLastAutoIncrement(Statement stmt) throws SQLException {
		ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID();");
		rs.next();
		return rs.getInt(1); // Only one column (id)
	}


	private void addPictureResponse(Question question, int position, String quizName) 
			throws SQLException{
		Statement stmt = conHandler.getConnection().createStatement();
		PictureResponse curQuestion = (PictureResponse) question;
		String sqlAddPictureResponseQuestion = 
				"INSERT INTO picture_response " +
					"VALUES(NULL," + "'" + quizName + "','" + curQuestion.getQuestionText() +
					"','" + curQuestion.getPictureUrl() + "'," + position + ");";
		stmt.executeUpdate(sqlAddPictureResponseQuestion);
		// Add answers
		int questionId = getLastAutoIncrement(stmt);
		for (String correctAnswer : curQuestion.getCorrectAnswers()){
			String sqlAddCorrect = 
					"INSERT INTO picture_response_correct_answers " + 
						"VALUES(" + questionId + ",'" + correctAnswer + "');";
			stmt.executeUpdate(sqlAddCorrect);
		}
		stmt.close();
	}


	private void addQuestionResponce(Question question, int position, String quizName) 
			throws SQLException{
		Statement stmt = conHandler.getConnection().createStatement();
		QuestionResponce curQuestion = (QuestionResponce) question;
		String sqlAddQuestionResponce = 
				"INSERT INTO question_response " + 
					"VALUES(NULL,'" + curQuestion.getQuestionText() + "','" +
						quizName + "'," + position + ");";
		stmt.executeUpdate(sqlAddQuestionResponce);
		// Populate answers
		int questionId = getLastAutoIncrement(stmt);
		for (String correctAnswer : curQuestion.getCorrectAnswers()){
			String sqlAddCorrect = 
					"INSERT INTO question_response_correct_answers " + 
						"VALUES(" + questionId + ",'" + correctAnswer + "');"; 
			stmt.executeUpdate(sqlAddCorrect);
		}
		stmt.close();
	}


	private void addFillBlank(Question question, int position, String quizName) 
			throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		FillBlank curQuestion = (FillBlank) question;
		String sqlAddFillBlankQuestion = 
				"INSERT INTO fill_in_blank " +
					"VALUES(NULL,'" +  curQuestion.getQuestionText() + "','" + quizName + 
					"'," + curQuestion.getFieldPositionIndex() + "," + position + ");";
		stmt.executeUpdate(sqlAddFillBlankQuestion);
		// Move on to answers
		int questionId = getLastAutoIncrement(stmt);
		for (String correctAnswer : curQuestion.getCorrectAnswers()){
			String sqlAddCorrect = 
						"INSERT INTO fill_in_blank_correct_answers " + 
							"VALUES (" + questionId + ",'" + correctAnswer + "');";
			stmt.executeUpdate(sqlAddCorrect);
		}
		stmt.close();
	}


	// Checks if quiz with provided name already exists in database.
	private boolean quizAlredyInDatabase(String quizName) 
			throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlQuizWithSameName = 
				"SELECT * FROM quizzes " +
				"WHERE quiz_name = " + "'" + quizName + "';";
		return (stmt.executeQuery(sqlQuizWithSameName).next());
	}


	// Return quiz from database with provided name.
	@Override
	public Quiz loadQuiz(String quizName) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlGetQuiz =
				"SELECT * FROM quizzes " +
				"WHERE quiz_name = " + "'" + quizName + "';";
		ResultSet rs = stmt.executeQuery(sqlGetQuiz);
		// If doesn't exist, return null
		if (!rs.next())
			return null;
		Quiz retrievedQuiz = quizFactory.getQuiz();
		// Fill quiz bean
		retrievedQuiz.setName(rs.getString(QUIZ.QUIZ_NAME.num()));
		retrievedQuiz.setDescription(rs.getString(QUIZ.QUIZ_DESCRIPTION.num()));
		retrievedQuiz.setCreationDate(rs.getTimestamp(QUIZ.DATE_CREATION.num()));
		retrievedQuiz.setCreator(rs.getString(QUIZ.CREATOR_NAME.num()));
		// Fill property bean
		QuizProperty prop = quizFactory.getQuizProperty();
		prop.setInstantCorrection(rs.getBoolean(QUIZ.INSTANT_CORRECTION.num()));
		prop.setRandomQuestion(rs.getBoolean(QUIZ.RANDOM_ORDER.num()));
		prop.setOnePage(rs.getBoolean(QUIZ.ONE_MULTIPLE_PAGE_MODE.num()));
		retrievedQuiz.setProperty(prop);
		// Collect questions
		ArrayList<Positioner> positioner = 
				new ArrayList<Positioner>(); // inner class for sorting
		collectQuestionResponse(quizName, positioner);
		collectFillBlank(quizName, positioner);
		collectMultipleChoise(quizName, positioner);
		collectPictureResponse(quizName, positioner);
		// Add question to retrivedQuiz
		Collections.sort(positioner);  // Sort questions to appear in order
		QuizQuestions questions = quizFactory.getQuizQuestions();
		for (Positioner p : positioner)
			questions.add(p.question);
		retrievedQuiz.setQuestions(questions);
		return retrievedQuiz;
	}


	private void collectPictureResponse(String quizName, ArrayList<Positioner> positioner) 
			throws SQLException {
		ResultSet rs = getQuestionsOfQuiz("picture_response", quizName);
		while (rs.next()){
			// Create new question and fill
			PictureResponse curQuestion = questionFactory.getPictureResponseQuestion();
			curQuestion.setQuestionText(rs.getString(PICTURE_RESPONSE.QUESTION.num()));
			curQuestion.setPictureUrl(rs.getString(PICTURE_RESPONSE.IMAGE_URL.num()));
			// Add answers
			int id = rs.getInt(PICTURE_RESPONSE.PROBLEM_ID.num());
			curQuestion.setCorrectAnswers(
					collectAnswers("picture_response_correct_answers", id));
			positioner.add(new Positioner(curQuestion, 
					rs.getInt(PICTURE_RESPONSE.REL_POSITION.num())));
		}

	}


	private void collectMultipleChoise(String quizName, ArrayList<Positioner> positioner) 
			throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		ResultSet rs = getQuestionsOfQuiz("multiple_choise", quizName);
		while (rs.next()){
			// Create new question, filling corresponding fields
			MultipleChoise curQuestion = questionFactory.getMultipleChoiseQuestion();
			curQuestion.setQuestionText(rs.getString(MULTIPLE_CHOISE.QUESTION.num()));
			int id = rs.getInt(MULTIPLE_CHOISE.PROBLEM_ID.num());
			String sqlPossibleChoises = 
					"SELECT answer, is_correct FROM multiple_choise_answers " + 
					"WHERE problem_id = " + id + ";";
			ResultSet choisesRS = stmt.executeQuery(sqlPossibleChoises);
			ArrayList<String> toStore = new ArrayList<String>();
			int correctIndex = -1;
			while (choisesRS.next()){
				toStore.add(choisesRS.getString(1)); // answer column
				if (choisesRS.getBoolean(2)) correctIndex = choisesRS.getRow() - 1; 
			}
			// Add the evaluated data about possible choises and correct one
			curQuestion.setPossibleChoises(toStore);
			curQuestion.setCorrectAnswerIndex(correctIndex);
			positioner.add(new Positioner(curQuestion, 
					rs.getInt(MULTIPLE_CHOISE.REL_POSITION.num())));
		}
	}


	private void collectFillBlank(String quizName, ArrayList<Positioner> positioner) 
			throws SQLException{
		ResultSet rs = getQuestionsOfQuiz("fill_in_blank", quizName);
		while (rs.next()){
			// Create new question and fill the bean
			FillBlank curQuestion = questionFactory.getFillBlankQuestion();
			curQuestion.setQuestionText(rs.getString(FILL_BLANK.QUESTION.num()));
			// Add answers
			int id = rs.getInt(FILL_BLANK.PROBLEM_ID.num());
			curQuestion.setCorrectAnswers(
					collectAnswers("fill_in_blank_correct_answers", id));
			positioner.add(new Positioner(curQuestion, 
					rs.getInt(FILL_BLANK.REL_POSITION.num())));
		}
	}


	private void collectQuestionResponse(String quizName, ArrayList<Positioner> positioner) 
			throws SQLException {
		ResultSet rs = getQuestionsOfQuiz("question_response", quizName);
		while (rs.next()){
			// Get new Question-response object and start filling
			QuestionResponce curQuestion = questionFactory.getQuestionResponceQuestion();
			curQuestion.setQuestionText(rs.getString(QUESTION_RESPONSE.QUESTION.num()));
			// Add answers
			int id = rs.getInt(QUESTION_RESPONSE.PROBLEM_ID.num());
			curQuestion.setCorrectAnswers(
				collectAnswers("question_response_correct_answers", id));
			positioner.add(new Positioner(curQuestion, 
					rs.getInt(QUESTION_RESPONSE.REL_POSITION.num())));
		}
	}

	private ResultSet getQuestionsOfQuiz(String table, String quizName) 
			throws SQLException{
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlRetrieveQuestionResponse = 
				"SELECT * FROM " + table + " " + 
				"WHERE quiz_name = '" + quizName + "' " + 
				"ORDER BY rel_position ;";
		return stmt.executeQuery(sqlRetrieveQuestionResponse);	
	}

	private Set<String> collectAnswers(String table, int id) 
			throws SQLException{
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlAnswers = 
				"SELECT correct_answer FROM " + table + " " + 
				"WHERE problem_id = " + id + ";";
		ResultSet rs = stmt.executeQuery(sqlAnswers);
		Set<String> answers = new HashSet<String>();
		while (rs.next()){
			answers.add(rs.getString(1)); // correct_answer column
		}
		stmt.close();
		return answers;
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
			popularQuizzes.add(rs.getString(1)); // name column
		return popularQuizzes;
	}

	// Returns list of recently created quizzes
	@Override
	public QuizCollection getRecentlyCreatedQuizzes() throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlRecentCreatedQuizzes = 
				"SELECT quiz_name FROM quizzes " +
				"SORT BY creation_date DESC " + 
				"LIMIT " + MAX_RECENTRY_CREATED_QUIZZES + ";";
		ResultSet rs = stmt.executeQuery(sqlRecentCreatedQuizzes);
		QuizCollection recentQuizzes = quizFactory.getQuizCollection();
		while (rs.next())
			recentQuizzes.add(rs.getString(1)); // quizName column
		return recentQuizzes;
	}

	// Returns list of recent quiz takers
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
				"ORDER BY attempt_date DESC " + 
				"LIMIT " + MAX_RECENT_TAKERS + ";";
		ResultSet rs = stmt.executeQuery(sqlUserJoinQuizzes);
		UserList recentTakers = userFactory.getUserList();
		while (rs.next())
			recentTakers.add(rs.getString(1)); // username column
		stmt.close();
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
				"ORDER BY score DESC " + 
				"LIMIT " + MAX_HIGHEST_PERFORMERS + ";";
		ResultSet rs = stmt.executeQuery(sqlUserJoinQuizzes);
		UserList highestPerformers = userFactory.getUserList();
		while (rs.next())
			highestPerformers.add(rs.getString(1)); // username column
		stmt.close();
		return highestPerformers;
	}


	@Override
	public void close() {
		conHandler.close();
	}

}
