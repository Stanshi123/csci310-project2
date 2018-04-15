package servlets;

import data.PasswordHasher;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static data.Constants.DEFAULT_COST;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String res = "";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpServlet() {
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
		
		HttpSession session = request.getSession();
		
		// get username and password from index.jsp
		String username, password;
		username = request.getParameter("username");
		password = request.getParameter("password");

		// set SQL variables
		Statement statement = null;
		ResultSet resultSet = null;
		Connection conn = null;
		String query = "";
		
		// return result (fail/success depending on SQL query)
		res = "fail";
		int user_id = -1;
		int count = 0;
		int action = 0;
		
		if (username != "" && password != "") {
			try {
				// establish connection
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/scrumdb?user=root&password=root&useSSL=false");
				
				statement = conn.createStatement(); // SQL statement
				
				// check if username already exists
				query
					= "SELECT "
						+ "COUNT(*) AS count "
					+ "FROM user "
					+ "WHERE username = '" + username + "';";
				resultSet = statement.executeQuery(query);
				while (resultSet.next()) {
					count = resultSet.getInt("count");
				}
				
				resultSet.close();
				// if count does not return 0, username already exists in the database
				if (count != 0)
					res = "fail";
				else {
					PasswordHasher hasher = new PasswordHasher(DEFAULT_COST);
					String hashedPassword = hasher.hash(password.toCharArray());
					query
						= "INSERT INTO user (username, password) "
							+ "VALUES ('" + username + "', '" + hashedPassword + "');";

					System.out.println("hashed " + hasher.hash(password.toCharArray()));

					action = statement.executeUpdate(query);
					if (action == 1) {
						res = "success";
					
						query
							= "SELECT "
								+ "user_id "
							+ "FROM user "
							+ "WHERE username = '" + username + "' AND password = '" + hashedPassword + "';";
						resultSet = statement.executeQuery(query);
						while (resultSet.next()) {
							user_id = resultSet.getInt("user_id");
							session.setAttribute("user_id", user_id); // Set session user if register is successful
						}
					}
					else res = "fail";
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
		}

		// return ajax response
		if (response.getWriter() != null) {
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(res);
		}
	}
	
	public String getResult() {
		return res;
	}
	
	public void deleteAfterTest() {

		// set SQL variables
		Statement statement = null;
		ResultSet resultSet = null;
		Connection conn = null;
		String query = "";
		
		// return result (fail/success depending on SQL query)
		int count = 0;
		
		try {
			// establish connection
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/scrumdb?user=root&password=root&useSSL=false");
			
			statement = conn.createStatement(); // SQL statement
			
			// check if username already exists
			query
				= "SELECT "
					+ "COUNT(*) AS count "
				+ "FROM user;";
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				count = resultSet.getInt("count");
			}

			query = "DELETE FROM user WHERE user_id = " + count;
			statement.execute(query);
			
			query = "ALTER TABLE user AUTO_INCREMENT = " + count;
			statement.execute(query);
			
			
			resultSet.close();		
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
	}
}
