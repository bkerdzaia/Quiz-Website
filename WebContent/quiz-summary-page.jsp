<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="application.*, quiz.*, factory.*, java.util.Comparator"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hello at quiz ${param.quizName}</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="script.js"></script>
</head>
<body>

	<%
		Quiz quiz = (Quiz) session.getAttribute(ServletConstants.QUIZ_NAME_PARAM);
		String user = (String) session.getAttribute(ServletConstants.USER_NAME_PARAM);
		UserList highestPerformers = (UserList) session.getAttribute(ServletConstants.HIGHEST_PERFORMANCE_ATTRIBUTE);
		UserList topPerformers = (UserList) session.getAttribute(ServletConstants.TOP_PERFORMANCE_ATTRIBUTE);
		QuizHistory perfomance = (QuizHistory) session.getAttribute(ServletConstants.PERFORMANCE_ATTRIBUTE);
		QuizFactory quizFactory = DefaultQuizFactory.getFactoryInstance();
	%>
	
	<%!
		private String getHtmlUsers(UserList  users) {
			if (users == null) return "";
			String htmlUser = "";
			for (String userName : users) {
				htmlUser += "<p><a href=\"homepage.jsp?" + ServletConstants.USER_NAME_PARAM + "=" + userName + "\">" 
						+ userName + "</a></p>\n";
			}
			return htmlUser;
		}
	%>
	
	
	<%!
		private String getHtmlPerformance(QuizHistory perfomance, String quizName, String user, Comparator<Performance> comparator) {
			String htmlPerformance = "";
			QuizFactory quizFactory = DefaultQuizFactory.getFactoryInstance();
			for (PerformanceOnQuiz quizPerformance : perfomance.sortByUser(user, comparator)) {
				htmlPerformance += "<p>quiz: " + quizName + 
						" date: " + quizPerformance.getDate() +
						" percent correct: " + quizPerformance.getPercentCorrect() + "%</p>";
			}
			return htmlPerformance;
		}
	%>

	<div id="logOutId"></div>

	<p>Welcome to funz ${param.userName} !</p>
	
	<p>quiz name is: <b><%= quiz.getName() %></b></p>
	
	<div>
		<p>description: <b><%= quiz.getDescription() %></b></p>
	</div>
	
	<div>
		<%
			if (!quiz.getCreator().equals(user)) {
				out.println("<p><a href=\"homepage.jsp?" + ServletConstants.USER_NAME_PARAM + "=" +  quiz.getCreator() +  "\">" + quiz.getCreator() + "</a></p>");
			}
		%>
	</div>
	
	<div>
		<p>A list of user's past performance ordered by: 
		<input type="radio" name="performance" value="date" checked onclick="showOrderByDate()">date
		<input type="radio" name="performance" value="percentCorrect" onclick="showOrderByPercentCorrect()">percent correct
		<input type="radio" name="performance" value="amountTime" onclick="showOrderByAmountTime()">amount of time</p>
		<p><b>user is <%= user %></b> past performance is </p>

		<div id="performanceOrderByDate"><%= getHtmlPerformance(perfomance, quiz.getName(), user, quizFactory.getOrderByDateInstance()) %></div>
		<div id="performanceOrderByPercentCorrect" style="display:none"><%= getHtmlPerformance(perfomance, quiz.getName(), user, quizFactory.getOrderByPercentCorrectInstance()) %></div>
		<div id="performanceOrderByAmountTime" style="display:none"><%= getHtmlPerformance(perfomance, quiz.getName(), user, quizFactory.getOrderByAmountTimeInstance()) %></div>
	</div>

	<div>
		<p>list of highest performers: <b><%= getHtmlUsers(highestPerformers) %></b></p>
	</div>
	
	<div>
		<p>list of top performers in the last day: <b><%= getHtmlUsers(topPerformers) %></b></p>
	</div>
	
	<div>
		<p>recent test takers performance: </p>
		<%
			for (PerformanceOnQuiz quizPerformance : perfomance) {
				out.println("</p>user: " + quizPerformance.getUser() + " date: " + quizPerformance.getDate()
					+ " percent correct: " + quizPerformance.getPercentCorrect() + " time amount:  " + quizPerformance.getAmountTime() + "</p>");
			}
		%>
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
		<p>Challenge Friend</p>
		<form action="ChallengeServlet" method="post"><br>
			Enter friend name: <input type="text" name="friendName"><br>
			<input type="submit" value="challenge">
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
