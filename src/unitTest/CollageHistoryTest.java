
package whiteboxtests;

import static org.junit.Assert.assertTrue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;

import org.mockito.Mockito;

import servlets.CollageHistoryServlet;

import java.util.Map;
import java.util.HashMap;

public class CollageHistoryTest extends Mockito {
	
	private CollageHistoryServlet servlet;
	private static final String TEST_USER_ID = "9"; // user_id for user test_user
	private static final String EMPTY_USER_ID = "10"; // user_id for user empty_user
	private static final String NON_EXISTANT_USER_ID = "0";
    
    @Test
    // Collage history for user test_user should return 10 collages
   public void testCollageHistory () throws Exception {
    	
    	HttpServletRequest request = mock(HttpServletRequest.class);
    	HttpServletResponse response = mock(HttpServletResponse.class);
    	
    	when (request.getParameter("user_id")).thenReturn(TEST_USER_ID);
    	when (request.getSession()).thenReturn(mock(HttpSession.class));
    	
    	servlet = new CollageHistoryServlet();
    	servlet.doGet(request, response);
    	
    	assertTrue(response != null);
    	
    	Map<String, String> collages = new HashMap<String, String>();
    	collages = servlet.getCollages(); // returns a map of title and image file path
    	
    	// empty_user has no collage history, so should return 0 results
    	assertTrue(collages.size() == 10);
    }
    
    @Test
    // Collage history for user empty_user should return 0 collages
   public void testEmptyCollageHistory () throws Exception {
    	
    	HttpServletRequest request = mock(HttpServletRequest.class);
    	HttpServletResponse response = mock(HttpServletResponse.class);
    	
    	when (request.getParameter("user_id")).thenReturn(EMPTY_USER_ID);
    	when (request.getSession()).thenReturn(mock(HttpSession.class));
    	
    	servlet = new CollageHistoryServlet();
    	servlet.doGet(request, response);
    	
    	assertTrue(response != null);
    	
    	Map<String, String> collages = new HashMap<String, String>();
    	collages = servlet.getCollages(); // returns a map of title and image file path
    	
    	// empty_user has no collage history, so should return 0 results
    	assertTrue(collages.size() == 0);
    }
    
    @Test
    // Collage history for user empty_user should return 0 collages
   public void testNonExistantUser () throws Exception {
    	
    	HttpServletRequest request = mock(HttpServletRequest.class);
    	HttpServletResponse response = mock(HttpServletResponse.class);
    	
    	when (request.getParameter("user_id")).thenReturn(NON_EXISTANT_USER_ID);
    	when (request.getSession()).thenReturn(mock(HttpSession.class));
    	
    	servlet = new CollageHistoryServlet();
    	servlet.doGet(request, response);
    	
    	assertTrue(response != null);
    	
    	Map<String, String> collages = new HashMap<String, String>();
    	collages = servlet.getCollages(); // returns a map of title and image file path
    	
    	// empty_user has no collage history, so should return 0 results
    	assertTrue(collages == null);
    }
}