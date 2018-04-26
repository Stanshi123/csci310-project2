package unitTest;

import static org.junit.Assert.*;

import java.awt.Font;

import org.junit.Test;

import Server.CollageBuilder;

public class CollageBuilderBugTest extends CollageBuilder{
	@Before
	public void initialize() {
		List<BufferedImage> images = new ArrayList<>();
		for(int i=0; i<30; i++)
		{
			URL imageURL = null;
			try {
				imageURL = new URL("https://s7d1.scene7.com/is/image/PETCO/puppy-090517-dog-featured-355w-200h-d");
			}catch(MalformedURLException e) {
			}

			try{
				BufferedImage image = ImageIO.read(imageURL);
				images.add(image);
			}catch(IOException e){
			}
		}

		this.images = images;
	}
	@Test
	public void testCreateFont() {
		Font fontCreated = createFont(800, 600, 3);
		assertTrue("The font created is an instance of font.", fontCreated instanceof Font);
	}

	@Test
	public void testHeightLargerThanWidth() {
		BufferedImage collageBuilt = createCollageWithImages(600, 800, true, false, null);
		assertTrue("The Collage built is an instance of image.", collageBuilt instanceof BufferedImage);
	}
}
