<%@page import="java.awt.TrayIcon.MessageType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="application.*, quiz.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hello to Funz</title>
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
			for (String quizName : quizzes) {
				htmlQuizes += "<p><a href=\"QuizSummaryServlet?name=" + quizName + "\">" 
						+ quizName + "</a></p>\n";
			}
			return htmlQuizes;
		}
	%>

	<p><b>Hello ${param.name}</b></p>
	
	<p><b>user name: <%= user.getName() %></b></p>
	<p><b>parameter value: <%= request.getParameter("name") %></b></p>

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
		<%--
			UserMessageList messages = user.getMessages();
			for (Message message : messages) {
				
			}
		--%>
	</div>

	<div>
		<p>friends recent activities (quizzes taken or created)</p>
		<%--
			FriendList friends = user.getFriends();
			for (User friend : friends) {
				out.println(friend.getHistory());
				out.println(getHtmlQuizzes(friend.getCreatedQuizzes()));
			}
		--%>
	</div>
	
	<div>
		<form action="create-quiz.html">
		<%
			if(request.getParameter("name").equals(user.getName())) {
				out.println("<input type=\"submit\" value=\"create a quiz\">");
			}
		%>
		</form>
	</div>
	
	<div>
		<%
			String parameterUserName = request.getParameter("name");
			if(!parameterUserName.equals(user.getName())) {
				out.println("<button type=\"button\">send friend request</button>");
			}
		%>
	</div>

</body>
</html>