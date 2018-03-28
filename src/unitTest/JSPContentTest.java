package unitTest;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import data.Constants;
import data.Result;
import data.ResultType.ResultType;

public class JSPContentTest extends Mockito {

	private BufferedImage mockUpImage;
	
	@Before
	public void initilalize() {
		URL imageURL = null;
		try {
			imageURL = new URL("https://s7d1.scene7.com/is/image/PETCO/puppy-090517-dog-featured-355w-200h-d");
		}catch(MalformedURLException e) {
		}
		try{
			mockUpImage = ImageIO.read(imageURL);
		}catch(IOException e){
		}
	}
	
	@Test
	public void testDisplayCaptionWithSuccess() {
		HttpSession session = mock(HttpSession.class);
		when (session.getAttribute(Constants.SESSION_CURRENT_RESULT)).thenReturn(new Result(ResultType.success,"dog",""));
		
		JSPContent jspContent = new JSPContent();
		String caption = jspContent.displayCaption(session);
		assertTrue(caption.equals("Collage for dog"));
	}
	
	@Test
	public void testDisplayCaptionWithFailure() {
		HttpSession session = mock(HttpSession.class);
		when (session.getAttribute(Constants.SESSION_CURRENT_RESULT)).thenReturn(new Result(ResultType.failure,"dog",""));

		JSPContent jspContent = new JSPContent();
		String caption = jspContent.displayCaption(session);
		assertTrue(caption.equals(""));
	}

	@Test
	public void testDisplayCollageWithSuccess() {
		HttpSession session = mock(HttpSession.class);
			
		when (session.getAttribute(Constants.SESSION_CURRENT_RESULT)).thenReturn(new Result(ResultType.success,"dog",mockUpImage));
		
		JSPContent jspContent = new JSPContent();
		assertTrue(jspContent.displayCollage(session) instanceof BufferedImage);
	}
	
	@Test
	public void testDisplayCollageWithFailure() {
		HttpSession session = mock(HttpSession.class);
		when (session.getAttribute(Constants.SESSION_CURRENT_RESULT)).thenReturn(new Result(ResultType.failure,"dog",""));
		
		JSPContent jspContent = new JSPContent();
		assertTrue(jspContent.displayCollage(session) == null);
	}
	
	@Test
	public void testDisplayBuiltCollagesWithSuccess() {
		HttpSession session = mock(HttpSession.class);
		List<Result> results = new ArrayList<>();
		Result result = new Result(ResultType.success,"dog",mockUpImage);
		results.add(result);
		when (session.getAttribute("SESSION_SAVED_COLLAGES")).thenReturn(results);
	
		JSPContent jspContent = new JSPContent();
		assertTrue(jspContent.displayBuiltCollages(session) != null);
		for (Object o : jspContent.displayBuiltCollages(session)) {
			assertTrue(o instanceof BufferedImage);
		}
		
	}
}
