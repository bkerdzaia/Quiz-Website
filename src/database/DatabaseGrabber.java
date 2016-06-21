package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
	 */
	public void connect() {
		this.conHandler = dbFactory.getDatabaseConnectionHandler();
		try{
			Statement stmt = conHandler.getConnection().createStatement();
			stmt.executeQuery("USE " + SCHEMA_NAME + " ;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Registers user inside database. If the userName already
	 * exists in database, registration will fail, returning
	 * value of 'false'; 'true' signifies successful registration.
	 * @param userName - system login of the user.
	 * @param password - secret string for proving identity.
	 * @return registration status
	 */
	public boolean registerUser(String userName, String password){
		Statement stmt = null;
		try {
			stmt = conHandler.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public User authenticateUser(String userName, String passwHash){
		User retrievedUser = null;
		return retrievedUser;
	}
	
	public void uploadQuiz(Quiz quiz) {}

/*	
	public User loadUser(String userName) {
		User user = UserFactory.getUser();
		user.setName(userName);
		user.setMadeQuiz(getPopularQuizzes());
		user.setCreatedQuiz(getRecentlyCreatedQuizzes());
		UserMessageList messages = UserFactory.getMessageList();
		messages.addMessage(new Challenge());
		user.setMessages(messages);
		user.setFriends(UserFactory.getFriendList());
		History hist = UserFactory.getHistory();
		QuizPerformance perf = new QuizPerformance();
		perf.setAmountTime(new Time(System.currentTimeMillis()));
		perf.setDate(new Date());
		perf.setPercentCorrect(20);
		perf.setQuiz(loadQuiz("quiz new"));
		hist.addQuiz(perf);
		user.setHistory(hist);
		return user;
	}
	
	public Quiz loadQuiz(String quizName) {
		Quiz quiz = QuizFactory.getQuiz();
		quiz.setName(quizName);
		quiz.setDescription("this is quiz " + quizName);
		QuizQuestions questions = QuizFactory.getQuizQuestions();
		MultipleChoice e = new MultipleChoice();
		questions.add(e);
		quiz.setQuestions(questions);
		User user = UserFactory.getUser();
		user.setName("user 1");
		quiz.setCreator(user);
		return quiz;
	}
	
	public QuizCollection getPopularQuizzes() {
		QuizCollection col = QuizFactory.getQuizCollection();
		col.add(loadQuiz("quiz 1"));
		col.add(loadQuiz("quiz 2"));
		col.add(loadQuiz("quiz 3"));
		col.add(loadQuiz("quiz 4"));
		return col;
	}
	
	public QuizCollection getRecentlyCreatedQuizzes() {
		QuizCollection col = QuizFactory.getQuizCollection();
		col.add(loadQuiz("quiz 1"));
		col.add(loadQuiz("quiz 3"));
		return col;
	}
	
	public UserList getRecentTestTakers(String quizName, Date date) {
		UserList l = new UserList();
		l.add(loadUser("user1"));
		l.add(loadUser("user2"));
		return l;
	}
	
	public UserList highestPerformers(String quizName, Date date) {
		UserList l = new UserList();
		l.add(loadUser("user1"));
		return l;
	}
*/	
	/**
	 * Frees up resources associated with current connection.
	 */
	public void close() {
		conHandler.close();
	}
}
