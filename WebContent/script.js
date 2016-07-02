
function passwordStrength() {
	var weak = "";
	if (document.getElementById("register-form-id").password.value.length < 6) {
		weak = "* weak password";
	}
	$("#register-form-id > span").text(weak);
};

function validateName() {
	var pat = /^[\w]+$/;
	return pat.test(document.getElementById("register-form-id").userName.value);
};

function registerClick() {
	$(function() {
		$("#login-form-id").hide();
		$("#register-form-id").show();
	});
};

function loginClick() {
	$(function() {
		$("#register-form-id").hide();
		$("#login-form-id").show();
	});
};
	
/* load log out html */
$(function() {
	$("#logOutId").load("logout.html");
});

/*  */
function loadOrderDate() {
	$(function(){
		$("#performanceOrder").load("order-by-date.jsp");
	});
}

/*  */
$(function() {
	$("input[name=performance]").click(function() {
		// alert(this.value);
		if (this.value === 'date') {
			loadOrderDate();
		} else {
			$("#performanceOrder").empty();
		}
	});
});

/** this function is for adding questions and answers */
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

/* gets questions body for the type of question
 * which has one question and may be have multiple answers */
function getQuestionAnswers(parantClassName, idNumber) {
	var className = toName(parantClassName, idNumber);
	return "Enter Question Here: " + getInputTextTag(className) +
			"Enter Possible Answer: " + getInputTextTag(className) +
			"<button type=\"button\" id=\"addPossibleAnswerButtonId" + className + 
			"\" onclick=\"addPossibleAnswer(" + "'" + className + "')\">+ Add Another answer</button><br>";
}

function getPictureResponseQuestion(parantClassName, idNumber) {
	var className = toName(parantClassName, idNumber);
	return "Enter Question Here: " + getInputTextTag(className) +
			"Enter URL Here: " + getInputTextTag(className) +
			"Enter Possible Answer: " + getInputTextTag(className) +
			"<button type=\"button\" id=\"addPossibleAnswerButtonId" + className + 
			"\" onclick=\"addPossibleAnswer(" + "'" + className + "')\">+ Add Another answer</button><br>";
}

/* variables for counting question types added number */
var fillInTheBlankQuestionNumber = 0;
var pictureResponseQuestionNumber = 0;
var questionResponseTypeNumber = 0;
var multipleChoiceTypeNumber = 0;

/* add question function */
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
			$("#addQuestionsType").prepend("<div  class=\"pictureResponseQuestion\">" + getPictureResponseQuestion("pictureResponseQuestion", pictureResponseQuestionNumber) +"</div>");
		}
		$("#addQuestionsType").prepend("<p><b>" + questionTypeValue + ": </b></p>");
	});
});