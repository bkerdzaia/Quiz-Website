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
		String address = "login.html";
		try {
			db.connect();
			User user = null;
			if (user == null) {
				User newUser = DefaultUserFactory.getFactoryInstance().getUser();
				newUser.setName(name);
				newUser.setPasswordHash(encryptPassword);
				db.registerUser(name, encryptPassword);
				address = "homepage.jsp?name=" + name;
			}
			
			db.close();
		} catch (SQLException e) {}
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}

}
