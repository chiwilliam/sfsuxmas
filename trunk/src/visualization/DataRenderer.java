package visualization;

import com.sfsu.xmas.data_sets.ExpressionDataSet;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class DataRenderer {

    protected int imageMargin = 20;
    protected int imageWidth = 500;
    protected int imageHeight = 80;

    public BufferedImage getDataVisualization(ExpressionDataSet expressionDatabase) throws IOException {
        // Create image
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = getVizCanvas(image);

        int markerHeight = 10;

        g2d.setColor(Color.BLACK);

        g2d.drawLine(
                0,
                50,
                imageWidth,
                50); // Horizontal line

        double maxExpression = expressionDatabase.getMaximumExpression();
        double minExpression = expressionDatabase.getMinimumExpression();
        double meanExpression = expressionDatabase.getMeanExpression();

        int minMin = (int) Math.floor(minExpression);
        int maxMax = (int) Math.ceil(maxExpression);
        int fullRange = (int) maxMax - minMin;

        int unit = (int) Math.ceil(fullRange / 10);

        double singleUnitWidth = (float) (imageWidth - (2 * imageMargin)) / (float) fullRange;

        int axisOffset = 50;

        g2d.drawLine(
                imageMargin,
                axisOffset,
                imageMargin,
                axisOffset + markerHeight); // MinMin Marker

        g2d.drawString(String.valueOf(minMin), (float) imageMargin - 10, (float) axisOffset + (2 * markerHeight) + 5);

        g2d.drawLine(
                imageWidth - imageMargin,
                axisOffset,
                imageWidth - imageMargin,
                axisOffset + markerHeight); // MaxMax Marker

        g2d.drawString(String.valueOf(maxMax), (float) imageWidth - imageMargin - 10, (float) axisOffset + (2 * markerHeight) + 5);

        int horizontalOffset = 0;

        /**
         * SCALE Markers
         */
        for (int i = minMin; i < maxMax; i++) {
            horizontalOffset = (int) (imageMargin + ((i - minMin) * singleUnitWidth));
            g2d.drawLine(
                    horizontalOffset,
                    axisOffset,
                    horizontalOffset,
                    axisOffset + (markerHeight / 2)); // MaxMax Marker
        }


        /**
         * MINIMUM Expression pointer
         */
        g2d.setColor(Color.BLUE);
        horizontalOffset = (int) (imageMargin + ((minExpression - minMin) * singleUnitWidth));
        g2d.drawLine(
                horizontalOffset,
                axisOffset,
                horizontalOffset,
                axisOffset - markerHeight); // Min Marker

        g2d.drawString("Min", (float) horizontalOffset - 15, (float) axisOffset - markerHeight - 5);

        /**
         * MAXIMUM Expression pointer
         */
        g2d.setColor(Color.RED);
        horizontalOffset = (int) (imageMargin + ((maxExpression - minMin) * singleUnitWidth));
        g2d.drawLine(
                horizontalOffset,
                axisOffset,
                horizontalOffset,
                axisOffset - markerHeight); // Max Marker

        g2d.drawString("Max", (float) horizontalOffset - 15, (float) axisOffset - markerHeight - 5);

        /**
         * MEAN Expression pointer
         */
        g2d.setColor(Color.BLACK);
        horizontalOffset = (int) (imageMargin + ((meanExpression - minMin) * singleUnitWidth));
        g2d.drawLine(
                horizontalOffset,
                axisOffset,
                horizontalOffset,
                axisOffset - markerHeight); // Max Marker

        g2d.drawString("Mean", (float) horizontalOffset - 15, (float) axisOffset - markerHeight - 5);

        return image;
    }

    protected Graphics2D getVizCanvas(BufferedImage image) {
        Graphics2D g2d = image.createGraphics();
        // Fill background with white
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, imageWidth, imageHeight);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return g2d;
    }
}
