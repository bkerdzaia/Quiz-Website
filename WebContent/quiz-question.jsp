<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="application.*, quiz.*, questions.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<p>Question: ${param.question}</p>
	
	<%
		Quiz quiz = (Quiz) session.getAttribute("quizName");
		QuizQuestions questions = quiz.getQuestions();
		int questionIndex = Integer.parseInt(request.getParameter("question"));
		Question question = questions.get(questionIndex-1);
	%>
	
	<p>Question is: <b><%= question.getQuestionText() %></b></p>
	<p>Possible answers are: <b>
	<%-- 
		for(String answer : question.getPossibleAnswers()) {
			out.println("<p>" + answer + "</p>");
		}
	--%></b></p>
	
	<%
		if (questions.size() > questionIndex - 1) {
			out.println("<a href=\"quiz-result-page.jsp\"><button type=\"button\">finish</button></a>");
		} else {
			out.println("<a href=\"quiz-question.jsp?question=" + questionIndex + 1 + 
					"\"><button type=\"button\">next</button></a>");
		}
	%>
	

</body>
</html>