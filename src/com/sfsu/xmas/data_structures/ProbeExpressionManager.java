package com.sfsu.xmas.data_structures;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;

public class ProbeExpressionManager {

    private static ProbeExpressionManager instance;
    private static JCS expressionCache;

    public static synchronized ProbeExpressionManager getInstance() {
        if (instance == null) {
            instance = new ProbeExpressionManager();
        }

        return instance;
    }

    private ProbeExpressionManager() {
        try {
            expressionCache = JCS.getInstance("expressionCache");
        } catch (CacheException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    public double[] getExpression(int dsID, String id) {
        double[] expression = new double[0];
        expression = (double[]) expressionCache.get(getKey(dsID, id));
        return expression;
    }

    public double[] setExpression(int dsID, String id, double[] expression) {
        try {
            expressionCache.put(getKey(dsID, id), expression);
        } catch (CacheException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return expression;
    }

    public double[] getSampleExpression(int dsID, String id) {
        double[] expression = new double[0];
        expression = (double[]) expressionCache.get(getSampleKey(dsID, id));
        return expression;
    }

    public double[] setSampleExpression(int dsID, String id, double[] expression) {
        try {
            expressionCache.put(getSampleKey(dsID, id), expression);
        } catch (CacheException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return expression;
    }

    private String getKey(int dsID, String id) {
        StringBuffer sb = new StringBuffer();
        sb.append("expression_ds_");
        sb.append(String.valueOf(dsID));
        sb.append("_pid_");
        sb.append(id);
        return sb.toString();
    }

    private String getSampleKey(int dsID, String id) {
        StringBuffer sb = new StringBuffer();
        sb.append("sample_expression_ds_");
        sb.append(String.valueOf(dsID));
        sb.append("_pid_");
        sb.append(id);
        return sb.toString();
    }
}
