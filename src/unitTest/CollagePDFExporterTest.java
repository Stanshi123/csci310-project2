package unitTest;

import org.junit.Before;
import org.junit.Test;
import servlets.CollagePDFExporter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.*;

public class CollagePDFExporterTest {

    private BufferedImage image;
    private static final String DEST = System.getProperty("user.home") + "/Desktop/test1.pdf";

    @Before
    public void initialize() {
        try {
            URL imageURL = new URL("https://s7d1.scene7.com/is/image/PETCO/puppy-090517-dog-featured-355w-200h-d");
            this.image = ImageIO.read(imageURL);
        }catch(IOException  e){}
    }

    @Test
    public void testConstructor() {
        CollagePDFExporter ce = new CollagePDFExporter(this.image);
        assertTrue(ce.getImage() != null);
        assertTrue(ce.getImage() instanceof  BufferedImage);
    }
    @Test
    public void testManipulatePdf() {
        CollagePDFExporter ce = new CollagePDFExporter(this.image);
        assertTrue(ce.manipulatePdf());
    }

}