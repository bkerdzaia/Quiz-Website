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
	<link rel="stylesheet" type="text/css" href="table-style.css">
</head>
<body>

	<%-- gets information from session --%>
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
				htmlUser += "<p><a href=\"user-page.jsp?" + ServletConstants.USER_NAME_PARAM + "=" + userName + "\">" 
						+ userName + "</a></p>\n";
			}
			return htmlUser;
		}
	%>
	
	<%-- returns a table of rows: date, percent correct, time amount --%>
	<%!
		private String getHtmlPerformance(QuizHistory perfomance, String user, Comparator<Performance> comparator) {
			String htmlPerformance = "";
			QuizFactory quizFactory = DefaultQuizFactory.getFactoryInstance();
			for (PerformanceOnQuiz quizPerformance : perfomance.sortByUser(user, comparator)) {
				htmlPerformance += 	"<tr>" +	
						"<td>" + quizPerformance.getDate() + "</td>" +
						"<td>" + quizPerformance.getPercentCorrect() + "%</td>" + 
						"<td>" + quizPerformance.getAmountTime() + "</td>" +
						"</tr>";
			}
			return htmlPerformance;
		}
	%>

	<jsp:include page="logout.html"></jsp:include>

	<p>Welcome to funz ${param.userName} !</p>
	
	<p>quiz name is: <b><%= quiz.getName() %></b></p>
	
	<div>
		<p>description: <b><%= quiz.getDescription() %></b></p>
	</div>
	
	<div>
		<%
			if (!quiz.getCreator().equals(user)) {
				out.println("<p><a href=\"user-page.jsp?" + ServletConstants.USER_NAME_PARAM + "=" +  quiz.getCreator() +  "\">" + quiz.getCreator() + "</a></p>");
			} else {
				out.println("<p><a href=\"homepage.jsp\">my page</a></p>");
			}
		%>
	</div>
	
	<div>
		<table border="1">
			<caption>
				A list of <%= user %>'s past performance on this quiz ordered by: <br>
				<input type="radio" name="performance" value="date" checked onclick="showOrderByDate()">date
				<input type="radio" name="performance" value="percentCorrect" onclick="showOrderByPercentCorrect()">percent correct
				<input type="radio" name="performance" value="amountTime" onclick="showOrderByAmountTime()">amount of time
			</caption>
			<thead>
				<tr>
					<th>date</th>
					<th>percent correct</th>
					<th>time amount</th>
				</tr>
			</thead>
			<tbody id="performanceOrderByDate">
				<%= getHtmlPerformance(perfomance, user, quizFactory.getOrderByDateInstance()) %>
			</tbody>
			
			<tbody id="performanceOrderByPercentCorrect" style="display:none">
				<%= getHtmlPerformance(perfomance, user, quizFactory.getOrderByPercentCorrectInstance()) %>
			</tbody>
			<tbody id="performanceOrderByAmountTime" style="display:none">
				<%= getHtmlPerformance(perfomance, user, quizFactory.getOrderByAmountTimeInstance()) %>
			</tbody>
		</table>
	</div>

	<div>
		<p>list of highest performers: <b><%= getHtmlUsers(highestPerformers) %></b></p>
	</div>
	
	<div>
		<p>list of top performers in the last day: <b><%= getHtmlUsers(topPerformers) %></b></p>
	</div>
	
	<div align="left">
		<table border="1">
			<caption>recent test takers performance</caption>
			<tr>
				<th>user</th>
				<th>date</th>
				<th>percent correct</th>
				<th>time amount</th>
			</tr>
			<%
				for (PerformanceOnQuiz quizPerformance : perfomance) {
					out.println("<tr>");
					out.println("<td>" + quizPerformance.getUser() + "</td>");
					out.println("<td>" + quizPerformance.getDate() + "</td>");
					out.println("<td>" + quizPerformance.getPercentCorrect() + "%</td>");
					out.println("<td>" + quizPerformance.getAmountTime() + "</td>");
					out.println("</tr>");
				}
			%>
		</table>
	</div>
	
	<div>
		<p>summary statistics: <%= quiz.getSummaryStatistics() %></p>
	</div>
	
	<div>
		<form name="take-a-quiz" action="DisplayQuizServlet" method="get">
			 <button type="submit" name="quizName" value="<%=quiz.getName()%>">Take a Quiz</button>
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