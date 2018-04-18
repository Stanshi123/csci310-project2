package unitTest;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.mockito.Mockito;

import data.Constants;
import data.Result;

public class JSPContent extends Mockito {
	
	public JSPContent() { 
		
	}
	
	public String displayCaption(HttpSession session) {
		
		Result currentResult = (Result) session.getAttribute(Constants.SESSION_CURRENT_RESULT);
		
		String caption = "";	// if currentResult is failure, no caption
		if (currentResult.isSuccess()) {
			// if currentResult is success, set to "Collage for [keyword]"
			caption = "Collage for " + currentResult.getKeyword();
		}
		return caption;
	}
	
	/* If result is success, return a BufferedImage. Else return a null.*/
	public BufferedImage displayCollage(HttpSession session) {
		Result currentResult = (Result) session.getAttribute(Constants.SESSION_CURRENT_RESULT);
		BufferedImage bImage = null; //testing purpose
		if (currentResult.isSuccess()) {
			// if currentResult is success, get image from BufferedImage
			// and display in <img>
			bImage = currentResult.getCollageImage();
			// change out to a String so that we don't print out
			String printOutString = "<img id=\"collage\""
			+ "src=\"data:image/jpg;base64, "
			+ Constants.getImage(bImage)
			+ "\" alt=\"img not found\""
			+ "name=\"" + currentResult.getKeyword() + "\""
			+ "class=\"display-collage collage-area\" />";
		} else {
			// if currentResult is error, get error message
			// and display in <div>
			// Change out to System.out
			String printOutString = "<div id=\"collage-placeholder\" class=\"collage-area centered-width\">"
					+ "<div class=\"error-message\">" + currentResult.getErrorMessage() + "</div></div>";
		}
		return bImage;
	}

	public List<BufferedImage> displayBuiltCollages(HttpSession session) {
		List<Result> savedCollages = (List<Result>) session.getAttribute("SESSION_SAVED_COLLAGES");
		List<BufferedImage> displayedCollages = new ArrayList<BufferedImage>();
		if (savedCollages == null || savedCollages.isEmpty()) {
			return displayedCollages;
		}
		for (int i = 0; i < savedCollages.size(); i++) {
			BufferedImage bImage = savedCollages.get(i).getCollageImage();
			// Change out to System.out
			String printOutString = "<div id=\"" + i + "\" class=\"saved-collage\" onClick=\"savedCollageClicked(this.id)\"><img src=\"data:image/jpg;base64, " +  Constants.getImage(bImage) + "\"class=\"saved-collage\"/></div>";
			displayedCollages.add(bImage);
		}
		return displayedCollages;
	}
}
