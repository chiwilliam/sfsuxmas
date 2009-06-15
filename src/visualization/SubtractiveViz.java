package visualization;

import com.sfsu.xmas.data_structures.Probes;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import com.sfsu.xmas.data_sets.ExpressionDataSetMultiton;
import com.sfsu.xmas.data_structures.Probe;
import java.util.HashMap;
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

        Probes tmpProbes = primaryProbes;
        
        HashMap<String, Probe> resultProbes = new HashMap<String, Probe>();
        
        Set<Entry<String, Probe>> primaryES = tmpProbes.entrySet();
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
                double[] subtractiveExpressionValues = new double[expressionPrimary.length];

                for(int i=0; i<expressionPrimary.length; i++){
                    subtractiveExpressionValues[i] = expressionPrimary[i] - expressionSecondary[i];
                    if(subtractiveExpressionValues[i] < mMinBin){
                        mMinBin = subtractiveExpressionValues[i];
                    }
                    if(subtractiveExpressionValues[i] > mMaxBin){
                        mMaxBin = subtractiveExpressionValues[i];
                    }
                }

                Probe pSub = new Probe(eDBID, p.getID());
                pSub.setTimePeriodExpression(subtractiveExpressionValues);
                resultProbes.put(p.getID(), pSub);
                
            } else {
                // Matching secondary probe doesn't exist
                // Remove p from primaryProbes
                resultProbes.remove(p.getID());
            }
        }

        Probes proSubtractive = new Probes(eDBID, resultProbes, true);

        //set mMinBin and mMaxBin based on the result dataset
        

        //initialize properties
        initializeProperties(mMinBin,mMaxBin);

        // Create image
        BufferedImage image = new BufferedImage(mWidth, mHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = getVizCanvas(image);

        imgMap.write("<Map name=\"Visualization_map\">\n");

        if (proSubtractive.size() > 0) {
            renderProbes(g2d, proSubtractive, null);
        }

        imgMap.write("</Map>");
        imgMap.close();

        return image;
    }

    protected void initializeProperties(double mMinBin, double mMaxBin) {

        mTimePeriodCount = ExpressionDataSetMultiton.getUniqueInstance().getDataSet(eDBID, false).getNumberOfTimePeriods();
        mMinExpressionBinValue = (int)mMinBin;
        mMaxExpressionBinValue = (int)mMaxBin;
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
