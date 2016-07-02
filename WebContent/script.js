
/* load log out html */
$(function() {
	$("#logOutId").load("logout.html");
});


/* this functions makes a user to order user's past performance
 * for specific quiz by date, percent correct, and amount of time */

function showOrderByDate() {
	$("#performanceOrderByDate").show();
	$("#performanceOrderByPercentCorrect").hide();
	$("#performanceOrderByAmountTime").hide();
}

function showOrderByPercentCorrect() {
	$("#performanceOrderByDate").hide();
	$("#performanceOrderByPercentCorrect").show();
	$("#performanceOrderByAmountTime").hide();
}

function showOrderByAmountTime() {
	$("#performanceOrderByDate").hide();
	$("#performanceOrderByPercentCorrect").hide();
	$("#performanceOrderByAmountTime").show();
}



/** this function is for adding questions and answers */

/* variables for counting question types added number */
var fillInTheBlankQuestionNumber = 0;
var pictureResponseQuestionNumber = 0;
var questionResponseTypeNumber = 0;
var multipleChoiceTypeNumber = 0;

var questionNumber = 0;


/* return concatenation of className and number */
function toName(className, number) {
	return className + number;
}

/* return the text field of given name */
function getInputTextTag(name) {
	return "<input type= \"text\" name= " + name + " required><br>";	
}

/* adds possible answer field in addPossibleAnswerButtonId id's before */
function addPossibleAnswer(className) {
	$("#addPossibleAnswerButtonId" + className).before("Enter Possible Answer: " + 
			getInputTextTag(className));
}

function getAddAnswerButton(className) {
	return  "<button type=\"button\" id=\"addPossibleAnswerButtonId" + className + 
			"\" onclick=\"addPossibleAnswer(" + "'" + className + "')\">+ Add Another answer</button>";
}

/* gets questions body for the type of question
 * which has one question and may be have multiple answers */
function getQuestionAnswers(parantClassName, idNumber) {
	var className = toName(parantClassName, idNumber);
	return "Enter Question Here: " + getInputTextTag(className) +
			"Enter Possible Answer: " + getInputTextTag(className) + getAddAnswerButton(className) + getCancelButton();
}

function getPictureResponseQuestion(parantClassName, idNumber) {
	var className = toName(parantClassName, idNumber);
	return "Enter Question Here: " + getInputTextTag(className) +
			"Enter URL Here: " + getInputTextTag(className) +
			"Enter Possible Answer: " + getInputTextTag(className) + getAddAnswerButton(className) + getCancelButton();
}


function removeQuestion(questionNom) {
	$("#questionNumber" + questionNom).remove();
}

function getCancelButton() {
	return  "<button type=\"button\" onclick=\"removeQuestion(" + questionNumber + ")\">Cancel</button><br>";
}

/* add question function */
$(function() {
	$("#addQuestionButton").click(function () {
		var questionTypeValue = $("#questionTypeElements").val();
		if (questionTypeValue === "MultipleChoiceType") {
			multipleChoiceTypeNumber++;
			var className = toName("multipleChoiceQuestion", multipleChoiceTypeNumber);
			$("#addQuestionsType").prepend("<div class=\"multipleChoiceQuestion\" id='questionNumber" + questionNumber + "'> "
					+ "<p><b>" + questionTypeValue + ": </b></p> " +
					"Enter Question Here: " + getInputTextTag(className) +
					"Enter Correct Answer: " + getInputTextTag(className) +
					"Enter Possible Answer: " + getInputTextTag(className) +
					"Enter Possible Answer: " + getInputTextTag(className) +
					"Enter Possible Answer: " + getInputTextTag(className) +
					getCancelButton() + "<br></div>");	
		} else if(questionTypeValue === "FillInTheBlankType") {
			fillInTheBlankQuestionNumber++;
			$("#addQuestionsType").prepend("<div  class=\"fillInTheBlankQuestion\" id='questionNumber" + questionNumber + "'> "
					+ "<p><b>" + questionTypeValue + ": </b></p> "  
					+ getQuestionAnswers("fillInTheBlankQuestion", fillInTheBlankQuestionNumber) +"</div>");
		} else if(questionTypeValue === "QuestionResponseType") {
			questionResponseTypeNumber++;
			var className = toName("questionResponseQuestion", questionResponseTypeNumber);
			$("#addQuestionsType").prepend("<div  class=\"questionResponseQuestion\" id='questionNumber" + questionNumber + "'> "
					+ "<p><b>" + questionTypeValue + ": </b></p> "  
					+ getQuestionAnswers("questionResponseQuestion", questionResponseTypeNumber) +"</div>");
			addPossibleAnswer(className);
		} else if(questionTypeValue === "PictureResponseType") {
			pictureResponseQuestionNumber++;
			$("#addQuestionsType").prepend("<div  class=\"pictureResponseQuestion\" id='questionNumber" + questionNumber + "'> "
					+ "<p><b>" + questionTypeValue + ": </b></p> "  
					+ getPictureResponseQuestion("pictureResponseQuestion", pictureResponseQuestionNumber) +"</div>");
		}
		questionNumber++;
	});
});