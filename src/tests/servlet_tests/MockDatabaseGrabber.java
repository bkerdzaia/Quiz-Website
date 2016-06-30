package tests.servlet_tests;
import quiz.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import database.DatabaseGrabber;

public class MockDatabaseGrabber implements DatabaseGrabber {

	@Override
	public void connect() throws SQLException {
		System.out.println("Ok");
	}

	@Override
	public boolean registerUser(String userName, String password) throws SQLException {
		return true;
	}

	@Override
	public boolean authenticateUser(String userName, String passwHash) throws SQLException {
		User sample = new User();
		sample.setName("John");
		sample.setAboutMe("Nice person");
		return true;
	}

	@Override
	public void truncateDatabase() throws FileNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub

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
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean editQuiz(Quiz quiz) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteQuiz(String quizName) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void storeAttempt(QuizPerformance perf) throws SQLException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public UserList getHighestPerformers(String quizName, Timestamp date) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addFriendRequest(String from, String to) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean acceptFriendRequest(String firstUser, String secondUser) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public History getRecentTakersStats(String quizName, Timestamp date) 
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean sendMessage(String from, String to, String message, Timestamp date) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeFriend(String firstUser, String secondUser) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
