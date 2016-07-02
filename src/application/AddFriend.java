package application;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import database.*;

/**
 * Servlet implementation class AddFriend
 */
@WebServlet("/AddFriend")
public class AddFriend extends HttpServlet implements ServletConstants {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("add friend servlet");
		String addressToRedirect;
		try {
			DefaultDatabaseGrabber db = (DefaultDatabaseGrabber) request.getServletContext().getAttribute(DATABASE_ATTRIBUTE);
			db.connect();
			String sender = (String) request.getSession().getAttribute(USER_NAME_PARAM);
			String recepient = request.getParameter(USER_NAME_PARAM);
			db.addFriendRequest(sender, recepient);
			db.close();
			addressToRedirect = HOMEPAGE_ADDRESS + "?" + USER_NAME_PARAM + "=" + recepient;
		} catch (Exception e) {
			e.printStackTrace();
			addressToRedirect = ERROR_PAGE_ADDRESS;
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(addressToRedirect);
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
