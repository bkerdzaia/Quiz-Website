package application;

import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import database.*;
import factory.*;
import quiz.*;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DatabaseGrabber db = (DatabaseGrabber) request.getServletContext().getAttribute(DatabaseListener.ATTRIBUTE_NAME);
		if (db == null) {
			db = DefaultDatabaseFactory.getFactoryInstance().getDatabaseGrabber();
			request.getServletContext().setAttribute(DatabaseListener.ATTRIBUTE_NAME, db);
		}
	
		// read all information and pass home page as session
		HttpSession session = request.getSession();
		try {
			db.connect();
			String name = request.getParameter("userName");
			Encryption encryption = new Encryption();
			String encryptPassword = encryption.encrypt(request.getParameter("password"));
			User user = db.authenticateUser(name, encryptPassword);
			String address = "login.html";			
			if(request.getParameter("register") != null && db.registerUser(name, encryptPassword)){
				user = DefaultUserFactory.getFactoryInstance().getUser();
				user.setName(name);
				user.setPasswordHash(encryptPassword);
				user.setHistory(DefaultUserFactory.getFactoryInstance().getHistory());
				user.setMessages(DefaultUserFactory.getFactoryInstance().getMessageList());
				user.setCreatedQuizzes(DefaultQuizFactory.getFactoryInstance().getQuizCollection());
				user.setFriends(DefaultUserFactory.getFactoryInstance().getFriendList());
			}
			if(user!=null){
				session.setAttribute("userName", user);
				QuizCollection popularQuizzes = db.getPopularQuizzes();
				session.setAttribute("popularQuizzes", popularQuizzes);
				QuizCollection recentlyCreatedQuiz = db.getRecentlyCreatedQuizzes();
				session.setAttribute("recentQuizzes", recentlyCreatedQuiz);
				address = "homepage.jsp?name=" + name;
				System.out.println("Successfull Login");
			}
			db.close();
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
			dispatcher.forward(request, response);
		} catch (SQLException e) {
			System.out.println("exception: " + e);
		}
	}


}