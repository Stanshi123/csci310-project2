package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

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
//	private static final String rootPathIvy = "/Users/Ivy/Desktop/310/310 Sprint2/WebContent/collages/";
	private static final String rootPathStan = "/Users/zifanshi/Documents/Egalloc-2.0/web/collages/";
	//private static final String rootPathWilliam = "C:\\Users\\William\\eclipse-workspace\\csci310-project2\\WebContent\\collages/";
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
		int user_id = -1;
		int action = 0;
		String title;
		String format, img_src;
		
		result = "fail";

		user_id = Integer.parseInt(request.getParameter("user_id"));
		format = request.getParameter("format");
		title = request.getParameter("title");
		img_src = request.getParameter("img_src");

		System.out.println("user id is: " + user_id);


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

		// set SQL variables
		PreparedStatement pStatement = null;
		Statement statement = null;
		ResultSet resultSet = null;
		Connection conn = null;
		String query = "";
		int AI_value = 0;
		try {
			// establish connection
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/scrumdb?user=root&password=root&useSSL=false");
			
			statement = conn.createStatement(); // SQL statement
			
			query = "SELECT `AUTO_INCREMENT` FROM INFORMATION_SCHEMA.TABLES\r\n" + 
					"     WHERE TABLE_SCHEMA = 'scrumdb' AND TABLE_NAME = 'saved_collage';";
			resultSet = statement.executeQuery(query);
			
			if (resultSet.next()) {
				AI_value = resultSet.getInt("AUTO_INCREMENT");
				
				// set filepath and write image to file
				String folderPath = rootPathStan + user_id + "/";
				String filePath = "collages/"+user_id+"/collage-"+AI_value+"."+format;

				System.out.println("file path is " + filePath);

				File userFolder = new File(folderPath);
				if(!userFolder.exists()){
					userFolder.mkdir();
				}

				File outputFile = new File(folderPath + "/collage-"+AI_value+ "." + format);
				if (format != null) {
					ImageIO.write(image, format, outputFile);
				}

				// insert image into database
				query = "INSERT INTO saved_collage (title, path, user_id) VALUES (?, ?, ?);";

				pStatement = conn.prepareStatement(query);
				pStatement.setString(1, title);
				pStatement.setString(2, filePath);
				pStatement.setInt(3, user_id);

				System.out.println("ydjfghiuyftjdhxfgnhjuytgf:" + filePath);
				action = pStatement.executeUpdate();
				
				if (action == 1) {
					result = "success";
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
			response.getWriter().write(result + "-" + AI_value);
		}
	}
}