package application;

import java.io.*;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import database.*;
import factory.*;
import quiz.*;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("register servlet");
		String name = request.getParameter("userName");
		String password = request.getParameter("password");
		Encryption encryption = new Encryption();
		String encryptPassword = encryption.encrypt(password);
		DatabaseGrabber db = (DatabaseGrabber) request.getServletContext().getAttribute(DatabaseListener.ATTRIBUTE_NAME);
		if (db == null) {
			db = DefaultDatabaseFactory.getFactoryInstance().getDatabaseGrabber();
			request.getServletContext().setAttribute(DatabaseListener.ATTRIBUTE_NAME, db);
		}
		HttpSession session = request.getSession();
		String address = "login.html";
		try {
			db.connect();
			User user = db.authenticateUser(name, encryptPassword);
			if (user == null) {
				user = DefaultUserFactory.getFactoryInstance().getUser();
				user.setName(name);
				user.setPasswordHash(encryptPassword);
				user.setHistory(DefaultUserFactory.getFactoryInstance().getHistory());
				user.setMessages(DefaultUserFactory.getFactoryInstance().getMessageList());
				user.setCreatedQuizzes(DefaultQuizFactory.getFactoryInstance().getQuizCollection());
				user.setFriends(DefaultUserFactory.getFactoryInstance().getFriendList());
				session.setAttribute("userName", user);
				QuizCollection popularQuizzes = db.getPopularQuizzes();
				session.setAttribute("popularQuizzes", popularQuizzes);
				QuizCollection recentlyCreatedQuiz = db.getRecentlyCreatedQuizzes();
				session.setAttribute("recentQuizzes", recentlyCreatedQuiz);
				//db.registerUser(name, encryptPassword);
				address = "homepage.jsp?name=" + name;
			}
			db.close();
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
			dispatcher.forward(request, response);
		} catch (SQLException e) { e.printStackTrace();}
	}

}
