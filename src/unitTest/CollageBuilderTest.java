package unitTest;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;

import Server.CollageBuilder;

public class CollageBuilderTest extends CollageBuilder {

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
	public void testCreateCollageWithImages() {
		BufferedImage collageBuilt = createCollageWithImages(800, 600, true, false, null);
		assertTrue("The Collage built is an instance of image.", collageBuilt instanceof BufferedImage);
	}


    @Test
	public void testCreateBackGroundImage() {
		BufferedImage backgroundImage = createBackgroundImage (800, 600, true, false, null);
		assertTrue("The background image created is an instance of image.", backgroundImage instanceof BufferedImage);
	}

	@Test
	public void testCreateRotatedBackGroundImage() {
		BufferedImage rotatedBackgroundImage = createBackgroundImage(800, 600, true, true, null);
		assertTrue("The rotated background image created is an instance of image.", rotatedBackgroundImage instanceof BufferedImage);
	}

	@Test
	public void testAddBorder() {
		BufferedImage imageWithBorder = addBorder(images.get(0));
		assertTrue("The image with border is an instance of image.", imageWithBorder instanceof BufferedImage);

	}

	@Test
	public void testResize() {
		BufferedImage imageResized = resize(images.get(0), 100, 100);
		assertTrue("The image resized is an instance of image.", imageResized instanceof BufferedImage);
	}

	@Test
	public void testAddBlackAndWhiteFilter(){
		BufferedImage imageWithBlackAndWhiteFilter = addBlackAndWhiteFilter(images.get(0));
		assertTrue("The image with black and white filter is an instance of image.", imageWithBlackAndWhiteFilter instanceof BufferedImage);
	}

	@Test
	public void testAddGrayScaleFilter(){
		BufferedImage imageWithGrayScaleFilter = addGrayScaleFilter(images.get(0));
		assertTrue("The image with gray scale filter is an instance of image.", imageWithGrayScaleFilter instanceof BufferedImage);
	}

	@Test
	public void testAddSepiaFilter(){
		BufferedImage imageWithSepiaFilter = addSepiaFilter(images.get(0), 1);
		assertTrue("The image with sepia filter is an instance of image.", imageWithSepiaFilter instanceof BufferedImage);
	}
}
