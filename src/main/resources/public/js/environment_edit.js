function newEnvironment(tableName) {
	var table = document.getElementById(tableName);
	
	var row = table.insertRow(table.rows.length);
	var pos = calculateNextEnvironmentPos();
	row.id = "entry-" + pos;
	
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);
	
	cell1.innerHTML = "<input type='text' id='entry-key-" + pos + "' name='entry-key-" + pos + "' />";
	cell2.innerHTML = "<input type='text' name='entry-val-" + pos + "' />";
	cell3.innerHTML = "<input class='b-button' type='button' onclick='delElement(\"entry-" + pos + "\")' value='Remove'/>";
}

function calculateNextEnvironmentPos() {
    
    var pos = 0;
	while (document.getElementById("entry-key-" + pos) != null) {
		pos++;
	}
	return pos;
}