package visualization;

import com.sfsu.xmas.data_sets.ExpressionDataSetMultiton;
import com.sfsu.xmas.trajectory_files.TrajectoryNode;
import com.sfsu.xmas.data_structures.Probe;
import com.sfsu.xmas.data_structures.Probes;
import com.sfsu.xmas.filter.FilterManager;
import com.sfsu.xmas.highlight.HighlightManager;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import org.w3c.dom.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import com.sfsu.xmas.monitoring.ExecutionTimer;
import com.sfsu.xmas.trajectory_files.TrajectoryFileFactory;

public abstract class AbstractPreciseViz extends AbstractViz {

    float[][] nodeLocation = new float[1000][2];//need to work out how many nodes there are
    int[] lineSpacingY = new int[1000]; //need to work out how many nodes there are

    public AbstractPreciseViz(String identifier, int expressionDataSetID, String trajFileName) {
        super(identifier, expressionDataSetID, trajFileName);
    }

    public AbstractPreciseViz(String identifier, int expressionDataSetID, String trajFileName, int width, int height) {
        super(identifier, expressionDataSetID, trajFileName, width, height);
    }

    @Override
    protected void initialize() {
        super.initialize();
        boxSize = 4;
        nodeLocation = new float[mNodeCount][2];
        lineSpacingY = new int[mNodeCount];
        mVerticalBinSpacing = (double) (getXAxisYCoord() - getTopOfYAxisCoord()) / mCountExpressionBins;
    }

    protected void renderAxes(Graphics2D g2d) {
        renderHorizontalShading(g2d);
        renderVerticalLines(g2d, null);
        renderHorizontalLines(g2d, null);

        renderYAxisScale(g2d);
        if (bottomAxis) {
            renderXAxisBottom(g2d);
        }
    }

