<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Admin</title>
</head>
<body>
	<div>
		<h1>Admin</h1>
	</div>
	<div>
		<h3>Create Survey</h3>
		<form action="admin/create" method="POST"><input type="submit" value="Add Survey"></form>
	</div>
	<div>
		<h2>All Surveys</h2>
		<ul>
			<c:forEach items="${surveyNames}" var="surveyName">
				<li><c:out value="${surveyName}"/></li>
			</c:forEach>
		</ul>
	</div>
</body>
</html>