<!DOCTYPE html>

<html>
  <head>
    <title>Execution group</title>
    <link rel="stylesheet" type="text/css" href="/css/bcash.css" /> 
    <script type="text/javascript" src="/js/util.js"></script>    
    <script type="text/javascript" src="/js/execution.js"></script>    
  </head>

  <body>  
	<#setting datetime_format="dd/MM/yyyy hh:mm:ss a">	
	<#function getImage status>
		<#if status == "SUCCESS"> 
			<#return "/img/success.gif">
		</#if> 
		<#if status == "FAILURE"> 
			<#return "/img/alert.gif">
		</#if> 
		<#if status == "ERROR"> 
			<#return "/img/error.gif">
		</#if>
		
	</#function>
	
	<div class="bcash" id="bcash">
		<h4>Execution</h4>
		<br>
		<form>
			<div class="b-form-group">
				Script: <input type="text" readonly id="script-id" value="${scriptId}"/>
			</div>
			<br>
			<div class="b-form-group">
				Environment:
				<select id="environments">
					<#list environments as environment>
						<option value="${environment.id}">${environment.id}</option> 
					</#list>
				</select>
			</div>
		</form>
		<br>
		<input class="b-button" type="button" onclick="executeOneScript()" value="Start"/>
		<a href="/">Dashboard</a>
		<div id="executions">
		</div>
	</div>
  </body>
</html>
