package application;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DefaultDatabaseGrabber;

/**
 * Servlet implementation class SendNote
 */
@WebServlet("/SendNote")
public class SendNote extends HttpServlet implements ServletConstants {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String reciever = request.getParameter(USER_NAME_PARAM);
		String text = request.getParameter("messageText");
		String sender = (String) request.getSession().getAttribute(USER_NAME_PARAM);
		String addressToRedirect;
		try {
			DefaultDatabaseGrabber db = (DefaultDatabaseGrabber) request.getServletContext().getAttribute(DATABASE_ATTRIBUTE);
			db.connect();
			db.sendMessage(sender, reciever, text, new Timestamp(System.currentTimeMillis()));
			db.close();
			addressToRedirect = HOMEPAGE_ADDRESS;
		} catch (Exception e) {
			e.printStackTrace();
			addressToRedirect = ERROR_PAGE_ADDRESS;
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(addressToRedirect);
		dispatcher.forward(request, response);
	}

}
