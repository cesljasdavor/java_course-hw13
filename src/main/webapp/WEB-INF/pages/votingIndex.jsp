<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/webapp2/styles.jsp">
<title>Voting for bands</title>
</head>
<body>
	<h1 align="center">Voting for favourite band:</h1>
	<p>From bands below, which is your favourite? Click on the link below to vote!</p>
	<ol>
		<c:forEach var="bandInfo" items="${bandInfos}">
			<li><a href="/webapp2/voting-vote?id=${bandInfo.id}">${bandInfo.name}</a></li>
		</c:forEach>
	</ol>
</body>
</html>