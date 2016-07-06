 // Code to dynamically change user's information

function showFields(){
	var element = document.getElementById("user-change-fields");

	element.innerHTML =
		"<input type=\"text\" id=\"new-picture\" placeholder=\"New Picture Url\">" + 
		"<input type=\"text\" id=\"new-about-me\" placeholder=\"Update info\">"+
		"<input type=\"submit\" id=\"submit\" value=\"Submit\" onclick=\"updateUser()\">";
	
}

function updateUser(){
	var servletPath = "EditUserInfo";
	var newPicUrl = $("#new-picture").val();
	var newAboutMe = $("#new-about-me").val();
	var userName  = $('#userName').val(); 

	xmlhttp = new XMLHttpRequest();

	// Register listener for the change of state (pass callback)
	xmlhttp.onreadystatechange = function() { 
		if (xmlhttp.readyState == gotResponse && xmlhttp.status == successMark) {  
			alert("success");
		}
	}
	
	$.get(servletPath,
			{
			newPicUrl:newPicUrl,
			newDesc:newAboutMe,
			userName:userName
			}, function(data){
				location.reload();
			});

}