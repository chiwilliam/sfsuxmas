package com.sfsu.xmas.data_files.readers;

import com.sfsu.xmas.data_files.readers.AbstractLibraryItemFileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProbeIDFileReader extends AbstractLibraryItemFileReader {

    protected ArrayList<String> probeIDs = new ArrayList<String>();
    
    public ProbeIDFileReader(String fileName) {
        super(fileName);
        try {
            BufferedReader br = getBufferedReaderForFile();

            String line = "";
            while ((line = br.readLine()) != null) {
                readLine(line);
            }
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(ProbeIDFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void readLine(String line) {
        String[] tokens = line.split(delim, -1);
        if (tokens.length != 1) {
            // Assume just a single identifier par line (i.e. first column holds data)
            System.err.println(ProbeIDFileReader.class.getSimpleName() + ": Incorrect attribute count read from line: " + line);
        }
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if (token.trim().length() > 0) {
                probeIDs.add(token);
            }
        }
    }

    public ArrayList<String> getRowEntries() {
        return probeIDs;
    }
}
