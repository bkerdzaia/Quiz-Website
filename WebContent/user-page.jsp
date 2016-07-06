<%@page import="quiz.*"%>
<%@page import="database.*"%>
<%@page import="application.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>User page: + ${param.userName}</title>
	<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>

	<%
		User user = (User) session.getAttribute(ServletConstants.USER_OBJECT_ATTRIBUTE);
		FriendList friends = user.getFriends();
		String loggedUser = (String) session.getAttribute(ServletConstants.USER_NAME_PARAM);
	%>
	
	<jsp:include page="logout.html"></jsp:include>
	<jsp:include page="homepage-link.jsp"></jsp:include>

	<p>description: <%= user.getAboutMe()  %></p>
	
	<p>my picture: </p>
	<img src="<%= user.getPictureUrl() %>" alt="<%= user.getName() %>">


	<div id="user-info">
		<p>user name: <%= user.getName() %></p>
		<p><b>session attribute value: <%= session.getAttribute(ServletConstants.USER_NAME_PARAM) %></b></p>
	</div>
	
	<!-- send friend request or send message display to the user that is viewed by logged in user -->
	<%
		// allow to change personal information
		if(loggedUser.equals(user.getName())) 
			return;
		if (friends.contains(loggedUser)) {
			// send message
			out.println("<div>" +
							"<form action='send-note.jsp'>" +
								"<input type='hidden' name='" + ServletConstants.USER_NAME_PARAM + "' value='" + user.getName() + "'>" +
								"<input type='submit' value='Send Message'>" +
							"</form>" +
						"</div>");
			out.println("<div>" +
					"<form action='RemoveFriend' method='post'>" +
						"<input type='hidden' name='" + ServletConstants.USER_NAME_PARAM + "' " +
							"value='" + user.getName() + "'>" + 
						"<input type='submit' value='remove friend'>" +
					 "</form>" +
				"</div>");
		} else {
			// friend request
			out.println("<div id='friendRequestId'>" +
							"<form action='SendFreindRequest' method='post'>" +
								"<input type='hidden' name='" + ServletConstants.USER_NAME_PARAM + "' " +
									"value='" + user.getName() + "'>" + 
								"<input type='submit' value='send friend request'>" +
							 "</form>" +
						"</div>");
		}
	%>
	
</body>
</html>