/* validation for input name */
function validateName() {
	var pat = /^[\w]+$/;
	return pat.test(document.getElementById("register-form-id").userName.value);
};

	
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

