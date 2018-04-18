package servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;
import java.util.Base64;
import java.util.HashMap;

/**
 * Servlet implementation class SaveToHistoryServlet
 */
@WebServlet("/DeleteCollageServlet")
public class DeleteCollageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	private static final String rootPathIvy = "/Users/Ivy/Desktop/310/310 Sprint2/WebContent/";
	private static final String rootPathStan = "/Users/zifanshi/Documents/Egalloc-2.0/web/";
	//private static final String rootPathWilliam = "C:\\Users\\William\\eclipse-workspace\\csci310-project2\\WebContent\\";
	
	String result = "";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteCollageServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int saved_collage_id = 0;
		int action = 0;
		result = "fail";

		System.out.println("we are here babe");
		System.out.println("Let's see" + request.getParameter("saved_collage_id"));
		if (request.getParameter("saved_collage_id") != null) {
			saved_collage_id = Integer.parseInt(request.getParameter("saved_collage_id"));
			System.out.println("we are also here babe");
		}

		// set SQL variables
		Statement statement = null;
		ResultSet resultSet = null;
		Connection conn = null;
		String query = "";
		
		try {
			// establish connection
			System.out.println("then we got here babe");
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/scrumdb?user=root&password=root&useSSL=false");
			
			statement = conn.createStatement(); // SQL statement

			// get file path
			query = "SELECT path FROM saved_collage WHERE saved_collage_id = " + saved_collage_id + ";";
			System.out.println("The id is" + saved_collage_id);
			resultSet = statement.executeQuery(query);
			if (resultSet.next()) {
				System.out.println(resultSet.getString("path"));
				String path = rootPathStan+ resultSet.getString("path");
				// delete from database
				query = "DELETE FROM saved_collage WHERE saved_collage_id = " + saved_collage_id + ";";

				action = statement.executeUpdate(query);

				File file = new File(path);
				System.out.println("path:   " + path);
				if (action == 1 && file.delete()) {
					result = "success";
				}
			    else {
					result = "fail";	  
				}
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
		
		// return ajax response
		if (response.getWriter() != null) {
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result);
		}
	}
}
