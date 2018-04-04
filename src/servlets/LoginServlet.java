package servlets;

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


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String res = "";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		
		if (username != "" && password != "") {
			try {
				// establish connection
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/scrumdb?user=root&password=root&useSSL=false");
				
				statement = conn.createStatement(); // SQL statement
				
				// check if user provides correct login credentials
				query = "SELECT "
						+ "COUNT(*) AS count "
						+ "FROM user "
							+ "WHERE username = '" + username + "' "
								+ "AND password = '" + password + "';";

				resultSet = statement.executeQuery(query);
				while (resultSet.next()) {
					count = resultSet.getInt("count");
				}

				query = "SELECT "
						+ "user_id "
						+ "FROM user "
						+ "WHERE username = '" + username + "' "
						+ "AND password = '" + password + "';";

				resultSet = statement.executeQuery(query);
				while (resultSet.next())
					user_id = resultSet.getInt("user_id");
	
				// if count returns 1, then the username/password combination matches the database
				if (count == 1)
					res = "success";
				
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

		if (count == 1)
			session.setAttribute("user_id", user_id); // Set session username if login is successful

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
}
