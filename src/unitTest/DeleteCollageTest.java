
package whiteboxtests;

import static org.junit.Assert.assertTrue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;

import org.mockito.Mockito;

import servlets.SaveToHistoryServlet;
import servlets.DeleteCollageServlet;

import java.util.Map;
import java.util.HashMap;

public class DeleteCollageTest extends Mockito {
	
	private SaveToHistoryServlet saveServlet;
	private DeleteCollageServlet deleteServlet;
	private static final String DELETE_TEST_USER_ID = "12";
    
    @Test
    // collage history size of insert_test_user should increase by 1 after SQL query
   public void testSaveToHistory () throws Exception {
    	HttpServletRequest request = mock(HttpServletRequest.class);
    	HttpServletResponse response = mock(HttpServletResponse.class);
    	
    	int currentSize, newSize;
    	
    	when (request.getParameter("user_id")).thenReturn(DELETE_TEST_USER_ID);
    	when (request.getSession()).thenReturn(mock(HttpSession.class));
    	
    	// use saveServlet to insert dummy collage
    	saveServlet = new SaveToHistoryServlet();
    	saveServlet.doPost(request, response);
    	
    	assertTrue(response != null);
    	
    	Map<String, String> collages = new HashMap<String, String>();
    	collages = saveServlet.getCollages();
    	currentSize = collages.size(); // size of collage history before deleting

    	// now use deleteServlet to delete the dummy collage
    	deleteServlet = new DeleteCollageServlet();
    	deleteServlet.doPost(request,  response);
    	
    	assertTrue(response != null);
    	
    	collages = deleteServlet.getCollages();
    	newSize = collages.size(); // size of collage history after deleting
    	
    	assertTrue(newSize+1 == currentSize); // size should change after inserting a new collage
    }
    
}