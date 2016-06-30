<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="application.*, quiz.*"%>
<%@ page errorPage="error-page.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Quiz Summary Page</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="script.js"></script>
<script type="text/javascript">
	loadOrderDate();
</script>
</head>
<body>


	<%
		Quiz quiz = (Quiz) session.getAttribute("quizName");
		String user = (String) session.getAttribute("userName");
		UserList highestPerformers = (UserList) session.getAttribute("highestPerformers");
		UserList topPerformers = (UserList) session.getAttribute("topPerformers");
		UserList recentPerformers = (UserList) session.getAttribute("recentPerformers");
	%>
	
	<%!
		private String getHtmlUsers(UserList  users) {
			if (users == null) return "";
			String htmlUser = "";
			for (String userName : users) {
				htmlUser += "<p><a href=\"homepage.jsp?name=" + userName + "\">" 
						+ userName + "</a></p>\n";
			}
			return htmlUser;
		}
	%>
	
	<div id="logOutId"></div>

	<p>Welcome to funz ${param.name} !</p>
	
	<div>
		<p>description: <b><%= quiz.getDescription() %></b></p>
	</div>
	
	<div>
		<p>creator of quiz: <b><%= quiz.getCreator() %></b></p>
	</div>
	
	<div>
		<p>A list of user's past performance ordered by: 
		<input type="radio" name="performance" value="date" checked>date
		<input type="radio" name="performance" value="percentCorrect">percent correct
		<input type="radio" name="performance" value="amountTime">amount of time</p>
		<p><b>user is <%= user %></b> past performance is </p>

		
		<div id="performanceOrder"></div>
		
		<%--
			History history = user.getHistory(); 
			for (QuizPerformance madeQuiz : history) {
				if (quiz.getName().equals(madeQuiz.getQuiz())) {
					out.println("<p>quiz: " + madeQuiz.getQuiz() + " date: " +
							madeQuiz.getDate() + " percent correct: " + madeQuiz.getPercentCorrect() + "%</p>");
				}
			}
		--%>
		
	</div>

	<div>
		<p>list of highest performers: <b><%= getHtmlUsers(highestPerformers) %></b></p>
	</div>
	
	<div>
		<p>list of top performers in the last day: <b><%= getHtmlUsers(topPerformers) %></b></p>
	</div>
	
	<div>
		<p>recent test takers performance: <b><%= getHtmlUsers(recentPerformers) %></b></p>
	</div>
	
	<div>
		<p>summary statistics: <%= quiz.getSummaryStatistics() %></p>
	</div>
	
	<div>
		<form name="take-a-quiz" action="DisplayQuizServlet" method="get">
			 <button type="submit" name="quizName" value=<%=quiz.getName()%>>Take a Quiz</button>
		</form>
	</div>
	
	<div>
		<%
			String creator = quiz.getCreator();
			if(creator.equals(user)) {
				out.println("<p>edit quiz for owner of quiz<//p>");
			}
		%>
	</div>

</body>
</html>
