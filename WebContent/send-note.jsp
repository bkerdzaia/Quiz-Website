<%@page import="application.ServletConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Message Sending</title>
</head>
<body>

	<div>
		<form action="homepage.jsp?<%=ServletConstants.USER_NAME_PARAM + "=" + session.getAttribute(ServletConstants.USER_NAME_PARAM) %>">
			<br><input type="submit" value="go hompage"><br>
		</form>
	</div>

	<div>
		<form action="SendNote" method="post"><br>
			Enter name of receiver: <input type="text" name="receiverName"><br><br>
			Enter message: <textarea rows="5" cols="20" name="messageText"></textarea><br><br>
			<input type="submit" value="send">
		</form>
	
	</div>

</body>
</html>