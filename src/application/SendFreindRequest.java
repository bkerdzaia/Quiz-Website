package application;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DefaultDatabaseGrabber;

/**
 * Servlet implementation class SendFreindRequest
 */
@WebServlet("/SendFreindRequest")
public class SendFreindRequest extends HttpServlet implements ServletConstants {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("send friend request servlet");
		String addressToRedirect;
		try {
			DefaultDatabaseGrabber db = (DefaultDatabaseGrabber) request.getServletContext().getAttribute(DATABASE_ATTRIBUTE);
			db.connect();
			String sender = (String) request.getSession().getAttribute(USER_NAME_PARAM);
			String recepient = request.getParameter(USER_NAME_PARAM);
			db.addFriendRequest(sender, recepient);
			db.close();
			addressToRedirect = USER_PAGE_ADDRESS + "?" + USER_NAME_PARAM + "=" + recepient;
		} catch (Exception e) {
			e.printStackTrace();
			addressToRedirect = ERROR_PAGE_ADDRESS;
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(addressToRedirect);
		dispatcher.forward(request, response);
	}
}
