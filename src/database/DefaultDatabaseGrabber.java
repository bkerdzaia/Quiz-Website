package database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import questions.*;
import application.UIParameters;
import factory.DatabaseFactory;
import factory.QuestionFactory;
import factory.QuizFactory;
import factory.UserFactory;
import quiz.Challenge;
import quiz.FriendList;
import quiz.FriendRequest;
import quiz.History;
import quiz.Performance;
import quiz.Quiz;
import quiz.QuizCollection;
import quiz.QuizHistory;
import quiz.UsersPerformance;
import quiz.PerformanceOnQuiz;
import quiz.QuizProperty;
import quiz.QuizQuestions;
import quiz.TextMessage;
import quiz.User;
import quiz.UserList;
import quiz.UserMessageList;

/**
 * @author dav23r Default implementation of DatabaseGrabber interface. Should be
 *         used to interact with database in production code.
 */
public class DefaultDatabaseGrabber implements DatabaseGrabber, 
						DatabaseParameters, UIParameters {

	// Factory is used to acquire connection handler.
	private DatabaseFactory dbFactory = null;
	private UserFactory userFactory = null;
	private QuizFactory quizFactory = null;
	private QuestionFactory questionFactory = null;
	private DatabaseConnectionHandler conHandler = null;

	// Little tuple for sorting questions (by position)
	class Positioner implements Comparable<Positioner> {

		Question question;
		int position;

		Positioner(Question q, int p) {
			question = q;
			position = p;
		}

		@Override
		public int compareTo(Positioner o) {
			return this.position - o.position;
		}
	}

	/*
	 * Following to are paths to database scripts (initialize and drop).
	 * 'protected' to make possible for subclasses to override.
	 */
	protected String truncateScriptPath = new File("").getAbsolutePath() + "/src/db_scripts/"
			+ DatabaseParameters.DB_TRUNCATE_SCRIPT;

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
				"SELECT username " + "FROM users " + 
				"WHERE username = " + "'" + userName + "'" + ";";
		ResultSet rs = stmt.executeQuery(sqlNumberEntriesSameName);
		// If database already contains entry with same name terminate.
		if (rs.next()) {
			stmt.close();
			return false;
		}
		// Otherwise add new user row to database
		String sqlAddNewUser = 
				"INSERT INTO users (username, passw_hash) " + 
				"VALUES " + "(" + "'" + userName + "'" + 
				"," + " " + "'" + passwHash + "'" + ");";

		stmt.executeUpdate(sqlAddNewUser);
		stmt.close();
		return true;
	}

	// Authenticate user based on login and hash of password
	@Override
	public boolean authenticateUser(String userName, String passwHash) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String queryUser = 
				"SELECT * FROM users " + 
				"WHERE username = " + "'" + userName + "';";
		ResultSet rs = stmt.executeQuery(queryUser);
		// User with provided userName doesn't exist in database
		if (!rs.next()) {
			stmt.close();
			return false;
		}
		// Otherwise check if stored hash and provided one are equal
		if (!rs.getString(USER.PASSW_HASH.num()).equals(passwHash)) {
			stmt.close();
			return false;
		}
		// If valid credentials are provided, return corresponding user.
		stmt.close();
		return true;
	}

	// Truncates all tables in database
	@Override
	public void truncateDatabase() throws IOException, SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		BufferedReader br = new BufferedReader(new FileReader(truncateScriptPath));
		String line;
		while ((line = br.readLine()) != null) {
			stmt.executeUpdate(line);
		}
		br.close();
		stmt.close();
	}

	// Returns message sent to user with provided user name.
	private UserMessageList getMessages(String userName) throws SQLException {
		UserMessageList messages = userFactory.getMessageList();
		messages.addAll(getTextMessages(userName));
		messages.addAll(getFriendRequests(userName));
		messages.addAll(getChallenges(userName));
		messages.sortByDate();
		return messages;
	}

	// Returns challenges sent to user with userName.
	private UserMessageList getChallenges(String userName) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlGetChallengesForUser = 
				"SELECT challenges.* FROM friends " +
					"JOIN challenges ON " +
						"friends.friendship_id = challenges.friendship_id " + 
							" AND (" + 
								"first_user_name = '" + userName + "'" + 
									" AND sender = 1" +
										" OR " +
								"second_user_name = '" + userName + "'" +
									"AND sender = 0)" +
				"ORDER BY sent_date;";
		ResultSet rs = stmt.executeQuery(sqlGetChallengesForUser);
		UserMessageList challenges = userFactory.getMessageList();
		// Iterate over result set and collect challenges for user
		while (rs.next()){
			Challenge curChallenge = userFactory.getChallenge();
			curChallenge.setSenderName(rs.getString(CHALLENGES.SENDER.num()));
			curChallenge.setQuizName(rs.getString(CHALLENGES.QUIZ_NAME.num()));
			curChallenge.setDate(rs.getTimestamp(CHALLENGES.SEND_DATE.num()));
			challenges.add(curChallenge);
		}
		stmt.close();
		return challenges;
	}

	// Returns friend request for user with provided userName
	private UserMessageList getFriendRequests(String userName) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlFriendRequests = 
				"SELECT initiator, sent_date FROM friend_requests " + 
				"WHERE acceptor = '" + userName + "';";
		ResultSet rs = stmt.executeQuery(sqlFriendRequests);
		UserMessageList friendRequests = userFactory.getMessageList();
		while (rs.next()) {
			FriendRequest curRequest = userFactory.getFriendRequest();
			curRequest.setSenderName(rs.getString(1)); // initiator column
			curRequest.setRecipient(userName);
			curRequest.setDate(rs.getTimestamp(2)); // date column
			friendRequests.add(curRequest);
		}
		return friendRequests;
	}

	// Return list of text message for user with provided name
	private UserMessageList getTextMessages(String userName) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlMessagesToUser = 
				"SELECT text, sent_date FROM friends " + 
					"JOIN messages ON " + 
						"friends.friendship_id = messages.friendship_id " + 
							" AND (" + 
						"first_user_name = '" + userName + "'" + 
									" AND " + "sender = 1" + 
								" OR " + 
									"second_user_name = '" + userName + "'" + 
									" AND " + 
								"sender = 0) " + 
				"ORDER BY sent_date DESC;";
		ResultSet rs = stmt.executeQuery(sqlMessagesToUser);
		UserMessageList textMessages = userFactory.getMessageList();
		while (rs.next()) {
			TextMessage curMessage = userFactory.getTextMessage();
			curMessage.setMessage(rs.getString(1)); // text column
			textMessages.add(curMessage);
		}
		stmt.close();
		return textMessages;
	}

	/*
	 * Given user name, constructs record of user's performance, and hands back
	 * corresponding 'history' object.
	 */
	private History<UsersPerformance> fillHistoryByUserName(String userName) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		History<UsersPerformance> userHistory = userFactory.getHistoryUsersPerformance();
		String sqlTakenQuizzes = 
				"SELECT * FROM quizzes_taken " + 
				"WHERE username = " + "'" + userName + "' " + 
				"ORDER BY attempt_date DESC " + 
				"LIMIT " + MAX_HISTORY_ENTRIES + ";";
		ResultSet rs = stmt.executeQuery(sqlTakenQuizzes);
		// Iterate over result set and collect performance objects.
		while (rs.next()) {
			// Create new performance object
			UsersPerformance curPerformance = quizFactory.getUsersPerformance();
			// And fill with corresponding values
			curPerformance.setDate(rs.getTimestamp(QUIZ_TAKEN.ATTEMPT_DATE.num()));
			curPerformance.setAmountTime(rs.getInt(QUIZ_TAKEN.AMOUNT_TIME.num()));
			curPerformance.setPercentCorrect(rs.getInt(QUIZ_TAKEN.PERCENT_CORRECT.num()));
			curPerformance.setQuiz(rs.getString(QUIZ_TAKEN.QUIZ_NAME.num()));
			// Add performance entry to history
			userHistory.add(curPerformance);
		}
		return userHistory;
	}

	// Returns collection of user's recently created quizzes
	public QuizCollection getCreatedQuizzesByUserName(String userName) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlQuizNameByUserName = 
				"SELECT quiz_name FROM quizzes " + 
				"WHERE quiz_creator = " + "'" + userName + "' " + 
				"ORDER BY creation_date " + "LIMIT " + MAX_RECENTLY_CREATED_BY_USER + " ;";
		ResultSet rs = stmt.executeQuery(sqlQuizNameByUserName);
		// Acquire new QuizCollection and start filling
		QuizCollection createdQuizzes = quizFactory.getQuizCollection();
		while (rs.next())
			createdQuizzes.add(rs.getString(1)); // there's only one column
		return createdQuizzes;
	}

	@Override
	public boolean uploadQuiz(Quiz quiz) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		// Won't upload, if the name is not unique.
		if (quizAlreadyInDatabase(quiz.getName()))
			return false;
		// Upload quiz fields
		QuizProperty prop = quiz.getProperty();
		String creator = quiz.getCreator();
		String sqlNewQuiz = 
				"INSERT INTO quizzes " + 
				"VALUES ('" + quiz.getName() + "','" + quiz.getCreationDate()
				+ "'," + "'" + creator + "'," + prop.isRandomSeq() + "," + 
				prop.isInstantlyMarked() + "," + prop.isOnePage() + "," + "'" + 
				quiz.getDescription() + "');";
		stmt.executeUpdate(sqlNewQuiz);
		// Now populate question tables
		int position = 0; // to keep track of question's position in quiz
		String quizName = quiz.getName();
		for (Question question : quiz.getQuestions()) {
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

	private void addMultipleChoise(Question question, int position, String quizName) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		MultipleChoise curQuestion = (MultipleChoise) question;
		String sqlAddMultChoiseQuestion = 
				"INSERT INTO multiple_choise " + 
				"VALUES(NULL," + "'" + quizName + "','"
				+ curQuestion.getQuestionText() + "'," + position + ");";
		stmt.executeUpdate(sqlAddMultChoiseQuestion);
		// Now add answers to corresponding table
		int questionId = getLastAutoIncrement(stmt);
		for (String possibleAnswer : curQuestion.getPossibleChoises()) {
			String sqlAddChoise = 
					"INSERT INTO multiple_choise_answers " + 
					"VALUES(" + questionId + ",'" + possibleAnswer + "'," + 
					curQuestion.isCorrectAnswer(possibleAnswer) + ");";
			stmt.executeUpdate(sqlAddChoise);
		}
		stmt.close();
	}

	// Returns last auto-increment id of mysql database
	private int getLastAutoIncrement(Statement stmt) throws SQLException {
		ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID();");
		rs.next();
		return rs.getInt(1); // Only one column (id)
	}

	private void addPictureResponse(Question question, int position, String quizName) 
			throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		PictureResponse curQuestion = (PictureResponse) question;
		String sqlAddPictureResponseQuestion = 
				"INSERT INTO picture_response " + 
				"VALUES(NULL," + "'" + quizName + "','" + curQuestion.getQuestionText() + 
					"','" + curQuestion.getPictureUrl() + "'," + position + ");";
		stmt.executeUpdate(sqlAddPictureResponseQuestion);
		// Add answers
		int questionId = getLastAutoIncrement(stmt);
		for (String correctAnswer : curQuestion.getCorrectAnswers()) {
			String sqlAddCorrect = 
					"INSERT INTO picture_response_correct_answers " + 
							"VALUES(" + questionId + ",'" + correctAnswer + "');";
			stmt.executeUpdate(sqlAddCorrect);
		}
		stmt.close();
	}

	private void addQuestionResponce(Question question, int position, String quizName) 
			throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		QuestionResponce curQuestion = (QuestionResponce) question;
		String sqlAddQuestionResponce = 
				"INSERT INTO question_response " + 
				"VALUES(NULL,'" + curQuestion.getQuestionText() + "','" + 
						quizName + "'," + position + ");";
		stmt.executeUpdate(sqlAddQuestionResponce);
		// Populate answers
		int questionId = getLastAutoIncrement(stmt);
		for (String correctAnswer : curQuestion.getCorrectAnswers()) {
			String sqlAddCorrect = 
					"INSERT INTO question_response_correct_answers " + 
					"VALUES(" + questionId + ",'" + correctAnswer + "');";
			stmt.executeUpdate(sqlAddCorrect);
		}
		stmt.close();
	}

	private void addFillBlank(Question question, int position, String quizName) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		FillBlank curQuestion = (FillBlank) question;
		String sqlAddFillBlankQuestion = 
				"INSERT INTO fill_in_blank " + 
				"VALUES(NULL,'" + curQuestion.getQuestionText() + "','" + quizName + 
						"'," + curQuestion.getFieldPositionIndex() + "," + position + ");";
		stmt.executeUpdate(sqlAddFillBlankQuestion);
		// Move on to answers
		int questionId = getLastAutoIncrement(stmt);
		for (String correctAnswer : curQuestion.getCorrectAnswers()) {
			String sqlAddCorrect = 
					"INSERT INTO fill_in_blank_correct_answers " + 
					"VALUES (" + questionId + ",'" + correctAnswer + "');";
			stmt.executeUpdate(sqlAddCorrect);
		}
		stmt.close();
	}

	// Checks if quiz with provided name already exists in database.
	private boolean quizAlreadyInDatabase(String quizName) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlQuizWithSameName = 
				"SELECT * FROM quizzes " + 
				"WHERE quiz_name = " + "'" + quizName + "';";
		return (stmt.executeQuery(sqlQuizWithSameName).next());
	}

	// Returns average score of users on the quiz
	private double getAverageScoreOnQuiz(String quizName) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlAverageScoreOnQuiz = 
				"SELECT AVG(percent_correct) FROM quizzes_taken " + 
				"WHERE quiz_name = " + "'" + quizName + "';";
		ResultSet rs = stmt.executeQuery(sqlAverageScoreOnQuiz);
		if (!rs.next())
			return 0;
		return rs.getDouble(1);
	}

	private void collectPictureResponse(String quizName, ArrayList<Positioner> positioner) 
			throws SQLException {
		ResultSet rs = getQuestionsOfQuiz("picture_response", quizName);
		while (rs.next()) {
			// Create new question and fill
			PictureResponse curQuestion = questionFactory.getPictureResponseQuestion();
			curQuestion.setQuestionText(rs.getString(PICTURE_RESPONSE.QUESTION.num()));
			curQuestion.setPictureUrl(rs.getString(PICTURE_RESPONSE.IMAGE_URL.num()));
			// Add answers
			int id = rs.getInt(PICTURE_RESPONSE.PROBLEM_ID.num());
			curQuestion.setCorrectAnswers(collectAnswers("picture_response_correct_answers", id));
			positioner.add(new Positioner(curQuestion, rs.getInt(PICTURE_RESPONSE.REL_POSITION.num())));
		}

	}

	private void collectMultipleChoise(String quizName, ArrayList<Positioner> positioner) 
			throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		ResultSet rs = getQuestionsOfQuiz("multiple_choise", quizName);
		while (rs.next()) {
			// Create new question, filling corresponding fields
			MultipleChoise curQuestion = questionFactory.getMultipleChoiseQuestion();
			curQuestion.setQuestionText(rs.getString(MULTIPLE_CHOISE.QUESTION.num()));
			int id = rs.getInt(MULTIPLE_CHOISE.PROBLEM_ID.num());
			String sqlPossibleChoises = "SELECT answer, is_correct FROM multiple_choise_answers "
					+ "WHERE problem_id = " + id + ";";
			ResultSet choisesRS = stmt.executeQuery(sqlPossibleChoises);
			ArrayList<String> toStore = new ArrayList<String>();
			int correctIndex = -1;
			while (choisesRS.next()) {
				toStore.add(choisesRS.getString(1)); // answer column
				if (choisesRS.getBoolean(2))
					correctIndex = choisesRS.getRow() - 1;
			}
			// Add the evaluated data about possible choises and correct one
			curQuestion.setPossibleChoises(toStore);
			curQuestion.setCorrectAnswerIndex(correctIndex);
			positioner.add(new Positioner(curQuestion, rs.getInt(MULTIPLE_CHOISE.REL_POSITION.num())));
		}
	}

	private void collectFillBlank(String quizName, ArrayList<Positioner> positioner) 
			throws SQLException {
		ResultSet rs = getQuestionsOfQuiz("fill_in_blank", quizName);
		while (rs.next()) {
			// Create new question and fill the bean
			FillBlank curQuestion = questionFactory.getFillBlankQuestion();
			curQuestion.setQuestionText(rs.getString(FILL_BLANK.QUESTION.num()));
			// Add answers
			int id = rs.getInt(FILL_BLANK.PROBLEM_ID.num());
			curQuestion.setCorrectAnswers(collectAnswers("fill_in_blank_correct_answers", id));
			positioner.add(new Positioner(curQuestion, rs.getInt(FILL_BLANK.REL_POSITION.num())));
		}
	}

	private void collectQuestionResponse(String quizName, ArrayList<Positioner> positioner) 
			throws SQLException {
		ResultSet rs = getQuestionsOfQuiz("question_response", quizName);
		while (rs.next()) {
			// Get new Question-response object and start filling
			QuestionResponce curQuestion = questionFactory.getQuestionResponceQuestion();
			curQuestion.setQuestionText(rs.getString(QUESTION_RESPONSE.QUESTION.num()));
			// Add answers
			int id = rs.getInt(QUESTION_RESPONSE.PROBLEM_ID.num());
			curQuestion.setCorrectAnswers(collectAnswers("question_response_correct_answers", id));
			positioner.add(new Positioner(curQuestion, rs.getInt(QUESTION_RESPONSE.REL_POSITION.num())));
		}
	}

	private ResultSet getQuestionsOfQuiz(String table, String quizName) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlRetrieveQuestionResponse = 
				"SELECT * FROM " + table + " " + 
				"WHERE quiz_name = '" + quizName + "' " + 
				"ORDER BY rel_position ;";
		return stmt.executeQuery(sqlRetrieveQuestionResponse);
	}

	/*
	 * Given table name and question id retrieves correct answers for that
	 * question, makes use of the fact that all the question answer
	 * tables(except MultChoise) have same 'correct_answer' column.
	 */
	private Set<String> collectAnswers(String table, int id) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlAnswers = 
				"SELECT correct_answer FROM " + table + " " + 
				"WHERE problem_id = " + id + ";";
		ResultSet rs = stmt.executeQuery(sqlAnswers);
		Set<String> answers = new HashSet<String>();
		while (rs.next()) {
			answers.add(rs.getString(1)); // correct_answer column
		}
		stmt.close();
		return answers;
	}

	// Returns list of recent quiz takers
	@Override
	public QuizHistory getRecentTakersStats(String quizName, Timestamp date) 
			throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlRecentStats = 
				"SELECT * FROM quizzes_taken " + 
				"WHERE attempt_date > '" + date + "' " + 
					" AND quiz_name = '" + quizName + "' " + 
				"ORDER BY attempt_date DESC " + "LIMIT "
				+ MAX_RECENT_TAKERS_STATS + ";";
		ResultSet rs = stmt.executeQuery(sqlRecentStats);
		QuizHistory recentStats = userFactory.getHistoryPerformanceOnQuiz();
		while (rs.next()) {
			PerformanceOnQuiz curPerf = quizFactory.getPerformanceOnQuiz();
			curPerf.setDate(rs.getTimestamp(QUIZ_TAKEN.ATTEMPT_DATE.num()));
			curPerf.setAmountTime(rs.getInt(QUIZ_TAKEN.AMOUNT_TIME.num()));
			curPerf.setPercentCorrect(rs.getDouble(QUIZ_TAKEN.PERCENT_CORRECT.num()));
			curPerf.setUser(rs.getString(QUIZ_TAKEN.USER_NAME.num()));
			recentStats.add(curPerf);
		}
		stmt.close();
		return recentStats;
	}

	// Returns list of highest performer user for particular quiz, starting from
	// given date
	@Override
	public UserList getHighestPerformers(String quizName, Timestamp date) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlHighestPerformers = 
				"SELECT username FROM quizzes_taken " + 
				"WHERE quiz_name = '" + quizName + "' " + 
						"AND attempt_date > '" + date + "' " + 
				"ORDER BY percent_correct DESC " + 
				"LIMIT " + MAX_HIGHEST_PERFORMERS + ";";
		ResultSet rs = stmt.executeQuery(sqlHighestPerformers);
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

	// Alter quiz with same name in database, with the content of alteredQuiz
	@Override
	public boolean editQuiz(Quiz alteredQuiz) throws SQLException {
		// If there is no such quiz, it can not be deleted
		if (!deleteQuiz(alteredQuiz.getName()))
			return false;
		uploadQuiz(alteredQuiz);
		return true;
	}

	// Delete quiz with provided name from database
	@Override
	public boolean deleteQuiz(String quizName) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		// If there's no such quiz, termninate and hand back 'false'
		if (!quizAlreadyInDatabase(quizName))
			return false;
		// Otherwise delete it from table
		String sqlQuizDelete = "DELETE FROM quizzes WHERE " + 
									"quiz_name = " + "'" + quizName + "';";
		stmt.executeUpdate(sqlQuizDelete);
		return true;
	}

	// Stores single attempt in database
	@Override
	public void storeAttempt(Performance perf, String userName, String quizName) 
			throws SQLException {
		// Extract field values
		Timestamp date = perf.getDate();
		int timeTaken = perf.getAmountTime();
		double percentCorrect = perf.getPercentCorrect();
		// Pass the values to sql update query
		Statement stmt = conHandler.getConnection().createStatement();
		String addPerformanceRecord = 
				"INSERT INTO quizzes_taken " + 
				"VALUES ('" + quizName + "',NULL,'" + userName
				+ "'," + percentCorrect + ",'" + date + "'," + timeTaken + ");";
		stmt.executeUpdate(addPerformanceRecord);
		stmt.close();
	}

	// Adds friend request record to database
	@Override
	public boolean addFriendRequest(String from, String to, Timestamp date) 
			throws SQLException {
		if (from.equals(to)) // are you kidding?
			return false;
		if (getFriendshipId(from, to) != -1) // stop man, they are friends
												// already
			return false;
		if (friendshipRequested(from, to)) // maybe you are not created for each
											// other
			return false;
		// 'to' already requested friendship with 'from'
		if (friendshipRequested(to, from)) {
			acceptFriendRequest(from, to);
			return true;
		}
		// Add request to 'friend_requests' table
		Statement stmt = conHandler.getConnection().createStatement();
		String addFriendRequestMessage = 
				"INSERT INTO friend_requests " + 
				"VALUES('" + from + "','" + to + "','" + date + "');";
		stmt.executeUpdate(addFriendRequestMessage);
		return true;
	}

	// Check if 'from' already requested friendship with 'to'
	private boolean friendshipRequested(String from, String to) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlIsFriendshipRequested = 
				"SELECT * FROM friend_requests " + 
				"WHERE initiator = '" + from + "' " + 
						"AND " + "acceptor = '" + to + "';";
		ResultSet rs = stmt.executeQuery(sqlIsFriendshipRequested);
		boolean isFriendshipRequest = rs.next();
		stmt.close();
		return isFriendshipRequest;
	}

	@Override
	public boolean acceptFriendRequest(String acceptor, String from) throws SQLException {
		if (getFriendshipId(acceptor, from) != -1) // they are friends already
			return false;
		if (!friendshipRequested(from, acceptor)) // man what are you accepting?
			return false;
		deleteFriendRequest(from, acceptor);
		// Make first argument the lexicographically smaller one
		if (acceptor.compareTo(from) > 0) {
			String temp = acceptor;
			acceptor = from;
			from = temp;
		}
		// Add friendship record to database
		Statement stmt = conHandler.getConnection().createStatement();
		// Make first argument lexicographically smaller one
		if (acceptor.compareTo(from) > 0) {
			String temp = acceptor;
			acceptor = from;
			from = temp;
		}
		String sqlAcceptRequest = 
				"INSERT INTO friends VALUES('" + acceptor + "','" + from + "', NULL);";
		stmt.executeUpdate(sqlAcceptRequest);
		stmt.close();
		return true;
	}

	// Deletes friend request coming from 'from' to 'acceptor'.
	private void deleteFriendRequest(String from, String acceptor) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlDeleteFriendRequest = 
				"DELETE FROM friend_requests " + 
				"WHERE initiator = '" + from + "' " + 
						"AND " + 
					   "acceptor = '" + acceptor + "';";
		stmt.executeUpdate(sqlDeleteFriendRequest);
		stmt.close();
	}

	// Retrieves friends of user with provided name
	private FriendList getFriends(String userName) throws SQLException {
		FriendList friends = userFactory.getFriendList();
		friends.addAll(getFriendsFromColumn(userName, false));
		friends.addAll(getFriendsFromColumn(userName, true));
		return friends;
	}

	// Helper method for getting friends, is called twice one with 'swapColumns'
	private FriendList getFriendsFromColumn(String userName, boolean swapColumns) 
			throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String firstColumn = "first_user_name", secondColumn = "second_user_name";
		if (swapColumns) {
			String temp = firstColumn;
			firstColumn = secondColumn;
			secondColumn = temp;
		}
		String sqlRetrieveFriends = 
				"SELECT " + firstColumn + " FROM friends " + 
				"WHERE " + secondColumn + "='" + userName + "';";
		ResultSet rs = stmt.executeQuery(sqlRetrieveFriends);
		FriendList friends = userFactory.getFriendList();
		while (rs.next())
			friends.add(rs.getString(1)); // friend name column
		stmt.close();
		return friends;
	}

	// Sends message from -> to (users are already friends)
	@Override
	public boolean sendMessage(String from, String to, String message, Timestamp date) 
			throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		// unique id of frienship in database
		int friendshipId = getFriendshipId(from, to);
		if (friendshipId == -1)
			return false;
		// in friends table first username is the lexicographically smaller one
		int directionBit = (from.compareTo(to) > 0) ? 1 : 0;
		String sqlSendMessage = 
				"INSERT INTO messages VALUES(NULL," + friendshipId + ",'" + message + "','" + 
						date + "'," + directionBit + ");";
		stmt.executeUpdate(sqlSendMessage);
		stmt.close();
		return true;
	}

	/*
	 * Returns friendship id for two provided user name, or -1 if provided users
	 * are not friends
	 */
	private int getFriendshipId(String firstUser, String secondUser) throws SQLException {
		// Make first argument lexicographically smaller one
		if (firstUser.compareTo(secondUser) > 0) {
			String temp = firstUser;
			firstUser = secondUser;
			secondUser = temp;
		}
		// Retrieve id from 'friends' tables
		Statement stmt = conHandler.getConnection().createStatement();
		String retrieveFriendshipId = 
				"SELECT friendship_id FROM friends " + 
				"WHERE first_user_name ='" + firstUser + 
					"' AND second_user_name = '" + secondUser + "';";
		ResultSet rs = stmt.executeQuery(retrieveFriendshipId);
		if (!rs.next())
			return -1;
		return rs.getInt(1); // friendship_id column
	}

	// Removes friend realation between users from database
	@Override
	public boolean removeFriend(String firstUser, String secondUser) throws SQLException {
		int friendshipId = getFriendshipId(firstUser, secondUser);
		if (friendshipId == -1) // I mean you are not even friends
			return false;
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlRemoveFriend = 
				"DELETE FROM friends " + 
				"WHERE friendship_id = " + friendshipId + ";";
		stmt.executeUpdate(sqlRemoveFriend);
		stmt.close();
		return true;
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
		// Fill more complex fields, first list of created quizzes
		retrievedUser.setCreatedQuizzes(getCreatedQuizzesByUserName(
				rs.getString(USER.USERNAME.num())));
		// Now, history of performance
		History<UsersPerformance> userHistory = 
				fillHistoryByUserName(rs.getString(USER.USERNAME.num()));
		retrievedUser.setHistory(userHistory);
		// Set friends and messages
		retrievedUser.setMessages(getMessages(userName));
		retrievedUser.setFriends(getFriends(userName));
		stmt.close();
		return retrievedUser;
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
		retrievedQuiz.setSummaryStatistics(getAverageScoreOnQuiz(quizName));
		// Fill property bean
		QuizProperty prop = quizFactory.getQuizProperty();
		prop.setInstantCorrection(rs.getBoolean(QUIZ.INSTANT_CORRECTION.num()));
		prop.setRandomQuestion(rs.getBoolean(QUIZ.RANDOM_ORDER.num()));
		prop.setOnePage(rs.getBoolean(QUIZ.ONE_MULTIPLE_PAGE_MODE.num()));
		retrievedQuiz.setProperty(prop);
		// Collect questions
		ArrayList<Positioner> positioner = new ArrayList<Positioner>(); // inner  class  for  sorting
		collectQuestionResponse(quizName, positioner);
		collectFillBlank(quizName, positioner);
		collectMultipleChoise(quizName, positioner);
		collectPictureResponse(quizName, positioner);
		// Add question to retrivedQuiz
		Collections.sort(positioner); // Sort questions to appear in order
		QuizQuestions questions = quizFactory.getQuizQuestions();
		for (Positioner p : positioner)
			questions.add(p.question);
		retrievedQuiz.setQuestions(questions);
		return retrievedQuiz;
	}

	// Hands back list of names of popular quizzes
	@Override
	public QuizCollection getPopularQuizzes() throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlQuizJoinTaken = 
				"SELECT quizzes.quiz_name, COUNT(*) AS popularity FROM quizzes " + 
					"JOIN quizzes_taken " + 
						"ON quizzes_taken.quiz_name = quizzes.quiz_name " +
				"GROUP BY quizzes.quiz_name " + 
				"ORDER BY popularity DESC " + "LIMIT " + MAX_POPULAR_QUIZZES + ";";
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
				"ORDER BY creation_date DESC " + "LIMIT "
				+ MAX_RECENTRY_CREATED_QUIZZES + ";";
		ResultSet rs = stmt.executeQuery(sqlRecentCreatedQuizzes);
		QuizCollection recentQuizzes = quizFactory.getQuizCollection();
		while (rs.next())
			recentQuizzes.add(rs.getString(1)); // quizName column
		return recentQuizzes;
	}

	// Store challenge message in database
	@Override
	public boolean sendChallenge(String from, String to, String quizName, Timestamp date) 
			throws SQLException {
		int friendshipId = getFriendshipId(from, to);
		if (friendshipId == -1)
			return false;
		int directionBit = (from.compareTo(to) < 0) ? 0 : 1; // if from < to then 0, else 1
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlAddChalenge = 
				"INSERT INTO challenges VALUES(" + 
				"NULL," + friendshipId + ",'" + date + "','" + 
						quizName + "'," + directionBit + ");";
		stmt.executeUpdate(sqlAddChalenge);
		stmt.close();
		return true;
	}

	// Searches for users with names similar to searchTerm
	@Override
	public UserList searchUsers(String searchTerm) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String sqlSimilarNames = 
				"SELECT username FROM users " + 
				"WHERE username LIKE '%" + searchTerm + "%'" + 
				"LIMIT " + MAX_USERS_SUGGESTIONS + ";";
		ResultSet rs = stmt.executeQuery(sqlSimilarNames);
		UserList similarUserNames = userFactory.getUserList();
		while (rs.next())
			similarUserNames.add(rs.getString(1)); // username column
		stmt.close();
		return similarUserNames;
	}

	// Edits parameters of user having particular user name
	@Override
	public boolean editUser(User changedUser) throws SQLException {
		Statement stmt = conHandler.getConnection().createStatement();
		String userName = changedUser.getName();
		String queryUser = 
				"SELECT * FROM users " + 
				"WHERE username = " + "'" + userName + "';";
		ResultSet rs = stmt.executeQuery(queryUser);
		if (!rs.next())
			return false; // user is not contained in database
		String sqlUpdateUserInfo =
				"UPDATE users SET profile_pic_url = '" + changedUser.getPictureUrl() + "'," +
						"description = '" + changedUser.getAboutMe() + "';";
		stmt.executeUpdate(sqlUpdateUserInfo);
		return true;
	}

}
