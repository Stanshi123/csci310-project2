
package whiteboxtests;

import static org.junit.Assert.assertTrue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;

import org.mockito.Mockito;

import servlets.SaveToHistoryServlet;
import servlets.CollageHistoryServlet;

import java.util.Map;
import java.util.HashMap;

public class SaveToHistoryTest extends Mockito {
	
	private SaveToHistoryServlet saveServlet;
	private CollageHistoryServlet historyServlet;
	private static final String INSERT_TEST_USER_ID = "11";
    
    @Test
    // collage history size of insert_test_user should increase by 1 after SQL query
   public void testSaveToHistory () throws Exception {
    	HttpServletRequest request = mock(HttpServletRequest.class);
    	HttpServletResponse response = mock(HttpServletResponse.class);
    	
    	int currentSize, newSize;
    	
    	when (request.getParameter("user_id")).thenReturn(INSERT_TEST_USER_ID);
    	when (request.getSession()).thenReturn(mock(HttpSession.class));
    	
    	historyServlet = new CollageHistoryServlet();
    	historyServlet.doGet(request, response);
    	
    	assertTrue(response != null);
    	
    	Map<String, String> collages = new HashMap<String, String>();
    	collages = historyServlet.getCollages();
    	currentSize = collages.size(); // size of collage history before inserting

    	saveServlet = new SaveToHistoryServlet();
    	saveServlet.doPost(request,  response);
    	
    	assertTrue(response != null);
    	
    	collages = saveServlet.getCollages();
    	newSize = collages.size(); // size of collage history after inserting
    	
    	assertTrue(newSize == currentSize+1); // size should change after inserting a new collage
    	
    	saveServlet.deleteAfterTest(); // delete inserted dummy collage
    }
    
}