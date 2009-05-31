package visualization;

import com.sfsu.xmas.data_sets.ExpressionDataSetMultiton;
import com.sfsu.xmas.trajectory_files.TrajectoryFileFactory;
import com.sfsu.xmas.trajectory_files.TrajectoryNode;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import org.w3c.dom.Node;
import com.sfsu.xmas.visualizations.ColorManager;
import java.awt.geom.Rectangle2D;

public abstract class AbstractTreeViz extends AbstractViz {

    int maxNodesPerBin = 10;
    int mNodeHeight = 12;
    int mNodeVerticalSpacing = 3;
    // The value of the minimum vertical bin
    int[][] binCount;
    double[][] nodeLocation;//need to work out how many nodes there are
    int[] lineSpaceingX;
    int[] lineSpaceingY = new int[1000]; //need to work out how many nodes there are

    public AbstractTreeViz(String identifier, int expressionDataSetID, String trajFileName) {
        super(identifier, expressionDataSetID, trajFileName);
    }

    @Override
    protected void initialize() {
        super.initialize();
        binCount = new int[mTimePeriodCount][mCountExpressionBins + 1];
        lineSpaceingX = new int[mTimePeriodCount];
        mNodeCount = TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN).getNumberOfNodes();

        nodeLocation = new double[mNodeCount][2];
        lineSpaceingY = new int[mNodeCount];
    }

    /**
     * Provides a human readable title for the visualization. Relies on data in the XML document provided to the visualization
     * @return the title of the visualization
     */
    public String getTitle() {
        String title = "Binned Visualization: View <a href=\"SLoadViz?exact\">Exact Visualization</a> or <a href=\"CListGenes?page=1\">Probe List</a>";
        return title;
    }

    public String getImageMap() {
        return imgMap.toString();
    }

    public int getHeight() {
        return mHeight;
    }

    protected void renderAxes(Graphics2D g2d) {

        renderHorizontalShading(g2d);
        renderVerticalLines(g2d, null);
        renderHorizontalLines(g2d, null);

        renderXAxisTop(g2d);
        renderYAxisScale(g2d);
        renderXAxisBottom(g2d);
    }

    public void drawNode(TrajectoryNode nd, Graphics2D g2d) throws IOException {
        g2d.setColor(Color.BLACK);

        int nodeBin = nd.getValue();
        int nodeDepth = nd.getDepth();

        int nodesAbove = Math.min(binCount[nodeDepth - 1][nodeBin - mMinExpressionBinValue], maxNodesPerBin);

        double[] canvasCoords = getNodeCanvasPosition(nd);
        double currentX = canvasCoords[0];
        double currentY = canvasCoords[1];


        if (nodesAbove < maxNodesPerBin) {
            drawBox(g2d, currentX, currentY, nd);
        } else if (nodesAbove == maxNodesPerBin) {
            drawExtensionBox(g2d, currentX, currentY, nd);
        }

        //See if parent exists
        if (nodeDepth > 1) {
            int parentID = Integer.parseInt(nd.getActualNode().getParentNode().getAttributes().getNamedItem("NodeID").getTextContent());

            Node n = (Node) nd.getActualNode().getParentNode();
            TrajectoryNode parentNode = new TrajectoryNode(n);
            if (nodeLocation[parentID - 1][0] == 0) {
                drawNode(parentNode, g2d);
            }

            lineSpaceingX[nodeDepth - 1]++;

            double parentX = nodeLocation[parentID - 1][0];
            double parentY = nodeLocation[parentID - 1][1];

            int lineStartX = (int) parentX + mNodeHeight;
            int lineStartY = (int) parentY + 2 + Math.min((lineSpaceingY[parentID - 1] * 2), mNodeHeight - 2);
            int lineFinishX = (int) currentX;
            int lineFinishY = (int) currentY + 5;


            int childBin = nd.getValue();
            int parentBin = parentNode.getValue();
            if (childBin == parentBin) {
                // no movement
                lineFinishY = (int) currentY + 2 + (lineSpaceingY[parentID - 1] * 2);
            } else {

                lineFinishY = (int) currentY + 5;
            }

            int startAnchorLength = (int) Math.round(((lineSpaceingX[nodeDepth - 1] % (timePeriodWidth / 4)) * 3) + (mWidth / 100));

            int childConnectorX = Math.max(lineFinishX - startAnchorLength, lineStartX + startAnchorLength);

            // x1,y1,x2,y2
            g2d.drawLine(lineStartX, lineStartY, lineStartX + startAnchorLength, lineStartY); // Parent Horiz

            g2d.drawLine(lineStartX + startAnchorLength, lineStartY, childConnectorX, lineFinishY); // Vertic

            g2d.drawLine(childConnectorX, lineFinishY, lineFinishX, lineFinishY); // Child Horiz

            lineSpaceingY[parentID - 1]++;
        }
    }

    private Color getBinColor(int i) {
        int red = 255; // - Math.round((150 / mCountExpressionBins) * i);

        int green = 255; // - Math.round((150 / mCountExpressionBins) * i);

        int blue = 255;
        // includes the cold - hot fading
//        if (i < 0) {
//            // in blue
//            red = 105; // + Math.round((150 / Math.abs(mMinExpressionBinValue)) * i);
//            green = 105; // + Math.round((150 / Math.abs(mMinExpressionBinValue)) * i);
//            blue = 255;
//        } else if (i > 0) {
        red = 255;
        green = 105; //255 - Math.round((150 / Math.abs(mCountExpressionBins + mMinExpressionBinValue)) * (i + mMinExpressionBinValue));

        blue = 105; //255 - Math.round((150 / Math.abs(mCountExpressionBins + mMinExpressionBinValue)) * (i + mMinExpressionBinValue));
//        } else {
//            red = 255;
//            green = 255;
//            blue = 255;
//        }

        return new Color(Math.abs(red), Math.abs(green), Math.abs(blue));
    }

    private double[] getNodeCanvasPosition(TrajectoryNode nd) {
        // Establish spacing
        int nodeBin = nd.getValue();
        int nodeDepth = nd.getDepth();
        int nodeID = nd.getID();
        float currentX = (int) Math.round(((nodeDepth - 1) * timePeriodWidth) + borderSize + 10);

        double verticalOffsetToBin = (mMaxExpressionBinValue - nodeBin) * mVerticalBinSpacing;

        // Max out on maxNodesPerBin
        int nodesAbove = Math.min(binCount[nodeDepth - 1][nodeBin - mMinExpressionBinValue], maxNodesPerBin);
        double verticalOffsetWithinBin = nodesAbove * (mNodeHeight + mNodeVerticalSpacing);

        double currentY = verticalOffsetToBin + verticalOffsetWithinBin + borderSize;

        currentY += mNodeVerticalSpacing; //Add Padding

        binCount[nodeDepth - 1][nodeBin - mMinExpressionBinValue]++;
        nodeLocation[nodeID - 1][ 0] = currentX;
        nodeLocation[nodeID - 1][ 1] = currentY;
        return new double[]{currentX, currentY};
    }

    public void drawThumbNailBox(Graphics2D g2d, float currentX, float currentY) {
        g2d.drawRect((int) currentX, (int) currentY, 2, 2);
    }

    public void drawBox(Graphics2D g2d, double currentX, double currentY, TrajectoryNode nd) {
        int nodeBin = nd.getValue();
        int nodeDepth = nd.getDepth();
        int nodeID = nd.getID();
        g2d.setColor(Color.BLACK);
        Node n = null;
        int parentBin = nodeBin;
        if (nodeDepth > 1) {
            n = (Node) nd.getParentNode();
            parentBin = Integer.parseInt(n.getAttributes().getNamedItem("Value").getTextContent());
        }
        int diff = nodeBin - parentBin;

        int boxWidth = mNodeHeight;

        if (nodeDepth == ExpressionDataSetMultiton.getUniqueInstance().getDataSet(eDBID, false).getNumberOfTimePeriods()) {
            boxWidth = 40;
            // Leaf node
            drawMovementShadedBox(diff, g2d, (int) currentX, (int) currentY, boxWidth);

            int matchingProbes = nd.getMatchingQuantity();
            int totalProbes = nd.getTotalQuantity();
            int totalHighlightedMatchingProbes = nd.getMatchingHighlighted();
            int totalHighlighted = nd.getHighlightedTotal();

            int boxPaddingLeft = 2;
            int boxPaddingTop = 10;

            g2d.setColor(Color.WHITE);
            if (matchingProbes == totalProbes) {
                addText(g2d, String.valueOf(matchingProbes), (int) currentX + boxPaddingLeft, (int) currentY + boxPaddingTop);
            } else {
                addText(g2d, String.valueOf(matchingProbes) + " | " + String.valueOf(totalProbes), (int) currentX + boxPaddingLeft, (int) currentY + boxPaddingTop);
            }

            if (totalHighlightedMatchingProbes > 0) {
                addText(g2d, String.valueOf(totalHighlightedMatchingProbes) + " | " + totalHighlighted, (int) currentX + boxWidth + boxPaddingLeft, (int) currentY + boxPaddingTop);
            } else {
            }
            g2d.setColor(Color.BLACK);
        } else {
            drawMovementShadedBox(diff, g2d, (int) currentX, (int) currentY, mNodeHeight);
        }

        if (nodeDepth == ExpressionDataSetMultiton.getUniqueInstance().getDataSet(eDBID, false).getNumberOfTimePeriods()) {
        } else {
        }
        String area = "<area shape=\"rect\" coords=\"" +
                String.valueOf((int) currentX) + "," +
                String.valueOf((int) currentY) + "," +
                String.valueOf(((int) currentX + boxWidth)) + "," +
                String.valueOf(((int) currentY + mNodeHeight)) + "\" " +
                "onclick=\"return lc(event, " + String.valueOf(nodeDepth) + ", " + String.valueOf(nodeID) + ")\"/>\n";
        imgMap.write(area);
    }

    private void drawExtensionBox(Graphics2D g2d, double currentX, double currentY, TrajectoryNode nd) {
        int nodeBin = nd.getValue();
        int nodeDepth = nd.getDepth();
        g2d.setColor(Color.BLACK);
        Node n = null;
        int parentBin = nodeBin;
        if (nodeDepth > 1) {
            n = (Node) nd.getParentNode();
            parentBin = Integer.parseInt(n.getAttributes().getNamedItem("Value").getTextContent());
        }
        int diff = nodeBin - parentBin;

        if (nodeDepth == ExpressionDataSetMultiton.getUniqueInstance().getDataSet(eDBID, false).getNumberOfTimePeriods()) {
            // Leaf node
            drawMovementShadedBox(diff, g2d, (int) currentX, (int) currentY, 40);

            addText(g2d, "...", (int) currentX + 3, (int) currentY + 11);
//            addText(g2d, "(...)", (float) currentX + 40 + 3, (float) currentY + 11);
//            System.out.println("printing node: " + nodeID);
        } else {
            drawMovementShadedBox(diff, g2d, (int) currentX, (int) currentY, mNodeHeight);
            addText(g2d, "...", (int) currentX + 3, (int) currentY + 11);
        }
    }

    public void drawMovementShadedBox(int diff, Graphics2D g2d, int currentX, int currentY, int width) {

//        int maxPossBinMove = Math.max(Math.abs(activeTrajectoryDocument.getMaximumSubtractiveDegree()), Math.abs(activeTrajectoryDocument.getMinimumSubtractiveDegree())); // should be max movement possible
        // includes the cold - hot fading

        Color appropriateColor = ColorManager.getColor(diff, TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN).getMinimumSubtractiveDegree(), TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN).getMaximumSubtractiveDegree());

        g2d.setColor(Color.BLACK);
        Rectangle2D box = new Rectangle2D.Float(currentX, currentY, width, mNodeHeight);
        // = new Color(red, green, blue);
        g2d.setColor(appropriateColor);
        g2d.fill(box);
        g2d.setColor(Color.BLACK);
        g2d.draw(box);
    }

    @Override
    protected void setImageHeight() {
        this.mHeight = 1000;
    }
}
