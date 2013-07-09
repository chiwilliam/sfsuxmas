package com.sfsu.xmas.data_files.readers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PathwayFileReader extends AbstractLibraryItemFileReader {

    protected HashMap<String, ArrayList<Integer>> probePathwayMemberships = new HashMap<String, ArrayList<Integer>>();
    protected HashMap<String, Integer> pathwaySourceAssociation = new HashMap<String, Integer>();
    private static final ArrayList<String> nullStrings = new ArrayList<String>(Arrays.asList("---", ""));
    private Vector<String> pathwayDescriptions = new Vector<String>();
    private Vector<String> pathwaySources = new Vector<String>();

    public PathwayFileReader(String fileName) {
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
            Logger.getLogger(PathwayFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void readLine(String line) {
        String[] tokens = line.split(delim, -1);
        if (tokens.length != 2) {
            // Incorrect
            System.err.println(PathwayFileReader.class.getSimpleName() + ": Incorrect line read for line: " + line);
        } else {
            String probeID = tokens[0];
            String pathwaysString = tokens[1];

            ArrayList<Integer> pathwaysForProbe = getPathwaysFromProbeToPathwayLine(pathwaysString);
            if (pathwaysForProbe.size() > 0) {
                probePathwayMemberships.put(probeID, pathwaysForProbe);
            }
        }
    }

    private ArrayList<Integer> getPathwaysFromProbeToPathwayLine(String line) {

        ArrayList<Integer> pathways = new ArrayList<Integer>();

        if (line != null && !nullStrings.contains(line.trim())) {
            ArrayList<String> pathwayMemberships = new ArrayList<String>();
            String[] pathwaysWithSources = line.split("///", -1);

            // For each pathway/source pair
            for (int i = 0; i < pathwaysWithSources.length; i++) {
                // Seperate the pathway from the source
                String[] pathwayAndSource = pathwaysWithSources[i].split("//", -1);
                if (pathwayAndSource.length >= 1 && pathwayAndSource.length <= 2) {
                    if (pathwayAndSource.length == 1) {
                        pathwayAndSource = new String[]{pathwayAndSource[0], "null"};
                    }

                    pathwayAndSource[0] = pathwayAndSource[0].trim().replace("\"", "");

                    if (!nullStrings.contains(pathwayAndSource[0])) {
                        // We have a non-"null" description
                        int activePathwayIndex = getIndexOfPathway(pathwayAndSource[0], pathwayAndSource[1]);
                        // See if the probe already has this association
                        if (!pathwayMemberships.contains(pathwayAndSource[0])) {
                            // Associate pathway with probe
                            pathways.add(activePathwayIndex);
                            pathwayMemberships.add(pathwayAndSource[0]);
                        }
                    }
                } else {
                    System.err.println(PathwayFileReader.class.getSimpleName() + ": Invalid token[] length (" + pathwayAndSource.length + ") != 2, for line: " + line);
                }
            }
        }

        return pathways;
    }

    /**
     * Returns the index of the source
     * 
     * @param description - source description
     * @return
     */
    protected int getIndexOfSource(String description) {
        description = clean(description);
        if (pathwaySources.contains(description)) {
        } else {
            // New source
            pathwaySources.add(description);
        }
        return pathwaySources.indexOf(description) + 1;
    }

    private String clean(String s) {
        return s.trim().replace("\"", "");
    }

    /**
     * Returns the index of the pathway
     * 
     * @param description - pathway description
     * @return
     */
    protected int getIndexOfPathway(String description, String source) {
        description = clean(description);
        if (pathwayDescriptions.contains(description)) {
            return pathwayDescriptions.indexOf(description) + 1; // Indexing from 1
        } else {
            // New pathway
            int sourceIndex = getIndexOfSource(source);
            pathwaySourceAssociation.put(description, sourceIndex);

            pathwayDescriptions.add(description);
            return pathwayDescriptions.size();
        }
    }

    public HashMap<String, ArrayList<Integer>> getProbePathwayMembership() {
        return probePathwayMemberships;
    }

    public HashMap<String, Integer> getPathwaySourceAssociation() {
        return pathwaySourceAssociation;
    }

    public Vector<String> getPathways() {
        return pathwayDescriptions;
    }

    public Vector<String> getSources() {
        return pathwaySources;
    }
}
