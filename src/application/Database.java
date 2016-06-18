package application;

import java.sql.Time;
import java.util.Date;

import factory.QuizFactory;
import factory.UserFactory;
import questions.MultipleChoice;
import quiz.Challenge;
import quiz.History;
import quiz.Quiz;
import quiz.QuizCollection;
import quiz.QuizPerformance;
import quiz.QuizQuestions;
import quiz.User;
import quiz.UserList;
import quiz.UserMessageList;

public class Database {

	public Database() {
		// TODO Auto-generated constructor stub
	}
	
	public void connect() {
		
	}
	
	public void uploadUser(User user) {
		
	}
	
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
	
	public void uploadQuiz(Quiz quiz) {
		
	}
	
	public boolean findUser(String userName, String password) {
		return true;
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
	
	public void close() {
		
	}
	
}
