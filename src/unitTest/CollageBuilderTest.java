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
		BufferedImage collageBuilt = cb.createCollageWithImages(800, 600, true, null);
		assertTrue("The Collage built is an instatnce of image.", collageBuilt instanceof BufferedImage);
	}

	public void testCreateBackGroundImage() {
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
		BufferedImage backgroundImage = cb.createBackGroundImage(800, 600, true, null);
		assertTrue("The background image created is an instatnce of image.", backgroundImage instanceof BufferedImage);
	}

	public void testCreateRotatedBackGroundImage() {
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
		BufferedImage rotatedBackgroundImage = cb.createRotatedBackGroundImage(800, 600, true, null);
		assertTrue("The rotated background image created is an instatnce of image.", rotatedBackgroundImage instanceof BufferedImage);
	}

	public void testAddBorder() {
		List<BufferedImage> images = new ArrayList<>();
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
		
		CollageBuilder cb = new CollageBuilder(images);
		BufferedImage imageWithBorder = cb.addBorder(images[0]);
		assertTrue("The image with border is an instatnce of image.", imageWithBorder instanceof BufferedImage);
	}

	public void testResize() {
		List<BufferedImage> images = new ArrayList<>();
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
		
		CollageBuilder cb = new CollageBuilder(images);
		BufferedImage imageResized = cb.resize(images[0], 100, 100);
		assertTrue("The image resized is an instatnce of image.", imageResized instanceof BufferedImage);
		//Check width and height here
	}

	public void testAddBlackAndWhiteFilter(){
		List<BufferedImage> images = new ArrayList<>();
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

		CollageBuilder cb = new CollageBuilder(images);
		BufferedImage imageWithBlackAndWhiteFilter = cb.addBlackAndWhiteFilter(images[0]);
		assertTrue("The image with black and white filter is an instatnce of image.", imageWithBlackAndWhiteFilter instanceof BufferedImage);	

	}

	public void testAddGrayScaleFilter(){
		List<BufferedImage> images = new ArrayList<>();
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

		CollageBuilder cb = new CollageBuilder(images);
		BufferedImage imageWithGrayScaleFilter = cb.addGrayScaleFilter(images[0]);
		assertTrue("The image with gray scale filter is an instatnce of image.", imageWithGrayScaleFilter instanceof BufferedImage);	

	}

	public void testAddSepiaFilter(){
		List<BufferedImage> images = new ArrayList<>();
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

		CollageBuilder cb = new CollageBuilder(images);
		BufferedImage imageWithSepiaFilter = cb.addSepiaFilter(images[0]);
		assertTrue("The image with sepia filter is an instatnce of image.", imageWithSepiaFilter instanceof BufferedImage);	

	}



}
