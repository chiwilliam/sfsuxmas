package com.sfsu.xmas.data_structures;

import com.sfsu.xmas.data_sets.ExpressionDataSet;
import com.sfsu.xmas.data_sets.ExpressionDataSetMultiton;
import com.sfsu.xmas.dao.ProbeDAO;
import com.sfsu.xmas.util.NumberUtils;
import java.io.Serializable;

/**
 * A probe is identified by an ID and contains:
 *  - Expression
 *  - Annotations - e.g. chromosome
 *  - Attributes - e.g. description
 * 
 */
public class Probe implements Serializable {

    private int dataSetID;
    private String id;
    private String[] probeData;
//    private double[] sampleExpression;
//    private double[] timePeriodExpression;
//    private ProbeDataTypeValues attributes;
//    private Labels labels = null;
//    private ProbeDataAccess probeDAO;
    // These 2 should eventually be the same thing
    public Probe(int dataSetID, String id) {
        this.dataSetID = dataSetID;
        this.id = id;
    }

    public Probe(int dataSetID, String id, String[] probeData) {
        this.dataSetID = dataSetID;
        this.id = id;
        this.probeData = probeData;
    }

    public String getID() {
        return id;
    }


    public double[] setTimePeriodExpression(double[] exp) {
        ProbeExpressionManager pem = ProbeExpressionManager.getInstance();
        return pem.setExpression(dataSetID, id, exp);
    }
    
    public double[] getTimePeriodExpression() {
        return getTimePeriodExpression(true);
    }
    
    public double[] getTimePeriodExpression(boolean fromCache) {
        double[] expression = new double[0];
        if (fromCache) {
            ProbeExpressionManager pem = ProbeExpressionManager.getInstance();
            expression = pem.getExpression(dataSetID, id);
            if (expression == null) {
                return populateTimePeriodExpression();
            }
        }
        return expression;
    }

    public double[] populateTimePeriodExpression() {
        ProbeDAO pDA = new ProbeDAO(dataSetID);
        return setTimePeriodExpression(pDA.getProbeTimePeriodExpression(id));
    }


    public double[] setSampleExpression(double[] exp) {
        ProbeExpressionManager pem = ProbeExpressionManager.getInstance();
        return pem.setSampleExpression(dataSetID, id, exp);
    }
    
    public double[] getSampleExpression() {
        return getSampleExpression(true);
    }
    
    public double[] getSampleExpression(boolean fromCache) {
        double[] expression = new double[0];
        if (fromCache) {
            ProbeExpressionManager pem = ProbeExpressionManager.getInstance();
            expression = pem.getSampleExpression(dataSetID, id);
            if (expression == null) {
                return populateSampleExpression();
            }
        }
        return expression;
    }

    public double[] populateSampleExpression() {
        ProbeDAO pDA = new ProbeDAO(dataSetID);
        return setSampleExpression(pDA.getProbeSamplesExpression(id));
    }

    public double getProfileTimePeriodBasedVolatility() {
        double volatility = 0.0;
        
        double[] timePeriodExpression = getTimePeriodExpression();

        for (int i = 1; i < timePeriodExpression.length; i++) {
            volatility += Math.abs(timePeriodExpression[i] - timePeriodExpression[i - 1]);
        }
        return NumberUtils.getDoubleToTwoDecimalPlaces(volatility);
    }

    public double getProfileTimePeriodBasedLinearTrend() {
        double trend = 0.0;
        
        double[] timePeriodExpression = getTimePeriodExpression();
        
        for (int i = 1; i < timePeriodExpression.length; i++) {
            trend += (timePeriodExpression[i] - timePeriodExpression[i - 1]);
        }
        return NumberUtils.getDoubleToTwoDecimalPlaces(trend);
    }

    public ExpressionDataSet getParentDatabase() {
        return ExpressionDataSetMultiton.getUniqueInstance().getDataSet(dataSetID, false);
    }
    /**
     * Populates other gene specific data such as description
     */
//    protected void populateGeneData() {
//        ProbeDA pDA = new ProbeDA(dataSetID);
//        ResultSet rs = pDA.getProbeData(id);
//        if (rs != null) {
//            try {
//                if (rs.next()) {
////                rs.getShort(columnIndex);
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(Probe.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
//    public Probe(String id) {
////        Database db = DatabaseManager.getActiveDataBase();
////        this.owningDatabase = db;
//        this.id = id;
//    }
//    void setAnnotations(Annotations annotations) {
//        annotations = annotations;
//    }
//    void setAttributes(ProbeDataTypeValues get) {
//        attributes = get;
//    }

//    private void initializeDAO() {
//        probeDAO = new ProbeDataAccess(owningDatabase);
//    }
//
//    private void populateProbeAttributes(ProbeAttributeHeaderList attributeHeaders) {
//        if (probeDAO == null) {
//            initializeDAO();
//        }
//        if (probeAttributes == null) {
//            probeAttributes = probeDAO.getProbeAttributes(id, attributeHeaders);
//        }
//    }
//
//    public ProbeAttributeList getProbeAttributes() {
//        if (probeAttributes == null) {
//            if (probeDAO == null) {
//                initializeDAO();
//            }
//            populateProbeAttributes(probeDAO.getProbeAttributeHeaders());
//        }
//        return probeAttributes;
//    }

//    public Labels getLabels() {
//        if (labels == null) {
//            // TODO: bdalziel
////            this.annos = new Annotations(id);
//        }
//        return labels;
//    }
}
