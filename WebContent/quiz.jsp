<%@page import="java.util.Collections"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="application.*, quiz.*, questions.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
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
				if(properties.isOnePage()){
					out.println("<form name=\"answers\"action=\"ResultPageServlet\" method=\"post\">");
					for(Question q: questions)
						out.println(q.displayQuestion());
					out.println("<br><br><button type=\"submit\"\">Submit</button>");
					out.println("</form>");
				}else{
					out.println("<form name=\"answers\"action=\"ResultPageServlet\" method=\"post\">");
					String hide = "";
					for(int i =0; i<questions.size();i++){
						if(i>0) hide = " style='display:none'";
						out.println("<div id = questionId" + i + hide + ">");
						out.println(questions.get(i).displayQuestion());
						if(i == questions.size()-1)
							out.println("<br><br><button type=\"submit\" >Submit</button>");
						else
							out.println("<button type=\"button\" onclick=\"myFunction("+i+")\">next</button>");
						out.println("</div>");
					}
					out.println("</form>");
				}
			%>
			<%-- if(questIndex<questions.size()-1){
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
						
					} --%>
		<!-- </form>  --> 
	</div>
	
	
</body>
</html>
