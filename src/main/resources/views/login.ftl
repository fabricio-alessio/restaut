<!DOCTYPE html>

<html>
	<head>
		<title>Login</title>
		<link rel="stylesheet" type="text/css" href="/css/bcash.css" />
	</head>

	<body>
		<div id="bcash">
			Need to Create an account? <a href="/signup">Signup</a><p>
			<h4>Login</h4>
			<#if login_error?? >
				<p class="error">${login_error}</p>
			</#if>
			<form class="b-form b-form-horizontal" method="post">
				<div class="b-form-group">
					Username:
					<input type="text" name="username" value="${username}"/>
				</div>
				<div class="b-form-group">
					Password: 
					<input type="password" name="password" value=""/>
				</div>

				<input class="b-button" type="submit" value="Login">
			</form>
		</div>
	</body>
</html>
