package com.sfsu.xmas.dao;

import com.sfsu.xmas.data_files.expression.FileProbes;
import com.sfsu.xmas.data_files.expression.FileProbe;
import com.sfsu.xmas.data_structures.expression.Sample;
import com.sfsu.xmas.data_structures.expression.Samples;
import com.sfsu.xmas.data_structures.expression.TimePeriod;
import com.sfsu.xmas.data_structures.expression.TimePeriods;
import com.sfsu.xmas.util.NumberUtils;
import com.sfsu.xmas.util.StringUtils;
import com.sfsu.xmas.data_files.readers.AbstractDataFileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExpressionDataFileDAO extends AbstractDataFileReader {

    public static final int blockSize = 1000;
    private TimePeriods timePeriods;
    private Samples samples;
    private ArrayList<String> probeIDs;

    public ExpressionDataFileDAO(String fileName, TimePeriods tps) {
        super(fileName);
        probeIDs = new ArrayList<String>();
        this.timePeriods = tps;
        populateSamples();
    }
    
    private void populateSamples() {
        try {
            BufferedReader br = getBufferedReaderForFile();
            // Read Samples Lin
            String strLine = br.readLine();
            samples = readSamples(strLine);
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(ExpressionDataFileDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Samples readSamples(String line) {
        String[] components = line.split(delim);
        Samples samps = new Samples();

        if (components != null) {

            // Ignore the first column header (probe IDs)
            for (int i = 1; i < components.length; i++) {
                String token = components[i];
                if (token != null && token.length() > 0) {
                    token = StringUtils.clean(token);
                } else {
                    token = "null_sample_" + i;
                }
                Sample sample = new Sample(i, token, getTimePeriodForSampleIndex(i));
                samps.add(sample);
            }
        }
        return samps;
    }

    private FileProbe readProbe(String line) {
        FileProbe probe = null;
        String[] components = line.split(delim);
        if (components != null) {
            String probeID = components[0];
            if (probeID != null && probeID.length() > 0) {
                probeID = probeID.trim();

                if (probeID.length() >= 50) {
                    probeID = probeID.substring(0, 49);
                }
                if (probeIDs.contains(probeID)) {
                    // Duplicate
                    return null;
                } else {
                    probeIDs.add(probeID);
                }
            } else {
                return probe;
            }

            ArrayList<Integer> nullIndexes = new ArrayList<Integer>();

            double expSum = (double) 0;
            int valueCount = 0;


            double[] exp = new double[samples.size()];
            for (int i = 1; i < Math.min(components.length, samples.size() + 1); i++) {
                String expressionToken = components[i];
                if (expressionToken != null && expressionToken.length() > 0 && !expressionToken.equals("")) {
                    try {
                        double expressionValue = NumberUtils.getDoubleToFourDecimalPlaces(Double.parseDouble(expressionToken));
                        exp[i - 1] = expressionValue;
                        expSum += expressionValue;
                        valueCount++;
                    } catch (NumberFormatException ex) {
                        // Bullshit string, imagine it's a null
                        nullIndexes.add(i - 1);
                    }
                } else {
                    // Empty value
                    nullIndexes.add(i - 1);
                }
            }
            if (components.length < samples.size() + 1) {
                // Null values at the end of the row
                for (int i = components.length; i <= samples.size() + 1; i++) {
                    nullIndexes.add(i - 2);
                }
            }

            Iterator<Integer> it = nullIndexes.iterator();
            while (it.hasNext()) {
                // Set mean for nulls
                exp[it.next()] = expSum / valueCount;
            }

            probe = new FileProbe(probeID, exp);
        }
        return probe;
    }

    public TimePeriod getTimePeriodForSampleIndex(int sampleIndex) {
        if (timePeriods != null) {
            return timePeriods.getTimePeriodForSample(sampleIndex);
        }
        return null;
    }

    public FileProbes getProbes() {
        return getProbes(0, blockSize);
    }

    public FileProbes getProbes(int block) {
        return getProbes(block, blockSize);
    }

    public FileProbes getProbes(int block, int blockSize) {
        FileProbes probes = new FileProbes();
        try {
            BufferedReader br = getBufferedReaderForFile();

            // Read Samples Line, but ignore
            String strLine = br.readLine();
            if (block > 0) {
                // Need to offset some:
                for (int j = 0; j < block * blockSize; j++) {
                    br.readLine();
                }
            }

            probes = new FileProbes();

            int readProbeLineIndex = 0;
            //Read the next X probes
            while ((strLine = br.readLine()) != null && readProbeLineIndex < blockSize) {
                FileProbe p = readProbe(strLine);
                if (p != null) {
                    probes.add(p);
                    readProbeLineIndex++;
                }
            }
            br.close();

        } catch (IOException ex) {
            Logger.getLogger(ExpressionDataFileDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return probes;
    }

//    public Probes getProbes(int block) {
//        if (probes == null) {
//            populateProbes();
//        }
//        return probes;
//    }
    public Samples getSamples() {
        if (samples == null) {
            populateSamples();
        }
        return samples;
    }
}
