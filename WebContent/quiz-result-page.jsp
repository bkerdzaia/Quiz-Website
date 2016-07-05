<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="application.*, quiz.*, questions.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%
			Quiz quiz = (Quiz) session.getAttribute("quizName");
			QuizQuestions questions = quiz.getQuestions();
			String timeSpentString = (String) session.getAttribute("time");
			long timeSpent = Long.parseLong(timeSpentString);
			long hours = (timeSpent / (1000*60*60)) % 24;
	        long minutes = ((timeSpent / (1000*60)) % 60);
	        long seconds = (timeSpent / 1000) % 60 ;
		%>
<title>Result Page</title>

</head>
<body>
	
	<jsp:include page="logout.html"></jsp:include>

<!-- <ul id="menu">
  <li id="que">questions: </li>
  <li id="ans">your answers: </li>
  <li id="cor">correct answers:</li>
</ul> -->

	<%	
		int score = 0;
		for(Question q: questions){
			boolean b=q.isUsersAnswerCorrect();
			out.println("<p id=\"resultop\">Question: "+q.getQuestionText()+"      Ans: "+q.getUsersChoice()+"      Correct: "+b+"</p>");
			if(b)score++;
		}
		out.println("<p>Your score is: " + score+"</p>");
	%>
	
	
	
	<div>
		<p><% if(hours==0) out.println();%></p>
		<p>Time Spent: <%=hours%> hours, <%=minutes%> min, <%=seconds%> sec</p>
	</div>
	
	<div>
		<p> comparisons to user's past performance</p>
		<p> comparisons with top performers or user's friends</p>
	</div>

</body>
</html>