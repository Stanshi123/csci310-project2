
package unitTest;

import static org.junit.Assert.assertTrue;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import servlets.CollageHistoryServlet;
import servlets.SaveToHistoryServlet;
import servlets.DeleteCollageServlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class DeleteCollageTest extends Mockito {
	
	private CollageHistoryServlet historyServlet;
	private SaveToHistoryServlet saveServlet;
	private DeleteCollageServlet deleteServlet;
	private static final String DELETE_TEST_USER_ID = "1";
	private static final String NON_EXISTANT_COLLAGE_ID = "0"; 
	private static final String DUMMY_COLLAGE_ID = "2";
	private static String IMAGE_SOURCE = "data:base64,";
	private static final String DUMMY_TITLE = "TITLE";
	private static final String DUMMY_FORMAT = "png";

	private HttpServletRequest request = mock(HttpServletRequest.class);
	private HttpServletResponse response = mock(HttpServletResponse.class);

	@Before
	public void initialize() {
		URL imageURL = null;
		BufferedImage image;
		try {
			imageURL = new URL("https://s7d1.scene7.com/is/image/PETCO/puppy-090517-dog-featured-355w-200h-d");
		}catch(MalformedURLException e) {
		}

		try{
			image = ImageIO.read(imageURL);
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(image, "png", os);
			IMAGE_SOURCE += Base64.getEncoder().encodeToString(os.toByteArray());
		}catch(IOException e){
		}
	}
    
    @Test
    // collage history size of insert_test_user should increase by 1 after SQL query
	public void testDeleteCollage () throws Exception {
		int currentSize, newSize;

		when (request.getParameter("user_id")).thenReturn(DELETE_TEST_USER_ID);
		when (request.getParameter("img_src")).thenReturn(IMAGE_SOURCE);
		when (request.getParameter("title")).thenReturn(DUMMY_TITLE);
		when (request.getParameter("format")).thenReturn(DUMMY_FORMAT);
		when (request.getSession()).thenReturn(mock(HttpSession.class));

		// use saveServlet to insert dummy collage
		saveServlet = new SaveToHistoryServlet();
		saveServlet.doPost(request, response);

		assertTrue(response != null);

		historyServlet = new CollageHistoryServlet();
		historyServlet.doGet(request, response);
		Map<Integer, Map<String, String>> collages;
		collages = historyServlet.getCollages();
		currentSize = collages.size(); // size of collage history before deleting

		// now use deleteServlet to delete the dummy collage
		when (request.getParameter("saved_collage_id")).thenReturn(DUMMY_COLLAGE_ID);
		deleteServlet = new DeleteCollageServlet();
		deleteServlet.doPost(request,  response);

		assertTrue(response != null);

		historyServlet.doGet(request, response);
		collages = historyServlet.getCollages();
		newSize = collages.size(); // size of collage history after deleting
		System.out.println("new size: " + newSize);
		System.out.println("current Size: " + currentSize);
		assertTrue(newSize+1 == currentSize); // size should change after deleting a new collage
	}

	@Test
	// deleting a non-existant collage should not change collage history size
	public void testDeleteNonExistantCollage () throws Exception {
		int currentSize, newSize;

		when (request.getParameter("user_id")).thenReturn(DELETE_TEST_USER_ID);
		when (request.getParameter("saved_collage_id")).thenReturn(NON_EXISTANT_COLLAGE_ID);
		when (request.getSession()).thenReturn(mock(HttpSession.class));

		// use saveServlet to insert dummy collage
		historyServlet = new CollageHistoryServlet();
		historyServlet.doGet(request, response);

		assertTrue(response != null);

		Map<Integer, Map<String, String>> collages = historyServlet.getCollages();
		currentSize = collages.size(); // size of collage history before deleting

		// now use deleteServlet to delete the dummy collage
		deleteServlet = new DeleteCollageServlet();
		deleteServlet.doPost(request,  response);

		assertTrue(response != null);

		historyServlet.doGet(request,  response);
		collages = historyServlet.getCollages();
		newSize = collages.size(); // size of collage history after deleting

		assertTrue(newSize == currentSize); // size should not change after deleting a non-existant collage
	}
}