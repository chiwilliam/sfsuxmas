package com.sfsu.xmas.visualizations.heat_map;

import com.sfsu.xmas.data_sets.ExpressionDataSet;
import com.sfsu.xmas.data_structures.Probe;
import com.sfsu.xmas.data_structures.expression.TimePeriod;
import com.sfsu.xmas.data_structures.expression.TimePeriods;
import com.sfsu.xmas.monitoring.ExecutionTimer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import com.sfsu.xmas.visualizations.ColorManager;

public class HeatMapGenerator {
    
    protected Probe probe;
    protected boolean timePeriods;
    protected int width;
    protected int height;
    protected double minimum;
    protected double maximum;
    protected double range;
    protected double expressionUnitWidth;

    public HeatMapGenerator(Probe probe, int width, int height, boolean timePeriods) {
        this.probe = probe;
        this.width = width;
        this.height = height;
        this.timePeriods = timePeriods;
        
        ExpressionDataSet pDB = probe.getParentDatabase();
        minimum = pDB.getMinimumExpression();
        maximum = pDB.getMaximumExpression();
        
        pDB = null;
        
        range = maximum - minimum;
    }

    public BufferedImage renderHeatMap() {
        ExecutionTimer et = new ExecutionTimer();
        et.start();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = getVizCanvas(image);

        double[] expression = new double[0];
        if (timePeriods) {
            expression = probe.getTimePeriodExpression();
        } else {
            expression = probe.getSampleExpression();
        }
        expressionUnitWidth = (double) width / expression.length;

        for (int i = 0; i < expression.length; i++) {
            double exp = expression[i]; // - expression[0];
            Color expColor = ColorManager.getColor(exp, minimum, maximum);
            g2d.setColor(expColor);
            // render cell
            int xOffset = (int) Math.floor(i * expressionUnitWidth);
            Rectangle cell = new Rectangle(xOffset, 0, (int) Math.ceil(expressionUnitWidth), height);
            g2d.fill(cell);
        }

        if (timePeriods) {
            // Time Scale
            for (int i = 1; i < expression.length; i++) {
                g2d.setColor(Color.BLACK);
                // render cell
                int xOffset = (int) Math.floor(i * expressionUnitWidth);
                g2d.drawLine(
                        xOffset,
                        0,
                        xOffset,
                        2);
            }
        } else {
            int sampleCount = 0;
            TimePeriods tps = probe.getParentDatabase().getTimePeriods();
            for (int i = 0; i < tps.size(); i++) {
                TimePeriod tp = tps.get(i);
                // Time Scale
                for (int j = Math.max(sampleCount, 1); j < sampleCount + tp.getSampleCount() && j < expression.length; j++) {
                    // render cell
                    int xOffset = (int) Math.floor(j * expressionUnitWidth);
                    int lineHeight = 2;
                    if (j == sampleCount) {
                        lineHeight = 4;
                    }
                    g2d.setColor(Color.BLACK);
                    g2d.drawLine(
                            xOffset,
                            0,
                            xOffset,
                            lineHeight);
                    g2d.setColor(Color.WHITE);
                    g2d.drawLine(
                            xOffset,
                            height,
                            xOffset,
                            height - lineHeight);
                }
                sampleCount += tp.getSampleCount();
            }
        }

        et.end();
        System.out.println("DURATION = " + et.duration() + " in class " + this.getClass()   .getName());

        return image;
    }

    protected Graphics2D getVizCanvas(BufferedImage image) {
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        return g2d;
    }
}
