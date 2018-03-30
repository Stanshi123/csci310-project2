package unitTest;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import Server.APIResponse;
import Server.Item;
import org.junit.Test;
import Server.APICommunicator;

public class APICommunicatorTest extends APICommunicator{


	/* This test tests the constructor as well as the getter for the APICommuncator class, the class getter should always
	 * return an array of images whose size is between 0 and 30.
	 */
	@Test
	public void testAPICommunicatorWithDog() {
		APICommunicator apicommunicator = new APICommunicator("dog");
		int size = apicommunicator.getImages().size();
		assertTrue("0 <= List size <= 30", (size >= 0 && size <= 30));
		for (Object o : apicommunicator.getImages() ) {
			assertTrue("This object is an image", o instanceof BufferedImage);
		}
	}
	
	@Test
	public void testAPICommunicatorWithGibberish() {
		APICommunicator apicommunicator = new APICommunicator("shudfvgiowadhgivudhbiavuadjcibaeuyfsvcdujhvzxckbjcsoijaksabs");
		int size = apicommunicator.getImages().size();
		assertTrue("0 <= List size <= 30", (size >= 0 && size <= 30));
		for (Object o : apicommunicator.getImages() ) {
			assertTrue("This object is an image", o instanceof BufferedImage);
		}
	}
	
	@Test
	public void testGetParamsStringWithDog() {
		// Constants
		APICommunicator apicommunicator = new APICommunicator();
		final String apikey = "YOUR_API_KEY";
		final String keyword = "dog";
		final String cx = "YOUR_CX";
		final String startIndex = "1";
		final String searchType = "image";
		
		// put into a map then test it
		Map<String,String> parameters = new HashMap<>();
		parameters.put("q", keyword);
		parameters.put("key" , apikey);
		parameters.put("cx", cx);
		parameters.put("start", startIndex);
		parameters.put("searchType", searchType);
		try {
			String query = apicommunicator.getParamsString(parameters);
			assertTrue("The query contains q=dog", query.matches(".*q=dog&?.*"));
			assertTrue("The query contains key=YOUR_API_KEY", query.matches(".*key=YOUR_API_KEY&?.*"));
			assertTrue("The query contains cx=YOUR_CX", query.matches(".*cx=YOUR_CX&?.*"));
			assertTrue("The query contains start=1", query.matches(".*start=1&?.*"));
			assertTrue("The query contains searchType=image", query.matches(".*searchType=image&?.*"));
		} catch (IOException e) {
		}
	}
	
	@Test
	public void testGetParamsStringWithEmptyString() {
		// Constants
		APICommunicator apicommunicator = new APICommunicator();

		// put into a map then test it
		Map<String,String> parameters = new HashMap<>();

		try {
			String query = apicommunicator.getParamsString(parameters);
			assertTrue("The query is empty", query.equals(""));
			} catch (IOException e) {
			}
	}

	@Test
	public void testRequestForKeyWord() {
		sendRequestForKeyWord(1);
	}

	@Test
	public void testProcessAPIResponseWithNullItems() {
		// Item is null
		APIResponse apiResponse = new APIResponse();
		urls.clear();
		processAPIResponse(apiResponse);
		assertTrue(urls.isEmpty());
	}

	@Test
	public void testProcessAPIResponseWithMarlFormedUrl() {
		// Item is null
		Item item = new Item("htp:/www.google.com");
		Item[] items = {item};
		APIResponse apiResponse = new APIResponse(items);
		urls.clear();
		processAPIResponse(apiResponse);
		assertTrue(urls.isEmpty());
	}

	@Test
	public void testProcessAPIResponse() {
		// Item is null
		Item item = new Item("https://i.ytimg.com/vi/SfLV8hD7zX4/maxresdefault.jpg");
		Item[] items = {item};
		APIResponse apiResponse = new APIResponse(items);
		urls.clear();
		processAPIResponse(apiResponse);
		assertTrue(urls.size() == 1);
	}

	@Test
	public void testProcessUrlForImage() {
		try {
			URL url = new URL("https://i.ytimg.com/vi/SfLV8hD7zX4/maxresdefault.jpg");
			images.clear();
			processUrlForImage(url);
			assertTrue(images.size() == 1);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}