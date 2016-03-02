<!DOCTYPE html>

<html>
  <head>
    <title>Remove script</title>
    <link rel="stylesheet" type="text/css" href="/css/bcash.css" />    
  </head>

  <body>  
	<#setting datetime_format="dd/MM/yyyy hh:mm:ss a">
	<div id="bcash">
		<h4>Remove script</h4>
		<form class="b-form b-form-horizontal" action="/scripts/remove" method="POST">
						
			<div class="b-form-group">
				Id: <input readonly type="text" name="id" value="${script.id}"/>
			</div>
						
			<div class="b-form-group">
				Request url: <input readonly type="text" name="request-url" value="${script.request.url}"/>
			</div>	
				
			<div class="b-form-group">
				Request method: <input readonly type="text" name="request-method" value="${script.request.method}"/>
			</div>			
			
			<div class="b-form-group">
				Request body: <textarea readonly name="request-body" rows="10" cols="60">${script.request.body}</textarea>
			</div>
							
			<div class="b-form-group">
				Response check http code: <input readonly type="text" name="response-http-code" value="${script.responseCheck.httpCode}"/>
			</div>
						
			<input class="b-button" type="submit" value="Remove"/>
			<a href="/">Dashboard</a>
		</form>
	</div>
	
  </body>
</html>
