<%@page import="java.util.Collections"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="application.*, quiz.*, questions.*"%>
<%@ page errorPage="error-page.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<link rel="stylesheet" type="text/css" href="style.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<%
			Quiz quiz = (Quiz) session.getAttribute("quizName");
			QuizProperty properties = quiz.getProperty();
			QuizQuestions questions = quiz.getQuestions();
			Integer questIndex = (Integer)session.getAttribute("questIndex");
			
		%>
<title><%=quiz.getName()%> at funz!</title>

<style>

	p{
	text-align:center;
	}
	[name=possibleAnswer]{
	display: block;
	padding: 15px;
	margin: auto;
	margin-left: auto;
	margin-right: auto;
	}
</style>
</head>
<body>
	<jsp:include page="logout.html"></jsp:include>

	<script>
	function myFunction(ind) {
		$("#questionId"+ind).hide();
		$("#questionId"+(ind+1)).show();
	}
	</script>
		
	<h1>Welcome to <%=quiz.getName()%> by <%=quiz.getCreator()%> </h1>
	
	
	<div id="div1">
		<!-- this method="get" should be changed to method="post" -->
		<!-- <form action="ResultPageServlet" method="get">  -->
			<%
				if(properties.isRandomSeq() && questIndex==0)
					Collections.shuffle(questions);
				out.println("<form name=\"answers\"action=\"ResultPageServlet\" method=\"post\">");
				String hide = "";
				for(int i =0; i<questions.size();i++){
					if(i>0 && !properties.isOnePage()) hide = " style='display:none'";
					out.println("<div id = questionId" + i + hide + ">");
					out.println(questions.get(i).displayQuestion());
					if(i == questions.size()-1)
						out.println("<br><br><button type=\"submit\" onclick=\"dateFunction()\" >Submit</button>");
					else if(!properties.isOnePage())
						out.println("<button type=\"button\" id=\"next\"onclick=\"myFunction("+i+")\">next</button>");
					out.println("</div>");
				}
				out.println("<input type=\"hidden\" id=\"w\" name=\"time\" value=\"\">");
				out.println("</form>");
			%>
	</div>
	
	<script>
	var date1 = new Date();
	function dateFunction() {
		var date2 = new Date();
		var TimeSpent = date2-date1;
		
		document.getElementById("w").value = TimeSpent;
	}
	</script>
</body>
</html>
