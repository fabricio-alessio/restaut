<!DOCTYPE html>

<html>
	<head>
		<title>Sign Up</title>
		<link rel="stylesheet" type="text/css" href="/css/bcash.css" />
	</head>

	<body>
		<div id="bcash">
			Already a user? <a href="/login">Login</a><p>
			<h4>Signup</h4>
			<form class="b-form b-form-horizontal" method="post">
				<div class="b-form-group">
					Username:
					<input type="text" name="username" value="${username}"/>
					<p class="error">${username_error!""}</p>
				</div>
    			<div class="b-form-group">
					Password: 
					<input type="password" name="password" value=""/>
					<p class="error">${password_error!""}</p>
				</div>
    			<div class="b-form-group">
					Verify Password: 
					<input type="password" name="verify" value=""/>
					<p class="error">${verify_error!""}</p>
				</div>
				<div class="b-form-group">
					Email (optional):
					<input type="text" name="email" value="${email}"/>
					<p class="error">${email_error!""}</p>
				</div>

				<input class="b-button" type="submit" value="Signup">
			</form>
		</div>
	</body>
</html>
