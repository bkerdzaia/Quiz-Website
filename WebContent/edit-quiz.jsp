<%@page import="questions.Question"%>
<%@page import="application.ServletConstants"%>
<%@page import="quiz.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page errorPage="error-page.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Edit quiz</title>
	<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
	<% 
		Quiz quiz = (Quiz) request.getSession().getAttribute(ServletConstants.QUIZ_NAME_PARAM);
		QuizProperty property = quiz.getProperty();
	%>
	
	<form id="quiz-creation" action="CreateQuizServlet" method="post"> 
	
		<div class="topbar">
			<div id="quiz-name">
				<p>Name of the quiz: </p>
				<input class="quiz-name-field" type="text" name="quizNameText" value="<%= quiz.getName() %>" placeholder="Enter quiz name"
					oninvalid="alert('enter valid characters');" pattern='^[\w ]+$' required>
			</div>
		
			<div id="quiz-description">
				<p>Description of the quiz:<p>
				<textarea placeholder=" Descibe your quiz" rows="4" cols="27" name="quizDescriptionText" required><%= quiz.getDescription() %></textarea>
			</div>
			
			<div id="quiz-properties">
				<p>Define properties of your quiz: </p>
				<div class="property-entry">
					<p> Randomize order </p> <input type="checkbox" name="QuizProperties" value="RandomQuestions" <%= property.isRandomSeq() ? "checked" : "" %>>
				</div>
				<div class="property-entry">
					<p> View mode:   </p>
						<select name="QuizProperties">
							<option value="DisplayOnePage" <%= property.isOnePage() ? "selected" : "" %>>One Page</option>
							<option value="DisplayMultiplePage" <%= property.isOnePage() ? "" : "selected" %>>Multiple Page</option>
						</select>
				</div>
				<div class="property-entry">
					<p>Immediate correction </p> <input type="checkbox" name="QuizProperties" value="ImmediateCorrection" <%= property.isInstantlyMarked() ? "checked" : "" %>>
				</div>
			</div>
		</div>
	
		<div id="questions">			
			<div id="addQuestionsType"> 
				 <%
					QuizQuestions questions = quiz.getQuestions(); 
				 	for (int i=0; i<questions.size(); i++) {
				 		out.println(questions.get(i).getQuestionHtmlForm(i));
				 	}
				 %>				
			</div>
		</div>

		<div id="submit">
			<input type="hidden" name="updateQuiz" value="<%= quiz.getName() %>">
			<input type="submit" value="Update quiz">
		</div>
	</form>
	

</body>
</html>