package servlets;

import data.PasswordHasher;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;
import java.util.Objects;

import static data.Constants.DEFAULT_COST;

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
		ResultSet resultSet = null;
		Connection conn = null;
		String query;
		PasswordHasher hasher = new PasswordHasher(DEFAULT_COST);

		// return result (fail/success depending on SQL query)
		res = "fail";
		int user_id = -1;
		int count = 0;

		if (!Objects.equals(username, "") && !Objects.equals(password, "")) {
			try {
				// establish connection
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/scrumdb?user=root&password=root&useSSL=false");

				// check if user provides correct login credentials
				query = "SELECT COUNT(*) AS count FROM user WHERE username = ? ;";

				PreparedStatement preparedStatement = conn.prepareStatement(query); // SQL statement
				preparedStatement.setString( 1, username);

				resultSet = preparedStatement.executeQuery();

				if (resultSet.next())
					count = resultSet.getInt("count");

				if (count == 1) {

					query = "SELECT password, user_id FROM user WHERE username = ? ;";

					preparedStatement = conn.prepareStatement(query); // SQL statement
					preparedStatement.setString( 1, username);

					resultSet = preparedStatement.executeQuery();

					String hashedPassword = "";
					if (resultSet.next()) {
						hashedPassword = resultSet.getString("password");
						user_id = resultSet.getInt("user_id");
					}

					if (hasher.authenticate(password.toCharArray(),hashedPassword)) {
						res = "success";
						session.setAttribute("user_id", user_id);
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
}

