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
	import="Server.Filter"
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
		<link rel="stylesheet" href="css/mainpage.css" >

		<%-- JavaScript --%>
		<script src="js/buildcollage.js" type="text/javascript"></script>
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.2/jspdf.min.js"></script>
		<style>
			#loader {
			    border: 16px solid #f3f3f3; /* Light grey */
			    border-top: 16px solid #3498db; /* Blue */
			    border-radius: 50%;
			    width: 120px;
			    height: 120px;
			    animation: spin 2s linear infinite;
			    margin: 0 auto;
			}
			
			@keyframes spin {
			    0% { transform: rotate(0deg); }
			    100% { transform: rotate(360deg); }
			}
		</style>
	</head>
	<body>
		<% if (session.getAttribute("user_id") == null) return;%>
		<% int user_id = (Integer) session.getAttribute("user_id"); %>
		<div id="content">
			<div id="collage-generator">
				<div id="collage-container">
					<div
						id="collage-caption"
						class="display-collage centered-width"
					>
						<%
							String caption = "";
							String palceholder = "Egalloc";
							Result currentResult = (Result) session.getAttribute(Constants.SESSION_CURRENT_RESULT);
							if(currentResult != null)
							{
								// if currentResult is failure, no caption
								if (currentResult.isSuccess()) 
								{
									// if currentResult is success, set to "Collage for [keyword]"
									caption = "Collage for topic " + currentResult.getKeyword() +", shape " + currentResult.getShape();
								}	
							}
							else
							{
								%>
									<div style="font-family:Helvetica, Arial, sans-serif; color: white;font-size:130px;">
									<%=palceholder%>
									 </div>
								<% 
							}
							%>
						<%=caption%>
					</div>

					<%-- #collage-area holds a collage image or a error message --%>
					<div
						id="collage-area"
						class="collage"
						style="width: 800; height:600; margin-left:auto; margin-right:auto;" 
					>
						<div id="animation">animation</div>
						<%
							if(currentResult != null){
								if (currentResult.isSuccess()) 
								{
									// if currentResult is success, get image from BufferedImage
									// and display in <img>
									BufferedImage bImage = currentResult.getCollageImage();
									out.print("<img id=\"main-collage\""
									+ "src=\"data:image/jpg;base64, "
									+ Constants.getImage(bImage)
									+ "\" alt=\"img not found\""
									+ "name=\"" + currentResult.getKeyword() + "\""
									+ "class=\"display-collage collage-area\" />");
								} 
								else 
								{
									// if currentResult is error, get error message
									// and display in <div>
									out.print("<div id=\"collage-placeholder\" class=\"collage-area centered-width\">"
									+ "<div class=\"error-message\">" 
									+ currentResult.getErrorMessage() 
									+ "</div></div>");
								}
							}
							
						%>		
					</div>
				</div>
				
			<div id="collage-option-container" style="width:50%; margin-left:auto; margin-right:auto; text-align: left; padding:50px;"  >
					<div class="option-line" id="search-bar-container" style="float:left; width:50%;">
						Collage Topic:
						<!-- input box for entering collage topic -->
						<input
							id="search-bar-input"
							class ="input-box"
							type="text"
							placeholder="Enter topic"
							<%if(currentResult != null)
							{%>
								value = <%=currentResult.getKeyword()%>
							<%
							}%>
						/>
				    </div>
					<div class="option-line" id="shape-input-container" align="right" style="float:right; width:50%;">
						Collage Shape:
						<!-- input box for entering collage shape -->
						<input 
							id ="shape-input" 
							type="text"
							class ="input-box"
							placeholder="Enter shape"
							<%if(currentResult != null)
							{%>
								value = <%=currentResult.getShape()%>
							<%
							}%>
						>
					</div>
					
					<br>
					
					<div class="option-line" id = "filter-input">
						Filter Options:
						&nbsp;
						<!-- radio button for no filter option -->
						<input
							id = "no-filter"
							type ="radio"
							name = "filter"
							value="No filter"
							<%if(currentResult == null)
							{%>
									Checked
							<%}
							else
							{
								if(currentResult.getFilter() == null)
								{%>
									Checked
								<%}
							}%>
						>
							No filter
						
						<!-- radio button for black and white option -->
						<input
							id = "black-and-white-filter"
							type ="radio"
							name = "filter"
							value="Black and White"
							<%if(currentResult != null && currentResult.getFilter() != null)
							{
								if(currentResult.getFilter().equals(Filter.BLACK_AND_WHITE))
								{%>
									Checked
								<%}
							}%>
						>
							Black and White
						
						<!-- radio button for grayscale option -->	
						<input
							id = "grayscale-filter"
							type ="radio"
							name = "filter"
							value="Grayscale"
							<%if(currentResult != null && currentResult.getFilter() != null)
							{
								if(currentResult.getFilter().equals(Filter.GREY_SCALE))
								{%>
									Checked
								<%}
							}%>
						>
							Grayscale
						
						<!-- radio button for sepia option -->	
						<input
							id = "sepia-filter"
							type ="radio"
							name = "filter"
							value="Sepia"
							<%if(currentResult != null && currentResult.getFilter() != null)
							{
								if(currentResult.getFilter().equals(Filter.SEPIA))
								{%>
									Checked
								<%}
							}%>
						>
							Sepia
					</div>
						
					<div class="option-line" id = "rotation-input">
						Rotation Options:
						<!-- radio button for rotation on option -->
						<input
							id = "on-rotation"
							type ="radio"
							name = "rotation"
							value="On"
							<%if(currentResult != null)
							{
								if(currentResult.isRotation())
								{%>
									Checked
								<%}
							}%>
						>
							On
						
						<!-- radio button for rotation off option -->
						<input
							id = "off-rotation"
							type ="radio"
							name = "rotation"
							value="Off"
							<%if(currentResult == null)
							{%>
								Checked
							<%}
							else
							{
								if(!currentResult.isRotation())
								{%>
									Checked
								<%}
							}%>
						>
							Off
					</div>
					
					
					<div class="option-line" id = "border-input">
						Border Options:
							<input
								id = "on-border"
								type ="radio"
								name = "border"
								value="On"
								<%if(currentResult != null){
									if(currentResult.isBorder()){%>
										Checked
									<%}
								}%>
							>
								On
								<input
								id = "off-border"
								type ="radio"
								name = "border"
								value="Off"
								<%if(currentResult == null){%>
										Checked
								<%}else{
									if(!currentResult.isBorder()){%>
										Checked
									<%}
								}%>
							>
								Off
					</div>
					
					<div id= "dimension-input">
						<div class="option-line" id="collage-width" style="float:left;width:50%;">
						Collage Width:
						<!-- input box for collage width -->
						<input
							id="collage-width-input"
							type = "text"
							class ="input-box"
							placeholder = "Enter collage width"
							value = "800"
							<%if(currentResult != null)
							{%>
								value = <%=currentResult.getWidth()%>
							<%}%>
						>
						</div>

						<div class="option-line" id="collage-height" align="right" style="float:right;width:50%;">
						Collage Height:
						<!-- input box for collage height -->
						<input
							id="collage-height-input"
							type = "text"
							class ="input-box"
							placeholder = "Enter collage height"
							value = "600"
							<%if(currentResult != null)
							{%>
								value = <%=currentResult.getHeight()%>
							<%}%>
						>
						</div>
					</div>
					
					<%-- #search-container holds search text bar,
					"Build Collage" button, and "Export Collage" button --%>
					<div class="option-line" id="search-button-container" style="padding-top:50px;padding-bottom:50px;">
						
						 <!-- Build Collage Button -->
						<button style="float:left; width:50%;"
							id="search-bar-submit"
							class="search-bar-button"
							type="submit"
							onclick="checkForText()"
						>Build Collage</button>
						
						<!-- Save to History Button -->
						<button style="float:left; width:50%;"
							id="search-bar-save-history"
							class="search-bar-button"
							type="submit"
						>Save to History</button>
						
						<!-- Export Collage Button (PDF) -->
						<button style="float:right; width:50%;"
							id="search-bar-export-pdf"
							class="search-bar-button"
							type="submit"
							onclick="exportCollage()"
						>Export Collage (PDF)</button> 
						
						<!-- Export Collage Button (PNG) -->
						<button style="float:right; width:50%;"
							id="search-bar-export-png"
							class="search-bar-button"
							type="submit"
							onclick="exportCollage()"
						>Export Collage (PNG)</button> 
					</div>
			
			</div>
			<div id="gallery"></div>
		</div>
		</div>
		
	    <script>
			var deleteButtons = null; // distinct delete button for every collage
			var collageGalery = null;
			var saveButton = document.querySelector("#search-bar-save-history");
			var collageParameters = null;
			var exportPNG = document.querySelector("#search-bar-export-png");
			var exportPDF = document.querySelector("#search-bar-export-pdf");
			
			saveButton.disabled = true;
			
			// retrieve gallery corresponding to user id
			var user_id = document.querySelector("#userID").innerHTML;
			$.ajax({
				url:"${pageContext.request.contextPath}/CollageHistoryServlet",
				type:"GET",
				data: {
					user_id: user_id
				},
				success: function(response){
					
					// clean gallery
					var gallery = document.querySelector("#gallery");
					gallery.innerHTML = "";
					
					// loop through response
					for (var key in response) 
					{	
						// create and initialize image element
						var img = document.createElement("img");
						img.src = "https://localhost:8443/"+response[key]["path"];
						img.alt = response[key]["title"];
						img.id = "collage-"+key;
						img.classList.add("collage-gallery");
						
						// create and initialize delete button
						var deleteButton = document.createElement("button");
						deleteButton.innerHTML = "Delete";
						deleteButton.id = "delete-button-"+key;
						deleteButton.classList.add("delete-button");
						gallery.appendChild(img);
						gallery.appendChild(deleteButton);
					}
					
					// add delete functionality to delete buttons
					deleteButtons = document.querySelectorAll(".delete-button");
					deleteFunctionality();
					
					// add click to show functionality on collages in gallery
					collageGallery = document.querySelectorAll(".collage-gallery");
					collageFunctionality();
				}
			});
			
			// trigger collage building
			var buildCollageButton = document.querySelector("#search-bar-submit");
			buildCollageButton.onclick=function() {
				
				buildCollageButton.disabled = true; // prevents double click
				
				// get parameters
				var width = document.querySelector("#collage-width-input").value;
				var height = document.querySelector("#collage-height-input").value;
				var filter = $('input[name="filter"]:checked').val();
				var rotation = $('input[name="rotation"]:checked').val();
				var border = $('input[name="border"]:checked').val();
				var topic = document.querySelector("#search-bar-input").value;
				var shape = document.querySelector("#shape-input").value;
				
				// check for empty fields
				if (width == "" || height == "" || topic == "" || shape == "") 
				{
					alert("A required field is missing!");
					buildCollageButton.disabled = false; // re-enable button
					return false;
				}
				
				collageParameters = {
					width: width,
					height: height,
					filter: filter,
					rotation: rotation,
					border: border,
					topic: topic,
					shape: shape
				};
				
				// show loading animation
				var loader = document.createElement("div");
				loader.classList.add("loader");
				loader.id = "loader";
				document.querySelector("#collage-area").innerHTML = "";
				document.querySelector("#collage-area").appendChild(loader);
				
				// ajax call
				$.ajax({
					url:"${pageContext.request.contextPath}/ServletEngine",
					type:"GET",
					data:{
						width: width,
						height: height,
						filter: filter,
						rotation: rotation,
						border: border,
						keyword: topic,
						shape: shape
					},
					success: function(response) {
						
						// return as JSON object
						
						var collageArea = document.querySelector("#collage-area"); // holds the collage
						var collageCaption = document.querySelector("#collage-caption"); // holds the collage title
						
						collageArea.innerHTML = ""; // clear collage area
						collageCaption.innerHTML = "Collage for topic "+topic+", shape "+shape; // set caption to search parameters
						
						// if success (returned object is not empty)
						if (response["success"] == "success") 
						{
							
							// set image source and alt
							var img = document.createElement("img");
							img.src = "data:image/jpg;base64,"+response["src"]; // response["src"] should return 64 string
							img.alt = collageCaption.innerHTML // set response["title"] as image alt attribute
							img.id = "main-collage";
							
							collageArea.appendChild(img); // shows image in collage area
							saveButton.disabled = false; // enable save button after building a new collage
						}
						// if fail
						else 
						{
							
							// create div for error message
							var errorMessage = document.createElement("div");
							errorMessage.id = "error-message";
							errorMessage.innerHTML = "Insufficient number of images found.";
							
							// show error message in collage area
							collageArea.appendChild(errorMessage);							
						}
						
						buildCollageButton.disabled = false; // re-enable button after request is complete
					}
				});
			}
			
			// enter key to build collage
			$(document).keypress(function(e) {
			    var keycode = (e.keyCode ? e.keyCode : e.which);
			    
			    if (keycode == '13') { // enter keycode is 13
					buildCollageButton.click(); // simulate click on build collage button
			    }
			});
			
			// adds delete function for every delete button
			function deleteFunctionality () {
				for (var i = 0; i < deleteButtons.length; i++) {
					deleteButtons[i].onclick = function() {
						
						// get ID
						var id = this.id.split("-")[2];
						var collageID = "collage-"+id;
						
						// get collage related to the clicked delete button
						var relatingCollage = document.querySelector("#"+collageID);
						var saved_collage_id = id;
						var currentID = document.querySelector("#"+this.id);
						
						// ajax functionality to remove from database
						// sends saved_collage_id and uses it to delete from database
						$.ajax({
							url:"${pageContext.request.contextPath}/DeleteCollageServlet",
							type:"POST",
							data: {
								saved_collage_id: saved_collage_id
							},
							success: function(response){
								if (response == "success") 
								{
									// remove collage and button after clicking on delete
									if (relatingCollage.parentNode) 
									{
                                        relatingCollage.parentNode.removeChild(relatingCollage);
                                        currentID.parentNode.removeChild(currentID);
                                    }
								}
							}
						});
					}	
				}
			}
			
			// adds click to swap function for every collage
			function collageFunctionality() {
				for (var i = 0; i < collageGallery.length; i++) {
					collageGallery[i].onclick = function() {
						
						collageParameters = null; // set recently built collage parameter to null
						
						var mainCollage = document.querySelector("#main-collage");
						var collageCaption = document.querySelector("#collage-caption");
						
						collageCaption.innerHTML = this.alt; // change collage caption to clicked collage's caption
						
						if (mainCollage != null) { // if a collage is already displayed, just change source
							mainCollage.src = this.src;
						}
						else {
							
							// get clicked image information and insert to new img element
							var collageArea = document.querySelector("#collage-area");
							var img = document.createElement("img");
							img.src = this.src;
							img.alt = this.alt;
							img.id = "main-collage";
							
							collageArea.innerHTML=""; // clear collage area
							collageArea.appendChild(img); // show image
							
							// set session 
							$.ajax({
								url:"${pageContext.request.contextPath}/SetCollageCookieServlet",
								type:"POST",
								data: {

								},
								success: function(response){
									
								}
							
							});
						}
						saveButton.disabled = true; // should not be able to save a saved collage
					}
				}
			}
			
			// save current collage to gallery
			saveButton.onclick=function() {
				
				// don't save collage if it's a previously saved collage
				if (collageParameters == null) 
				{
					return false;
				}
				
				var collage = document.querySelector("#main-collage");
				
				// if no collage is displayed, do nothing
				if (collage == null) 
				{
					//alert("No collage is displayed!");
					return false;
				}
				
				// set parameters to send				
				var title = document.querySelector("#collage-caption").innerHTML;
				var format = "png";
				var img_src = collage.src;
				
				$.ajax({
					url:"${pageContext.request.contextPath}/SaveToHistoryServlet",
					type:"POST",
					data: {
						user_id: user_id,
						title: title,
						format: format,
						img_src: img_src
					},
					success: function(response){
						
						// if fail
						if (response.split("-")[0] === "fail") 
						{
							alert("Could not save image to history");
						}
						else 
						{ 
							// should return saved_collage_id
							var gallery = document.querySelector("#gallery");
						
							// create new image element
							var img = document.createElement("img");
							img.src = img_src;
							img.id = "collage-"+response.split("-")[1];
							img.classList.add("collage-gallery");
							
							// create new delete button
							var deleteButton = document.createElement("button");
							deleteButton.innerHTML = "Delete";
							deleteButton.id = "delete-button-"+response.split("-")[1];
							deleteButton.classList.add("delete-button");
							
							// append to gallery section
							gallery.appendChild(img);
							gallery.appendChild(deleteButton);

							// add delete functionality to delete buttons
							deleteButtons = document.querySelectorAll(".delete-button");
							deleteFunctionality();
							
							// add click to show functionality on collages in gallery
							collageGallery = document.querySelectorAll(".collage-gallery");
							collageFunctionality();
						}
					}
				});
			}			
			
			exportPNG.onclick=function() {
				
				var mainCollage = document.querySelector("#main-collage");
				if (mainCollage != null) 
				{
					var exp = document.createElement("a");
					exp.download = mainCollage.id + ".png";
					exp.href = mainCollage.src;
					exp.click();
					delete exp;
				}
				else 
				{
					alert("No collage to export!");
				}
			}
			
			exportPDF.onclick=function() {
				
				var mainCollage = document.querySelector("#main-collage");
				if (mainCollage != null) 
				{
					var img = new Image();
					var dataURL;
					img.src = mainCollage.src;
					img.onload = function() {
						var canvas = document.createElement("canvas");
						var pdf = new jsPDF('landscape');
						canvas.width = img.width;
					
						canvas.height = img.height;
						var context = canvas.getContext('2d');
						context.drawImage(img, 0, 0);
						
						dataURL = canvas.toDataURL('image/png');
						
						var w = img.width;
						var h = img.height;
						var ratio = h/w;
						
						if (w > pdf.internal.pageSize.width) 
						{
							w = pdf.internal.pageSize.width;
						}
							
						h = w * ratio;
						var yPos = (pdf.internal.pageSize.width - h)/4; 
						
						pdf.addImage(dataURL, "PNG", 10, yPos, w-20, h-10);
						pdf.save("main-collage.pdf");
					}
				}
				else {
					alert("No collage to export!");
				}
			}
	   	</script>
	</body>
</html>
