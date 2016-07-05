package application;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.DatabaseGrabber;
import quiz.User;

/**
 * Servlet implementation class UserPageServlet
 */
@WebServlet("/UserPageServlet")
public class UserPageServlet extends HttpServlet implements ServletConstants {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String redirectAdress;
		try {
			DatabaseGrabber db = (DatabaseGrabber) request.getServletContext()
					.getAttribute(ServletConstants.DATABASE_ATTRIBUTE);
			db.connect();
			HttpSession session = request.getSession();
			String userName = request.getParameter(ServletConstants.USER_NAME_PARAM);
			User user = db.loadUser(userName);
			session.setAttribute(USER_OBJECT_ATTRIBUTE, user);
			db.close();
			redirectAdress = "user-page.jsp";
		} catch (SQLException e) {
			e.printStackTrace();
			redirectAdress = ERROR_PAGE_ADDRESS;
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(redirectAdress);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
