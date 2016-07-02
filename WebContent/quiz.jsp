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
			Integer questIndex = (Integer)session.getAttribute("questIndex");
			
		%>
<title><%=quiz.getName()%> at funz!</title>
</head>
<body>
	<h1>Welcome to <%=quiz.getName()%> by <%=quiz.getCreator()%> </h1>
	
	
	<div id="div1">
		<!-- this method="get" should be changed to method="post" -->
		<!-- <form action="ResultPageServlet" method="get">  -->
			<%
				if(properties.isRandomSeq() && questIndex==0)
					Collections.shuffle(questions);
				if(properties.isOnePage()){
					out.println("<form name=\"answers\"action=\"ResultPageServlet\" method=\"post\">");
					for(Question q: questions)
						out.println(q.displayQuestion());
					out.println("<br><br><button type=\"submit\"\">Submit</button>");
				}else{
					if(questIndex<questions.size()-1){
						if(questIndex!=0) out.println(request.getAttribute("possibleAnswer"));
						out.println("<form action=\"quiz.jsp\" method=\"get\">");
						out.println(questions.get(questIndex).displayQuestion());
						out.println("<button type=\"submit\">next</button>");
						session.setAttribute("questIndex", questIndex+1);
					}
					else {
						out.println("<form action=\"ResultPageServlet\" method=\"get\">");
						out.println(questions.get(questIndex).displayQuestion());
						out.println("<br><br><button type=\"submit\">Submit</button>");
						
					}
					
				}
			%>
		<!-- </form>  --> 
	</div>
	
	<script>
	function myFunction() {
    	var quest=document.getElementsByName("possibleAnswer");
    	//for(var i=0; i<quest.length; i++)
    	//	questions.get(i).setUsersChoice(quest[i].value);
    	document.getElementById("po").value=Array.prototype.slice.call(quest);
	}
	</script>
		
</body>
</html>