/** this function is for adding questions and answers */

/* variables for counting question types added number */
var fillInTheBlankQuestionNumber = 0;
var pictureResponseQuestionNumber = 0;
var questionResponseTypeNumber = 0;
var multipleChoiceTypeNumber = 0;

/* count of all created questions */
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

/* returns a button which adds possible answers */
function getAddAnswerButton(className) {
	return  "<button type=\"button\" id=\"addPossibleAnswerButtonId" + className + 
			"\" onclick=\"addPossibleAnswer(" + "'" + className + "')\">+ Add Another answer</button>";
}

/* gets questions body for the type of question
 * which has one question and may be have multiple answers */
function getQuestionAnswers(idNumber) {
	var className = toName("questionResponseQuestion", idNumber);
	return "Enter Question Here: " + getInputTextTag(className) +
			"Enter Possible Answer: " + getInputTextTag(className) + getAddAnswerButton(className) + getCancelButton();
}

/* gets questions body for the type of fill blank
 * it requires user to input one blank in question field */
function getFillBlank(idNumber) {
	var className = toName("fillInTheBlankQuestion", idNumber);
	return "Enter Question Here: <input type='text' name='" + className + 
			"' oninvalid='alert(\"question should have one blank ___ (three _) !\");' pattern='([^_]*(___){1}[^_]+)|([^_]+(___){1}[^_]*)' required><br>" +
			"Enter Possible Answer: " + getInputTextTag(className) + getAddAnswerButton(className) + getCancelButton();
}

/* gets questions body for the type of question
 * which has one question and the url of picture 
 * and may be have multiple answers */
function getPictureResponseQuestion(idNumber) {
	var className = toName("pictureResponseQuestion", idNumber);
	return "Enter Question Here: " + getInputTextTag(className) +
			"Enter URL Here: " + getInputTextTag(className) +
			"Enter Possible Answer: " + getInputTextTag(className) + getAddAnswerButton(className) + getCancelButton();
}

/* removes the question of given id */
function removeQuestion(questionNom) {
	$("#questionNumber" + questionNom).remove();
}

/* returns the cancel button which removes question */
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
					+ getFillBlank(fillInTheBlankQuestionNumber) +"</div>");
		} else if(questionTypeValue === "QuestionResponseType") {
			questionResponseTypeNumber++;
			var className = toName("questionResponseQuestion", questionResponseTypeNumber);
			$("#addQuestionsType").prepend("<div  class=\"questionResponseQuestion\" id='questionNumber" + questionNumber + "'> "
					+ "<p><b>" + questionTypeValue + ": </b></p> "  
					+ getQuestionAnswers(questionResponseTypeNumber) +"</div>");
		} else if(questionTypeValue === "PictureResponseType") {
			pictureResponseQuestionNumber++;
			$("#addQuestionsType").prepend("<div  class=\"pictureResponseQuestion\" id='questionNumber" + questionNumber + "'> "
					+ "<p><b>" + questionTypeValue + ": </b></p> "  
					+ getPictureResponseQuestion(pictureResponseQuestionNumber) +"</div>");
		}
		questionNumber++;
	});
});