package application;

import java.util.Date;

import quiz.Quiz;
import quiz.QuizCollection;
import quiz.User;
import quiz.UserList;

public class Database {

	public Database() {
		// TODO Auto-generated constructor stub
	}
	
	public void connect() {
		
	}
	
	public void uploadUser(User user) {
		
	}
	
	public User loadUser(String userName) {
		return null;
	}
	
	public void uploadQuiz(Quiz quiz) {
		
	}
	
	public boolean findUser(String userName, String password) {
		return true;
	}
	
	public Quiz loadQuiz(String quizName) {
		return null;
	}
	
	public QuizCollection getPopularQuizzes() {
		return null;
	}
	
	public QuizCollection getRecentlyCreatedQuizzes() {
		return null;
	}
	
	public UserList getRecentTestTakers(String quizName, Date date) {
		return null;
	}
	
	public UserList highestPerformers(String quizName, Date date) {
		return null;
	}
	
	public void close() {
		
	}
	
}
