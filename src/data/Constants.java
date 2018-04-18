package data;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import data.ResultType.ResultType;

public class Constants {
	public static final String KEYWORD_PARAMETER = "keyword";
	public static final String SHAPE_PARAMETER = "shape";
	public static final String WIDTH_PARAMETER = "width";
	public static final String HEIGHT_PARAMETER = "height";
	public static final String FILTER_PARAMETER = "filter";
	public static final String ROTATION_PARAMETER = "rotation";
	public static final String BORDER_PARAMETER = "border";
	
	
	
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
	
	public static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    public static final String ID = "$31$";
    public static final int DEFAULT_COST = 16;
    public static final int SIZE = 128;
    public static final Pattern layout = Pattern.compile("\\$31\\$(\\d\\d?)\\$(.{43})");

}
