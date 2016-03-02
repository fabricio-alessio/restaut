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

<#assign tab = "&nbsp;&nbsp;&nbsp;&nbsp;">
<#list executions as execution>
	<#assign spaces = "">
	<#if execution.level != 0>
		<#list 1..execution.level as i>
			<#assign spaces = spaces + tab + tab>
		</#list>
	</#if>
	${spaces}<img src="${getImage(execution.status)}" onclick="toggleVisible('${execution.id}')">
	${execution.date?datetime} - ${execution.scriptId}	
	<div><div id='${execution.id}' style="display: none;">
	
		<#if execution.request??>
			<!-- Request -->
			${spaces}${tab}<img src="/img/expand.gif" onclick="toggleVisible('request_${execution.id}')">
			<strong>Request</strong>: (${execution.request.method}) ${execution.request.url}
			<div><div id='request_${execution.id}' style="display: none;">
				${spaces}${tab}${tab} <strong>Headers</strong>:<br>
				<#if execution.request.headers??>
					<#list execution.request.headers?keys as key>
						${spaces}${tab}${tab}${tab} ${key} : ${execution.request.headers[key]} <br>
					</#list>
				</#if>
				${spaces}${tab}${tab} <strong>Params</strong>:<br>
				<#if execution.request.params??>
					<#list execution.request.params?keys as key>
						${spaces}${tab}${tab}${tab} ${key} : ${execution.request.params[key]} <br>
					</#list>
				</#if>
				${spaces}${tab}${tab} <strong>Body</strong>: <br>
				<#if execution.request.body??>
					<table>
						<tr>
							<td>${spaces}${tab}${tab}${tab}</td>
							<td>														
								<textarea readonly id="request-body-${execution.id}" rows="15" cols="120">${execution.request.body}</textarea>
							</td>
						</tr>
					</table>
					${spaces}${tab}${tab}${tab} <input class="b-button" type="button" onclick="jsonPrettyElementValue('request-body-${execution.id}')" value="Beautify"/>
				</#if>
			</div></div>
		</#if>
		
		<#if execution.response??>
			<!-- Response -->
			${spaces}${tab}<img src="/img/expand.gif" onclick="toggleVisible('response_${execution.id}')">
			<strong>Response</strong>: ${execution.response.httpCode}
			<div><div id='response_${execution.id}' style="display: none;">
				${spaces}${tab}${tab} <strong>Body</strong>:<br>
				<#if execution.response.body??>
					<table>
						<tr>
							<td>${spaces}${tab}${tab}${tab}</td>
							<td>
								
								<textarea readonly id="response-body-${execution.id}" rows="15" cols="120">${execution.response.body}</textarea>
							</td>
						</tr>
					</table>
					${spaces}${tab}${tab}${tab} <input class="b-button" type="button" onclick="jsonPrettyElementValue('response-body-${execution.id}')" value="Beautify"/>
				</#if>
			</div></div>
		</#if>
					
		<!-- Validations -->
		${spaces}${tab}<img src="/img/expand.gif" onclick="toggleVisible('validations_${execution.id}')">
		<strong>Validations</strong>:
		<div><div id='validations_${execution.id}' style="display: none;">
			<#list execution.validations as validation>
				${spaces}${tab}${tab}<img src="${getImage(validation.status)}">${validation.test} 
				<#if validation.error?? && validation.error != "">
					<strong>Erro</strong>: ${validation.error}
				</#if>
				<br> 
			</#list>
		</div></div>
		
		<#if execution.preExecutions??>
			<!-- Pre executions -->
			${spaces}${tab}<img src="/img/expand.gif" onclick="toggleVisible('preExecutions_${execution.id}')">
			<#assign executions = execution.preExecutions>
			<strong>Pre-executions</strong>:			
			<div id='preExecutions_${execution.id}' style="display: none;">
				<#include "/_execution_list.ftl">
			</div>
		</#if>
	</div></div>
</#list>
	