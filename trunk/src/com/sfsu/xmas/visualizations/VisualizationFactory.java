/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sfsu.xmas.visualizations;

import com.sfsu.xmas.data_sets.ExpressionDataSet;
import com.sfsu.xmas.session.SessionAttributeManager;
import javax.servlet.http.HttpServletRequest;
import visualization.ComparativeViz;
import visualization.HybridViz;
import visualization.IVisualization;
import visualization.PreciseViz;
import visualization.TreeViz;

/**
 *
 * @author bdalziel
 */
public class VisualizationFactory {

    protected static VisualizationFactory instance;

    public static VisualizationFactory getUniqueInstance() {
        if (instance == null) {
            instance = new VisualizationFactory();
        }
        return instance;
    }

    private VisualizationFactory() {
    }

    public IVisualization getVisualization(String identifier, HttpServletRequest request) {
        IVisualization cg = null;

        ExpressionDataSet eDB = SessionAttributeManager.getActivePrimaryExpressionDatabase(request);
        ExpressionDataSet eDBSecond = SessionAttributeManager.getActiveSecondaryExpressionDatabase(request);

        if (SessionAttributeManager.isProfileVisualization(request)) {
            if (SessionAttributeManager.isComparative(request) && eDBSecond != null) {
                System.out.println("LOADING: Comparative Visualization");
                cg = new ComparativeViz(identifier, eDB, eDBSecond, SessionAttributeManager.getActiveTrajectoryFile(request));
            } else {
                System.out.println("LOADING: Exact Visualization");
                cg = new PreciseViz(identifier, eDB, SessionAttributeManager.getActiveTrajectoryFile(request));
            }
        } else if (SessionAttributeManager.isHybridVisualization(request)) {
            System.out.println("LOADING: Hybrid Visualization");
            cg = new HybridViz(identifier, eDB, SessionAttributeManager.getActiveTrajectoryFile(request));
        } else {
            System.out.println("LOADING: Traj Visualization");
            cg = new TreeViz(identifier, eDB, SessionAttributeManager.getActiveTrajectoryFile(request));
        }
        return cg;
    }
}
