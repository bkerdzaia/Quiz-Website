<%@page import="java.awt.TrayIcon.MessageType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="application.*, quiz.*, database.*"%>
<%@ page errorPage="error-page.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hello to Funz</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="script.js"></script>
</head>
<body>

	<%
		DatabaseGrabber db = (DatabaseGrabber) application.getAttribute(ServletConstants.DATABASE_ATTRIBUTE);
		db.connect(); 
		System.out.println("here");
		String userName = (String) session.getAttribute("userName");
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
				htmlQuizes += "<p><a href=\"QuizSummaryServlet?name=" + quizName + "\">" 
						+ quizName + "</a></p>\n";
			}
			return htmlQuizes;
		}
	%>
	
	
	<div id="logOutId"></div>

	<p><b>Hello ${param.name}</b></p>
	
	<p><b>user name: <%= user.getName() %></b></p>
	<p><b>parameter value: <%= request.getParameter("name") %></b></p>

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

	<div>
		<p>received messages</p>
		<%
			UserMessageList messages = user.getMessages();
			for (Message message : messages) {
				out.println(message.displayMessage());		
			}
		%>
	</div>

	<div>
		<p>friends recent activities (quizzes taken or created)</p>
		<%
			FriendList friends = user.getFriends();
			for (String friend : friends) {
				out.println(friend);
			}
		%>
	</div>
</body>
</html>