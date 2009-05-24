package visualization;

import com.sfsu.xmas.data_sets.ExpressionDataSet;
import com.sfsu.xmas.data_sets.ExpressionDataSetMultiton;
import com.sfsu.xmas.trajectory_files.LeafNodes;
import com.sfsu.xmas.data_structures.Probes;
import com.sfsu.xmas.data_structures.expression.TimePeriod;
import com.sfsu.xmas.data_structures.expression.TimePeriods;
import com.sfsu.xmas.filter.FilterManager;
import com.sfsu.xmas.highlight.HighlightManager;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.StringWriter;
import com.sfsu.xmas.trajectory_files.TrajectoryFile;
import com.sfsu.xmas.trajectory_files.TrajectoryFileFactory;

public abstract class AbstractViz implements IVisualization {

    protected double expressionMinUnit;
    protected String identifier;
    
    protected int eDBID;
    protected String trajFN;
//    protected TrajectoryFile activeTrajectoryDocument;
//    protected ExpressionDataSet expressionDatabase;
    protected int numberOfHighProbes;
    protected int mWidth;
    protected int mHeight;
//    protected int axisWidth;
//    protected int axisHeight;
    protected int boxSize = 2;
    protected double timePeriodWidth;
//    protected TrajectoryDocument trajectoryDocument;
    protected int borderSize = 30;
    protected int minBorderSize = 5;
    protected StringWriter imgMap = new StringWriter();
    protected int mMinExpressionBinValue;
    protected int mMaxExpressionBinValue;
    protected int mCountExpressionBins;
    protected double mVerticalBinSpacing;
    protected int mNodeCount;
    protected int mTimePeriodCount;
    protected boolean cropToData = false;
    protected boolean bottomAxis = true;
    protected boolean useTopPadding = false;
    protected int topPadding;
    protected Composite originalComposite;
    protected boolean fadedPalette = false;
    protected LeafNodes leaves;
    public static final Color[] colors = new Color[]{
        Color.BLACK,
        Color.BLUE,
        Color.CYAN,
        Color.DARK_GRAY,
        Color.GRAY,
        Color.GREEN,
        Color.LIGHT_GRAY,
        Color.MAGENTA,
        Color.ORANGE,
        Color.PINK,
        Color.RED,
        Color.YELLOW
    };
    protected static final Stroke baseStroke = new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    protected static final Stroke highStroke = new BasicStroke(1.7f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    protected static final Stroke faintStroke = new BasicStroke(0.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    protected static final Stroke compStroke = new BasicStroke(
            /* line width */0.8f,
            /* cap style */ BasicStroke.CAP_ROUND,
            /* join style, miter limit */ BasicStroke.JOIN_ROUND, 1.0f,
            /* the dash pattern */ new float[]{8.0f, 5.0f},
            /* the dash phase */ 0.0f);
    protected static final Stroke highCompStroke = new BasicStroke(
            /* line width */1.0f,
            /* cap style */ BasicStroke.CAP_ROUND,
            /* join style, miter limit */ BasicStroke.JOIN_ROUND, 1.0f,
            /* the dash pattern */ new float[]{8.0f, 5.0f},
            /* the dash phase */ 0.0f);
    protected static final AlphaComposite faintComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.5);
    protected static final AlphaComposite baseComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 1);

    public AbstractViz(String identifier, ExpressionDataSet expressionDatabase, TrajectoryFile activeTrajectoryDocument) {
        this.identifier = identifier;
//        this.expressionDatabase = expressionDatabase;
//        this.activeTrajectoryDocument = activeTrajectoryDocument;
        
        eDBID = expressionDatabase.getID();
        trajFN = activeTrajectoryDocument.getFileName();
        setCropToData();
        setImageHeight();
        setImageWidth();
        numberOfHighProbes = HighlightManager.getUniqueInstance().getHighlighted(identifier).size();
    }

