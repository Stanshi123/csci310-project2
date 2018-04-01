<%@ page
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%@page
	import="data.Result"
	import="java.util.*"
	import="data.Constants"
	import="java.awt.image.BufferedImage"
%>

<!doctype html>
<html class="no-js" lang="" >
	<head>
		<meta charset="utf-8">
		<meta http-equiv="x-ua-compatible" content="ie=edge" >
		<title></title>
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" >
		<link rel="manifest" href="site.webmanifest" >

		<%-- CSS --%>
		<link rel="stylesheet" href="css/main.css" >
		<link rel="stylesheet" href="css/mainpage.css" >

		<%-- JavaScript --%>
		<script src="js/buildcollage.js" type="text/javascript"></script>

	</head>
	<body>
		<div id="content">
			<div id="collage-generator">
				<div id="collage-container">
					<div
						id="collage-caption"
						class="display-collage centered-width"
					>
							<%
							Result currentResult = (Result) session.getAttribute(Constants.SESSION_CURRENT_RESULT);
							String caption = "";	// if currentResult is failure, no caption
							if (currentResult.isSuccess()) {
								// if currentResult is success, set to "Collage for [keyword]"
								caption = "Collage for topic " + currentResult.getKeyword();
							}
						%>
						<%=caption%>
					</div>

					<%-- #collage-area holds a collage image or a error message --%>
					<div
						id="collage-area"
						class="collage"
						style="width: 800px; height: 600px;" 
					>
					<!-- <img class="galleryImage"  src="https://files.slack.com/files-pri/T9SH3RP6H-F9YNE9H61/usc-text.png" alt="Swans"> -->
					
						<%
							if (currentResult.isSuccess()) {
								// if currentResult is success, get image from BufferedImage
								// and display in <img>
								BufferedImage bImage = currentResult.getCollageImage();
								out.print("<img id=\"collage\""
								+ "src=\"data:image/jpg;base64, "
								+ Constants.getImage(bImage)
								+ "\" alt=\"img not found\""
								+ "name=\"" + currentResult.getKeyword() + "\""
								+ "class=\"display-collage collage-area\" />");
							} else {
								// if currentResult is error, get error message
								// and display in <div>
								out.print("<div id=\"collage-placeholder\" class=\"collage-area centered-width\">"
										+ "<div class=\"error-message\">" + currentResult.getErrorMessage() + "</div></div>");
							}
						%>
					</div>
				</div>
				
				
				
				<div id="collage-option-container" style=" margin-left: 30%;  text-align: left; "  >
					<div id= "dimension-input">
						Collage Width:
						<input
							id="collage-width-input"
							type = "text"
							class ="input-box"
							placeholder = "Enter collage width"
							value = "800"
						>
						&nbsp;
						Collage Height:
						<input
							id="collage-height-input"
							type = "text"
							class ="input-box"
							placeholder = "Enter collage height"
							value = "600"
						>
						
						<br>
						<div id = "filter-input">
							Filter Options:
							&nbsp;
							<input
								id = "no-filter"
								type ="radio"
								name = "filter"
								value="No filter"
								checked
							>
								No filter
								
							<input
								id = "black-and-white-filter"
								type ="radio"
								name = "filter"
								value="Black and White"
							>
								Black and White
								
							<input
								id = "grayscale-filter"
								type ="radio"
								name = "filter"
								value="Grayscale"
							>
								Grayscale
								<input
								id = "sepia-filter"
								type ="radio"
								name = "filter"
								value="Sepia"
							>
								Sepia
						</div>
						
						
						<div id = "rotation-input">
						Rotation Options:
							<input
								id = "on-rotation"
								type ="radio"
								name = "rotation"
								value="On"
							>
								On
								<input
								id = "off-rotation"
								type ="radio"
								name = "rotation"
								value="Off"
								Checked
							>
								Off
						</div>
						
						
						<div id = "border-input">
						Border Options:
							<input
								id = "on-border"
								type ="radio"
								name = "border"
								value="On"
							>
								On
								<input
								id = "off-border"
								type ="radio"
								name = "border"
								value="Off"
								Checked
							>
								Off
						</div>
						
						<div id="shape-input-container">
						Collage Shape:
						<input 
							id ="shape-input" 
							type="text"
							class ="input-box"
							placeholder="Enter shape"
						>
					</div>
				</div>

				<%-- #search-container holds search text bar,
				"Build Collage" button, and "Export Collage" button --%>
				<div id="search-container">
					<div id="search-bar-container">
						<input
							id="search-bar-input"
							class ="input-box"
							type="text"
							placeholder="Enter topic"
						/>
						<br>
						<br>
						<button
							id="search-bar-submit"
							class="search-bar-button"
							type="submit"
							onclick="checkForText()"
						>Build Collage</button>
						<button
							id="search-bar-export"
							class="search-bar-button"
							type="submit"
							onclick="exportCollage()"
						>Export Collage</button> 
					</div>
				</div>
			</div>

		
		</div>
		
		<%-- JavaScript --%>
		<script src="js/main.js"></script>
	    <script>
				// Implements "Press Enter to trigger 'Build Collage' button functionality"
	    	document.querySelector("#search-bar-input").addEventListener("keyup", event => {
		      if(event.key !== "Enter") return; // Use `.key` instead.
		      document.querySelector("#search-bar-submit").click(); // Things you want to do.
		      event.preventDefault(); // No need to `return false;`.
	  		});
	   	</script>
	</body>
</html>
