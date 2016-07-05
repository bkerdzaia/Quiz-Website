<%@page import="application.ServletConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Message Sending</title>
	<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>

	<jsp:include page="logout.html"></jsp:include>
	<jsp:include page="homepage-link.jsp"></jsp:include>

	<div>
		<form action="SendNote" method="post"><br>
			<p>Sending message to <b><%= request.getParameter(ServletConstants.USER_NAME_PARAM) %></b></p>
			Enter message: <textarea rows="5" cols="20" name="messageText" required></textarea><br><br>
			<input type="hidden" name="<%= ServletConstants.USER_NAME_PARAM %>" value="<%= request.getParameter(ServletConstants.USER_NAME_PARAM) %>">
			<input type="submit" value="send">
		</form>
	
	</div>

</body>
</html>