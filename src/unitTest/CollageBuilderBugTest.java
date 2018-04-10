package unitTest;

import static org.junit.Assert.*;

import java.awt.Font;

import org.junit.Test;

import Server.CollageBuilder;

public class CollageBuilderBugTest extends CollageBuilder{
	@Test
	public void testCreateFont() {
		Font fontCreated = createFont(800, 600, 3);
		assertTrue("The font created is an instance of font.", fontCreated instanceof Font);
	}
}
