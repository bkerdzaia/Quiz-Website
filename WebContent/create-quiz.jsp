<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="quiz.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create a quiz</title>
</head>
<body>

	<%
		User user = (User) session.getAttribute("userName");
	%>

	<p>creator name is: <b><%= user.getName() %></b></p>
	
	<div>
		<p>quiz name is: <input type="text" name="quizNameText"><p>
	</div>

	<div>
		<p>quiz description is:<p>
		<textarea cols="40" rows="3" name="quizDescriptionText"></textarea>
	</div>
	
	<div>
		<p>quiz properties: </p>
		<form>
			<input type="checkbox" name="QuizProperties" value="Random Questions">Random Questions<br>
			<select>
				<option value="Display One page">One Page</option>
				<option value="Display Multiple page">Multiple Page</option>
			</select><br>
			<input type="checkbox" name="QuizProperties" value="Immediate correction">Immediate correction<br>
			<input type="checkbox" name="QuizProperties" value="Practice Mode">Practice Mode<br>
		</form>
	</div>


	<div>
		<p>Question Types: </p>
		<form>
			<select>
				<option value="QuestionType">Question-Response</option>
				<option value="QuestionType">Fill in the Blank</option>
				<option value="QuestionType">Multiple Choice</option>
				<option value="QuestionType">Picture-Response</option>
			</select>
			<button>add question</button>
		</form>
	</div>
	
	<!--  multiple choice visualization -->
	<div>
		<form id="multipleChoiceQuestion">
			Enter Question Here: <input type="text" name="question"><br>
			Enter Correct Answer: <input type="text" name="correctAnswer"><br>
			Enter Possible Answer: <input type="text" name="possibleAnswer1"><br>
			Enter Possible Answer: <input type="text" name="possibleAnswer2"><br>
			Enter Possible Answer: <input type="text" name="possibleAnswer2"><br>
			<input type="submit" value="add">
			<button value="cancel">cancel</button>
		</form>
	</div>
	
	<!--  fill in the blank visualization -->
	<div>
		<form id="fillInTheBlankQustion">
			Enter Question Here: <input type="text" name="question"><br>
			Enter Possible Answer: <input type="text" name="possibleAnswers"><br>
			<button value="addPossibleAnswer">+ Add Another answer</button><br>
			<input type="submit" value="add">
			<button value="cancel">cancel</button>
		</form>
	</div>
	
	<!--  question response visualization -->
	<div>
		<form id="questionResponseQustion">
			Enter Question Here: <input type="text" name="question"><br>
			Enter Possible Answer: <input type="text" name="possibleAnswers"><br>
			<button value="addPossibleAnswer">+ Add Another answer</button><br>
			<input type="submit" value="add">
			<button value="cancel">cancel</button>
		</form>
	</div>
	
	<!--  picture response visualization -->
	<div>
		<form id="pictureResponseQustion">
			Enter Picture URL Here: <input type="text" name="question"><br>
			Enter Possible Answer: <input type="text" name="possibleAnswers"><br>
			<button value="addPossibleAnswer">+ Add Another answer</button><br>
			<input type="submit" value="add">
			<button value="cancel">cancel</button>
		</form>
	</div>
	
</body>
</html>