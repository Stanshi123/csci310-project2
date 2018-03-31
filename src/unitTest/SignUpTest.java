
package whiteboxtests;

import static org.junit.Assert.assertTrue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;

import org.mockito.Mockito;

import servlets.SignUpServlet;

public class SignUpTest extends Mockito {
	
	private SignUpServlet servlet;
    
    @Test
    public void testSignUpSuccessfully() throws Exception {
    	HttpServletRequest request = mock(HttpServletRequest.class);
    	HttpServletResponse response = mock(HttpServletResponse.class);
    	
    	when (request.getParameter("username")).thenReturn("newAccount");
    	when (request.getParameter("password")).thenReturn("newAccount");
    	when (request.getSession()).thenReturn(mock(HttpSession.class));
    	
    	servlet = new SignUpServlet();
    	servlet.doPost(request, response);
    	
    	assertTrue(response != null);
    	String result = servlet.getResult();
    	assertTrue(result.equals("success"));
    }
    
    @Test
    public void testLoginFail() throws Exception {
    	HttpServletRequest request = mock(HttpServletRequest.class);
    	HttpServletResponse response = mock(HttpServletResponse.class);
    	
    	when (request.getParameter("username")).thenReturn("william");
    	when (request.getParameter("password")).thenReturn("william");
    	when (request.getSession()).thenReturn(mock(HttpSession.class));
    	
    	servlet = new SignUpServlet();
    	servlet.doPost(request, response);
    	
    	assertTrue(response != null);
    	String result = servlet.getResult();
    	assertTrue(result.equals("fail"));
    }
    
    @Test
    public void testEmptyParameters() throws Exception {
    	HttpServletRequest request = mock(HttpServletRequest.class);
    	HttpServletResponse response = mock(HttpServletResponse.class);
    	
    	when (request.getParameter("username")).thenReturn("");
    	when (request.getParameter("password")).thenReturn("");
    	
    	servlet = new SignUpServlet();
    	servlet.doPost(request, response);
    	
    	assertTrue(response != null);
    	String result = servlet.getResult();
    	assertTrue(result.equals("fail"));
    }
    
    @Test
    public void testEmptyUsername() throws Exception {
    	HttpServletRequest request = mock(HttpServletRequest.class);
    	HttpServletResponse response = mock(HttpServletResponse.class);
    	
    	when (request.getParameter("username")).thenReturn("");
    	when (request.getParameter("password")).thenReturn("newAccount");
    	
    	servlet = new SignUpServlet();
    	servlet.doPost(request, response);
    	
    	assertTrue(response != null);
    	String result = servlet.getResult();
    	assertTrue(result.equals("fail"));
    }
    
    @Test
    public void testEmptyPassword() throws Exception {
    	HttpServletRequest request = mock(HttpServletRequest.class);
    	HttpServletResponse response = mock(HttpServletResponse.class);
    	
    	when (request.getParameter("username")).thenReturn("newAccount");
    	when (request.getParameter("password")).thenReturn("");
    	
    	servlet = new SignUpServlet();
    	servlet.doPost(request, response);
    	
    	assertTrue(response != null);
    	String result = servlet.getResult();
    	assertTrue(result.equals("fail"));
    }
}