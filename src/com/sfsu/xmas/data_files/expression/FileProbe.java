package com.sfsu.xmas.data_files.expression;

public class FileProbe {

    String id;
    double[] expression;
    
    public FileProbe(String id, double[] expression) {
        this.id = id;
        this.expression = expression;
    }
    
    public String getID() {
        return id;
    }
    
    public double[] getExpression() {
        return expression;
    }
    
}
