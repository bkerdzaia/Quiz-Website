package application;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class QuizLookupServlet
 */
@WebServlet("/QuizLookupServlet")
public class QuizLookupServlet extends HttpServlet implements ServletConstants{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		// If user is not logged in, redirect to login page
		if (session.getAttribute(USER_NAME_PARAM) == null){
			response.sendRedirect(LOGIN_ADDRESS);
			return;
		}
		// If quizToFind is not provided, redirect to home page
		String quizToFind = request.getParameter("quizToFind");
		if (quizToFind == null){
			RequestDispatcher dispatcher = request.getRequestDispatcher(HOMEPAGE_ADDRESS);
			dispatcher.forward(request, response);
			return;
		}
			
	}

}
