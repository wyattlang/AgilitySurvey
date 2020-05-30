<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Survey Complete</title>
</head>
<body>
	<div>
		<h1>Thank you for completing our survey!</h1>
	</div>
	<div>
		<p>We will E-mail your results to you at: <c:out value="${user.email}"/></p>
	</div>
	<div>
		<p>Visit our Websites</p>
		<a href="https://ntiertraining.com/"><img src="../WebContent/WEB-INF/views/img/nttlogo.png"/></a>
		<a href="http://gsd.guru/"><img src="../WebContent/WEB-INF/views/img/gsdlogo.png"/></a>
	</div>
</body>
</html>