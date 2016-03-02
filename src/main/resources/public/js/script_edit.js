function newHeader(tableName) {
	var table = document.getElementById(tableName);
	
	var row = table.insertRow(table.rows.length);
	var pos = calculateNextHeaderPos();
	row.id = "request-header-" + pos;
	
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);
	
	cell1.innerHTML = "<input type='text' id='h" + pos + "' name='header-key-" + pos + "' />";
	cell2.innerHTML = "<input type='text' name='header-val-" + pos + "' />";
	cell3.innerHTML = "<input class='b-button' type='button' onclick='delElement(\"request-header-" + pos + "\")' value='Remove'/>";
}

function calculateNextHeaderPos() {
    
    var pos = 0;
	while (document.getElementById("h" + pos) != null) {
		pos++;
	}
	return pos;
}

function newParam(tableName) {
	var table = document.getElementById(tableName);
	
	var row = table.insertRow(table.rows.length);
	var pos = calculateNextParamPos();
	row.id = "request-param-" + pos;
	
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);
	
	cell1.innerHTML = "<input type='text' id='p" + pos + "' name='param-key-" + pos + "' />";
	cell2.innerHTML = "<input type='text' name='param-val-" + pos + "' />";
	cell3.innerHTML = "<input class='b-button' type='button' onclick='delElement(\"request-param-" + pos + "\")' value='Remove'/>";
}

function calculateNextParamPos() {
    
    var pos = 0;
	while (document.getElementById("p" + pos) != null) {
		pos++;
	}
	return pos;
}

function newResponseCheck(tableName, options) {
	var table = document.getElementById(tableName);
	
	var row = table.insertRow(table.rows.length);
	var pos = calculateNextCheckPos();
	row.id = "check-field-" + pos;
	
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);
	var cell4 = row.insertCell(3);
	
	cell1.innerHTML = "<input type='text' id='field-key-" + pos + "' name='field-key-" + pos + "'/>";
	cell2.innerHTML = "<select name='check-" + pos + "'> " + options + " </select>";
	cell3.innerHTML = "<input type='text' name='check-value-" + pos + "'/>";
	cell4.innerHTML = "<input class='b-button' type='button' onclick='delElement(\"check-field-" + pos + "\")' value='Remove'/>";
}

function calculateNextCheckPos() {
    
    var pos = 0;
	while (document.getElementById("field-key-" + pos) != null) {
		pos++;
	}
	return pos;
}

function changeAuthorizationType() {

	var type = document.getElementById("authorization-type").value;
	
	disableAllAuthorization();    	
	enableAuthorization(type);
}

function disableAllAuthorization() {

	disable("authorizationOAuth1");
	disable("authorizationBasic");
}

function enableAuthorization(type) {

	if (type == "OAUTH1") {
		enable("authorizationOAuth1");
	} else if (type == "BASIC") {
		enable("authorizationBasic");
	}
}

function editDescription() {
    	
	enable("mceu_13");
	disable("descriptionView");
	disable("buttonEditDescription");
	enable("buttonViewDescription");      
}

function viewDescription() {

	descriptionView.innerHTML = tinyMCE.get('description').getContent();

	disable("mceu_13");
	enable("descriptionView");
	enable("buttonEditDescription");
	disable("buttonViewDescription");
}

function addPreCondition(from, to, input) {
	
	var valueToAdd = from.value;
	
	if (valueToAdd == "") {
		return;
	}
	
	var option = document.createElement("option");
	option.text = valueToAdd;
	to.add(option);

	from.remove(from.selectedIndex);
	
	sortSelectItems(to);
	
	addPreConditionValue(valueToAdd, input);	
}

function delPreCondition(from, to, input) {

	var valueToDel = from.value;
	
	if (valueToDel == "") {
		return;
	}
	
	var option = document.createElement("option");
	option.text = valueToDel;
	to.add(option);
	
	from.remove(from.selectedIndex);
	
	sortSelectItems(to);
	
	delPreConditionValue(valueToDel, input)	
}

function addPreConditionValue(value, input) {

	if (value == "") {
		return;
	}
	
	if (input.value.indexOf(value) != -1) {
		return;
	}
	
	input.value += value + "\n";
}

function delPreConditionValue(value, input) {
	
	if (value == "") {
		return;
	}
	
	if (input.value.indexOf(value) == -1) {
		return;
	}
	
	input.value = input.value.replace(value + "\n", "");	
}