package com.sfsu.xmas.data_files.readers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProbeAttributeFileReader extends AbstractLibraryItemFileReader {

    protected String[] attributeHeaders;
    protected HashMap<String, String[]> probeAttributeData;

    public ProbeAttributeFileReader(String fileName) {
        super(fileName);
        try {
            BufferedReader br = getBufferedReaderForFile();

            // Read Samples Line
            attributeHeaders = readHeaderLine(br.readLine());

            probeAttributeData = new HashMap<String, String[]>();
            String line = "";
            while ((line = br.readLine()) != null) {
                readLine(line);
            }

            br.close();
        } catch (IOException ex) {
            Logger.getLogger(ProbeAttributeFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String[] readHeaderLine(String line) {
        // Each token is a column header, with the exception of the first which is the probe ID header
        String[] tokens = line.split(delim, -1);

        ArrayList<String> heads = new ArrayList<String>();
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if (token != null && token.length() > 0) {
                heads.add(token.replace(' ', '_'));
            }
        }

        String[] headers = new String[heads.size()];
        for (int i = 0; i < heads.size(); i++) {
            headers[i] = heads.get(i); // token.replace(' ', '_');
        }
        return headers;
    }

    public void readLine(String line) {
        String[] tokens = line.split(delim, -1);

        String probeID = tokens[0];
        if (probeID.length() >= 50) {
            probeID = probeID.substring(0, 49);
        }
        if (!probeAttributeData.containsKey(probeID)) {
            String[] attributes = new String[attributeHeaders.length];
            for (int i = 0; i < Math.min(tokens.length, attributeHeaders.length); i++) {
                String attri = tokens[i];
                if (attri != null && attri.length() > 0 && !attri.equals(" ")) {
                    attributes[i] = attri;
                } else {
                    attributes[i] = "null";
                }
            }

            if (tokens.length < attributeHeaders.length) {
                for (int i = tokens.length; i < attributeHeaders.length; i++) {
                    attributes[i] = "null";
                }
            }
            probeAttributeData.put(probeID, attributes);
        }
    }

    public String[] getColumnHeaders() {
        return attributeHeaders;
    }

    public HashMap<String, String[]> getFileData() {
        return probeAttributeData;
    }
}
