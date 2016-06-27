package tests.servlet_tests;
import quiz.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import database.DatabaseGrabber;
import factory.*;

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
	public User authenticateUser(String userName, String passwHash) throws SQLException {
		User sample = new User();
		sample.setName("John");
		sample.setAboutMe("Nice person");
		return null;
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
	public QuizCollection getCreatedQuizzesByUserName(String userName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeAttempt(QuizPerformance perf) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
