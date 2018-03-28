package data;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import data.ResultType.ResultType;

public class Constants {
	public static final String KEYWORD_PARAMETER = "keyword";
	public static final String SAVED_COLLAGE_ID_PARAMETER = "sc-id";
	public static final String ERROR_MESSAGE = "Insufficient number of images found";
	
	public static final String SESSION_ERROR = "isError";
	public static final String SESSION_SAVED_COLLAGES = "savedCollages";
	public static final String SESSION_CURRENT_RESULT = "currentCollage";
	
	public static final String BUILD_COLLAGE = "Build Collage";
	public static final String BUILD_ANOTHER_COLLAGE = "Build Another Collage";
	public static final String EXPORT_COLLAGE = "Export Collage";
	
	public static String getImage(BufferedImage bImage) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bImage, "jpg", baos );
			baos.flush();
			byte[] imageInByteArray = baos.toByteArray();
			baos.close();
			String b64 = javax.xml.bind.DatatypeConverter.printBase64Binary(imageInByteArray);
			return b64;
		} catch (IOException e) {
			return "";
		}
	}
	
	public static final int COLLAGE_WIDTH = 2000;
	public static final int COLLAGE_HEIGHT = 1500;

}
