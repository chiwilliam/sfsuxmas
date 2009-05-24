package com.sfsu.xmas.data_sets;

public abstract class AbstractDataSet implements IDataSet {

    protected int id;
    protected String name = "No Name";
    protected String description = "No Description";
    protected int creationDate;
    protected boolean isFresh = false;

    public abstract void populateBasicData();

    public abstract void populateFully();
    
    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCreationDate() {
        return new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm").format(new java.util.Date((long) creationDate * (long) 1000));
    }

    public synchronized void makeStale() {
        System.out.println("Making data stale for: '" + name + "'");
        isFresh = false;
    }

    public synchronized boolean isFresh() {
        return isFresh;
    }
}