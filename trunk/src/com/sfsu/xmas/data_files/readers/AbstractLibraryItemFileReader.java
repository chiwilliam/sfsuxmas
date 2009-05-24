package com.sfsu.xmas.data_files.readers;

import com.sfsu.xmas.data_files.readers.ILibraryItemFileReader;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractLibraryItemFileReader implements ILibraryItemFileReader {

    protected String fileName = "";
    protected static final String delim = "\t";
    protected static final String extension = ".txt";

    public AbstractLibraryItemFileReader(String fileName) {
        this.fileName = fileName;
    }

    /**
     * An arraylist of string arrays provides flexibility to read a simple line 
     * of tokens (single string array in the array list) or multiple multi-token
     * attributes from the same line, such as pathway/source pairings (multiple 
     * 2-item string arrays in the arraylist)
     * 
     * @param line - a line of deliminated text
     * @return - 
     */
//    public abstract ArrayList<String[]> readLine(String line);

    
//    public ArrayList<String[]> getRowEntries() {
//        return rowEntries;
//    }
    
    public BufferedReader getBufferedReaderForFile() {
        FileInputStream fstream = null;
        try {
            String fileLocation = com.sfsu.xmas.globals.FileGlobals.getKnowledgeDataRoot() + this.fileName + extension;
            fstream = new FileInputStream(fileLocation);
            DataInputStream in = new DataInputStream(fstream);
            return new BufferedReader(new InputStreamReader(in));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AbstractLibraryItemFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
