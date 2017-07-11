<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/css; charset=UTF-8"%>

<% 
	String color = (String) session.getAttribute("pickedBgCol");	
	if(color == null) {
		color = "white";
	 }
%>

body {
	background-color: <%= color %>;
}

.centered-container {
	display: table;
	margin-right: auto;
	margin-left: auto;
}

<%
	String[] colors = new String[] {
			"black",
			"blue",
			"yellow",
			"brown",
			"pink",
			"purple",
			"gray"
	};

	Random rand = new Random();
	int index = rand.nextInt(colors.length);
	
	String foreground = colors[index];
%>

#funny-page {
	color: <%= foreground %>;
}

a:link {
	color: #0275d8;
	text-decoration: none;
}

a:visited {
	color: #0275d8;
	text-decoration: none;
}

a:hover {
	color: purple;
}

.error {
	background-color: black;
	color: red;
}

.info {
	background-color: Aquamarine; 
}

table.rez td {
	text-align: center;
}
