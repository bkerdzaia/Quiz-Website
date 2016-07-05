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
 * Servlet implementation class RemoveFriend
 */
@WebServlet("/RemoveFriend")
public class RemoveFriend extends HttpServlet implements ServletConstants {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String addressToRedirect;
		try {
			DefaultDatabaseGrabber db = (DefaultDatabaseGrabber) request.getServletContext().getAttribute(DATABASE_ATTRIBUTE);
			db.connect();
			String remover = (String) request.getSession().getAttribute(USER_NAME_PARAM);
			String removeWith = request.getParameter(USER_NAME_PARAM);
			db.removeFriend(remover, removeWith);
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
