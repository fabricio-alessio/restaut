<!DOCTYPE html>

<html>
  <head>
    <title>Remove environment</title>
    <link rel="stylesheet" type="text/css" href="/css/bcash.css" />    
  </head>

  <body>
	<div id="bcash">
		<h4>Remove environment</h4>
		<form class="b-form b-form-horizontal" action="/environments/remove" method="POST">
						
			<div class="b-form-group">
				Id: <input readonly type="text" name="id" value="${environment.id}"/>
			</div>				
						
			<input class="b-button" type="submit" value="Remove"/>
			<a href="/">Dashboard</a>
		</form>
	</div>
	
  </body>
</html>
