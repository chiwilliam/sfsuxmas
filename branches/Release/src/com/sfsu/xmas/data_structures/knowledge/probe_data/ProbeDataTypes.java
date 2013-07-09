package com.sfsu.xmas.data_structures.knowledge.probe_data;

import java.util.HashMap;

public class ProbeDataTypes extends HashMap<Integer, ProbeDataType> {

    public static final String PROBE_DATA_TYPE_VALUE_IDENTIFIER = "%_PDT_%";
    
    public void updateLinks(HashMap<String, String> links) {
        for (int i = 0; i < size(); i++) {
             ProbeDataType head = get(i);
             if (links.containsKey(head.getAttribute())) {
                 head.setLink(links.get(head.getAttribute()));
             }
        }
    }
    
    public void updateLink(String dataType, String link) {
        for (int i = 0; i < size(); i++) {
             ProbeDataType head = get(i);
             if (head.getAttribute().equals(dataType)) {
                 head.setLink(link);
             }
        }
    }

}
