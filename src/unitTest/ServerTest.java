package unitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import Server.Server;
import data.Result;

public class ServerTest {

	@Test
	public void testGetResultForKeywordWithDog() {
		Server server = Server.getInstance();
		Result result = server.getResultForKeyword("dog");
		assertTrue(result instanceof Result);	
	}
	
	@Test
	public void testGetResultForKeywordWithGibberish() {
		Server server = Server.getInstance();
		Result result = server.getResultForKeyword("shudfvgiowadhgivudhbiavuadjcibaeuyfsvcdujhvzxckbjcsoijaksab");
		assertTrue(result instanceof Result);	
	}
	
	@Test
	public void testGetResultForKeywordWithDogTwice() {
		Server server = Server.getInstance();
		Result result = server.getResultForKeyword("dog");
		// call it again
		result = server.getResultForKeyword("dog");
		assertTrue(result instanceof Result);	
	}

}
