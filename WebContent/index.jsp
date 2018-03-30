<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style>
	#error_msg {
		color: red;
	}
</style>
</head>
<body>
	<div id="credentials">
		<input id="username" type="text" name="username">
		<input id="password" type="password" name="password">
		<button id="loginButton">Login</button>
		<div id="error_msg"></div>
	</div>
	
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
	var loginButton = document.querySelector("#loginButton");
	var error_msg = document.querySelector("#error_msg");
	
	loginButton.onclick=function() {
		var username = document.querySelector("#username").value;
		var password = document.querySelector("#password").value;
		
		if (username == "" || password == "") {
			error_msg.innerHTML = "A required field is empty.";
			return false;
		}
		$.ajax({
			url:"${pageContext.request.contextPath}/LoginServlet",
			type:"POST",
			data: { 
				username: username,
				password: password
			},
			success: function(response){
				if (response == "success") {
					window.location.replace("http://localhost:8080/CS310-P2/jsp/mainpage.jsp");
				} else {
					error_msg.innerHTML = "Wrong username/password combination.";
				}
			}
		});
	}
</script>
</body>
</html>