package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
		String result = "fail";
		
		int count = 0;
		try {
			// establish connection
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/scrumdb?user=root&password=root&useSSL=false");
			
			statement = conn.createStatement(); // SQL statement
			
			// check if user provides correct login credentials
			query = "SELECT COUNT(*) AS count FROM user WHERE username = '" + username + "' AND password = '" + password + "';";
			resultSet = statement.executeQuery(query);

			while (resultSet.next())
				count = resultSet.getInt("count");

			// if count returns 1, then the username/password combination matches the database
			if (count == 1)
				result = "success";
			
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

		if (count == 1)
			session.setAttribute("username", username); // Set session username if login is successful

		// return ajax response
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(result);
	}

}
