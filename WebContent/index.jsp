<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/loginpage.css">
<title>Welcome to Egalloc</title>

</head>
<body>


	<div id="title-label"><label>Egalloc</label></div>

	<div id="login-container">
		<div id="error_msg"></div>
		<div id="username-input-container" class="input-container">
			<label>Username</label> <br> 
			<input id="username" type="text" class="input-box" placeholder="Enter username" name="username">
		</div>

		<br>

		<div id="username-input-container" class="input-container">
			<label>Password</label> <br> 
			<input id="password" type="password" class="input-box" placeholder="Enter password" name="password">
		</div>

		<br>

		<div id="buttons-container" class="input-container">
			<button id="loginButton" class="login-page-button">Login</button>
			<button id="signUpButton" class="login-page-button">Sign Up</button>
		</div>
	</div>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script>
		var error_msg = document.querySelector("#error_msg");
		var signUpButton = document.querySelector("#signUpButton");
		var loginButton = document.querySelector("#loginButton");

		signUpButton.onclick=function() {
			callServlet("register");
		}

		loginButton.onclick=function() {
			callServlet("login");
		}

		function callServlet(type) {

			// check for empty fields
			var username = document.querySelector("#username").value;
			var password = document.querySelector("#password").value;			
			if (username == "" || password == "") {
				error_msg.innerHTML = "A required field is empty.";
				return false;
			}

			// see if it's register or login
			var servletName = "${pageContext.request.contextPath}/";
			var errorMessage = "";
			if (type == "register") {
				servletName = servletName + "SignUpServlet";
				errorMessage = "Failed to register. Please try another username";
			}
			else {
				servletName = servletName + "LoginServlet";
				errorMessage = "Wrong username/password combination.";
			}

			// ajax call
			$.ajax({
				url: servletName,
				type: "POST",
				data: {
					username: username,
					password: password
				},
				success: function(response) {
					if (response == "success") {
						window.location.replace("mainpage.jsp");
					} else {
						error_msg.innerHTML = errorMessage;
					}
				}
			});
		}
	</script>

</body>
</html>