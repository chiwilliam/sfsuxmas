package com.sfsu.xmas.data_structures.knowledge;

public abstract class MultiProbeDataStructure {

    protected int id;
    protected String name = "Not Set";
    protected String description = "Not Set";
    protected int filteredCardinality = -1;
    protected int unfilteredCardinality = -1;
    protected int totalCardinality = -1;
//    protected AnnotationDatabase owningDatabase;
//    protected boolean dataPopulated = false;
    protected String[] probes;


//    public void setFilteredCardinality(int filtCardinality) {
//        this.filteredCardinality = filtCardinality;
//    }
//
//    public void setUnfilteredCardinality(int unfiltCardinality) {
//        this.unfilteredCardinality = unfiltCardinality;
//    }
    public void setTotalCardinality(int totalCardinality) {
        this.totalCardinality = totalCardinality;
    }

//    public int getFilteredCardinality() {
//        if (filteredCardinality < 0) {
//            System.out.println(this.getClass().getSimpleName() + ": INEFFICIENT OPERATOR");
//            populateCardinalities();
//        }
//        return filteredCardinality;
//    }
//
//    public int getUnfilteredCardinality() {
//        if (unfilteredCardinality < 0) {
//            System.out.println(this.getClass().getSimpleName() + ": INEFFICIENT OPERATOR");
//            populateCardinalities();
//        }
//        return unfilteredCardinality;
//    }
    public int getTotalCardinality() {
        if (totalCardinality < 0 || probes == null) {
            System.out.println(this.getClass().getSimpleName() + ": INEFFICIENT OPERATOR");
            populateMembers();
        }
        return totalCardinality;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    protected abstract void populate();

    protected abstract void populateMembers();//    protected abstract void populateCardinalities();
}
