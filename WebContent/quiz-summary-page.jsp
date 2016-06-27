<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="application.*, quiz.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>

	<script type="text/javascript">
		function loadOrderDate() {
			$(function(){
				$("#performanceOrder").load("order-by-date.jsp");
			});
		}
	
		$(function() {
			$("input[name=performance]").click(function() {
				// alert(this.value);
				if (this.value === 'date') {
					loadOrderDate();
				} else {
					$("#performanceOrder").empty();
				}
			});
		});
		loadOrderDate();
	</script>

	<%
		Quiz quiz = (Quiz) session.getAttribute("quizName");
		User user = (User) session.getAttribute("userName");
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

	<p>Hello at quiz ${param.name}</p>
	
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
		<p><b>user is <%= user.getName() %></b> past performance is </p>

		
		<div id="performanceOrder"></div>
		
		<%
			History history = user.getHistory(); 
			for (QuizPerformance madeQuiz : history) {
				if (quiz.getName().equals(madeQuiz.getQuiz())) {
					out.println("<p>quiz: " + madeQuiz.getQuiz() + " date: " +
							madeQuiz.getDate() + " percent correct: " + madeQuiz.getPercentCorrect() + "%</p>");
				}
			}
		%>
		
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
		<p><a href="quiz-question.jsp?question=1">take a quiz</a></p>
	</div>
	
	<div>
		<%
			String creator = quiz.getCreator();
			if(creator.equals(user.getName())) {
				out.println("<p>edit quiz for owner of quiz<//p>");
			}
		%>
	</div>

</body>
</html>