<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="quiz.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create a quiz</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>

<script>
	function toName(className, number) {
		return className + number;
	}

	function getInputTextTag(name) {
		return "<input type= \"text\" name= " + name + " required><br>";	
	}
	
	function addPossibleAnswer(className) {
		$("#addPossibleAnswerButtonId" + className).before("Enter Possible Answer: " + 
				getInputTextTag(className));
	}
	
	function getQuestionAnswers(parantClassName, idNumber) {
		var className = toName(parantClassName, idNumber);
		return "Enter Question Here: " + getInputTextTag(className) +
				"Enter Possible Answer: " + getInputTextTag(className) +
				"<button type=\"button\" id=\"addPossibleAnswerButtonId" + className + 
				"\" onclick=\"addPossibleAnswer(" + "'" + className + "')\">+ Add Another answer</button><br>";
	}
	
	var fillInTheBlankQuestionNumber = 0;
	var pictureResponseQuestionNumber = 0;
	var questionResponseTypeNumber = 0;
	var multipleChoiceTypeNumber = 0;

	$(function() {
		$("#addQuestionButton").click(function () {
			var questionTypeValue = $("#questionTypeElements").val();
			if (questionTypeValue === "MultipleChoiceType") {
				multipleChoiceTypeNumber++;
				var className = toName("multipleChoiceQuestion", multipleChoiceTypeNumber);
				$("#addQuestionsType").prepend("<div class=\"multipleChoiceQuestion\"> " +
						"Enter Question Here: " + getInputTextTag(className) +
						"Enter Correct Answer: " + getInputTextTag(className) +
						"Enter Possible Answer: " + getInputTextTag(className) +
						"Enter Possible Answer: " + getInputTextTag(className) +
						"Enter Possible Answer: " + getInputTextTag(className) +
						"</div><br>");	
			} else if(questionTypeValue === "FillInTheBlankType") {
				fillInTheBlankQuestionNumber++;
				$("#addQuestionsType").prepend("<div  class=\"fillInTheBlankQuestion\">" + getQuestionAnswers("fillInTheBlankQuestion", fillInTheBlankQuestionNumber) +"</div>");
			} else if(questionTypeValue === "QuestionResponseType") {
				questionResponseTypeNumber++;
				$("#addQuestionsType").prepend("<div  class=\"questionResponseQuestion\">" + getQuestionAnswers("questionResponseQuestion", questionResponseTypeNumber) +"</div>");
			} else if(questionTypeValue === "PictureResponseType") {
				pictureResponseQuestionNumber++;
				$("#addQuestionsType").prepend("<div  class=\"pictureResponseQuestion\">" + getQuestionAnswers("pictureResponseQuestion", pictureResponseQuestionNumber) +"</div>");
			}
			$("#addQuestionsType").prepend("<p><b>" + questionTypeValue + ": </b></p>");
		});
	});
</script>

</head>
<body>

	<%
		User user = (User) session.getAttribute("userName");
	%>

	<p>creator name is: <b><%= user.getName() %></b></p>
	
	<form action="CreateQuizServlet" method="post"> 
	
		<div>
			<p>quiz name is: <input type="text" name="quizNameText" required><p>
		</div>
	
		<div>
			<p>quiz description is:<p>
			<textarea cols="40" rows="3" name="quizDescriptionText" required></textarea>
		</div>
		
		<div>
			<p>quiz properties: </p>
			<input type="checkbox" name="QuizProperties" value="Random Questions">Random Questions<br>
			<select name="QuizProperties">
				<option value="Display One page">One Page</option>
				<option value="Display Multiple page">Multiple Page</option>
			</select><br>
			<input type="checkbox" name="QuizProperties" value="Immediate correction">Immediate correction<br>
			<input type="checkbox" name="QuizProperties" value="Practice Mode">Practice Mode<br>
		</div>
	
	
		<div>
			<p>Question Types: </p>
			<select id="questionTypeElements">
				<option value="QuestionResponseType">Question-Response</option>
				<option value="FillInTheBlankType">Fill in the Blank</option>
				<option value="MultipleChoiceType">Multiple Choice</option>
				<option value="PictureResponseType">Picture-Response</option>
			</select>
			<button type="button" id="addQuestionButton">add question</button>
		</div>
		
		<div id="addQuestionsType">
		</div>
		<br><br>
		<input type="submit" value="submit quiz"> 
	</form>
	
</body>
</html>