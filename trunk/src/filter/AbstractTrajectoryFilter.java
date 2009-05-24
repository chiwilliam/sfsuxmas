/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import com.sfsu.xmas.filter.FilterManager;
import com.sfsu.xmas.filter.AbstractFilter;

public abstract class AbstractTrajectoryFilter extends AbstractFilter {

    protected int[][] shape;
    private boolean inverse = false;
    protected int maxBin;
    private double binUnit;

    public AbstractTrajectoryFilter(int[][] shape, double binUnit, int maxBin) {
        this.shape = shape;
        this.maxBin = maxBin;
        this.binUnit = binUnit;
    }

    public String getXPath() {
        StringBuffer fullXPath = new StringBuffer();
        return fullXPath.toString().substring(0, fullXPath.length() - 3);
    }

    public abstract String getXPathForTimePeriod(int timePeriod);

    public String getXPathToNode() {
        return getXPath();
    }

    public String getType() {
        return "Trajectory shape";
    }

    public String getSimpleDescription() {
        StringBuffer desc = new StringBuffer();
        desc.append("<table>");
        desc.append("<thead>");
        desc.append("<tr>");
        for (int i = 0; i < shape.length; i++) {
            desc.append("<th>");
            desc.append("TP" + (i + 1));
            desc.append("</th>");
        }
        desc.append("</tr>");
        desc.append("</thead>");

        desc.append("<tbody>");
        desc.append("<tr valign=\"middle\">");
        for (int i = 0; i < shape.length; i++) {
            desc.append("<td style=\"text-align: right;\">");
            int[] timePeriod = shape[i];
            for (int j = 0; j < timePeriod.length; j++) {
                if (timePeriod[j] != FilterManager.blankShapeValue) {
                    double tpValue = (maxBin - (timePeriod.length - j) + 1) * binUnit;
                    if (tpValue > 0) {
                        desc.append("+");
                    }
                    desc.append(tpValue);
                    desc.append("<br />");
                }
            }
            desc.append("</td>");
        }
        desc.append("</tr>");
        desc.append("</tbody>");
        desc.append("</table>");
        return desc.toString();
    }

    public int[] getTimePeriodValues(int entry) {
        return shape[entry - 1];
    }

    public int[][] getShape() {
        return shape;
    }

    public void invert() {
        inverse = true;
    }

    public boolean isInverted() {
        return inverse;
    }
}
