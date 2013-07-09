package visualization;

import com.sfsu.xmas.data_structures.Probes;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ComparativeViz extends AbstractPreciseViz {

    protected int secondaryDatabase;
    protected int primaryDatabase;
    protected boolean isComparative;
//    protected boolean isPrimary;

    public ComparativeViz(String identifier, int expressionDataSetID, int secondaryDataSetID, String trajFileName) {
        super(identifier, expressionDataSetID, trajFileName);
        this.primaryDatabase = expressionDataSetID;
        this.secondaryDatabase = secondaryDataSetID;
    }

    public BufferedImage generateGraph() throws IOException {
        initialize();

        Probes probes;

        // Create image
        BufferedImage image = new BufferedImage(mWidth, mHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = getVizCanvas(image);

        imgMap.write("<Map name=\"Visualization_map\">\n");
//        if (isComparative) {
//            if (isPrimary) {
        // Set to secondary and go, then reset
//
//        probes = getProbesToRender();
//        String[] probeIDs = probes.keySet().toArray(new String[0]);

        eDBID = secondaryDatabase;
        probes = getProbesToRender();
        if (probes.size() > 0) {

            g2d.setStroke(compStroke);
            renderProbes(g2d, probes, null);
            g2d.setStroke(baseStroke);
        }
//            } else {
//                expressionDatabase = primaryDatabase;
//                probes = getProbesToRender();
//                if (probes.size() > 0) {
//                    g2d.setStroke(compStroke);
//                    renderProbes(g2d, probes, null);
//                    g2d.setStroke(baseStroke);
//                }
//                expressionDatabase = secondaryDatabase;
//            }
//        }

        eDBID = primaryDatabase;
        probes = getProbesToRender();
        if (probes.size() > 0) {
            renderProbes(g2d, probes, null);
        }

        imgMap.write("</Map>");
        imgMap.close();

        return image;
    }
}
