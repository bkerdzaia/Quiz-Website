package application;

import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DatabaseGrabber;
import quiz.UserList;

/**
 * @author dav23r
 * Provides user suggestions based on search term.
 */
@WebServlet("/UsersLiveSearch")
public class UsersLiveSearch extends HttpServlet implements ServletConstants{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response){
		
		response.setContentType("text/html");
		String searchTerm = request.getParameter("searchTerm");
		DatabaseGrabber db = (DatabaseGrabber) 
				request.getServletContext().getAttribute(DATABASE_ATTRIBUTE);
		try {
			db.connect();
			UserList similarUsers = db.searchUsers(searchTerm);
			PrintWriter out = response.getWriter();
			for (String userName : similarUsers){
				out.append(
						"<a href=" + USER_PAGE_ADDRESS + "?" + 
							USER_NAME_PARAM + "=" + userName + ">" +
							userName + 
						"</a>\n"
				);
			}
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