    public AbstractViz(String identifier, ExpressionDataSet expressionDatabase, TrajectoryFile activeTrajectoryDocument, int width, int height) {
        this.identifier = identifier;
//        this.expressionDatabase = expressionDatabase;
//        this.activeTrajectoryDocument = activeTrajectoryDocument;
        
        eDBID = expressionDatabase.getID();
        trajFN = activeTrajectoryDocument.getFileName();
        
        setCropToData();

        this.mWidth = width;
        this.mHeight = height;

        numberOfHighProbes = HighlightManager.getUniqueInstance().getHighlighted(identifier).size();
    }

    protected void initialize() {
        mTimePeriodCount = ExpressionDataSetMultiton.getUniqueInstance().getDataSet(eDBID, false).getNumberOfTimePeriods();
        if (TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN) != null) {
            mMinExpressionBinValue = TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN).getMinimumBinValue(); //int) Math.floor(expressionDatabase.getMinimumExpression()); //trajectoryDocument.getMinimumBinValue();
            mMaxExpressionBinValue = TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN).getMaximumBinValue(); //int) Math.ceil(expressionDatabase.getMaximumExpression());
            expressionMinUnit = TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN).getBinUnit();
        } else {
            mMinExpressionBinValue = (int) Math.floor(ExpressionDataSetMultiton.getUniqueInstance().getDataSet(eDBID, false).getMinimumExpression());
            mMaxExpressionBinValue = (int) Math.floor(ExpressionDataSetMultiton.getUniqueInstance().getDataSet(eDBID, false).getMaximumExpression());
            expressionMinUnit = 1;
        }

        populateDataToRender();
