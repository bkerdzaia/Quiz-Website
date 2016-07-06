<%@page import="factory.DefaultUserFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="application.*, quiz.*, database.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<!-- Redirect to login page if session does not contain user name atribute -->
	<%
		if (session.getAttribute(ServletConstants.USER_NAME_PARAM) == null){
			response.sendRedirect(ServletConstants.LOGIN_ADDRESS);
			return;
		}
	%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Hello to Funz</title>
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script src="livesearch.js"> </script>
	<script src="script.js"></script>
	<script src="homepage.js"></script>
	<link rel="stylesheet" type="text/css" href="style.css">
	<link rel="stylesheet" type="text/css" href="table-style.css">

	<script>
		function loadFriendPerformance() {
			var friendValue = $("#friendSearch").val();
			if (friendValue === undefined || friendValue === null) {
				friendValue = "";
			}
			$.ajax({
				type: 'GET',
				url: 'FriendSearchServlet',
				headers: {"X-Test-Header": "test-value"},
				data: { 
					friendNamePrefix: friendValue
				},
				success: function(htmlResult) {
					$("#friendAdd").html(htmlResult);
				},
				error: function() {
		    		$("#friendAdd").html("<p style='color:red;'>Can't load friends performance</p>");
		    	}
			});
		}
		loadFriendPerformance();
	</script>
	
</head>
<body>

	<%
		String userName = (String) session.getAttribute(ServletConstants.USER_NAME_PARAM);
		User user = (User) session.getAttribute(ServletConstants.USER_OBJECT_ATTRIBUTE);
		QuizCollection popularQuizzes = (QuizCollection) session.getAttribute(ServletConstants.POPULAR_QUIZZES_ATTRIBUTE); 
		QuizCollection recentlyCreatedQuiz = (QuizCollection) session.getAttribute(ServletConstants.RECENTLY_CREATED_QUIZZES_ATTRIBUTE);
	%>

	<%!
		private String getHtmlQuizzes(QuizCollection quizzes) {
			if (quizzes == null) return "";
			String htmlQuizes = "";
			for (String quizName : quizzes) {
				htmlQuizes += "<p><a href='" + ServletConstants.QUIZ_SUMMARY_SERVLET + "?" + ServletConstants.QUIZ_NAME_PARAM + "=" + quizName + "'>" 
						+ quizName + "</a></p>\n";
			}
			return htmlQuizes;
		}
	%>
	
	
	<jsp:include page="logout.html"></jsp:include>

	<div class="topbar" id="homepage-bar">
		
		<div id="message-box">
			<p> Your inbox </p>
			<%
				UserMessageList messages = user.getMessages();
				for (Message receivedMessage : messages) {
					out.println(receivedMessage.displayMessage());		
				}
			%>
		</div>
		
		<div id="quiz-lookup">
			<p> Search for quiz </p>		
			<form action="QuizLookupServlet" method="get">
				<input type="text" name="quizToFind" placeholder="Enter quiz name" required>
				<input type="submit" name="quiz-submit" value="Find Quiz">
			</form>	
		</div>
		
		<div id="search-users">
			<p> Search for users </p>
			<input type="text" placeholder="Type user name"  
			       onkeyup="searchSimilarUsers(this.value)" required>
			<div id="live-user-suggestions" class="hide-borders">
			
			</div>
		</div> 

	</div>

	<div class="failure-message">
	<%
		String message = (String) request.getAttribute(ServletConstants.MESSAGE_ATTR);
		if (message != null){
			out.println("<p>*" + message + "</p>");
		}
	%>
	</div>

	<p id="welcome-home">
		<b>Welcome back <%= userName %>, have fun on your belowed website</b>	
			<a href="<%= ServletConstants.USER_PAGE_ADDRESS + "?" 
				+ ServletConstants.USER_NAME_PARAM + "=" + userName %>">[See your profile] </a>


	</p>

	<div id="homepage-summary">	
		<div id="popular-quizzes">
			<div class="category-header">
				<p>Popular quizzes</p>
			</div>
			<div class="category-content">
				<%= getHtmlQuizzes(popularQuizzes) %>
			</div>
		</div>

		<div id="recently-created">
			<div class="category-header">
				<p>Recently created quizzes</p>
			</div>
			<div class="category-content">
				<%= getHtmlQuizzes(recentlyCreatedQuiz) %>
			</div>
		</div>

		<div id="taken-quizzes">
			<div class="category-header">	
				<p>${userName}'s recent taken quizzes</p>
			</div>
			<div class="category-content">
				<%
					History<UsersPerformance> history = user.getHistory();
					for(UsersPerformance userPerformance : history) {
						out.println("<p><a href=href='" + ServletConstants.QUIZ_SUMMARY_SERVLET + 
								"?" + ServletConstants.QUIZ_NAME_PARAM + "=" + userPerformance.getQuiz() + "'>" + 
								userPerformance.getQuiz() + "</a></p>");
					}
				%>
			</div>
		</div>

		<div id="created-quizzes">
			<div class="category-header">
				<p>Created by ${userName} </p>
			</div>
			<div class="category-content">
				<%= getHtmlQuizzes(user.getCreatedQuizzes()) %>
			</div>
		</div>


	</div>

	<div id="friends-activities">
		<p>Friends' recent activities (quizzes taken or created)</p>
		<input type="text" id="friendSearch" placeholder=" Enter Friend Name" onkeyup="loadFriendPerformance()">
		
		<div id="friendAdd">
		
		</div>
	</div>
	
	<!-- display create a quiz -->
	<div>
		<form action="create-quiz.html">
			<input type="submit" value="create a quiz">
		</form>
	</div>
	
</body>
</html>