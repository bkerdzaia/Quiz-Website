<%@page import="java.awt.TrayIcon.MessageType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="application.*, quiz.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hello to quiz web site</title>
</head>
<body>

	<%
		User user = (User) session.getAttribute("userName");
		QuizCollection popularQuizzes = (QuizCollection) session.getAttribute("popularQuizzes");
		QuizCollection recentlyCreatedQuiz = (QuizCollection) session.getAttribute("recentQuizzes");
	%>

	<%!
		private String getHtmlQuizzes(QuizCollection quizzes) {
			if (quizzes == null) return "";
			String htmlQuizes = "";
			for (Quiz quiz : quizzes) {
				htmlQuizes += "<p><a href=\"QuizSummaryServlet?name=" + quiz.getName() + "\">" 
						+ quiz.getName() + "</a></p>\n";
			}
			return htmlQuizes;
		}
	%>

	<p>Hello ${param.name}</p>
	<p><b>user name: <%= user.getName() %></b></p>

	<div>
		<p>list of popular quizzes</p>
		<%= getHtmlQuizzes(popularQuizzes)	%>
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
				
			}
		%>
	</div>

	<div>
		<p>friends recent activities (quizzes taken or created)</p>
		<%
			FriendList friends = user.getFriends();
			for (User friend : friends) {
				out.println(friend.getHistory());
				out.println(getHtmlQuizzes(friend.getCreatedQuizzes()));
			}
		%>
	</div>

</body>
</html>