<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Login</title>
	<script src="script.js"></script>
	<link rel="stylesheet" type="text/css" href="style.css">
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	
	<!--  If session already contains user redirect to user's homepage -->
<%
	if (session.getAttribute("userName") != null){
		System.out.println(session.getAttribute("userName"));
    	String redirectURL = "homepage.jsp";
    	response.sendRedirect(redirectURL);
	}
%>

<script>
<<<<<<< HEAD
	function passwordStrength() {
		var weak = "";
		if (document.getElementById("register-form-id").password.value.length < 6) {
			weak = "* weak password";
		}
		$("#register-form-id > span").text(weak);
	};
	
	function validateName() {
		var pat = /^[\w]+$/;
		return pat.test(document.getElementById("register-form-id").userName.value);
	}
=======

>>>>>>> dd16858645b8745a8f0aaa9b791492d8320812b4
</script>



</head>
<body>
<h1>Welcome to Funz!</h1>
<h2 id="intro"> [place where fun quizzes are made] </h2>
	<div>
<<<<<<< HEAD
		<form  id="login-form-id" action="LoginServlet" method="post"  onsubmit="validateName()">
			<input type="text" name="userName" placeholder="Username" required><br /><br /> 
			<input type="password" name="password" placeholder="Password" required><br /><br /> 
			<input name="login" type="submit" value="Log In">
			<input name="register" type="submit" value="Register">
			<br><br>
=======
		<form  id="login-form-id" action="LoginServlet" method="post">
			<input type="text" id="userName" name="userName" placeholder="Username" required>
			<input type="password" name="password" placeholder="Password" required> 
			<input type="submit" name="Log in" value="Log In">
			<input type="submit" name="Register" value="Register">
>>>>>>> dd16858645b8745a8f0aaa9b791492d8320812b4
		</form>
	</div>
	
	<div class="failure-message">
		<% 
			String completionMessage = (String) request.getAttribute("message");
			if (completionMessage != null)
				out.println("<h3>*" + completionMessage + "</h3");
		%> 
	</div>

</body>
</html>
