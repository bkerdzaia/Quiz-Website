package application;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
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
		// TODO Auto-generated method stub
		System.out.println("login servlet");
		Database db = (Database) request.getServletContext().getAttribute(DatabaseListener.ATTRIBUTE_NAME);
		if (db == null) {
			db = DatabaseFactory.getDatabase();
			request.getServletContext().setAttribute(DatabaseListener.ATTRIBUTE_NAME, db);
		}
		// read all information and pass home page as session
		HttpSession session = request.getSession();
		User user = db.loadUser(request.getParameter("userName"));
		session.setAttribute("userName", user);
		QuizCollection popularQuizzes = db.getPopularQuizzes();
		session.setAttribute("popularQuizzes", popularQuizzes);
		QuizCollection recentlyCreatedQuiz = db.getRecentlyCreatedQuizzes();
		session.setAttribute("recentQuizzes", recentlyCreatedQuiz);
		db.connect();
		String name = request.getParameter("userName");
		String password = request.getParameter("password");
		Encryption encryption = UserFactory.getEncryption();
		String encryptPassword = encryption.encrypt(password);
		String address = "login.html";
		if(db.findUser(name, encryptPassword)) {
			address = "homepage.jsp?name=" + name;
		} 
		db.close();
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}

}
