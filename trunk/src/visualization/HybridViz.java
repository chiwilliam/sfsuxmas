package visualization;

import com.sfsu.xmas.trajectory_files.LeafNodes;
import com.sfsu.xmas.trajectory_files.TrajectoryNode;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import com.sfsu.xmas.monitoring.ExecutionTimer;
import com.sfsu.xmas.trajectory_files.TrajectoryFileFactory;

public class HybridViz extends PreciseViz {

    public HybridViz(String identifier, int expressionDataSetID, String trajFileName) {
        super(identifier, expressionDataSetID, trajFileName);
    }

    @Override
    public BufferedImage generateGraph() throws IOException {
        initialize();
        // These are the nodes in the final time period of each trajectory

        ExecutionTimer et = new ExecutionTimer();
        et.start();
        LeafNodes leavesX = TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN).getLeafNodes(identifier);
        et.end();
        System.out.println("\n" + et.duration() + " ms");

        // Render normal
        int[] leafBin = new int[mCountExpressionBins + 1];
        int trajectoryEndBin = 0;
        TreeMap<Integer, TrajectoryNode> tm = new TreeMap<Integer, TrajectoryNode>();
        for (int leafIndex = 0; leafIndex < leavesX.size(); leafIndex++) {
            TrajectoryNode leaf = (TrajectoryNode) leavesX.get(leafIndex);
            trajectoryEndBin = leaf.getValue();
            int leafBinIndex = trajectoryEndBin - mMinExpressionBinValue;
            leafBin[leafBinIndex]++;
            tm.put(leafIndex, leaf);
        }

        // Create image
        BufferedImage image = new BufferedImage(mWidth, mHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = getVizCanvas(image);

        imgMap.write("<Map name=\"Visualization_map\">\n"); // Begin image map

        ArrayList<double[]> meanTrajs = new ArrayList<double[]>();

        Set<Integer> tms = tm.keySet();

        int trajIndex = 0;

        for (Integer index : tms) {
            if (trajIndex > 100) {
                break;
            }
            TrajectoryNode n = (TrajectoryNode) tm.get(index);
            try {

                g2d.setStroke(faintStroke);
                g2d.setComposite(faintComposite);
                double[] meanT = renderTrajectory(g2d, n, getColor(index));
                g2d.setStroke(baseStroke);
                g2d.setComposite(baseComposite);

                if (n.getMatchingQuantity() > 2) {
                    meanTrajs.add(meanT);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            trajIndex++;
        }

        for (int i = 0; i < meanTrajs.size(); i++) {
            renderMeanTrajectory(g2d, meanTrajs.get(i), getColor(i));
        }

        g2d.dispose();

        imgMap.write("</Map>");

        imgMap.close();
        return image;
    }
}
