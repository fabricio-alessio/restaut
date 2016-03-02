var globalScriptId = 0;
var intervalFunction = null;

function executeOneScript() {

	var scriptId = document.getElementById("script-id").value;
	var environmentId = document.getElementById("environments").value;
	document.getElementById("executions").innerHTML = "<h4>Executions</h4>";
		
	executeScript(scriptId, null, environmentId);	
}

function execute() {

	var masterId = document.getElementById("master-execution-id").value;
	var environmentId = document.getElementById("environments").value;
	var interval = parseInt(document.getElementById("interval").value);
	document.getElementById("executions").innerHTML = "<h4>Executions</h4>";
		
	var executor = function(scriptIds) {		
		globalScriptId = 0;
		intervalFunction = setInterval(function () {
			executeScriptInInterval(scriptIds, masterId, environmentId);			
		}, interval);
	}
	
	getScriptIds(executor);
}

function getScriptIds(executorFunc) {
	
	var http = new XMLHttpRequest();
	
	http.onreadystatechange = function() {
		if (http.readyState == 4) {
			if (http.status = 200) {
				var resp = http.responseText;
				var resp = resp.substring(1, resp.length-1);
				if (resp == "") {
					document.getElementById("executions").innerHTML = "<br>No scripts to execute!"
					return;
				}
				executorFunc(resp.split(", "));
			}
		}
	};
	

	var tags = document.getElementById("tags").value;
	
	http.open("GET", "/scripts/ids?tags=" + tags, true);
	http.send();
}

function executeScriptInInterval(scriptIds, masterId, environmentId) {
	
	executeScript(scriptIds[globalScriptId], masterId, environmentId);
	globalScriptId++;
	if (globalScriptId >= scriptIds.length && intervalFunction != null) {
		clearInterval(intervalFunction);
	}
}

function executeScript(scriptId, masterId, environmentId) {
	
	var http = new XMLHttpRequest();
	
	http.onreadystatechange = function() {
		if (http.readyState == 4) {
			if (http.status = 200) {
				document.getElementById("executions").innerHTML += http.responseText;
			}
		}
	};
	
	if (masterId == null) {
		http.open("GET", "/scripts/execute/" + scriptId + "?environmentId=" + environmentId, true);
	} else {
		http.open("GET", "/scripts/execute/" + scriptId + "?masterId=" + masterId + "&environmentId=" + environmentId, true);
	}
	http.send();
}
