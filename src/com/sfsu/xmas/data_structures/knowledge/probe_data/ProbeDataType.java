package com.sfsu.xmas.data_structures.knowledge.probe_data;

public class ProbeDataType {

    private int id;
    private String attributeName;
    private String link;

    public ProbeDataType(int id, String attributeName, String link) {
        this.id = id;
        this.attributeName = attributeName;
        this.link = link;
    }

    public int getID() {
        return id;
    }
    
    public String getAttribute() {
        return attributeName;
    }

//    public String getLink() {
//        if (isLinked()) {
//            return link.replace(ProbeDataTypes.dataValueLinkKey, dataType);
//        }
//        return "";
//    }

    public boolean isLinked() {
        return link != null && link.length() > 0;
    }

    public String getLink(String value) {
        if (isLinked()) {
            return link.replace(ProbeDataTypes.PROBE_DATA_TYPE_VALUE_IDENTIFIER, value);
        } else {
            return "";
        }
    }

    public String getLink() {
        if (isLinked()) {
            return link;
        } else {
            return "";
        }
    }

    void setLink(String link) {
        this.link = link;
    }
}
