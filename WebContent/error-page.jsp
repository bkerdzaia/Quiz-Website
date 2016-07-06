<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Error Occurred</title>
<link rel="stylesheet" type="text/css" href="style.css">
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="script.js"></script>
</head>
<body>

	
	<jsp:include page="logout.html"></jsp:include>

	<form action="homepage.jsp?">
		<input type="submit" value="return home">
	</form>

	<h3> Exception Occurred</h3>
	
	<h3>Exception is: <%= exception %></h3>


</body>
</html>