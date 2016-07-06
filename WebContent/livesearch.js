/**
 * Functions implementing live search or "search suggestions"
 * on various text fields. (AJAX technology is used) 
 */

var gotResponse = 4;
var successMark = 200;

var userSuggestionsServlet = "UsersLiveSearch";
var quizSuggestionsServlet = "QuizLookupServlet";
var userSuggestionsBoxId = "live-user-suggestions";
var quizSuggestionsBoxId = "live-quiz-suggestions";


// General function sending request to servers
function interactWithServer(servletPath, resultBoxId, searchTerm) {
	if (searchTerm.length == 0) { // just empty suggestions box and return
		document.getElementById(resultBoxId).innerHTML = "";
		hideBorders(resultBoxId);
		return;
	}
	if (!window.XMLHttpRequest) 
		return; // If the type of request is not supported by browser return
	    
	xmlhttp = new XMLHttpRequest();

	// Register listener for the change of state (pass callback)
	xmlhttp.onreadystatechange = function() { 
		if (xmlhttp.readyState == gotResponse && xmlhttp.status == successMark) {  
			document.getElementById(resultBoxId).innerHTML = xmlhttp.responseText;
			if (xmlhttp.responseText != "")
				showBorders(resultBoxId);
			else 
				hideBorders(resultBoxId);
		}
	}
	xmlhttp.open("GET", servletPath + "?searchTerm=" + searchTerm, true);
	xmlhttp.send();
}

function hideBorders(resultBoxId){
	$('#' + resultBoxId).removeClass("show-borders");
	$('#' + resultBoxId).addClass("hide-borders");

};

function showBorders(resultBoxId){
	$('#' + resultBoxId).removeClass("hide-borders")
	$('#' + resultBoxId).addClass("show-borders");	
};


// Send request to 'similar users' servlet
function searchSimilarUsers(searchTerm){
	interactWithServer(userSuggestionsServlet, userSuggestionsBoxId, searchTerm);
};

// Send request to 'similar quizzes' servlet
function searchSimilarQuizzes(searchTerm){
	interactWithServer(quizSuggestionsServlet, quizSuggestionsBoxId, searchTerm);
}