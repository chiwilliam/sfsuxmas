package com.sfsu.xmas.visualizations.trajectories;

import com.sfsu.xmas.data_sets.ExpressionDataSet;
import com.sfsu.xmas.trajectory_files.TrajectoryFile;
import com.sfsu.xmas.trajectory_files.TrajectoryFileFactory;
import visualization.*;
import com.sfsu.xmas.trajectory_files.TrajectoryNode;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class TrajectoryViz extends AbstractPreciseViz {

    protected int nodeID;
    protected int timePeriod;
    protected boolean legend;

    public TrajectoryViz(String identifier, ExpressionDataSet expressionDatabase, TrajectoryFile trajFile, int nodeID, int timePeriod, int width, int height, boolean legend) {
        super(identifier, expressionDatabase, trajFile, width, height);
        this.legend = legend;
        this.nodeID = nodeID;
        this.timePeriod = timePeriod;
        initialize();
    }

    @Override
    protected void populateDataToRender() {
        leaves = TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN).getLeafNodes(identifier, nodeID, timePeriod);
    }

    public BufferedImage generateGraph() throws IOException {
        int[] leafBin = new int[mCountExpressionBins + 1];
        int trajectoryEndBin = 0;
        TreeMap<Integer, TrajectoryNode> tm = new TreeMap<Integer, TrajectoryNode>();
        for (int leafIndex = 0; leafIndex < leaves.size(); leafIndex++) {
            TrajectoryNode leaf = (TrajectoryNode) leaves.get(leafIndex);
            trajectoryEndBin = leaf.getValue();
            int leafBinIndex = trajectoryEndBin - mMinExpressionBinValue;
            leafBin[leafBinIndex]++;
            tm.put(leaf.getMatchingQuantity(), leaf);
        }

        BufferedImage image = new BufferedImage(mWidth, mHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = getVizCanvas(image);

        ArrayList<double[]> meanTrajs = new ArrayList<double[]>();

        Set<Map.Entry<Integer, TrajectoryNode>> entries = tm.entrySet();
        Iterator<Entry<Integer, TrajectoryNode>> it = entries.iterator();
        while (it.hasNext()) {
            Entry<Integer, TrajectoryNode> entry = it.next();
            TrajectoryNode n = entry.getValue();
            try {
                g2d.setStroke(faintStroke);
                g2d.setComposite(faintComposite);
                meanTrajs.add(renderTrajectory(g2d, n, getColor(10)));
                g2d.setStroke(baseStroke);
                g2d.setComposite(baseComposite);

                g2d.setColor(Color.BLACK);
                if (legend && tm.size() <= 1) {
                    addText(g2d, "# Probes = " + String.valueOf(n.getMatchingQuantity()), 5 + 3, getTopLegendTextOffset());
                    addText(g2d, "Volatility = " + String.valueOf(n.getVolatility()), (mWidth / 3), getTopLegendTextOffset());
                    int trend = n.getTrend();
                    if (trend > 0) {
                        g2d.setColor(Color.RED);
                    } else if (trend < 0) {
                        g2d.setColor(Color.BLUE);
                    } else {
                        g2d.setColor(Color.BLACK);
                    }
                    addText(g2d, "Trend = " + String.valueOf(n.getTrend()), (mWidth / 3) * 2, getTopLegendTextOffset());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (legend && tm.size() <= 1) {
            addText(g2d, "# Trajectories = " + String.valueOf(tm.size()), 5 + 3, getTopLegendTextOffset());
        }

        for (int i = 0; i < meanTrajs.size(); i++) {
            renderMeanTrajectory(g2d, meanTrajs.get(i), Color.BLACK);
        }
        g2d.dispose();
        imgMap.write("</Map>");
        imgMap.close();
        return image;
    }

    private int getTopLegendTextOffset() {
        return ((topPadding + minBorderSize) / 2) + (minBorderSize / 2);
    }

    @Override
    protected void initialize() {
        bottomAxis = false;
        useTopPadding = true;
        topPadding = 0;
        super.initialize();
    }

    @Override
    protected void setCropToData() {
        cropToData = true;
    }
}
