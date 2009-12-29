package com.sfsu.xmas.data_sets;

import com.sfsu.xmas.dao.DAOFactoryFactory;
import com.sfsu.xmas.dao.ExpressionDataSetDAO;
import com.sfsu.xmas.dao.DataSetDAO;
import com.sfsu.xmas.data_structures.Probe;
import com.sfsu.xmas.data_structures.Probes;
import com.sfsu.xmas.data_structures.expression.Samples;
import com.sfsu.xmas.data_structures.expression.TimePeriods;
import com.sfsu.xmas.filter.FilterList;
import com.sfsu.xmas.filter.FilterManager;
import com.sfsu.xmas.globals.FileGlobals;
import filter.MatchingProbesForFilterSet;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class ExpressionDataSet extends AbstractDataSet {

    private int numberOfProbes = 0;
    private Samples samples;
    private Probes probes;
    private TimePeriods timePeriods;
    private double maximumExpression = 0;
    private double minimumExpression = 0;
    private double meanExpression = 0;
    private ExpressionDataSetDAO dataAccess;

    public ExpressionDataSet(int id) {
        this.id = id;
        dataAccess = DAOFactoryFactory.getUniqueInstance().getDatabaseDAOFactory().getExpressionDataSetDAO(this.id);
        populateBasicData();
    }

    public ExpressionDataSet(int id, boolean populateFully) {
        this.id = id;
        dataAccess = DAOFactoryFactory.getUniqueInstance().getDatabaseDAOFactory().getExpressionDataSetDAO(this.id);
        populateBasicData();
        if (populateFully) {
            populateFully();
        }
    }

    public boolean hasData() {
        return getNumberOfProbes() > 0 && getNumberOfSamples() > 0;
    }

    public File[] getCapturedAnalyses() {
        String path = FileGlobals.getActiveRoot() + ExpressionDataSetMultiton.getUniqueInstance().getDataSet(id, false).getName() + File.separatorChar;
        ArrayList<File> caFiles = new ArrayList<File>();

        File[] files = new java.io.File(path).listFiles();
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                int dotLocation = files[i].getName().indexOf('.');
                if (dotLocation > 0) {
                    String fileName = files[i].getName().substring(0, dotLocation);
                    String extension = files[i].getName().substring(dotLocation, files[i].getName().length());
                    if (fileName.startsWith(FileGlobals.ANALYSIS_CAPTURE_PREFIX) && extension.equals(FileGlobals.XML_FILE_EXTENSION)) {
                        caFiles.add(files[i]);
                    }
                } else {
                }
            }
        }
        return caFiles.toArray(new File[caFiles.size()]);
    }

    public File[] getTrajectoryFiles() {
        String path = FileGlobals.getActiveRoot() + ExpressionDataSetMultiton.getUniqueInstance().getDataSet(id, false).getName() + File.separatorChar;
        ArrayList<File> caFiles = new ArrayList<File>();

        File[] files = new java.io.File(path).listFiles();
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                int dotLocation = files[i].getName().indexOf('.');
                if (dotLocation > 0) {
                    String fileName = files[i].getName().substring(0, dotLocation);
                    String extension = files[i].getName().substring(dotLocation, files[i].getName().length());
                    if (fileName.startsWith(FileGlobals.TRAJECTORY_DATA_FILE_PREFIX) && extension.equals(FileGlobals.XML_FILE_EXTENSION)) {
                        caFiles.add(files[i]);
                    }
                } else {
                }
            }
        }
        return caFiles.toArray(new File[caFiles.size()]);
    }

    public boolean isTrajectoryFile(String fileName) {
        File[] files = getTrajectoryFiles();
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.getName().equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    public synchronized void populateBasicData() {
        DataSetDAO databasesDA = new DataSetDAO();
        if (databasesDA.isExistingExpressionDataSet(id)) {
            this.name = dataAccess.getName();
            this.description = dataAccess.getDescription();
            this.creationDate = dataAccess.getCreationDate();
            this.numberOfProbes = dataAccess.getNumberOfProbes();
            this.maximumExpression = dataAccess.getMaximumExpression();
            this.minimumExpression = dataAccess.getMinimumExpression();
            this.meanExpression = dataAccess.getMeanExpression();
            this.samples = dataAccess.getSamples();
            this.timePeriods = dataAccess.getTimePeriods();
            isFresh = true;
        } else {
            samples = new Samples();
            timePeriods = new TimePeriods();
        }
    }

    public synchronized void populateFully() {
        if (probes == null) {
            String[] ids = dataAccess.getProbes();
            probes = new Probes(this.id, ids);
        }
    }

    public int getNumberOfTimePeriods() {
        return timePeriods.size();
    }

    public int getNumberOfSamples() {
        return samples.size();
    }

    public int getNumberOfProbes() {
        return numberOfProbes;
    }

    public double getMaximumExpression() {
        return maximumExpression;
    }

    public double getMinimumExpression() {
        return minimumExpression;
    }

    public double getMeanExpression() {
        return meanExpression;
    }

    public Probes getProbes() {
        return probes;
    }

    public Probes getProbes(String identifier, boolean populateExpression) {
        if (populateExpression && probes != null) {
            if(!probes.expressionsPopulated()){
                probes.populateTimePeriodExpressions();
            }
        }
        if(probes == null){
            populateFully();
        }
        
        HashMap<String, Probe> matchingPs = new HashMap<String, Probe>();
        
        
        FilterList filters = FilterManager.getUniqueInstance().getFilterListForIdentifier(identifier);


        MatchingProbesForFilterSet includedPs = filters.getIncludedProbeFiltersByIDs();
        MatchingProbesForFilterSet excludedPs = filters.getExcludedProbeFiltersByIDs();
        
        
        Set<Entry<String, Probe>> i = probes.entrySet();
        Iterator<Entry<String, Probe>> it = i.iterator();
        while (it.hasNext()) {
            Entry<String, Probe> probeEntry = it.next();
            
            String probeID = probeEntry.getKey();
            boolean isolated = includedPs.size() <= 0 || (includedPs.size() > 0 && includedPs.isProbeIsolated(probeID));
            boolean excluded = excludedPs.size() > 0 && excludedPs.isProbeExcluded(probeID);

            boolean probeStillInAnalysis = isolated && !excluded;

            if (probeStillInAnalysis) {
                // Probe is ok
                matchingPs.put(probeEntry.getKey(), probeEntry.getValue());
            }
        }
        Probes matchingProbes = new Probes(getID(), matchingPs, probes.expressionsPopulated());
        if (populateExpression && !matchingProbes.expressionsPopulated()) {
            // Shouldn't really ever get here...
            matchingProbes.populateTimePeriodExpressions();
        }
        return matchingProbes;
    }

    public synchronized Probes getProbes(String[] ids, boolean populateExpression) {
        if (probes == null) {
            populateFully();
        }
        if (populateExpression && !probes.expressionsPopulated()) {
            probes.populateTimePeriodExpressions();
        }

        HashMap<String, Probe> subset = new HashMap<String, Probe>();
        if (ids != null) {
            if (ids.length == probes.size()) {
                return probes;
            } else { // ids.length <= probes.size()/2
                for (int i = 0; i < ids.length; i++) {
                    String key = ids[i];
                    if (probes.containsKey(key)) {
                        subset.put(key, probes.get(key));
                    }
                }
            }
        }
        return new Probes(id, subset, probes.expressionsPopulated());
    }

    public Probe getProbe(String id, boolean expressionsPopulated) {
        if (probes == null) {
            populateFully();
        }
        return probes.get(id);
    }

    public Samples getSamples() {
        return this.samples;
    }

    public TimePeriods getTimePeriods() {
        return this.timePeriods;
    }
    
    // TODO implement in data layer
    public boolean isPCADRelavent() {
        return true;
    }
    
}
