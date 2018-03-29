package Server;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

public class CollageBuilder {
    private List<BufferedImage> images;

    private static final int NUMBER_OF_ROW = 10;
    private static final int IMAGE_PER_ROW = 15;
    private static final int BORDER_PIXEL = 3;


    public CollageBuilder() {
        images = new ArrayList<>();
        BufferedImage image = null;
        try {
            URL url = new URL("https://media0dk-a.akamaihd.net/87/16/2cda3e87c8d45e18cc7e3e931c26a244.jpg");
            image = ImageIO.read(url);
        } catch (Exception e) {
            System.out.println("Read fail");
        }

        for (int i = 0; i < 30; i++) {
            images.add(image);
        }
    }
    public CollageBuilder(List<BufferedImage> images) {
        this.images = images;
    }

    public BufferedImage createCollageWithImages(int width, int height, boolean addBorder, Filter filter) {
        BufferedImage backGround = createBackGroundImage(width, height, addBorder, filter);

        // add filter
        if (filter != null) {
            switch (filter) {
                case GREY_SCALE:
                    backGround = addGreyScaleFilter(backGround);
                    break;
                case BLACK_AND_WHITE:
                    backGround = addBlackAndWhiteFilter(backGround);
                    break;
                case SEPIA:
                    backGround = addSepiaFilter(backGround, 1);
                    break;
            }
        }

        BufferedImage textImage = new BufferedImage(
                backGround.getWidth(),
                backGround.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = textImage.createGraphics();
        FontRenderContext frc = g.getFontRenderContext();
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, backGround.getWidth()/2);
        GlyphVector gv = font.createGlyphVector(frc, "USC");
        Rectangle2D box = gv.getVisualBounds();
        int xOff = 25 + (int)-box.getX();
        int yOff = 80 + (int)-box.getY();
        Shape shape = gv.getOutline(xOff,yOff);
        g.setClip(shape);
        g.drawImage(backGround,0,0,null);
        g.setClip(null);
        g.setStroke(new BasicStroke(0));
//        g.setRenderingHint(
//            RenderingHints.KEY_ANTIALIASING,
//            RenderingHints.VALUE_ANTIALIAS_ON);
        g.draw(shape);
        g.dispose();

        System.out.println("Image finished");

        File file = new File("test.png");
        try {
            ImageIO.write(textImage,"png",file);
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return textImage;
    }

    private BufferedImage createBackGroundImage(int width, int height, boolean addBorder, Filter filter) {
        BufferedImage collageSpace = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = collageSpace.createGraphics();

        g.setColor(Color.white);

        int imageSize = width * height / 150;

        double ratio = width/ height;
        int imageHeight = (int) Math.sqrt(imageSize / ratio);
        int imageWidth = imageHeight * (int) ratio;

        for (int i = 0; i < NUMBER_OF_ROW; i++) {

            for (int j = 0; j < IMAGE_PER_ROW; j++) {
                AffineTransform original = g.getTransform();
                BufferedImage image = resize(images.get( (int) (Math.random() * 30)), imageWidth, imageHeight);
                int xCoordinate = j * imageWidth;
                int yCoordinate = i * imageHeight;
                if (addBorder) {
                    image = addBorder(image);
                }
                System.out.println("drawing image " + i * j);
                g.drawImage(image,xCoordinate,yCoordinate,null);
                g.setTransform(original);
            }
        }
        return collageSpace;
    }

    // This method adds border to the image
    private BufferedImage addBorder(BufferedImage image){
        //create a new image
        BufferedImage borderedImage = new BufferedImage(image.getWidth() + BORDER_PIXEL * 2,image.getHeight() + BORDER_PIXEL * 2,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = borderedImage.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0,0, borderedImage.getWidth(), borderedImage.getHeight());

        //draw the old one on the new one
        g.drawImage(image,BORDER_PIXEL,BORDER_PIXEL,null);
        return borderedImage;
    }

    // This resize an image
    private BufferedImage resize(BufferedImage img, int width, int height) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = image.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return image;
    }
    
    // This method add a black and white filter a image
    private BufferedImage addBlackAndWhiteFilter(BufferedImage image)
    {
        int width = image.getWidth();
        int height = image.getHeight();
        
        int limit = 255 * 50 / 100;
        
        for(int i = 0, j; i < width; ++i) {
            for(j = 0; j < height; ++j) {
                Color color = new Color(image.getRGB(i, j));
                if(limit <= color.getRed() || limit <= color.getGreen() || limit <= color.getBlue()) {
                    image.setRGB(i, j, Color.WHITE.getRGB());
                } else {
                    image.setRGB(i, j, Color.BLACK.getRGB());
                }
            }
        }
        return image;
    }
    
    // This method add a gray scale filter a image
    private BufferedImage addGrayScaleFilter(BufferedImage master) {
        BufferedImage gray = new BufferedImage(master.getWidth(), master.getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        op.filter(master, gray);
        
        return gray;
    }
    
    //This method add a sepia filter a image
    private BufferedImage addSepiaFilter(BufferedImage img, int sepiaIntensity) {
        BufferedImage sepia = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        // Play around with this.  20 works well and was recommended
        //   by another developer. 0 produces black/white image
        int sepiaDepth = 20;
        
        int w = img.getWidth();
        int h = img.getHeight();
        
        // We need 3 integers (for R,G,B color values) per pixel.
        int[] pixels = new int[w * h * 3];
        img.getRaster().getPixels(0, 0, w, h, pixels);
        
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                
                int rgb = img.getRGB(x, y);
                Color color = new Color(rgb, true);
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();
                int gry = (r + g + b) / 3;
                
                r = g = b = gry;
                r = r + (sepiaDepth * 2);
                g = g + sepiaDepth;
                
                if (r > 255) {
                    r = 255;
                }
                if (g > 255) {
                    g = 255;
                }
                if (b > 255) {
                    b = 255;
                }
                
                // Darken blue color to increase sepia effect
                b -= sepiaIntensity;
                
                // normalize if out of bounds
                if (b < 0) {
                    b = 0;
                }
                if (b > 255) {
                    b = 255;
                }
                
                color = new Color(r, g, b, color.getAlpha());
                sepia.setRGB(x, y, color.getRGB());
                
            }
        }
        
        return sepia;
    }
}
