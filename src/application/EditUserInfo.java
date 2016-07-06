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
		
		String newPicUrl = request.getParameter("newPicUrl");
		String newDesc = request.getParameter("newDesc");
		String name = request.getParameter("userName");
		
		User changedUser = new User();
		changedUser.setName(name);
		changedUser.setAboutMe(newDesc);
		changedUser.setPictureUrl(newPicUrl);
		
		DatabaseGrabber db = (DatabaseGrabber) 
				request.getServletContext().getAttribute(DATABASE_ATTRIBUTE);
		
		try{
			db.connect();
			boolean res = db.editUser(changedUser);
			System.out.println(res);
			db.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		
		session.setAttribute(ServletConstants.USER_OBJECT_ATTRIBUTE, changedUser);
	}

}
