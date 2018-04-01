function checkForText() {
	let input = document.getElementById("search-bar-input").value;
	if (input.length == 0) {
		// do nothing
	} else {
		// input exists, start build collage process
		buildCollage();
	}
}

function buildCollage() {
	
	let width = document.getElementById("collage-width-input").value;
	let height = document.getElementById("collage-height-input").value;
	
	var filterOptions = document.getElementsByName('filter');
	let filter;
	for (var i = 0, length = filterOptions.length; i < length; i++)
	{
	 if (filterOptions[i].checked)
	 	{
		 	filter = filterOptions[i].value;
		 	break;
	 	}
	}
	
	var rotationOptions = document.getElementsByName('rotation');
	let rotation;
	
	for (var j = 0, length = rotationOptions.length; j < length; j++)
	{
	 if (rotationOptions[j].checked)
	 	{
		 	rotation = rotationOptions[j].value;
		 	break;
	 	}
	}
	
	
	var borderOptions = document.getElementsByName('border');
	let border;
	
	for (var k = 0, length = borderOptions.length; k < length; k++)
	{
	 if (borderOptions[k].checked)
	 	{
		 	border = borderOptions[k].value;
		 	break;
	 	}
	}
	
	
	let shape = document.getElementById("shape-input").value;
	
	let keyword = document.getElementById("search-bar-input").value;
	
	
	let request = "ServletEngine?keyword=" + keyword+"&width="+width +"&height="+height + "&filter=" +filter
							+ "&rotation=" + rotation + "&border=" +border + "&shape="+shape;
	
	console.log(request);

	var xhttp = new XMLHttpRequest();
	xhttp.open("GET", request, true);
	xhttp.onload = function (e) {
		if (xhttp.readyState === 4) {
			if (xhttp.status === 200) {
				redirectToSecondPage(xhttp.responseText);
			} else {
				console.error("error: " + xhttp.responseText);
			}
		}
	};
	xhttp.onerror = function (e) {
		console.error(xhttp.statusText);
	};
	xhttp.send();
}

function redirectToSecondPage() {
	let secondPageURL = getURL()
	window.location.replace(secondPageURL);
}

function getURL() {
	var currUrl = window.location.href
	var url = currUrl.substring(0, currUrl.lastIndexOf("/")+1) + "mainpage.jsp";
	return url;
}