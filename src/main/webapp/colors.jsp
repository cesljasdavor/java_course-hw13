<%@ page import="java.awt.Color" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="styles.jsp">
<title>Background color chooser</title>
</head>

<body>
	<h4 align="center">Supported background colors are:</h4>
	<ol>
		<li><a href="/webapp2/color-changer?color=white">White</a></li>
		<li><a href="/webapp2/color-changer?color=red">Red</a></li>
		<li><a href="/webapp2/color-changer?color=green">Green</a></li>
		<li><a href="/webapp2/color-changer?color=cyan">Cyan</a></li>
	</ol>
	<a href="/webapp2/index.jsp">Go back</a>
</body>
</html>