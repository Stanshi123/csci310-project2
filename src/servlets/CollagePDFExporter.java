package servlets;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CollagePDFExporter {
    private static final String DEST = System.getProperty("user.home") + "/Desktop/test1.pdf";
    private BufferedImage image;

    public BufferedImage getImage() {
        return image;
    }

    public CollagePDFExporter(BufferedImage image) {
        this.image = image;
    }

    public boolean manipulatePdf() {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", baos);
            Image iTextImage = Image.getInstance(baos.toByteArray());

            Document document = new Document(iTextImage);
            PdfWriter.getInstance(document, new FileOutputStream(DEST));
            document.open();
            document.setPageSize(iTextImage);
            document.newPage();
            iTextImage.setAbsolutePosition(0, 0);
            document.add(iTextImage);
            document.close();
            return true;
        } catch (IOException | DocumentException ioe) {
            return false;
        }
    }
}