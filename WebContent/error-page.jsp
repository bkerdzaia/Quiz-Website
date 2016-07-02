<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="application.*"%>
<%@ page isErrorPage="true" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Error Occurred</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="script.js"></script>
</head>
<body>

	
	<div id="logOutId"></div>

	<form action="homepage.jsp?<%=ServletConstants.USER_NAME_PARAM + "=" + request.getParameter(ServletConstants.USER_NAME_PARAM) %>">
		<input type="submit" value="go hompage">
	</form>

	<h3> Exception Occurred</h3>
	
	<p>Exception is: <%= exception %></p>


</body>
</html>