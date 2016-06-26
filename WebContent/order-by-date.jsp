<%@ page import="application.*, quiz.*, java.util.*"%>
<p><b>order by date</b></p>
<% 
	User user = (User) session.getAttribute("userName");
	History history = user.getHistory(); 
	history.sort(null);
%>