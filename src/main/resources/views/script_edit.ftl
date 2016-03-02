<!DOCTYPE html>

<html>
  <head>
    <title>Script configuration</title>
    <link rel="stylesheet" type="text/css" href="/css/bcash.css" />
    <link rel="stylesheet" type="text/css" href="/css/util.css" />
    <script type="text/javascript" src="/js/util.js"></script>
    <script type="text/javascript" src="/js/script_edit.js"></script>
    <script src="//cdn.tinymce.com/4/tinymce.min.js"></script>
    <script>tinymce.init({ mode : 'specific_textareas', editor_selector : 'mceEditor' });</script>    
  </head>

  <body onload="jsonPrettyElementValue('request-body')">  
	<#setting datetime_format="dd/MM/yyyy hh:mm:ss a">
	<div id="bcash">
		<h4>Script configuration</h4>
		<#if message?? >
			<p class="<#if error??>error<#else>message</#if>">${message}</p>
		</#if>
		<form class="b-form b-form-horizontal" action="/scripts/update" method="POST">
			
			<input class="b-button" type="submit" value="Save"/>
			<a href="/">Dashboard</a>
			<#if new?? >
				<input type="hidden" name="new" value="true"/>
			</#if>
			
			<div class="b-form-group">
				Identification: <input <#if !new?? >readonly</#if> type="text" name="id" value="${script.id}"/>
			</div>
			
			<div class="b-form-group" >
				Description:
				<div id="descriptionView" class="ligth-border" >
					${script.description}
				</div>
				<input id="buttonEditDescription" class="b-button" type="button" onclick="editDescription()" value="Edit"/>
				<textarea name="description" id="description" class="mceEditor" rows="5" cols="30" style="display: none;">${script.description}</textarea>
				<input id="buttonViewDescription" class="b-button" type="button" onclick="viewDescription()" value="View" style="display: none;"/>							    
			</div>
						
			<div class="b-form-group" >
				Tags:
				<#assign tags = "">
				<#if script.tags??>
					<#list script.tags as tag>
						<#assign tags = tags + tag + " ">
					</#list>
				</#if>
				<input type="text" id="tags" name="tags" value="${tags}"/>				
			</div>
			
			<div class="b-form-group">
				Pre-conditions:
				<textarea name="preConditions" rows="5" cols="30" readonly style="display: none;" >${preConditions}</textarea>
				<table>
					<tr>
					    <td style="width:45%">
						    <select id="preConditionSelect" size="8">
						       	<#list scriptIds as scriptId>
						       		<option value="${scriptId}">${scriptId}</option>
						       	</#list>
						    </select>
					    </td>
					    <td style="width:10%">
							<input class="b-button-vertical" type="button" 
								onclick="addPreCondition(this.form.preConditionSelect, this.form.preConditionSelected, this.form.preConditions)" 
								value=">>"/>
							</br>
							<input class="b-button-vertical" type="button" 
								onclick="delPreCondition(this.form.preConditionSelected, this.form.preConditionSelect, this.form.preConditions)" 
								value="<<"/>
						</td>
					    <td style="width:45%">
						    <select id="preConditionSelected" size="8">
							    <#if script.preConditions??>
							       	<#list script.preConditions as preCondition>
							       		<option value="${preCondition}">${preCondition}</option>
							       	</#list>
								</#if>
						    </select>
						</td>
					</tr>
				</table>
			</div>
			
			<div class="b-form-group">
				Request headers: 
				<table id="request-headers">
					<#if script.request.headers??>
						<#assign pos = 0>
						<#list script.request.headers?keys as key>
							<tr id="request-header-${pos}">
								<td><input type="text" id="h${pos}" name="header-key-${pos}" value="${key}"/></td>
								<td><input type="text" name="header-val-${pos}" value="${script.request.headers[key]}"/></td>
								<td><input class="b-button" type="button" onclick="delElement('request-header-${pos}')" value="Remove"/></td>
							</tr>
							<#assign pos = pos + 1>
						</#list>
					</#if>
				</table>
				<input class="b-button" type="button" onclick="newHeader('request-headers')" value="Add"/>
			</div>
			
			<div class="b-form-group">
				Request authorization:
				<select id="authorization-type" name="authorization-type" onchange="changeAuthorizationType()">
			       	<#list authorizationTypes as type>
			       		<option <#if authTypeSelected == type>selected</#if> value="${type}">${type}</option>
			       	</#list>
			    </select>
			    <div class="b-form-group" id="authorizationOAuth1" <#if "OAUTH1" != authTypeSelected.name() >style="display: none;"</#if>>
			   		Authorization realm: <input type="text" name="authorization-realm" 						value="<#if authOAuth1??>${authOAuth1.realm}</#if>"/>
			   		Authorization consumer key: <input type="text" name="authorization-consumer-key" 		value="<#if authOAuth1??>${authOAuth1.consumerKey}</#if>"/>
			   		Authorization nonce: <input type="text" name="authorization-nonce" 						value="<#if authOAuth1??>${authOAuth1.nonce}</#if>"/>
			   		Authorization signature method: 
			   		<select name="authorization-signature-method">
				       	<#list signatureMethods as method>
				       		<option <#if authOAuth1?? && authOAuth1.signatureMethod == method>selected</#if> value="${method}">${method}</option>
				       	</#list>
				    </select>			   		
			   	</div>
			    <div class="b-form-group" id="authorizationBasic" <#if "BASIC" != authTypeSelected.name() >style="display: none;"</#if>>
			    	Authorization username: <input type="text" name="authorization-username" 				value="<#if authBasic??>${authBasic.username}</#if>"/>
			    	Authorization password: <input type="text" name="authorization-password" 				value="<#if authBasic??>${authBasic.password}</#if>"/>
			    </div>
			</div>
						
			<div class="b-form-group">
				Request url: <input type="text" name="request-url" value="${script.request.url}"/>
			</div>	
				
			<div class="b-form-group">
				Request params: 
				<table id="request-params">
					<#if script.request.params??>
						<#assign pos = 0>
						<#list script.request.params?keys as key>
							<tr id="request-param-${pos}">
								<td><input type="text" id="p${pos}" name="param-key-${pos}" value="${key}"/></td>
								<td><input type="text" name="param-val-${pos}" value="${script.request.params[key]}"/></td>
								<td><input class="b-button" type="button" onclick="delElement('request-param-${pos}')" value="Remove"/></td>
							</tr>
							<#assign pos = pos + 1>
						</#list>
					</#if>
				</table>
				<input class="b-button" type="button" onclick="newParam('request-params')" value="Add"/>
			</div>
				
			<div class="b-form-group">
				Request method: 
				<select name="request-method">
			       	<option value="POST"   <#if script.request.method == "POST"  >selected</#if> >POST  </option>       	
			       	<option value="GET"    <#if script.request.method == "GET"   >selected</#if> >GET   </option>       	
			       	<option value="PUT"    <#if script.request.method == "PUT"   >selected</#if> >PUT   </option>       	
			       	<option value="DELETE" <#if script.request.method == "DELETE">selected</#if> >DELETE</option>
			    </select>
			</div>			
			
			<div class="b-form-group">
				Request body: <textarea name="request-body" id="request-body" rows="15" cols="60">${script.request.body}</textarea>
				<input class="b-button" type="button" onclick="jsonPrettyElementValue('request-body')" value="Beautify"/>
			</div>
							
			<div class="b-form-group">
				Response check http code: <input type="text" name="response-http-code" value="${script.responseCheck.httpCode}"/>
			</div>
			
			<div class="b-form-group">
				Response check fields: 
				<table id="check-fields">
					<#if script.responseCheck.fieldChecks??>
						<#assign pos = 0>
						<#list script.responseCheck.fieldChecks?keys as key>
							<tr id="check-field-${pos}">
								<td><input type="text" id="field-key-${pos}" name="field-key-${pos}" value="${key}"/></td>
								<td>
									<select name="check-${pos}">
										<#list checks as check>
											<option value="${check}" <#if script.responseCheck.fieldChecks[key].check == check >selected</#if> >${check}</option> 
										</#list>
									</select>
								</td>
								<td><input type="text" name="check-value-${pos}" value="${script.responseCheck.fieldChecks[key].expectedValue}"/></td>
								<td><input class="b-button" type="button" onclick="delElement('check-field-${pos}')" value="Remove"/></td>
							</tr>
							<#assign pos = pos + 1>
						</#list>
					</#if>
				</table>
				<input class="b-button" type="button" onclick="newResponseCheck('check-fields', '<#list checks as check> <option value=${check}>${check}</option> </#list> ')" value="Add"/>
			</div>
			
			<input class="b-button" type="submit" value="Save"/>
			<a href="/">Dashboard</a>
		</form>
	</div>
	
  </body>
</html>
