package unitTest;

import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.File;
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

import servlets.CollageHistoryServlet;

public class CollageHistoryServletTest extends Mockito {
	
	private CollageHistoryServlet servlet;
	private static final String TEST_USER_ID = "1"; // user_id for user test_user
	private static final String NON_EXISTANT_USER_ID = "0";

	HttpServletRequest request = mock(HttpServletRequest.class);
	HttpServletResponse response = mock(HttpServletResponse.class);
    
    @Test
    // Collage history for user test_user should return 10 collages
    public void testCollageHistory () throws Exception {
	    	
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
	    	
		String title = "collage_test";
		String format = "png";
		
	    	// set filepath and write image to file
		String filePath = "/Users/zifanshi/Documents/Egalloc-2.0/web/collages/" + TEST_USER_ID + "/" +title + "." + format;
		File outputFile = new File(filePath);
		String imgPath = "collages/" + TEST_USER_ID + "/" +title + "." + format;
		ImageIO.write(image, "png", outputFile);

		// set SQL variables
		Statement statement = null;
		ResultSet resultSet = null;
		Connection conn = null;
		String query = "";
		int action = 0;
		String result = "";
			
		try {
				// establish connection
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/scrumdb?user=root&password=root&useSSL=false");
				
			statement = conn.createStatement(); // SQL statement
				
			// check if image title already exists
				
			// insert image into database
			query = "INSERT INTO saved_collage (title, path, user_id) "
					+ "VALUES ('"+title+"','"+imgPath+"',"+TEST_USER_ID+");";
			action = statement.executeUpdate(query);
				
			if (action == 1) {
				result = "success";
			}
		} catch (SQLException sqle) {
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
	    	
	 	when (request.getParameter("user_id")).thenReturn(TEST_USER_ID);
	 	when (request.getSession()).thenReturn(mock(HttpSession.class));
		
	    	servlet = new CollageHistoryServlet();
	    	servlet.doGet(request, response);
	    	
	    	assertTrue(response != null);
	    	
	    	Map<Integer, Map<String, String>> collages = new HashMap<Integer , Map<String, String>>();
	    	collages = servlet.getCollages(); // returns a map of title and image file path
	    	
	    	// test_user has collage history, so should return 1 result
		System.out.println(collages.size());
	    	assertTrue(collages.size() == 2);
    }

    @Test
    // Collage history for user non_existant_user should return 0 collage
    public void testNonExistantUser () throws Exception {
		when (request.getParameter("user_id")).thenReturn(NON_EXISTANT_USER_ID);
		when (request.getSession()).thenReturn(mock(HttpSession.class));

		servlet = new CollageHistoryServlet();
		servlet.doGet(request, response);

		assertTrue(response != null);

		Map<Integer, Map<String, String>> collages = new HashMap<Integer,Map<String, String>>();
		collages = servlet.getCollages(); // returns a map of title and image file path
		assertTrue(collages.size() == 0);
    }
    
}
