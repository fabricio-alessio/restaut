function disable(what) {	    
	document.getElementById(what).style.display = "none";
}

function enable(what) {
	document.getElementById(what).style.display = "block";
}

function isEnabled(what) {
	return document.getElementById(what).style.display != "none";
}

function toggleVisible(what) {
	if (document.getElementById(what).style.display == "block") {
		disable(what);
	} else {
		enable(what);
	}
}

function delElement(name) {
	var element = document.getElementById(name);
	element.outerHTML = "";
	delete element;
}

function jsonPrettyElementValue(elemName) {
	
	var element = document.getElementById(elemName);	
	element.value = JSON.stringify(JSON.parse(element.value),null,2);  
	
}

function jsonPrettyElementInnerText(elemName) {
	
	var element = document.getElementById(elemName);	
	element.innerText = JSON.stringify(JSON.parse(element.innerText),null,2);  
	
}

function sortSelectItems(select) {
	
	var optionVal = new Array();

    for (i = 0; i < select.length; i++) {
        optionVal.push(select.options[i].text);
    }

    for (i = select.length; i >= 0; i--) {
        select.remove(i);
    }

    optionVal.sort();

    for (var i = 0; i < optionVal.length; i++) {
        var opt = optionVal[i];
        var el = document.createElement("option");
        el.textContent = opt;
        el.value = opt;
        select.appendChild(el);
    }
}
