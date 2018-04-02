package unitTest;

import data.Result;
import org.junit.Test;
import Server.*;

import static org.junit.Assert.assertTrue;

public class ServerTest {

	@Test
	// keyword: dog
	// Shape: dog
	// Height: 800
	// Filter: No filter
	// Rotation: false
	// Border: false
	public void testGetResultForKeywordWithDogNoFilter() {
		Server server = Server.getInstance();
		Result result = server.getResultForKeyword("dog","dog",800,800,
                "No filter", false, false);
        assertTrue(result instanceof Result);
	}

	@Test
	// keyword: dog
	// Shape: dog
	// Height: 800
	// Filter: No filter
	// Rotation: true
	// Border: false
	public void testGetResultForKeywordWithDogNoFilterWithRotation() {
		Server server = Server.getInstance();
		Result result = server.getResultForKeyword("dog","dog",800,800,
				"No filter", true, false);
		assertTrue(result instanceof Result);
	}


	@Test
	// keyword: dog
	// Shape: dog
	// Height: 800
	// Filter: No filter
	// Rotation: false
	// Border: true
	public void testGetResultForKeywordWithDogNoFilterWithBorder() {
		Server server = Server.getInstance();
		Result result = server.getResultForKeyword("dog","dog",800,800,
				"No filter", false, false);
		assertTrue(result instanceof Result);
	}

	@Test
	// keyword: dog
	// Shape: dog
	// Height: 800
	// Filter: Black and White
	// Rotation: false
	// Border: false
	public void testGetResultForKeywordWithDogBlackAndWhite() {
		Server server = Server.getInstance();
		Result result = server.getResultForKeyword("dog","dog",800,800,
				"Black And White", false, false);
		assertTrue(result instanceof Result);
	}

	@Test
	// keyword: dog
	// Shape: dog
	// Height: 800
	// Filter: Sepia
	// Rotation: false
	// Border: false
	public void testGetResultForKeywordWithDogSepia() {
		Server server = Server.getInstance();
		Result result = server.getResultForKeyword("dog","dog",800,800,
				"Sepia", false, false);
		assertTrue(result instanceof Result);
	}

	@Test
	// keyword: dog
	// Shape: dog
	// Height: 800
	// Filter: GrayScale
	// Rotation: false
	// Border: false
	public void testGetResultForKeywordWithDogGrayscale() {
		Server server = Server.getInstance();
		Result result = server.getResultForKeyword("dog","dog",800,800,
				"Grayscale", false, false);
		assertTrue(result instanceof Result);
	}


	@Test
	public void testGetResultForKeywordWithGibberish() {
		Server server = Server.getInstance();
		Result result = server.getResultForKeyword("shudfvgiowadhgivudhbiavuadjcibaeuyfsvcdujhvzxckbjcsoijaksab",
                "Gibberish", 800,800, "No filter", false, false);
		assertTrue(result instanceof Result);	
	}
	
	@Test
	public void testGetResultForKeywordWithDogTwice() {
		Server server = Server.getInstance();
        Result result = server.getResultForKeyword("dog","dog",800,800,
                "No filter", false, false);
		// call it again
        result = server.getResultForKeyword("dog","dog",800,800,
                "No filter", false, false);
		assertTrue(result instanceof Result);	
	}


}
