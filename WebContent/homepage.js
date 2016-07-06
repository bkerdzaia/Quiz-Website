 /* Fill blank space */
var fillBlankSpace = function(){
	var elements = document.getElementsByClassName("category-content");
	for (var i = 0; i < elements.length; i++){
		if (elements[i].innerHTML.indexOf("<") == -1){
			elements[i].innerHTML = '<p><em> Nothing yet</em></p>';
		}
	}
};

$(document).ready(fillBlankSpace);