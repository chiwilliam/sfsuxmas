/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sfsu.xmas.visualizations;

import com.sfsu.xmas.data_sets.ExpressionDataSet;
import com.sfsu.xmas.servlet.ServletUtil;
import com.sfsu.xmas.session.SessionAttributeManager;
import com.sfsu.xmas.session.SessionAttributes;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import visualization.ComparativeViz;
import visualization.HybridViz;
import visualization.IVisualization;
import visualization.PreciseViz;
import visualization.SubtractiveViz;
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

    public synchronized IVisualization getVisualization(String identifier, HttpServletRequest request) {
        IVisualization cg = null;

        ExpressionDataSet eDB = SessionAttributeManager.getActivePrimaryExpressionDatabase(request);
        ExpressionDataSet eDBSecond = SessionAttributeManager.getActiveSecondaryExpressionDatabase(request);

        if (SessionAttributeManager.isProfileVisualization(request)) {
            if ((SessionAttributeManager.isComparative(request) || SessionAttributeManager.isSubtractive(request) || SessionAttributeManager.isDataSelector(request))
                 && eDBSecond != null) {
                if(SessionAttributeManager.isComparative(request)){
                    System.out.println("LOADING: Comparative Visualization");
                    cg = new ComparativeViz(identifier, eDB.getID(), eDBSecond.getID(), SessionAttributeManager.getActiveTrajectoryFile(request).getFileName());
                }
                else if(SessionAttributeManager.isSubtractive(request)){
                    System.out.println("LOADING: Subtractive Comparative Visualization");
                    cg = new SubtractiveViz(identifier, eDB.getID(), eDBSecond.getID(), SessionAttributeManager.getActiveTrajectoryFile(request).getFileName());
                }
                else if(SessionAttributeManager.isDataSelector(request)){
                    System.out.println("LOADING: Single Datasets Visualization");
                    String cookieKey = SessionAttributes.IMAGE_TYPE_DATASELECTOR;
                    Cookie cookie = ServletUtil.getCookieFromKey(request, cookieKey);
                    //view secondary dataset
                    if(cookie.getValue().equals("false")){
                        cg = new PreciseViz(identifier, eDBSecond.getID(), SessionAttributeManager.getActiveTrajectoryFile(request).getFileName());
                    }
                    //view primary dataset
                    else{
                        cg = new PreciseViz(identifier, eDB.getID(), SessionAttributeManager.getActiveTrajectoryFile(request).getFileName());
                    }
                }
            } else {
                System.out.println("LOADING: Exact Visualization");
                cg = new PreciseViz(identifier, eDB.getID(), SessionAttributeManager.getActiveTrajectoryFile(request).getFileName());
            }
        } else if (SessionAttributeManager.isHybridVisualization(request)) {
            System.out.println("LOADING: Hybrid Visualization");
            cg = new HybridViz(identifier, eDB.getID(), SessionAttributeManager.getActiveTrajectoryFile(request).getFileName());
        } else {
            System.out.println("LOADING: Traj Visualization");
            cg = new TreeViz(identifier, eDB.getID(), SessionAttributeManager.getActiveTrajectoryFile(request).getFileName());
        }
        
        return cg;
    }
}
