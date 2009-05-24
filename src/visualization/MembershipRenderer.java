package visualization;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;

public class MembershipRenderer {

    protected int imageMargin = 0;
    protected int barHeight = 9;
    protected int imageWidth = 120;
    protected int imageHeight = 50;
    
    protected int max;
    protected int maxPoss;
    protected int actual;

    public MembershipRenderer(int max, int maxPoss, int actual) {
        this.max = max;
        this.maxPoss = maxPoss;
        this.actual = actual;
    }

    public BufferedImage getVisualization() throws IOException {
        // Create image
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = getVizCanvas(image);


        double unit = (double) imageWidth / (double) Math.max(max, maxPoss) * 1.0;
        
        RoundRectangle2D box = new RoundRectangle2D.Float(0, 0, Math.round(actual*unit), barHeight, 2, 2);
        g2d.setColor(Color.GREEN);
        g2d.fill(box);
        
        box = new RoundRectangle2D.Float(0, 10, Math.round(maxPoss*unit), barHeight, 2, 2);
        g2d.setColor(Color.BLUE);
        g2d.fill(box);
        
        box = new RoundRectangle2D.Float(0, 20, Math.round(max*unit), barHeight, 2, 2);
        g2d.setColor(Color.RED);
        g2d.fill(box);

        g2d.setColor(Color.BLACK);
        g2d.drawLine(
                0,
                30,
                imageWidth,
                30); // Horizontal line

        return image;
    }

    protected Graphics2D getVizCanvas(
            BufferedImage image) {
        Graphics2D g2d = image.createGraphics();
        // Fill background with white
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, imageWidth, imageHeight);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return g2d;
    }
}
