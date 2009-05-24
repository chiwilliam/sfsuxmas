/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import com.sfsu.xmas.dao.CapturedAnalysisDAO;
import com.sfsu.xmas.filter.FilterManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class TrajectoryShapeFilter extends AbstractTrajectoryFilter {

    protected int minBin;

    public TrajectoryShapeFilter(int[][] shape, double binUnit, int maximumSubtractiveDegree, int minimumSubtractiveDegree) {
        super(shape, binUnit, maximumSubtractiveDegree);
        this.minBin = minimumSubtractiveDegree;
    }

    @Override
    public String getXPathForTimePeriod(int timePeriod) {
        StringBuffer trajectoryConditionXpath = new StringBuffer();
        if (timePeriod > 1) {
            int numberOfConditions = 0;
            if (timePeriod >= 1) {
                int[] difference = getTimePeriodValues(timePeriod);
                for (int k = 0; k < difference.length; k++) {
                    int value = difference[k];
                    if (value != FilterManager.blankShapeValue) {

                        // Don't use for first time period, because there is no movement relative to the non-existant previous time period
                        if (numberOfConditions > 0) {
                            trajectoryConditionXpath.append(mStringOr);
                        }
                        trajectoryConditionXpath.append("@Value = (../@Value + (" + (k + minBin) + ") )");
                        numberOfConditions++;
                    }
                }
            }
        }
        return trajectoryConditionXpath.toString();
    }

    @Override
    public Node toXML(Document doc) {
        Element filter = doc.createElement(CapturedAnalysisDAO.XML_KEY_FILTER);
        for (int i = 0; i < shape.length; i++) {
            int[] timePeriod = shape[i];
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < timePeriod.length; j++) {
                if (timePeriod[j] != FilterManager.blankShapeValue) {
                    int value = maxBin - (timePeriod.length - j) + 1;
                    sb.append(value);
                    sb.append('|');
                }
            }
            filter.setAttribute("time_period_" + (i + 1), sb.toString());
        }
        return filter;
    }
}
