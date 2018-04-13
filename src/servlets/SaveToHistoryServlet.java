package servlets;

import java.io.IOException;
import java.nio.file.Files;
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

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.File;

/**
 * Servlet implementation class SaveToHistoryServlet
 */
@WebServlet("/SaveToHistoryServlet")
public class SaveToHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String result = "";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveToHistoryServlet() {
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
		int user_id, action = 0;
		String title, format, img_src;
		
		result = "fail";
		
		user_id = Integer.parseInt(request.getParameter("user_id"));
		format = request.getParameter("format");
		title = request.getParameter("title");
		img_src = request.getParameter("img_src");
		
		String [] parts = img_src.split(",");
		img_src = parts[1];
		
		// save image to server
		BufferedImage image = null;
		byte[] imageByte;

		// get image bytes from image src base64 string
		imageByte = Base64.getDecoder().decode(img_src);
		
		// set to buffered image
		ByteArrayInputStream bais = new ByteArrayInputStream(imageByte);
		image = ImageIO.read(bais);
		bais.close();
		
		// set filepath and write image to file
		String folderPath = "/Users/Ivy/Desktop/310/310 Sprint2/WebContent/collages/" + user_id;
		File userFolder = new File(folderPath);
		if(!userFolder.exists()){
			userFolder.mkdir();
		}
		String filePath = folderPath + "/" + title + "." + format;
		File outputFile = new File(filePath);
		ImageIO.write(image, format, outputFile);

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
			query = "INSERT INTO saved_collage (collage_name, collage_path, user_id) "
					+ "VALUES ('"+title+"','"+filePath+"',"+user_id+");";
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
