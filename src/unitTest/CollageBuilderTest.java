package unitTest;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.Test;

import Server.CollageBuilder;

public class CollageBuilderTest {

	@Test
	public void testCreateCollageWithImages() {
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
		
		CollageBuilder cb = new CollageBuilder(images);
		BufferedImage collageBuilt = cb.createCollageWithImages(800, 600,true, null);
		assertTrue("The Collage built is an instatnce of image.", collageBuilt instanceof BufferedImage);
	}

}
