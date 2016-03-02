<!DOCTYPE html>

<html>
  <head>
    <title>Environment configuration</title>
    <link rel="stylesheet" type="text/css" href="/css/bcash.css" />
    <script type="text/javascript" src="/js/util.js"></script>
    <script type="text/javascript" src="/js/environment_edit.js"></script>
  </head>

  <body>  
	<#setting datetime_format="dd/MM/yyyy hh:mm:ss a">
	<div id="bcash">
		<h4>Environment configuration</h4>
		<#if message?? >
			<p class="<#if error??>error<#else>message</#if>">${message}</p>
		</#if>
		<form class="b-form b-form-horizontal" action="/environments/update" method="POST">
			
			<#if new?? >
				<input type="hidden" name="new" value="true"/>
			</#if>
			
			<div class="b-form-group">
				Id: <input <#if !new?? >readonly</#if> type="text" name="id" value="${environment.id}"/>
			</div>
				
			<div class="b-form-group">
				Entries: 
				<table id="entries">
					<#if environment.entries??>
						<#assign pos = 0>
						<#list environment.sortedKeys as key>
							<tr id="entry-${pos}">
								<td><input type="text" id="entry-key-${pos}" name="entry-key-${pos}" value="${key}"/></td>
								<td><input type="text" name="entry-val-${pos}" value="${environment.entries[key]}"/></td>
								<td><input class="b-button" type="button" onclick="delElement('entry-${pos}')" value="Remove"/></td>
							</tr>
							<#assign pos = pos + 1>
						</#list>
					</#if>
				</table>
				<input class="b-button" type="button" onclick="newEnvironment('entries')" value="Add"/>
			</div>
			
			<input class="b-button" type="submit" value="Save"/>
			<a href="/">Dashboard</a>
		</form>
	</div>
	
  </body>
</html>
