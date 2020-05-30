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
	<title><c:out value="${survey.name}"/></title>
</head>
<body>
	<div class="container">
		<div class="form-group"> 
			<form action="survey/completed" method="POST">
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
				<div>
					<br/>
					<label for="comment">Comments</label>
					<br/>
					<textarea placeholder="What did we miss?" name="comment"></textarea>
				</div>
				<div>
					<input type="submit" value="Submit Survey" name="submitButton"/> 
				</div>
			</form>
		</div>	
	</div>	
</body>
</html>