<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>

<script>
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
	
	function registerClick() {
		$(function() {
			$("#login-form-id").hide();
			$("#register-form-id").show();
		});
	};
	
	function loginClick() {
		$(function() {
			$("#register-form-id").hide();
			$("#login-form-id").show();
		});
	};
	loginClick();
</script>

<style>
	input{
		width: 30%;
		padding: 10px;
    	border: 3px;
    	display: block;
    	margin : 0 auto;
	}
	
	input[type = submit]{
    	margin: 4px 2px;
    	margin-left: auto;
    	margin-right: auto;
    	background-color: lightblue;
    	color: white;
	}
	
	h1, h2{
		text-align: center;
		color: lightblue;
	}
	
	h1{
		font-size: 400%;
	}
	
</style>

</head>
<body>
<h1>Welcome to Funz!</h1>
<h2>place where fun quizzes are made</h2>
<br> <br> <br>
	<div>
		<form  id="login-form-id" action="LoginServlet" method="post">
			<input type="text" name="userName" placeholder="Username" required><br /><br /> 
			<input type="password" name="password" placeholder="Password" required><br /><br /> 
			<input name="login" type="submit" value="Log In">
			<input name="register" type="submit" value="Register">
			<br><br>
		</form>
	</div>
	
	<div id="message">
		<% 
			String completionMessage = (String) request.getAttribute("message");
			if (completionMessage != null)
				out.println(completionMessage);
			System.out.println(completionMessage);
		%> 
	</div>

</body>
</html>
