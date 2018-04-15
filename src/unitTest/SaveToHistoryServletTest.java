package unitTest;

import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.mockito.Mockito;

import data.Constants;
import servlets.CollageHistoryServlet;
import servlets.SaveToHistoryServlet;

public class SaveToHistoryServletTest extends Mockito{
	private SaveToHistoryServlet saveServlet;
	private static final String INSERT_TEST_USER_ID = "11";
	
    @Test
    // collage history size of insert_test_user should increase by 1 after SQL query
    public void testSaveToHistory () throws Exception {
	    	HttpServletRequest request = mock(HttpServletRequest.class);
	    	HttpServletResponse response = mock(HttpServletResponse.class);
	    	
	    	URL imageURL = null;
	    	BufferedImage image = null;
		try {
			imageURL = new URL("https://s7d1.scene7.com/is/image/PETCO/puppy-090517-dog-featured-355w-200h-d");
		}catch(MalformedURLException e) {
		}

		try{
			image = ImageIO.read(imageURL);
		}catch(IOException e){
		}
	    	
		when (request.getParameter("title")).thenReturn("collage_test");
		when (request.getParameter("user_id")).thenReturn(INSERT_TEST_USER_ID);
	    	when (request.getParameter("format")).thenReturn("png");
	    	when (request.getParameter("img_src")).thenReturn("Data:base64," + Constants.getImage(image));
	    	when (request.getSession()).thenReturn(mock(HttpSession.class));
	
	    	saveServlet = new SaveToHistoryServlet();
	    	saveServlet.doPost(request,  response);
	    	
	    	assertTrue(response != null);
	    	
		// set SQL variables
		Statement statement = null;
		ResultSet resultSet = null;
		Connection conn = null;
		String query = "";
		String title = null;
		
		try {
			// establish connection
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/scrumdb?user=root&password=root&useSSL=false");
			
			statement = conn.createStatement(); // SQL statement
			
			// check if user provides correct login credentials
			query = "SELECT * FROM saved_collage WHERE user_id = " + INSERT_TEST_USER_ID;
			resultSet = statement.executeQuery(query);

			resultSet.next();
			title = resultSet.getString("collage_name");
			
		}catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		}finally {
			try {
				// close connections
				if (resultSet != null) resultSet.close();
				if (statement != null) statement.close();
				if (conn != null) conn.close();
				
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		
		assertTrue(title.equals("collage_test"));
    }
    
}
