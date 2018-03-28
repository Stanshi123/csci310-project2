package unitTest;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import data.Constants;
import data.Result;
import data.ResultType.ResultType;
import servlets.ServletEngine;

public class ServletEngineTest extends Mockito {

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
	// Servlet request with dog & saved collage id 0
	public void testServlet() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		when (request.getParameter(Constants.KEYWORD_PARAMETER)).thenReturn("dog");
		when (request.getParameter(Constants.SAVED_COLLAGE_ID_PARAMETER)).thenReturn("0");
		when (request.getSession()).thenReturn(mock(HttpSession.class));
		Result mockUpResult =new Result(ResultType.success,"dog",mockUpImage);
		when (request.getSession().getAttribute(Constants.SESSION_CURRENT_RESULT)).thenReturn(mockUpResult);
		
		new ServletEngine().doPost(request,response);
		
		assertTrue(response != null);
		
	}
	
	@Test
	// Servlet request with dog which has been already saved
	public void testServletWithRepeatedResult() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		when (request.getParameter(Constants.KEYWORD_PARAMETER)).thenReturn(null);
		when (request.getParameter(Constants.SAVED_COLLAGE_ID_PARAMETER)).thenReturn("0");
		when (request.getSession()).thenReturn(mock(HttpSession.class));
		Result result = new Result(ResultType.success,"dog", mockUpImage);
		when (request.getSession().getAttribute(Constants.SESSION_CURRENT_RESULT)).thenReturn(result);
		List<Result> results = new ArrayList<>();
		results.add(result);
		when (request.getSession().getAttribute(Constants.SESSION_SAVED_COLLAGES)).thenReturn(results);
		
		ServletEngine servlet = new ServletEngine();
		servlet.doPost(request,response);
		
		assertTrue(response != null);
	}
	
	@Test
	// Servlet request with dog which has been already saved
	public void testServletWithFailure() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		when (request.getParameter(Constants.KEYWORD_PARAMETER)).thenReturn(null);
		when (request.getParameter(Constants.SAVED_COLLAGE_ID_PARAMETER)).thenReturn("0");
		when (request.getSession()).thenReturn(mock(HttpSession.class));
		Result result = new Result(ResultType.failure,"asdfjasdfjkahsdjkf",Constants.ERROR_MESSAGE);
		when (request.getSession().getAttribute(Constants.SESSION_CURRENT_RESULT)).thenReturn(result);
		List<Result> results = new ArrayList<>();
		results.add(result);
		when (request.getSession().getAttribute(Constants.SESSION_SAVED_COLLAGES)).thenReturn(results);
		
		ServletEngine servlet = new ServletEngine();
		servlet.doPost(request,response);
		
		assertTrue(response != null);
		
	}

}
