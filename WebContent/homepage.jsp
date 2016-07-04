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
<script src="script.js"></script>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>

	<%
		DatabaseGrabber db = (DatabaseGrabber) 
			application.getAttribute(ServletConstants.DATABASE_ATTRIBUTE);
		db.connect(); 
		String userName = (String) session.getAttribute(ServletConstants.USER_NAME_PARAM);
		User user = db.loadUser(userName);
		QuizCollection popularQuizzes = db.getPopularQuizzes(); 
		QuizCollection recentlyCreatedQuiz = db.getRecentlyCreatedQuizzes();
		db.close();
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
	
	
	<div id="logOutId"></div>

	<div>
		<p> Search for quiz </p>		
		<form id="quiz-lookup" action="QuizLookupServlet" method="get">
			<input type="text" name="quizToFind" placeholder="Enter quiz name" required>
			<input type="submit" name="quiz-submit" value="Find Quiz">
		</form>	
	</div>
	
	<div class="failure-message">
	<%
		String message = (String) request.getAttribute(ServletConstants.MESSAGE_ATTR);
		if (message != null){
			out.println("<p>*" + message + "</p>");
		}
	%>
	</div>

	<p><b>Hello ${param.name}</b></p>
	
	
	<div>
		<p>list of popular quizzes</p>
		<%= getHtmlQuizzes(popularQuizzes) %>
	</div>

	<div>
		<p>list of recently created quizzes</p>
		<%= getHtmlQuizzes(recentlyCreatedQuiz) %>
	</div>

	<div>
		<p>list of user's recent quiz taking activities</p>
		<%= user.getHistory() %>
	</div>

	<div>
		<p>list of user's created quizzes</p>
		<%= getHtmlQuizzes(user.getCreatedQuizzes()) %>
	</div>

	<!-- display messages list -->
	<div>
		<p>received messages</p>
		<%
			UserMessageList messages = user.getMessages();
			for (Message receivedMessage : messages) {
				out.println(receivedMessage.displayMessage());		
			}
		%>
	</div>

	<div>
		<p>friends recent activities (quizzes taken or created)</p>
		<%
			FriendList friends = user.getFriends();
			for (String friend : friends) {
				out.println("<p>" + friend + "<p>");
			}
		%>
	</div>
	
	<!-- display create a quiz -->
	<div>
		<form action="create-quiz.html">
			<input type="submit" value="create a quiz">
		</form>
	</div>
	
</body>
</html>