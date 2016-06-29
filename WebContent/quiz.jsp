<%@page import="java.util.Collections"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="application.*, quiz.*, questions.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<%
			Quiz quiz = (Quiz) session.getAttribute("quizName");
			QuizProperty properties = quiz.getProperty();
			QuizQuestions questions = quiz.getQuestions();
		%>
<title><%=quiz.getName()%> at funz!</title>
</head>
<body>
	<h1>Welcome to <%=quiz.getName()%> by <%=quiz.getCreator()%> </h1>
	
	<div>
		<!-- this method="get" should be changed to method="post" -->
		<form action="ResultPageServlet" method="get">
			<%
				if(properties.isRandomSeq())
					Collections.shuffle(questions);
				for(Question q: questions)
					out.println(q.displayQuestion());
			%>
			<br><br>
			<button type="submit">Submit</button>
		</form>
	</div>
		
</body>
</html>