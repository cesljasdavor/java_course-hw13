<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/webapp2/styles.jsp">
<title>Error: Wrong parameters</title>
</head>
<body>
	<div class="error centered-container">
		<h1 align="center">Wrong parameters</h1>
		<p>Unfortunately you have passed some wrong parameters.</p>
		<p>Here is the list of wrong parameters and for each the range
			they should be in:</p>
		<ul style="list-style-type: none">
			<c:forEach var="paramInfo" items="${ParamInfos}">
				<li>${paramInfo.name} = ${paramInfo.value}. Must be an integer
					in range [${paramInfo.min}, ${paramInfo.max}]</li>
			</c:forEach>
		</ul>
		<a href="/webapp2/">Back to home page</a>
	</div>
</body>
</html>