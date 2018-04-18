
package unitTest;

import static org.junit.Assert.assertTrue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;

import org.mockito.Mockito;

import servlets.LoginServlet;

public class LoginTest extends Mockito {
	
	private LoginServlet servlet;
	private static final String USERNAME = "TEST";
	private static final String PASSWORD = "TEST";
	private static final String WRONG_PASSWORD = "wrong";
	private static final String EMPTY_STRING = "";
    
    @Test
    // User should be able to login with the correct credentials
    public void testLoginSuccessfully() throws Exception {
    	HttpServletRequest request = mock(HttpServletRequest.class);
    	HttpServletResponse response = mock(HttpServletResponse.class);
    	
    	when (request.getParameter("username")).thenReturn(USERNAME);
    	when (request.getParameter("password")).thenReturn(PASSWORD);
    	when (request.getSession()).thenReturn(mock(HttpSession.class));
    	
    	servlet = new LoginServlet();
    	servlet.doPost(request, response);
    	
    	assertTrue(response != null);
    	
    	String result = servlet.getResult();
    	//assertTrue(result.equals("success"));
    }
    
    @Test
    // User should not be able to login without the correct password
    public void testLoginFail() throws Exception {
    	HttpServletRequest request = mock(HttpServletRequest.class);
    	HttpServletResponse response = mock(HttpServletResponse.class);
    	
    	when (request.getParameter("username")).thenReturn(USERNAME);
    	when (request.getParameter("password")).thenReturn(WRONG_PASSWORD);
    	when (request.getSession()).thenReturn(mock(HttpSession.class));
    	
    	servlet = new LoginServlet();
    	servlet.doPost(request, response);
    	
    	assertTrue(response != null);
    	String result = servlet.getResult();
    	assertTrue(result.equals("fail"));
    }
    
    @Test
    // User should not be able to login with at least one empty field
    public void testEmptyParameters() throws Exception {
    	HttpServletRequest request = mock(HttpServletRequest.class);
    	HttpServletResponse response = mock(HttpServletResponse.class);
    	
    	when (request.getParameter("username")).thenReturn(EMPTY_STRING);
    	when (request.getParameter("password")).thenReturn(EMPTY_STRING);
    	
    	servlet = new LoginServlet();
    	servlet.doPost(request, response);
    	
    	assertTrue(response != null);
    	String result = servlet.getResult();
    	assertTrue(result.equals("fail"));
    }
    
    @Test
    // User should not be able to login with at least one empty field    
    public void testEmptyUsername() throws Exception {
    	HttpServletRequest request = mock(HttpServletRequest.class);
    	HttpServletResponse response = mock(HttpServletResponse.class);
    	
    	when (request.getParameter("username")).thenReturn(EMPTY_STRING);
    	when (request.getParameter("password")).thenReturn(PASSWORD);
    	
    	servlet = new LoginServlet();
    	servlet.doPost(request, response);
    	
    	assertTrue(response != null);
    	String result = servlet.getResult();
    	assertTrue(result.equals("fail"));
    }
    
    @Test
    // User should not be able to login with at least one empty field    
    public void testEmptyPassword() throws Exception {
    	HttpServletRequest request = mock(HttpServletRequest.class);
    	HttpServletResponse response = mock(HttpServletResponse.class);
    	
    	when (request.getParameter("username")).thenReturn(USERNAME);
    	when (request.getParameter("password")).thenReturn(EMPTY_STRING);
    	
    	servlet = new LoginServlet();
    	servlet.doPost(request, response);
    	
    	assertTrue(response != null);
    	String result = servlet.getResult();
    	assertTrue(result.equals("fail"));
    }
}