    protected NodeList getLeavesFromRootWithLeafFilter(Node rootNode) {
        NodeList ls = null;
        try {
//            if (rootNode.getNodeName().equals("TP5")) {
//                leaves = org.apache.xpath.XPathAPI.selectNodeList(rootNode, ".");
//            } else {
            String filterXPath = FilterManager.getUniqueInstance().getFilterListForIdentifier(identifier).getFullXPath(ExpressionDataSetMultiton.getUniqueInstance().getDataSet(eDBID, false).getNumberOfTimePeriods());
            System.out.println("XPath: " + filterXPath);
            XPathFactory factory = XPathFactory.newInstance();

            XPath xPath = factory.newXPath();
            XPathExpression compiledXPath = xPath.compile(filterXPath);
            ls = (NodeList) compiledXPath.evaluate(rootNode, XPathConstants.NODESET);
            System.out.println("Number of genes returned: " + ls.getLength());
//            }
        } catch (XPathExpressionException ex) {
            Logger.getLogger(AbstractTreeViz.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ls;
    }

    protected Probes getProbesToRender() {
        Probes probes = getProbes();
        return probes;
    }

    protected Probes getSecondaryProbesToRender() {
        Probes probes = getProbes();
        return probes;
    }

    protected Graphics2D renderProbes(Graphics2D g2d, Probes probes, Color color) {
        ExecutionTimer et = new ExecutionTimer();
        et.start();
        int probeIndex = 0;

        ArrayList<String> idsToHoldBack = HighlightManager.getUniqueInstance().getHighlightListForIdentifier(identifier).getHighlighted();

        int highlightIndex = 0;

        // Empty
        Probes probesToRenderLast = new Probes(probes.getExpressionDataSetID(), new String[0]);
        int[] probesToRenderLastInOrderIndex = new int[idsToHoldBack.size()];

        Set<Entry<String, Probe>> probeIDs = probes.entrySet();
        
        Iterator<Entry<String, Probe>> it = probeIDs.iterator();
        while (it.hasNext() && probeIndex < 1000) {
            Entry<String, Probe> probeEntry = it.next();

            if (idsToHoldBack.contains(probeEntry.getKey())) {
                probesToRenderLast.put(probeEntry.getKey(), probeEntry.getValue());
                probesToRenderLastInOrderIndex[highlightIndex] = probeIndex;
                highlightIndex++;
            } else {
                renderProbe(probeEntry.getValue(), g2d, probeIndex, false, color);
            }
            probeIndex++;
        }

        int p2last = 0;
        Set<Entry<String, Probe>> lastProbeIDs = probesToRenderLast.entrySet();
        it = lastProbeIDs.iterator();
        while (it.hasNext()) {
            Entry<String, Probe> probeToRendLastEntry = it.next();
            renderProbe(probeToRendLastEntry.getValue(), g2d, probesToRenderLastInOrderIndex[p2last], true, color);
            p2last++;
        }
        et.end();
        System.out.println("DURATION = " + et.duration() + " in class " + AbstractPreciseViz.class.getName());
        return g2d;
    }

    public void renderProbe(Probe probe, Graphics2D g2d, int index, boolean highlighted, Color color) {
        if (color != null) {
            g2d.setColor(color);
        } else {
            g2d.setColor(getColor(index));
        }
//        Expression expression = probe.getExpression();
        double[] timePeriodExpression = probe.getTimePeriodExpression();

        double[] expressionValues = new double[timePeriodExpression.length];

        double profileShift = 0.0;
        if (TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN) != null && !TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN).isPreserved()) {
            profileShift = TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN).getCollapsedExpressionShiftAmount(timePeriodExpression);
            for (int i = 0; i < timePeriodExpression.length; i++) {
                expressionValues[i] = timePeriodExpression[i] - profileShift;
            }
        } else {
            for (int i = 0; i < timePeriodExpression.length; i++) {
                expressionValues[i] = timePeriodExpression[i];
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

                renderTimePoint(highlighted, currentXCoord, currentYCoord, g2d, i, probe.getID());

                if (i > 0) {
                    connectNodeToPreviousTimePoint(highlighted, g2d, i, currentXCoord, currentYCoord, previousXCoord, previousYCoord);
                }
                previousXCoord = currentXCoord;
                previousYCoord = currentYCoord;
            }
        }
    }

    protected double[] renderTrajectory(Graphics2D g2d, TrajectoryNode n, Color color) {
        Probes memberProbes = ExpressionDataSetMultiton.getUniqueInstance().getDataSet(eDBID, false).getProbes(n.getMatchingProbes(), true);
//        memberProbes.setProbeIDs();
//        memberProbes.populateExpression(sam);

        renderProbes(g2d, memberProbes, color);

        double[] expression = new double[ExpressionDataSetMultiton.getUniqueInstance().getDataSet(eDBID, false).getNumberOfTimePeriods()];
        Set<String> ids = memberProbes.keySet();
        Iterator<String> it = ids.iterator();
        while (it.hasNext()) {
            Probe p = memberProbes.get(it.next());
            double[] pExp = p.getTimePeriodExpression();
            double profileShift = 0.0;
            if (TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN) != null && !TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN).isPreserved()) {
                profileShift = TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN).getCollapsedExpressionShiftAmount(pExp);
            }
            for (int j = 0; j < pExp.length; j++) {
                double expressionValue = pExp[j];
                if (TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN) != null && !TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN).isPreserved()) {
                    expressionValue = expressionValue - profileShift;
                }
                expression[j] += expressionValue;
            }
        }

        for (int i = 0; i < ExpressionDataSetMultiton.getUniqueInstance().getDataSet(eDBID, false).getNumberOfTimePeriods(); i++) {
            expression[i] = expression[i] / memberProbes.size();
        }
        return expression;

    }

    protected void renderMeanTrajectory(Graphics2D g2d, double[] expression, Color color) {
        if (color != null) {
            g2d.setColor(color);
        } else {
            g2d.setColor(Color.BLACK);
        }

        int previousXCoord = 0;
        int previousYCoord = 0;

        for (int i = 0; i < ExpressionDataSetMultiton.getUniqueInstance().getDataSet(eDBID, false).getNumberOfTimePeriods(); i++) {
            double expressionValue = expression[i];

            int currentXCoord = getXCoordForExpression(i);
            int currentYCoord = getYCoordForExpression(expressionValue);

            renderTimePoint(true, currentXCoord, currentYCoord, g2d, i, "");
            if (i > 0) {
                connectNodeToPreviousTimePoint(true, g2d, i, currentXCoord, currentYCoord, previousXCoord, previousYCoord);
            }
            previousXCoord = currentXCoord;
            previousYCoord = currentYCoord;
        }
    }

    public Color getColor(int index) {
        Color col = colors[(index % colors.length)];
        return col;
    }

    public String getTitle() {
        String title = "Exact Visualization: View <a href=\"SLoadViz\">Binned Visualization</a> or <a href=\"CListGenes?page=1\">Probe List</a>";
        return title;
    }

    public String getImageMap() {
        return imgMap.toString();
    }

    public int getHeight() {
        return mHeight;
    }

    protected void setImageHeight() {
        this.mHeight = 550;
    }
}
