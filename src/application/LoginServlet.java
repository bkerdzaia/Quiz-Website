package application;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import database.*;
import factory.*;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String DATABASE_ATTRIBUTE = "database";

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String address = "login.html";			
		try {
			DatabaseGrabber db = (DatabaseGrabber) request.getServletContext().getAttribute(DATABASE_ATTRIBUTE);
			if (db == null) {
				db = DefaultDatabaseFactory.getFactoryInstance().getDatabaseGrabber();
				request.getServletContext().setAttribute(DATABASE_ATTRIBUTE, db);
			}
		
			// read all information and pass home page as session
			HttpSession session = request.getSession();
			db.connect();
			String name = request.getParameter("userName");
			Encryption encryption = new Encryption();
			String encryptPassword = encryption.encrypt(request.getParameter("password"));
			boolean userEntered = db.authenticateUser(name, encryptPassword);
			if(request.getParameter("register") != null) {// && db.registerUser(name, encryptPassword)){
				session.setAttribute("userName", name);
				address = "homepage.jsp?name=" + name;
			}
			if(userEntered){
				session.setAttribute("userName", name);
				address = "homepage.jsp?name=" + name;
				System.out.println("Successfull Login");
			}
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
			address = "error-page.jsp";
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}


}