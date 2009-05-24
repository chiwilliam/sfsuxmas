package com.sfsu.xmas.data_structures;

import com.sfsu.xmas.data_structures.expression.Samples;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.sfsu.xmas.dao.ProbeDAO;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Probes extends HashMap<String, Probe> {

    private int expressionDataSetID;
    private int knowledgeDataSetID;
    private boolean expressionsPopulated = false;

    public Probes(int expressionDataSetID, HashMap<String, Probe> probes, boolean expressionsPopulated) {
        this.expressionDataSetID = expressionDataSetID;
        putAll(probes);
        this.expressionsPopulated = expressionsPopulated;
    }

    public Probes(int expressionDataSetID, String[] probeIDs) {
        this.expressionDataSetID = expressionDataSetID;
        for (int i = 0; i < probeIDs.length; i++) {
            String probeID = probeIDs[i];
            put(probeID, new Probe(expressionDataSetID, probeID));
        }
    }
    
    public int getExpressionDataSetID() {
        return expressionDataSetID;
    }

    public void setKnowledgeLibrary(int knowledgeDataSetID) {
        this.knowledgeDataSetID = knowledgeDataSetID;
    }
    
    public void populateTimePeriodExpressions() {
        if (!expressionsPopulated) {
            ProbeDAO pDA = new ProbeDAO(expressionDataSetID);
            ResultSet rs = pDA.getAllProbesTimePeriodsExpression();
            if (rs != null) {
                try {
                    while (rs.next()) {
                        // Single row per probe
                        String id = rs.getString("probe_id");
                        String timePeriodString = rs.getString("time_periods");
                        String expressionString = rs.getString("expression");
                        String[] timePeriods = timePeriodString.split("\\|");
                        String[] expression = expressionString.split("\\|");

                        double[] expressionValues = new double[Math.max(timePeriods.length, expression.length)];
                        if (timePeriods.length == expression.length) {
                            for (int i = 0; i < timePeriods.length && i < expression.length; i++) {
                                int tp = Integer.valueOf(timePeriods[i]);
                                expressionValues[tp - 1] = Double.valueOf(expression[i]);
                            }
                        }
                        Probe p = get(id);
                        if (p != null) {
                            double[] setTimePeriodExpression = p.setTimePeriodExpression(expressionValues);
                        } else {
                            System.err.println("Probe not found for ID=\"" + id + "\"");
                        }
                    }
                    expressionsPopulated = true;
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Samples.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public Probe getProbeAt(int i) {
        Set<String> keys = keySet();
        String[] keyArray = keys.toArray(new String[keys.size()]);
        return get(keyArray[i]);
    }
    
    public boolean tooBig() {
        return size() > 5000;
    }
    
    public boolean expressionsPopulated () {
        return expressionsPopulated;
    }
}
