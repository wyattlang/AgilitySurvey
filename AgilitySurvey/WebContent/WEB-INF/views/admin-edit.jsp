<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<%
	int questionCounter = 0;
%>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Edit</title>
</head>
<body>
	<div class="container">
		<div class="form-group">
			<form action="admin/edit" method="POST">
				<div>
					<h1><c:out value="${survey.name}"/></h1>
				</div>
				<div>
					<c:forEach items="${survey.questions}" var="q">
						<div>
							<p class="form-control"><c:out value="${q.prompt}"/></p>
						</div>
						<div>
							<% int choiceCounter = 0; %>
							<c:forEach items="${q.choices}" var="c">
								<div>
									<input type="checkbox" class="form-control" name="<%= "q" + questionCounter + "c" + choiceCounter++ %>"/>
									<label for="${c.text}">${c.text}</label>
								</div>
							</c:forEach>
							<% questionCounter++; %>
						</div>
					</c:forEach>
				</div>
			</form>
		</div>
	</div>
</body>
</html>