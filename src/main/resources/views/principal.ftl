<!DOCTYPE html>

<html>
  <head>
    <title>Welcome</title>
    <style type="text/css">
      .label {text-align: right}
      .error {color: red}
    </style>

  </head>

  <body>
    Welcome
		<p>
		<ul>
			<#list scripts as script>		
				<li><a href="/scripts/execute/${script.id}">${script.id}</a></li>
			</#list>
		</ul>
  </body>

</html>
