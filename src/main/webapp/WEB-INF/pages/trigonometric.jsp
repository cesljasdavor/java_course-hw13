<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/webapp2/styles.jsp">
<title>Trigonometrical values</title>
</head>
<body>
	<h1 align="center">Trigonometric values in range [${a}, ${b}]</h1>
	<div class="centered-container">
		<a href="/webapp2/">Go back</a>
		<table border="1">
			<tr>
				<th>Angle in degrees</th>
				<th>Sinus</th>
				<th>Cosinus</th>
			</tr>
			<c:forEach var="sinusValue" items="${sinusValues}" varStatus="status">
				<tr>
					<td>${a + status.index}</td>
					<td>${sinusValue}</td>
					<td>${cosinusValues[status.index]}</td>
				</tr>
			</c:forEach>
		</table>
		<a href="/webapp2/">Go back</a>
	</div>
</body>
</html>