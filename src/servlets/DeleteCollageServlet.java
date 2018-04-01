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
	String result = "";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteCollageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int saved_collage_id;
		int action = 0;
		result = "fail";
			
		
		saved_collage_id = Integer.parseInt(request.getParameter("collage_id"));

		// set SQL variables
		Statement statement = null;
		ResultSet resultSet = null;
		Connection conn = null;
		String query = "";
		
		try {
			// establish connection
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/scrumdb?user=root&password=root&useSSL=false");
			
			statement = conn.createStatement(); // SQL statement
			
			// check if image title already exists
			
			// insert image into database
			query = "DELETE FROM saved_collage WHERE saved_collage_id = " + saved_collage_id + ";";

			action = statement.executeUpdate(query);
			if (action == 1) {
				result = "success";
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
