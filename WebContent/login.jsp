<!DOCTYPE html>
<%@page import="application.ServletConstants"%>
<html>
<head>
<meta charset="UTF-8">
	<title>Login</title>
	<link rel="stylesheet" type="text/css" href="style.css">
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script src="script.js"></script>
	
	<!--  If session already contains user redirect to user's homepage -->
<%
	if (session.getAttribute(ServletConstants.USER_NAME_PARAM) != null){
		System.out.println(session.getAttribute(ServletConstants.USER_NAME_PARAM));
    	String redirectURL = ServletConstants.HOMEPAGE_ADDRESS;
    	response.sendRedirect(redirectURL);
    	return;
	}
%>

</head>
<body>
<h1>Welcome to Funz!</h1>
<h2 id="intro"> [place where fun quizzes are made] </h2>
	<div>
		<form  id="login-form-id" action="LoginServlet" method="post"  onsubmit="return validateName()">
			<input type="text" name="userName" placeholder="Username" required><br /><br /> 
			<input type="password" name="password" placeholder="Password" required><br /><br /> 
			<input name="login" type="submit" value="Log In">
			<input name="register" type="submit" value="Register">
			<br><br>
		</form>
	</div>
	
	<div class="failure-message">
		<% 
			String completionMessage = (String) request.getAttribute("message");
			if (completionMessage != null)
				out.println("<p>*" + completionMessage + "</p");
		%> 
	</div>

</body>
</html>
