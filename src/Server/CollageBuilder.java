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
import java.net.URL;
import java.util.*;
import java.util.List;

public class CollageBuilder {
    protected List<BufferedImage> images;

    private static final int NUMBER_OF_ROW = 10;
    private static final int IMAGE_PER_ROW = 15;
    private static final int BORDER_PIXEL = 3;

    private String shapeKeyword = "USC";


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
    public CollageBuilder(List<BufferedImage> images, String shapeKeyword) {
        this.shapeKeyword = shapeKeyword;
        this.images = images;
    }

    public BufferedImage createCollageWithImages(int width, int height, boolean addBorder, boolean rotate, Filter filter) {
        BufferedImage backGround = createBackgroundImage(width, height, addBorder, rotate, filter);

        // add filter
        if (filter != null) {
            switch (filter) {
                case GREY_SCALE:
                    backGround = addGrayScaleFilter(backGround);
                    break;
                case BLACK_AND_WHITE:
                    backGround = addBlackAndWhiteFilter(backGround);
                    break;
                case SEPIA:
                    backGround = addSepiaFilter(backGround, 1);
                    break;
            }
        }

        BufferedImage textImage = new BufferedImage(backGround.getWidth(), backGround.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = textImage.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, textImage.getWidth(), textImage.getHeight());
        FontRenderContext frc = g.getFontRenderContext();
        Font font = createFont(textImage.getWidth, textImage.getHeight, shapeKeyword.length());
        GlyphVector gv = font.createGlyphVector(frc, shapeKeyword);
        Rectangle2D box = gv.getVisualBounds();
        int xOff = (width - (int)box.getWidth()) / 2 - (int)box.getX();
        int yOff = (height - (int)box.getHeight()) / 2 - (int)box.getY();
        Shape shape = gv.getOutline(xOff,yOff);
        g.setClip(shape);
        g.drawImage(backGround,0,0,null);
        g.setClip(null);
        g.setStroke(new BasicStroke(0));
        //g.setColor(Color.BLACK);
        g.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g.draw(shape);
        g.dispose();


        return textImage;
    }


    protected BufferedImage createBackgroundImage(int width, int height, boolean addBorder, boolean rotate, Filter filter) {
        BufferedImage collageSpace = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = collageSpace.createGraphics();

        g.setColor(Color.white);

        int imageSize = width * height / 150;

        double ratio = width/ height;
        int imageHeight = (int) Math.sqrt(imageSize / ratio);
        int imageWidth = imageHeight * (int) ratio;

        BufferedImage backgroundImage = resize(images.get( (int) (Math.random() * 30)), width, height);
        g.drawImage(backgroundImage, 0, 0, null);

        for (int i = 0; i < NUMBER_OF_ROW; i++) {

            for (int j = 0; j < IMAGE_PER_ROW; j++) {
                AffineTransform original = g.getTransform();
                int xCoordinate = j * imageWidth;
                int yCoordinate = i * imageHeight;
                BufferedImage image = resize(images.get((int) (Math.random() * 30)), imageWidth, imageHeight);
                if (addBorder) {
                    image = addBorder(image);
                }
                if (rotate) {
                	    double angle = Math.random() * 90.0 - 45.0;
                    g.rotate(angle, xCoordinate, yCoordinate);
                }
                g.drawImage(image, xCoordinate, yCoordinate, null);
                g.setTransform(original);
            }
        }
        return collageSpace;
    }

    // This method adds border to the image
    protected BufferedImage addBorder(BufferedImage image){
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
    protected BufferedImage resize(BufferedImage img, int width, int height) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = image.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return image;
    }

    protected Font createFont(int width, int height, int wordLength){
        Font font = null;
        if(height < width / 2) {
            if(wordLength >= 3) {
                font = new Font(Font.SANS_SERIF, Font.BOLD, (int)((double)height * 2.7 / wordLength));
            }else {
                font = new Font(Font.SANS_SERIF, Font.BOLD, (int)((double)height * 1.1));
            }
        }else {
            if(wordLength >= 3) {
                font = new Font(Font.SANS_SERIF, Font.BOLD, (int)((double)width * 1.2 / wordLength));
            }else {
                font = new Font(Font.SANS_SERIF, Font.BOLD, (int)((double)width * 0.6));
            }
        }
        return font;
    }
    
    protected BufferedImage addBlackAndWhiteFilter(BufferedImage img)
    {
        int w = img.getWidth();
        int h = img.getHeight();

        int limit = 255 * 50 / 100;

        for(int i = 0, j; i < w; ++i) {
            for(j = 0; j < h; ++j) {
                Color color = new Color(img.getRGB(i, j));
                if(limit <= color.getRed() || limit <= color.getGreen() || limit <= color.getBlue()) {
                    img.setRGB(i, j, Color.WHITE.getRGB());
                } else {
                    img.setRGB(i, j, Color.BLACK.getRGB());
                }
            }
        }
        return img;
    }


    // Add Grey filter
    protected BufferedImage addGrayScaleFilter(BufferedImage master) {
        BufferedImage gray = new BufferedImage(master.getWidth(), master.getHeight(), BufferedImage.TYPE_INT_ARGB);

        ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        op.filter(master, gray);

        return gray;
    }


    protected BufferedImage addSepiaFilter(BufferedImage img, int sepiaIntensity) {
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
