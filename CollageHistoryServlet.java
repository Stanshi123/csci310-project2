package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.ArrayList;

/**
 * Servlet implementation class CollageHistoryServlet
 */
@WebServlet("/CollageHistoryServlet")
public class CollageHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private List<String> filePaths = new ArrayList<String>();
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public List<String> getFilePaths() {
		return filePaths;
	}
	
}
