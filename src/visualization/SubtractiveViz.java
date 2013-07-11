package visualization;

import com.sfsu.xmas.data_sets.ExpressionDataSetMultiton;
import com.sfsu.xmas.data_structures.Probe;
import com.sfsu.xmas.data_structures.Probes;
import com.sfsu.xmas.highlight.HighlightManager;
import com.sfsu.xmas.monitoring.ExecutionTimer;
import com.sfsu.xmas.trajectory_files.TrajectoryFileFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class SubtractiveViz extends AbstractPreciseViz {

    protected int secondaryDatabase;
    protected int primaryDatabase;
    protected boolean isComparative;
    protected boolean isPrimary;

    public SubtractiveViz(String identifier, int expressionDataSetID, int secondaryDataSetID, String trajFileName) {
        super(identifier, expressionDataSetID, trajFileName);
        this.primaryDatabase = expressionDataSetID;
        this.secondaryDatabase = secondaryDataSetID;
    }

    public BufferedImage generateGraph() throws IOException {

        System.out.println("Subtractive Comparative graph will be generated!");

        double mMinBin = 0;
        double mMaxBin = 0;

        eDBID = secondaryDatabase;
        Probes secondaryProbes = getProbesToRender();

        eDBID = primaryDatabase;
        Probes primaryProbes = getProbesToRender();

        Set<Entry<String, Probe>> primaryES = primaryProbes.entrySet();
        Iterator<Entry<String, Probe>> primaryIt = primaryES.iterator();

        while (primaryIt.hasNext()) {
            Entry<String, Probe> entry = primaryIt.next();
            Probe p = entry.getValue();

            Probe matchingSecondaryP = secondaryProbes.get(p.getID());
            if (matchingSecondaryP != null) {
                // Subtract matchingSecondaryP expression values from p
                // Set p back into primary
                double[] expressionPrimary = p.getTimePeriodExpression();
                double[] expressionSecondary = matchingSecondaryP.getTimePeriodExpression();

                for (int i = 0; i < expressionPrimary.length; i++) {
                    double subtractiveExpressionValues = expressionPrimary[i] - expressionSecondary[i];
                    if (subtractiveExpressionValues < mMinBin) {
                        mMinBin = subtractiveExpressionValues;
                    }
                    if (subtractiveExpressionValues > mMaxBin) {
                        mMaxBin = subtractiveExpressionValues;
                    }
                }

            }
        }

        //set mMinBin and mMaxBin based on the result dataset


        //initialize properties
        initializeProperties(mMinBin, mMaxBin);

        // Create image
        BufferedImage image = new BufferedImage(mWidth, mHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = getVizCanvas(image);

        imgMap.write("<Map name=\"Visualization_map\">\n");

        renderProbes(g2d, primaryProbes, secondaryProbes, null);

        imgMap.write("</Map>");
        imgMap.close();

        return image;
    }

    protected Graphics2D renderProbes(Graphics2D g2d, Probes primaryProbes, Probes secondaryProbes, Color color) {
        ExecutionTimer et = new ExecutionTimer();
        et.start();
        int probeIndex = 0;

        ArrayList<String> idsToHoldBack = HighlightManager.getUniqueInstance().getHighlightListForIdentifier(identifier).getHighlighted();

        int highlightIndex = 0;

        // Empty
        Probes highlightedProbesToRenderLast = new Probes(primaryProbes.getExpressionDataSetID(), new String[0]);
        int[] probesToRenderLastInOrderIndex = new int[idsToHoldBack.size()];

        Set<Entry<String, Probe>> probeIDs = primaryProbes.entrySet();

        Iterator<Entry<String, Probe>> it = probeIDs.iterator();
        while (it.hasNext() && probeIndex < 1000) {
            Entry<String, Probe> probeEntry = it.next();

            if (secondaryProbes.containsKey(probeEntry.getKey())) {

                if (idsToHoldBack.contains(probeEntry.getKey())) {
                    highlightedProbesToRenderLast.put(probeEntry.getKey(), probeEntry.getValue());
                    probesToRenderLastInOrderIndex[highlightIndex] = probeIndex;
                    highlightIndex++;
                } else {
                    renderProbe(probeEntry.getValue(), secondaryProbes.get(probeEntry.getKey()), g2d, probeIndex, false, color);
                }
                probeIndex++;
            }
        }

        int p2last = 0;
        Set<Entry<String, Probe>> lastProbeIDs = highlightedProbesToRenderLast.entrySet();
        it = lastProbeIDs.iterator();
        while (it.hasNext()) {
            Entry<String, Probe> probeToRendLastEntry = it.next();
            renderProbe(probeToRendLastEntry.getValue(), g2d, probesToRenderLastInOrderIndex[p2last], true, color);
            p2last++;
        }
        et.end();
        System.out.println("DURATION = " + et.duration() + " in class " + this.getClass().getName());
        return g2d;
    }

    public void renderProbe(Probe primaryProbe, Probe secondaryProbe,Graphics2D g2d, int index, boolean highlighted, Color color) {
        if (color != null) {
            g2d.setColor(color);
        } else {
            g2d.setColor(getColor(index));
        }
//        Expression expression = probe.getExpression();
        double[] timePeriodExpression = primaryProbe.getTimePeriodExpression();

        
        double[] secondaryTimePeriodExpression = secondaryProbe.getTimePeriodExpression();
        
        double[] expressionValues = new double[timePeriodExpression.length];

        double profileShift = 0.0;
        if (TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN) != null && TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN).isCollapsed()) {
            profileShift = TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN).getCollapsedExpressionShiftAmount(timePeriodExpression);
            for (int i = 0; i < timePeriodExpression.length; i++) {
                expressionValues[i] = timePeriodExpression[i] - profileShift;
            }
        } else {
            for (int i = 0; i < timePeriodExpression.length; i++) {
                expressionValues[i] = timePeriodExpression[i] - secondaryTimePeriodExpression[i];
            }
        }

        int previousXCoord = 0;
        int previousYCoord = 0;

        if (ExpressionDataSetMultiton.getUniqueInstance().getDataSet(eDBID, false).getNumberOfTimePeriods() != timePeriodExpression.length) {
            System.out.println(this.getClass().getSimpleName() + ": Probe expression is wrong length - needed " + ExpressionDataSetMultiton.getUniqueInstance().getDataSet(eDBID, false).getNumberOfTimePeriods() + ", got " + timePeriodExpression.length);
        } else {

            for (int i = 0; i < ExpressionDataSetMultiton.getUniqueInstance().getDataSet(eDBID, false).getNumberOfTimePeriods(); i++) {
                double expressionValue = expressionValues[i];

                int currentXCoord = getXCoordForExpression(i);
                int currentYCoord = getYCoordForExpression(expressionValue);

                renderTimePoint(highlighted, currentXCoord, currentYCoord, g2d, i, primaryProbe.getID());

                if (i > 0) {
                    connectNodeToPreviousTimePoint(highlighted, g2d, i, currentXCoord, currentYCoord, previousXCoord, previousYCoord);
                }
                previousXCoord = currentXCoord;
                previousYCoord = currentYCoord;
            }
        }
    }

    protected void initializeProperties(double mMinBin, double mMaxBin) {

        mTimePeriodCount = ExpressionDataSetMultiton.getUniqueInstance().getDataSet(eDBID, false).getNumberOfTimePeriods();
        mMinExpressionBinValue = (int) mMinBin;
        mMaxExpressionBinValue = (int) mMaxBin;
        expressionMinUnit = 1;

        mCountExpressionBins = Math.abs(mMaxExpressionBinValue - mMinExpressionBinValue + 1);

        timePeriodWidth = (double) 0;
        if (mTimePeriodCount > 0) {
            timePeriodWidth = (double) (mWidth - borderSize) / mTimePeriodCount;
        }
        System.out.println("Bins: " + mCountExpressionBins + ", Min: " + mMinExpressionBinValue + ", Max: " + mMaxExpressionBinValue);

        boxSize = 4;
        nodeLocation = new float[mNodeCount][2];
        lineSpacingY = new int[mNodeCount];
        mVerticalBinSpacing = (double) (getXAxisYCoord() - getTopOfYAxisCoord()) / mCountExpressionBins;
    }
}
