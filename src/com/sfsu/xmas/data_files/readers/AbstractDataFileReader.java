package com.sfsu.xmas.data_files.readers;

import com.sfsu.xmas.data_files.readers.ILibraryItemFileReader;
import com.sfsu.xmas.data_structures.expression.TimePeriods;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractDataFileReader implements ILibraryItemFileReader {

    protected String fileName = "";
    protected static final String delim = "\t";
    protected static final String extension = ".txt";

    public AbstractDataFileReader(String fileName) {
        this.fileName = fileName;
    }

    public AbstractDataFileReader(BufferedReader br, TimePeriods tps) {
    }

    public BufferedReader getBufferedReaderForFile() {
        FileInputStream fstream = null;
        try {
            String fileLocation = com.sfsu.xmas.globals.FileGlobals.getExpressionDataRoot() + this.fileName + extension;
            if (new File(fileLocation).exists()) {
                fstream = new FileInputStream(fileLocation);
                DataInputStream in = new DataInputStream(fstream);
                return new BufferedReader(new InputStreamReader(in));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AbstractDataFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
