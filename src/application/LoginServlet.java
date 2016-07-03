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
public class LoginServlet extends HttpServlet implements ServletConstants{
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String addressToRedirect = LOGIN_ADDRESS;			
		String userName = request.getParameter(USER_NAME_PARAM);
		Encryption encryptor = new Encryption();
		String encryptedPassword = encryptor.encrypt(request.getParameter(PASSWORD_PARAM));

		try {
			DatabaseGrabber db = (DatabaseGrabber) 
					request.getServletContext().getAttribute(DATABASE_ATTRIBUTE);
		
			db.connect();
			// If user wants to register in the system
			if(request.getParameter(REGISTER_PARAM) != null) { 
				// Case of failure
				if (!db.registerUser(userName, encryptedPassword)){
					request.setAttribute(MESSAGE_ATTR, CANT_REGISTER);
				// Everything went good
				}else{ 
					addressToRedirect = HOMEPAGE_ADDRESS + "?" + USER_NAME_PARAM + "=" + userName;
					session.setAttribute(USER_NAME_PARAM, userName);
				}
			} 
			// Otherwise login is issued by user
			else {
				// User with provided credentials exists in database
				if(db.authenticateUser(userName, encryptedPassword)){
					session.setAttribute(USER_NAME_PARAM, userName);
					addressToRedirect = HOMEPAGE_ADDRESS + "?" + USER_NAME_PARAM + "=" + userName;
				}
				// Who the heck you are?
				else{
					request.setAttribute(MESSAGE_ATTR, CANT_LOGIN);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			addressToRedirect = ERROR_PAGE_ADDRESS;
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(addressToRedirect);
		dispatcher.forward(request, response);
	}
	
	
	/**
	 * 'Get method' is called from 'logout' button to delete userName from session. 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{

		HttpSession session = request.getSession();
		if (request.getParameter(LOG_OUT_PARAM) != null){
			session.removeAttribute(USER_NAME_PARAM);
			response.sendRedirect(LOGIN_ADDRESS);
		}
	}

}