//        if (cropToData) {
//            mMinExpressionBinValue = leaves.getMinExpressionBin();
//            mMaxExpressionBinValue = leaves.getMaxExpressionBin();
//        }

        mCountExpressionBins = Math.abs(mMaxExpressionBinValue - mMinExpressionBinValue + 1);

        timePeriodWidth = (double) 0;
        if (mTimePeriodCount > 0) {
            timePeriodWidth = (double) (mWidth - borderSize) / mTimePeriodCount;
        }
        System.out.println("Bins: " + mCountExpressionBins + ", Min: " + mMinExpressionBinValue + ", Max: " + mMaxExpressionBinValue);
    }

    protected void populateDataToRender() {
    }

    protected AlphaComposite makeComposite(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        return (AlphaComposite.getInstance(type, alpha));
    }

    protected int getXCoordForExpression(int timePeriodID) {
        return (int) Math.round((timePeriodID * timePeriodWidth) + borderSize + (timePeriodWidth / 2));
    }

    protected int getYCoordForExpression(double expressionValue) {
        return (int) Math.round(getXAxisYCoord() - (((expressionValue / expressionMinUnit) - mMinExpressionBinValue) * mVerticalBinSpacing));
    }

    protected Probes getProbes() {
        Probes probes;
        if (TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN) != null && FilterManager.getUniqueInstance().getFilterListForIdentifier(identifier).hasTrajFileBasedFilters()) {
            probes = ExpressionDataSetMultiton.getUniqueInstance().getDataSet(eDBID, false).getProbes(TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN).getProbes(identifier), true);
        } else {
            probes = ExpressionDataSetMultiton.getUniqueInstance().getDataSet(eDBID, false).getProbes(identifier, true);
        }
        return probes;
    }

    protected Graphics2D renderXAxis(Graphics2D g2d, int verticalOffset, int verticalTextOffset) {
        g2d.setColor(Color.BLACK);
        g2d.drawLine(borderSize, verticalOffset, mWidth, verticalOffset);

        TimePeriods timePeriods = ExpressionDataSetMultiton.getUniqueInstance().getDataSet(eDBID, false).getTimePeriods();
        for (int i = 0; i < timePeriods.size(); i++) {
            TimePeriod timePeriod = timePeriods.get(i);
            String timePeriodDescription = String.valueOf(i + 1);
            if (timePeriods.size() < 10) {
                timePeriodDescription = timePeriod.getDescription();
            }
            if (timePeriodDescription.length() > 20) {
                timePeriodDescription = timePeriodDescription.substring(0, 16) + "...";
            }
            addText(g2d, timePeriodDescription,
                    (int) Math.round(borderSize + (timePeriodWidth * i) + 2),
                    verticalOffset + verticalTextOffset);
            g2d.drawLine((int) Math.round((timePeriodWidth * i) + borderSize), verticalOffset + 2, (int) Math.round((timePeriodWidth * i) + borderSize), verticalOffset);
        }
        return g2d;
    }

    protected Graphics2D renderXAxisTop(Graphics2D g2d) {
        return renderXAxis(g2d, borderSize, -5);
    }

    protected Graphics2D renderXAxisBottom(Graphics2D g2d) {
        return renderXAxis(g2d, mHeight - borderSize, 15);
    }

    protected void renderVerticalLines(Graphics2D g2d, Color color) {
        if (color == null) {
            color = new Color(191, 207, 255);
        }
        int hOffset = borderSize;
        for (int i = 1; i <= ExpressionDataSetMultiton.getUniqueInstance().getDataSet(eDBID, false).getNumberOfTimePeriods() - 1; i++) {
            g2d.setColor(color);
            g2d.setStroke(faintStroke);
            g2d.drawLine(
                    (int) Math.round((timePeriodWidth * i) + hOffset),
                    getTopOfYAxisCoord(),
                    (int) Math.round((timePeriodWidth * i) + hOffset),
                    getXAxisYCoord());
            g2d.setStroke(baseStroke);
        }
    }

    protected void renderHorizontalLines(Graphics2D g2d, Color color) {
        if (color == null) {
            color = new Color(191, 207, 255);
        }
        int hOffset = borderSize;
        int vOffset = (int) Math.round(getXAxisYCoord() - (mVerticalBinSpacing * mCountExpressionBins));
        for (int i = 0; i <= mCountExpressionBins; i++) {
            g2d.setColor(color);
            g2d.setStroke(faintStroke);
            g2d.drawLine(
                    hOffset,
                    (int) Math.round((mVerticalBinSpacing * i) + vOffset),
                    getRightOfXAxisXCoord(),
                    (int) Math.round((mVerticalBinSpacing * i) + vOffset));
            g2d.setStroke(baseStroke);
        }
    }

    protected void renderHorizontalShading(Graphics2D g2d) {
        Color backgroundAlt = new Color(240, 240, 240);
        int hOffset = borderSize;
        int vOffset = (int) Math.round(getXAxisYCoord() - (mVerticalBinSpacing * mCountExpressionBins));
        for (int i = 0; i < mCountExpressionBins; i++) {
            if ((mMaxExpressionBinValue - i) % 2 == 0) {
                g2d.setColor(backgroundAlt);
                g2d.fillRect(hOffset, (int) Math.round((mVerticalBinSpacing * i) + vOffset), mWidth - borderSize, (int) Math.round(mVerticalBinSpacing));
            }
        }
    }

    protected Graphics2D getVizCanvas(BufferedImage image) {
        Graphics2D g2d = image.createGraphics();
        // Fill background with white
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, mWidth, mHeight);
        renderAxes(g2d);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return g2d;
    }

    protected static void addText(Graphics2D g2d, String text, int x, int y) {
        Font font = new Font("Arial", Font.PLAIN, 11);
        FontRenderContext frc = g2d.getFontRenderContext();
        GlyphVector gv = font.createGlyphVector(frc, text);
        g2d.drawGlyphVector(gv, x, y);
    }

    protected void renderYAxisScale(Graphics2D g2d) {
        double binUnit = expressionMinUnit;
        g2d.setColor(Color.BLACK);
        int vOffset = (int) Math.round(getXAxisYCoord() - (mVerticalBinSpacing * mCountExpressionBins));
        for (int i = 0; i < (mCountExpressionBins + 1); i++) {
            g2d.drawLine(borderSize - 2, (int) Math.round((mVerticalBinSpacing * i) + vOffset), borderSize, (int) Math.round((mVerticalBinSpacing * i) + vOffset)); // Horizontal line

            double label = ((mMaxExpressionBinValue - i) + 1) * binUnit;
            String unitLabel = String.valueOf(label);
            int labelOffset = (int) Math.round((mVerticalBinSpacing * i) + vOffset + 4);
            if (TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN) != null && !TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN).isPreserved()) {
                // Collapsed, mess with scale
                labelOffset = (int) Math.round(labelOffset - (mVerticalBinSpacing / 2));
            }
            if (mHeight <= 300) {
                if (i == 0 || i == mCountExpressionBins || (((mMaxExpressionBinValue - i) + 1) * binUnit) % 10 == 0) {
                    if (label % 1 == 0) {
                        unitLabel = String.valueOf(Math.round(label));
                    }
                }
            }
            addText(g2d, unitLabel, 2, labelOffset);
        }
        g2d.drawLine(borderSize, getTopOfYAxisCoord(), borderSize, getXAxisYCoord());
    }

    protected abstract void renderAxes(Graphics2D g2d);

    protected abstract void setImageHeight();

    protected void setImageWidth() {
        this.mWidth = 564;
    }

    protected void connectNodeToPreviousTimePoint(boolean highlighted, Graphics2D g2d, int currentTimePeriod, int currentX, int currentY, int previousX, int previousY) {
        if (currentTimePeriod > 0) {
            Stroke currentStroke = g2d.getStroke();
            Composite currentComposite = g2d.getComposite();
            if (highlighted) {
                if (currentStroke == compStroke) {
                    g2d.setStroke(highCompStroke);
                    g2d.setComposite(baseComposite);
                } else {
                    g2d.setStroke(highStroke);
                    g2d.setComposite(baseComposite);
                }
            } else if (numberOfHighProbes > 0) {
                // Make faint
                g2d.setStroke(faintStroke);
                g2d.setComposite(faintComposite);
            }
            g2d.drawLine(
                    previousX,
                    previousY,
                    currentX,
                    currentY); // Horizontal line

            // Reset
            g2d.setStroke(currentStroke);
            g2d.setComposite(currentComposite);
        }
    }

    protected void renderTimePoint(boolean highlighted, int xCoord, int yCoord, Graphics2D g2d, int timePeriodIndex, String probeID) {
        xCoord = xCoord - (boxSize / 2);
        yCoord = yCoord - (boxSize / 2);
        if (((numberOfHighProbes > 0 && highlighted) || numberOfHighProbes == 0) && g2d.getStroke() != faintStroke) {
            // Only put a node if there isn't anything highlighted, OR this is infact highlighted
            Ellipse2D rrect = null;
            rrect = new Ellipse2D.Double(xCoord, yCoord, boxSize, boxSize);
            g2d.fill(rrect);
        }
        if (!probeID.equals("")) {
            StringBuffer nodeImageMapArea = new StringBuffer();
            nodeImageMapArea.append("<area shape=\"rect\" coords=\"");
            nodeImageMapArea.append(String.valueOf((int) xCoord));
            nodeImageMapArea.append(",");
            nodeImageMapArea.append(String.valueOf(yCoord));
            nodeImageMapArea.append(",");
            nodeImageMapArea.append(String.valueOf(xCoord + boxSize));
            nodeImageMapArea.append(",");
            nodeImageMapArea.append(String.valueOf(yCoord + boxSize));
            nodeImageMapArea.append("\" onclick=\"return enc(event, ");
            nodeImageMapArea.append(timePeriodIndex);
            nodeImageMapArea.append(", '");
            nodeImageMapArea.append(probeID);
            nodeImageMapArea.append("');\"/>\n");

            imgMap.write(nodeImageMapArea.toString());
        }
    }

    protected int getXAxisYCoord() {
        int vOffset = 0;
        if (bottomAxis) {
            vOffset = mHeight - borderSize;
        } else {
            vOffset = mHeight - minBorderSize;
        }
        return vOffset;
    }

    protected int getRightOfXAxisXCoord() {
        return mWidth;
    }

    protected int getTopOfYAxisCoord() {
        int vOffset = 0;
        if (useTopPadding) {
            vOffset = topPadding + minBorderSize;
        } else {
            vOffset = minBorderSize;
        }
        return vOffset;
    }

    protected void setCropToData() {
        cropToData = false;
    }
}
