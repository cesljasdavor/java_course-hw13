<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Generički css za sve stranice -->
<link rel="stylesheet" type="text/css" href="styles.jsp">
<link rel="icon" type="image/x-icon" href="logo.png">
<link
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet">
<title>Webapp2:Home</title>
</head>
<body>
	<h1 align="center" style="color: navy">
		Welcome to my second web-app <span style="color: black"
			class="fa fa-rebel" aria-hidden="true"></span>
	</h1>
	<p>
		You can choose background color of all pages from this application by
		visiting <a href="/webapp2/colors.jsp">Background color chooser</a>
	</p>

	<p>
		Here you can check out <a href="/webapp2/trigonometric?a=0&b=90">Trigonometric
			demo</a>
	</p>
	<br>
	<h3 align="center">Custom trigonometric table setup</h3>
	<div class="centered-container">
		<form action="trigonometric" method="GET">
			Initial angle:<br> 
			<input type="number" name="a" min="0"max="360" step="1" value="0"><br> 
			Final angle:<br>
			<input type="number" name="b" min="0" max="360" step="1" value="360"><br>
			<input type="submit" value="To table">
			<input type="reset" value="Reset">
		</form>
	</div>
	<hr>
	<p>
		Here is one really <a href="/webapp2/stories/funny.jsp">funny
			story</a>
	</p>
	<br>
	<p>
		Check out our <a href="/webapp2/report.jsp">Operating Systems
			usage report</a>
	<hr>
	<p>
		Here you can get <a href="/webapp2/powers?a=1&b=100&n=3">Demo
			powers .xls file</a>
	</p>
	<p>
		Each sheet has numbers in range [<i>from</i>, <i>to</i>] in one column
		and in the next that numbers powered by <i>i</i>, where <i>i</i> is in
		range [<i>1</i>,<i>n</i>]
	</p>
	<p>
		Therefor document will contain <i>n</i> sheets
	</p>
	<br>
	<h3 align="center">Custom number powers .xls file</h3>
	<div class="centered-container">
		<form action="powers" method="GET">
			from <i>(a)</i>:<br> 
			<input type="number" name="a" min="-100" max="100" step="1" value="0"><br> 
			to <i>(b)</i>:<br>
			<input type="number" name="b" min="-100" max="100" step="1" value="50"><br> 
			number of sheets <i>(n)</i>:<br> 
			<input type="number" name="n" min="1" max="5" step="1" value="2"><br>
			<input type="submit" value="Download .xls"> 
			<input type="reset" value="Reset">
		</form>
	</div>
	<hr>
	<p>
		On this <a href="/webapp2/appinfo.jsp">link</a> you can check out some
		information about this application
	</p>
	<p>
		This <a href="/webapp2/voting">link</a> leads to my favourite band
		voting application
	</p>
	<hr>
	<h4 align="center">
		Autor ove stranice je: <b style="color: blue">Davor Češljaš</b> <span
			class="fa fa-copyright" aria-hidden="true"></span>
	</h4>
</body>
</html>