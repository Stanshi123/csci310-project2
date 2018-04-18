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


	<div id="title-label">
		<label>Egalloc</label>
	</div>

	<div id="login-container">
		<div id="error_msg"></div>
		<div id="username-input-container" class="input-container">
			<label>Username</label> <br> <input id="username" type="text"
				class="input-box" placeholder="Enter username" name="username">
		</div>
		<br>
		<div id="username-input-container" class="input-container">
			<label>Password</label> <br> <input id="password"
				type="password" class="input-box" placeholder="Enter password"
				name="password">
		</div>

		<br>
		<div id="buttons-container" class="input-container">
			<button id="loginButton" class="login-page-button">Login</button>
			<button id="signUpButton" class="login-page-button">Sign Up</button>
		</div>

	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script>
		var loginButton = document.querySelector("#loginButton");
		var error_msg = document.querySelector("#error_msg");
		var signUpButton = document.querySelector("#signUpButton");

		signUpButton.onclick = function() {
			var username = document.querySelector("#username").value;
			var password = document.querySelector("#password").value;

			if (username == "" || password == "") {
				error_msg.innerHTML = "A required field is empty.";
				return false;
			}
			$
					.ajax({
						url : "${pageContext.request.contextPath}/SignUpServlet",
						type : "POST",
						data : {
							username : username,
							password : password
						},
						success : function(response) {
							if (response == "success") {
								window.location
										.replace("mainpage.jsp");
							} else {
								error_msg.innerHTML = "Failed to register. Please try another username";
							}
						}
					});
		}

		loginButton.onclick = function() {
			var username = document.querySelector("#username").value;
			var password = document.querySelector("#password").value;

			if (username == "" || password == "") {
				error_msg.innerHTML = "A required field is empty.";
				return false;
			}
			$.ajax({
				url : "${pageContext.request.contextPath}/LoginServlet",
				type : "POST",
				data : {
					username : username,
					password : password
				},
				success : function(response) {
					if (response == "success") {
						window.location
								.replace("mainpage.jsp");
					} else {
						error_msg.innerHTML = "Wrong username/password combination.";
					}
				}
			});
		}
	</script>

</body>
</html>