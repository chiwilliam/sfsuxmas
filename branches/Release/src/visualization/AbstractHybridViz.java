package visualization;

import com.sfsu.xmas.data_sets.ExpressionDataSetMultiton;
import com.sfsu.xmas.trajectory_files.TrajectoryNode;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import org.w3c.dom.Node;

public abstract class AbstractHybridViz extends PreciseViz {

    int[] lineSpacingX;

    public AbstractHybridViz(String identifier, int expressionDataSetID, String trajFileName) {
        super(identifier, expressionDataSetID, 0, trajFileName, true);
        lineSpacingX = new int[ExpressionDataSetMultiton.getUniqueInstance().getDataSet(eDBID, false).getNumberOfTimePeriods()];
    }

    public void drawHybridNode(TrajectoryNode nd, Graphics2D g2d) throws IOException {
        g2d.setColor(Color.BLACK);

        int nodeDepth = nd.getDepth();

//        Probes probes = sam.getActiveDatabase().getProbes(nd.getMatchingProbes());

//        probes.populateExpression(sam);
        double meanExpression = 5; //probes.getMedianTimePeriodValue(nodeDepth);

        int xCoord = getXCoordForExpression(nodeDepth);
        int yCoord = getYCoordForExpression(meanExpression);

        //See if parent exists
        if (nodeDepth > 1) {
            int parentID = Integer.parseInt(nd.getActualNode().getParentNode().getAttributes().getNamedItem("NodeID").getTextContent());

            Node n = (Node) nd.getActualNode().getParentNode();
            TrajectoryNode parentNode = new TrajectoryNode(n);
            if (nodeLocation[parentID - 1][0] == 0) {
                drawHybridNode(parentNode, g2d);
            }

            lineSpacingX[nodeDepth - 1]++;

            double parentX = nodeLocation[parentID - 1][0];
            double parentY = nodeLocation[parentID - 1][1];

            int lineStartX = (int) parentX;
            int lineStartY = (int) parentY;
            int lineFinishX = (int) xCoord;
            int lineFinishY = (int) yCoord;


            int childBin = nd.getValue();
            int parentBin = parentNode.getValue();
            if (childBin == parentBin) {
                // no movement
                lineFinishY = (int) yCoord + 2 + (lineSpacingY[parentID - 1] * 2);
            } else {

                lineFinishY = (int) yCoord + 5;
            }

            int startAnchorLength = (int) Math.round(((lineSpacingX[nodeDepth - 1] % (timePeriodWidth / 4)) * 3) + (mWidth / 100));

            int childConnectorX = Math.max(lineFinishX - startAnchorLength, lineStartX + startAnchorLength);

            // x1,y1,x2,y2
            g2d.drawLine(lineStartX, lineStartY, lineStartX + startAnchorLength, lineStartY); // Parent Horiz

            g2d.drawLine(lineStartX + startAnchorLength, lineStartY, childConnectorX, lineFinishY); // Vertic

            g2d.drawLine(childConnectorX, lineFinishY, lineFinishX, lineFinishY); // Child Horiz

            lineSpacingY[parentID - 1]++;
        }
    }
}
