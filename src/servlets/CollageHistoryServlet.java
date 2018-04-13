package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;
import java.util.HashMap;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Servlet implementation class CollageHistoryServlet
 */
@WebServlet("/CollageHistoryServlet")
public class CollageHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Map<String, String> collages = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CollageHistoryServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get user id
		// get list of image paths from database
		// add to list of image paths List<String> imagePaths
		// send back to front end to display
		
		// get username and password from index.jsp
		int user_id = 0;
		
		user_id = Integer.parseInt(request.getParameter("user_id"));

		// set SQL variables
		Statement statement = null;
		ResultSet resultSet = null;
		Connection conn = null;
		String query = "";

		String json = "";
		
		try {
			// establish connection
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/scrumdb?user=root&password=root&useSSL=false");
			
			statement = conn.createStatement(); // SQL statement
			
			// check if user provides correct login credentials
			query = "SELECT * FROM saved_collage WHERE user_id = " + user_id;
			resultSet = statement.executeQuery(query);

			collages = new HashMap<String, String>();
			while (resultSet.next()) {
				String title = resultSet.getString("collage_name");
				String path = resultSet.getString("collage_path");
				collages.put(title, path);
			}
			
			ObjectMapper mapper = new ObjectMapper();
			try {
				json = mapper.writeValueAsString(collages);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				// close connections
				if (resultSet != null) resultSet.close();
				if (statement != null) statement.close();
				if (conn != null) conn.close();
				
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		
		if (response.getWriter() != null) {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
		}
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public Map<String, String> getCollages() {
		return collages;
	}
}
