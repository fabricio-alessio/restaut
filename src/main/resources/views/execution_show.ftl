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
	Execução:
	<p>
	<#setting datetime_format="dd/MM/yyyy hh:mm:ss a">
	${execution.date?datetime} - ${execution.scriptId}
  </body>

</html>
