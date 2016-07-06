package application;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.DatabaseGrabber;
import quiz.User;

/**
 * @author dav23r
 * Provides functionaly of changing user's information
 * for logged in user.
 */
@WebServlet("/EditUserInfo")
public class EditUserInfo extends HttpServlet implements ServletConstants{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response){

		HttpSession session = request.getSession();
		User updatedUser = (User) session.getAttribute(USER_OBJECT_ATTRIBUTE);
		DatabaseGrabber db = (DatabaseGrabber) session.getAttribute(DATABASE_ATTRIBUTE);
		try{
			db.connect();
			boolean status = db.editUser(updatedUser);
			System.out.println(status);
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}

}
