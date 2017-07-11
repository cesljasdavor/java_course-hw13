<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="styles.jsp">
<title>Voting results</title>
</head>
<body>
<body>
	<h1 align="center">Voting results</h1>
	<a href="/webapp2/">Back to home page</a>
	<p>These are the voting results</p>
	<table border="1" cellspacing="0" class="rez centered-container">
		<thead>
			<tr>
				<th>Band</th>
				<th>No. of votes</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="bandInfo" items="${bandInfos}">
				<tr>
					<td>${bandInfo.name}</td>
					<td>${bandInfo.votes}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<h2>Graphics display of the results</h2>
	<img class="centered-container" alt="Pie-chart" src="/webapp2/voting-graphics" width="400" height="400" />
	<h2>Results in XLS format</h2>
	<p>
		Results in XLS format are available <a href="/webapp2/voting-xls">here</a>
	</p>
	<h2>Various</h2>
	<p>Examples of winning bands song:</p>
	<ul>
		<c:forEach var="winner" items="${winners}">
		<li><a href="${winner.represSong}"
			target="_blank">${winner.name}</a></li>
		</c:forEach>
	</ul>
</body>

</body>

</html>