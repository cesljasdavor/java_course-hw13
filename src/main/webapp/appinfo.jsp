<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="styles.jsp">
<title>Application Info</title>
</head>
<body>
	<%!
	public String getTimeRunning() {
		long startMillis = (Long) getServletContext().getAttribute("startTime");
		long millis = System.currentTimeMillis() - startMillis;
		long seconds = (millis / 1000) % 60;
		long minutes = (millis / (1000 * 60)) % 60;
		long hours = millis / (1000 * 60 * 60);
		return String.format("%02d hours %02d minutes %02d seconds %03d milliseconds", hours, minutes, seconds,
				millis % 1000);
	}
	%>
	<div class="centered-container info">
		<h1 align="center">Application Information</h1>
		<p class="centered-container">Time running: <span style="color: navy"> <%= getTimeRunning() %></span> </p>
		<a href="/webapp2/">Go back</a>
	</div>
</body>
</html>