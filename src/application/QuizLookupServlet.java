package application;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import database.DatabaseGrabber;
import quiz.Quiz;
import quiz.QuizCollection;

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
		
		response.setContentType("text/html");
		String searchTerm = request.getParameter("searchTerm");
		DatabaseGrabber db = (DatabaseGrabber) 
				request.getServletContext().getAttribute(DATABASE_ATTRIBUTE);
		try {
			db.connect();
			QuizCollection similarQuizzes = db.searchQuizzes(searchTerm);
			PrintWriter out = response.getWriter();
			for (String quizName : similarQuizzes){
				out.append(
						"<a href=" + QUIZ_SUMMARY_SERVLET + "?" + 
							QUIZ_NAME_PARAM + "=" + quizName + ">" +
							quizName + 
						"</a>\n"
				);
			}
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
