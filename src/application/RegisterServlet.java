package application;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import factory.UserFactory;
import quiz.User;

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
		Encryption encryption = UserFactory.getEncryption();
		String encryptPassword = encryption.encrypt(password);
		Database db = (Database) request.getServletContext().getAttribute(DatabaseListener.ATTRIBUTE_NAME);
		db.connect();
		String address = "login.html";
		if (!db.findUser(name, encryptPassword)) {
			User newUser = UserFactory.getUser();
			newUser.setName(name);
			newUser.setPassword(encryptPassword);
			newUser.setHistory(UserFactory.getHistory());
			address = "homepage.jsp?name=" + name;
		}
		db.close();
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}

}
