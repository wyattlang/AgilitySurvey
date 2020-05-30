<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sign-Up</title>
</head>
<body>
    <div class="form-group">
        <form action="survey" method="POST">
            <h2 class="form-title">
            	Take Survey
            </h2>
            <div class="form-group">
                <label for="firstName">First Name</label>
                <input type="text" id="firstName" name="firstName" class="form-control">
            </div>
            <div class="form-group">
                <label for="lastName">Last Name</label>
                <input type="text" id="lastName" name="lastName" class="form-control">
            </div>
            <div class="form-group">
                <label for="emailField">Email</label>
                <input type="email" id=emailField name="emailField" class="form-control">
            </div>
            <div class="form-group">
                <input type="submit" id="submitRegistration" class="btn btn-secondary" value="Start">
            </div>
        </form>
    </div>
</body>
</html>