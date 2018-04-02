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
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
	
	@Before
	public void initialize() {
		URL imageURL = null;
		try {
			imageURL = new URL("https://s7d1.scene7.com/is/image/PETCO/puppy-090517-dog-featured-355w-200h-d");
		}  catch(MalformedURLException e) {
		}
		try{
		    if (imageURL != null) {
                mockUpImage = ImageIO.read(imageURL);
            }
		} catch(IOException e){
		}

        when (request.getParameter(Constants.KEYWORD_PARAMETER)).thenReturn("dog");
		when (request.getParameter(Constants.SHAPE_PARAMETER)).thenReturn("dog");
        when (request.getParameter(Constants.SAVED_COLLAGE_ID_PARAMETER)).thenReturn("0");
        when (request.getSession()).thenReturn(mock(HttpSession.class));
        Result mockUpResult =new Result(ResultType.success,"dog",mockUpImage);
        when (request.getSession().getAttribute(Constants.SESSION_CURRENT_RESULT)).thenReturn(mockUpResult);
        when (request.getParameter(Constants.WIDTH_PARAMETER)).thenReturn("800");
        when (request.getParameter(Constants.HEIGHT_PARAMETER)).thenReturn("600");
        when (request.getParameter(Constants.FILTER_PARAMETER)).thenReturn("No filter");
        when (request.getParameter(Constants.ROTATION_PARAMETER)).thenReturn("On");
        when (request.getParameter(Constants.BORDER_PARAMETER)).thenReturn("On");
	}

	@Test
	// Servlet request with dog & saved collage id 0
    // No filter
    // Rotation On
    // Border On
	public void testServletNoFilter() throws Exception {
		new ServletEngine().doPost(request,response);
		assertTrue(response != null);
		
	}

    @Test
    // Servlet request with dog & saved collage id 0
    // Black-White filter
    // Rotation On
    // Border On
    public void testServletBlackAndWhite() throws Exception {
        when (request.getParameter(Constants.FILTER_PARAMETER)).thenReturn("Black and White");
        new ServletEngine().doPost(request,response);
        assertTrue(response != null);
    }

    @Test
    // Servlet request with dog & saved collage id 0
    // Black-White filter
    // Rotation On
    // Border On
    public void testServletSepia() throws Exception {
        when (request.getParameter(Constants.FILTER_PARAMETER)).thenReturn("Sepia");
        new ServletEngine().doPost(request,response);
        assertTrue(response != null);
    }

    @Test
    // Servlet request with dog & saved collage id 0
    // Black-White filter
    // Rotation On
    // Border On
    public void testServletGrayScale() throws Exception {
        when (request.getParameter(Constants.FILTER_PARAMETER)).thenReturn("GrayScale");
        new ServletEngine().doPost(request,response);
        assertTrue(response != null);
    }

    @Test
    // Servlet request with dog & saved collage id 0
    // Black-White filter
    // Rotation Off
    // Border On
    public void testServletRotationOff() throws Exception {
        when (request.getParameter(Constants.ROTATION_PARAMETER)).thenReturn("Off");
        new ServletEngine().doPost(request,response);
        assertTrue(response != null);
    }

    @Test
    // Servlet request with dog & saved collage id 0
    // Black-White filter
    // Rotation On
    // Border Off
    public void testServletBorderOff() throws Exception {
        when (request.getParameter(Constants.BORDER_PARAMETER)).thenReturn("Off");
        new ServletEngine().doPost(request,response);
        assertTrue(response != null);
    }

	@Test
	// Servlet request with dog which has been already saved
	public void testServletWithRepeatedResult() throws Exception {
		when (request.getParameter(Constants.KEYWORD_PARAMETER)).thenReturn(null);
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
		when (request.getParameter(Constants.KEYWORD_PARAMETER)).thenReturn(null);
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
