/**
 * Functions implementing live search or "search suggestions"
 * on various text fields. (AJAX technology is used) 
 */

var gotResponse = 4;
var successMark = 200;
var userSuggestionsServlet = "UsersLiveSearch";
var resultBoxId = "live-user-suggestions"

function searchSimilarUsers(searchTerm) {
	if (searchTerm.length == 0) { // just empty suggestions box and return
		document.getElementById(resultBoxId).innerHTML = "";
		hideBorders();
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
				showBorders();
			else 
				hideBorders();
		}
	}
	xmlhttp.open("GET", userSuggestionsServlet + "?searchTerm=" + searchTerm, true);
	// Add header here
	xmlhttp.send();
}

function hideBorders(){
	$('#' + resultBoxId).removeClass("show-borders");
	$('#' + resultBoxId).addClass("hide-borders");

}

function showBorders(){
	$('#' + resultBoxId).removeClass("hide-borders")
	$('#' + resultBoxId).addClass("show-borders");	

}