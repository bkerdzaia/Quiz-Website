package application;

/**
 * @author dav23r
 * Stores some servlet-related constants
 */
public interface ServletConstants {

	public static final String DATABASE_ATTRIBUTE = "database";
	public static final String LOGIN_ADDRESS = "login.jsp";
	public static final String HOMEPAGE_ADDRESS = "HomepageServlet";
	public static final String USER_PAGE_ADDRESS = "UserPageServlet";
	public static final String ERROR_PAGE_ADDRESS = "error-page.jsp";
	public static final String QUIZ_SUMMARY_SERVLET = "QuizSummaryServlet";
	public static final String USER_NAME_PARAM = "userName";
	public static final String QUIZ_NAME_PARAM = "quizName";
	public static final String HIGHEST_PERFORMANCE_ATTRIBUTE = "highestPerformers";
	public static final String TOP_PERFORMANCE_ATTRIBUTE = "topPerformers";
	public static final String PERFORMANCE_ATTRIBUTE = "performance";
	public static final String POPULAR_QUIZZES_ATTRIBUTE = "popularQuizzes";
	public static final String RECENTLY_CREATED_QUIZZES_ATTRIBUTE = "recentlyCreatedQuiz";
	public static final String USER_OBJECT_ATTRIBUTE = "userObject";
	public static final String PASSWORD_PARAM = "password";
	public static final String REGISTER_PARAM = "register";

	public static final String MESSAGE_ATTR = "message";
	public static final String CANT_REGISTER = 
			"User with provided name already exists. Pick another.";
	public static final String LOGIN_SUCCESS = "Login successfull!";
	public static final String CANT_LOGIN = "Unable to login. Check your name/password.";
	public static final String NO_QUIZ_FOUND = "Quiz with provided name doesn't exist.";

	public static final String LOG_OUT_PARAM = "logout";
	public static final String QUIZ_FIND = "quizToFind";
	
	
	public static final String APPLICATION_FACTORY = "applicationFactory";
	public static final String QUESTION_FACTORY = "questionFactory";
	public static final String QUIZ_FACTORY = "quizFactory";
	public static final String DATABASE_FACTORY = "databaseFactory";
	public static final String USER_FACTORY = "userFactory";

}
