<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="application.*, quiz.*, questions.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%
			Quiz quiz = (Quiz) session.getAttribute("quizName");
			QuizQuestions questions = quiz.getQuestions();
			
		%>
<title>Result Page</title>
</head>
<body>
	
	your answers: 
	<%	
		for(Question q: questions)
			out.println(q.getUsersChoice()+"<br>");

	%>
	
	<div>
		<p>users score and time</p>
	</div>
	
	<div>
		<p>all the user's answers along with correct answers</p>
	</div>
	
	<div>
		<p> comparisons to user's past performance</p>
		<p> comparisons with top performers or user's friends</p>
	</div>

</body>
</html>