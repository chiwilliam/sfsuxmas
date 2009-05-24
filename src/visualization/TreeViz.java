package visualization;

import com.sfsu.xmas.data_sets.ExpressionDataSet;
import com.sfsu.xmas.trajectory_files.TrajectoryFile;
import com.sfsu.xmas.trajectory_files.TrajectoryFileFactory;
import com.sfsu.xmas.trajectory_files.TrajectoryNode;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class TreeViz extends AbstractTreeViz {

    public TreeViz(String identifier, ExpressionDataSet expressionDatabase, TrajectoryFile trajFile) {
        super(identifier, expressionDatabase, trajFile);
    }

    @Override
    protected void initialize() {
        super.initialize();
    }

    public BufferedImage generateGraph() throws IOException {
        initialize();

        // Render normal
        int[] leafBin = new int[mCountExpressionBins + 1];
        int trajectoryEndBin = 0;
        TreeMap<Integer, TrajectoryNode> tm = new TreeMap<Integer, TrajectoryNode>();
        for (int leafIndex = 0; leafIndex < leaves.size(); leafIndex++) {
            TrajectoryNode leaf = (TrajectoryNode) leaves.get(leafIndex);
            trajectoryEndBin = leaf.getValue();
            int leafBinIndex = trajectoryEndBin - mMinExpressionBinValue;
            leafBin[leafBinIndex]++;
            tm.put(leafIndex, leaf);
        }

        int maxLeaf = 1;
        // ok, this works out the bin with the greatest number of probes
        for (int i = 0; i < mCountExpressionBins; i++) {
            if (leafBin[i] > maxLeaf) {
                maxLeaf = leafBin[i];
            }
        }

        maxLeaf = Math.min(maxNodesPerBin, maxLeaf);

        mVerticalBinSpacing = (double) (maxLeaf + 2) * (mNodeHeight + mNodeVerticalSpacing);  // +2 to add spacing top and bottom

        mHeight = (int) Math.round(mVerticalBinSpacing * (mCountExpressionBins) + 2 * borderSize);

        // Create image
        BufferedImage image = new BufferedImage(mWidth, mHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = getVizCanvas(image);

        imgMap.write("<Map name=\"Visualization_map\">\n"); // Begin image map

        Set<Map.Entry<Integer, TrajectoryNode>> entries = tm.entrySet();

        Iterator<Entry<Integer, TrajectoryNode>> it = entries.iterator();
        while (it.hasNext()) {
            Entry<Integer, TrajectoryNode> entry = it.next();
            TrajectoryNode n = entry.getValue();
            try {
                drawNode(n, g2d);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        g2d.dispose();
        imgMap.write("</Map>");
        imgMap.close();

        return image;
    }

    @Override
    protected void populateDataToRender() {
        leaves = TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN).getLeafNodes(identifier);
    }
}
