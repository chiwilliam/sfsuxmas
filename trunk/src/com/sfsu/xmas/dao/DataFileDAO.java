package com.sfsu.xmas.dao;

import com.sfsu.xmas.globals.FileGlobals;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DataFileDAO {

    public DataFileDAO() {
        
    }
    
    public File[] getExpressionDataFiles () {
        File[] files = new File[0];
        try
        {
            String fileDir = com.sfsu.xmas.globals.FileGlobals.getExpressionDataRoot();
            if (new File(fileDir).exists()) {
                files = new java.io.File(fileDir).listFiles();
            }
        }
        catch(Exception e)
        {
        }
        return files;
    }
    
    public File[] getKnowledgeDataFiles () {
        File[] files = new File[0];
        try
        {
            String fileDir = com.sfsu.xmas.globals.FileGlobals.getKnowledgeDataRoot();
            if (new File(fileDir).exists()) {
                files = new java.io.File(fileDir).listFiles();
            }
        }
        catch(Exception e)
        {
        }
        return files;
    }
    
    public File[] getProbeAttributeDataFiles () {
        File[] files = getKnowledgeDataFiles();
        ArrayList<File> attributeFiles = new ArrayList<File>();
        
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.getName().startsWith(FileGlobals.PROBE_DATA_FILE_PREFIX)) {
                attributeFiles.add(file);
            }
        }
        return attributeFiles.toArray(new File[attributeFiles.size()]);
    }
    
    public File[] getPathwayDataFiles () {
        File[] files = getKnowledgeDataFiles();
        ArrayList<File> attributeFiles = new ArrayList<File>();
        
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.getName().startsWith(FileGlobals.PATHWAY_DATA_FILE_PREFIX)) {
                attributeFiles.add(file);
            }
        }
        return attributeFiles.toArray(new File[attributeFiles.size()]);
    }
    
    public File[] getGODataFiles () {
        File[] files = getKnowledgeDataFiles();
        ArrayList<File> attributeFiles = new ArrayList<File>();
        
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.getName().startsWith(FileGlobals.GO_DATA_FILE_PREFIX)) {
                attributeFiles.add(file);
            }
        }
        return attributeFiles.toArray(new File[attributeFiles.size()]);
    }
    
    public File[] getLabelDataFiles () {
        File[] files = getKnowledgeDataFiles();
        ArrayList<File> attributeFiles = new ArrayList<File>();
        
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.getName().startsWith(FileGlobals.LABEL_DATA_FILE_PREFIX)) {
                attributeFiles.add(file);
            }
        }
        return attributeFiles.toArray(new File[attributeFiles.size()]);
    }
    
}
