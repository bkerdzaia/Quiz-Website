/* validation for input name */
function validateName() {
	var pat = /^[\w]+$/;
	if (pat.test(document.getElementById("login-form-id").userName.value)) {
		return true;
	}
	alert("not valid name: " + document.getElementById("login-form-id").userName.value);
	return false;
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

