package com.sfsu.xmas.data_files.readers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GOFileReader extends AbstractLibraryItemFileReader {

    protected HashMap<String, ArrayList<String>> probesToGOTerms = new HashMap<String, ArrayList<String>>();
    protected HashMap<String, String[]> goTerms = new HashMap<String, String[]>();//    protected HashMap<String, Integer> pathwaySourceAssociation = new HashMap<String, Integer>();
//    private Vector<String> goTermDescriptions = new Vector<String>();
//    private Vector<String> pathwaySources = new Vector<String>();
    private static final ArrayList<String> nullStrings = new ArrayList<String>(Arrays.asList("---", ""));

    public GOFileReader(String fileName) {
        super(fileName);
        try {
            BufferedReader br = getBufferedReaderForFile();

            // Off set, don't need
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                readLine(line);
            }
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(GOFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void readLine(String line) {
        String[] tokens = line.split(delim, -1);
        if (tokens.length != 2) {
            // Incorrect
            System.err.println(GOFileReader.class.getSimpleName() + ": Incorrect line read for line: " + line);
        } else {
            String probeID = tokens[0];
            ArrayList<String> goTerm = getGOTermsForProbe(tokens[1]);
            if (goTerm.size() > 0) {
                probesToGOTerms.put(probeID, goTerm);
            }
        }
    }

    private ArrayList<String> getGOTermsForProbe(String line) {
        ArrayList<String> goTermsForProbe = new ArrayList<String>();

        if (line != null && !nullStrings.contains(line.trim())) {
            String[] goTerm = line.split("///", -1);

            // For each pathway/source pair
            for (int i = 0; i < goTerm.length; i++) {
                // Seperate the pathway from the source
                String[] goTermComponents = goTerm[i].split("//", -1);
                if (goTermComponents.length == 3) {
                    goTermComponents[0] = goTermComponents[0].trim().replace("\"", "");

                    if (!nullStrings.contains(goTermComponents[0])) {
                        // We have a non-"null" description
                        String activeGOTermIndex = goTermComponents[0];
                        // See if the probe already has this association
                        if (!goTermsForProbe.contains(activeGOTermIndex)) {
                            // Associate pathway with probe
                            goTermsForProbe.add(activeGOTermIndex);
                            if (!goTerms.containsKey(activeGOTermIndex)) {
                                
                                for (int j = 1; j < goTermComponents.length; j++) {
                                    goTermComponents[j] = goTermComponents[j].trim().replace("\"", "");
                                }
                                
                                goTerms.put(activeGOTermIndex, goTermComponents);
                            }
                        }
                    }
                } else {
                    System.err.println(GOFileReader.class.getSimpleName() + ": Invalid token[] length (" + goTermComponents.length + ") != 3, for line: " + line);
                }
            }
        }

        return goTermsForProbe;
    }
    
    public HashMap<String, String[]> getGOTerms() {
        return goTerms;
    }
    
    public HashMap<String, ArrayList<String>> getProbeToGOTerms() {
        return probesToGOTerms;
    }
    
}
