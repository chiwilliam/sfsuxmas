package com.sfsu.xmas.filter;

import filter.*;
import com.sfsu.xmas.data_structures.Probe;
import com.sfsu.xmas.data_structures.Probes;
import com.sfsu.xmas.data_structures.knowledge.GOTerm;
import com.sfsu.xmas.data_structures.knowledge.GOTerms;
import com.sfsu.xmas.data_structures.knowledge.Label;
import com.sfsu.xmas.data_structures.knowledge.Labels;
import com.sfsu.xmas.data_structures.knowledge.Pathway;
import com.sfsu.xmas.data_structures.knowledge.Pathways;
import java.util.HashMap;
import java.util.Iterator;


import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.xerces.dom.DOMImplementationImpl;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Basic idea is to maintain filter sessions for users
 * @author bdalziel
 */
public class FilterManager extends HashMap<String, FilterList> {

    private static FilterManager uniqueInstance;

    public static FilterManager getUniqueInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new FilterManager();
        }
        return uniqueInstance;
    }

    private FilterManager() {
    }

//    protected FilterList getFilterListForIdentifier(String identifier) {
//        if (containsKey(identifier)) {
//            return get(identifier);
//        }
//        return new FilterList();
//    }
    public static TrajNodeFilter makeNodeFilter(int nodeID, int timePeriod, boolean exclude) {
        TrajNodeFilter filter = new TrajNodeFilter(nodeID, timePeriod);
        if (exclude) {
            filter.exclude();
        }
        return filter;
    }

    public synchronized FilterList getFilterListForIdentifier(String identifier) {
        if (containsKey(identifier)) {
            return get(identifier);
        }
        return new FilterList();
    }

    public void removeTrajectoryShapeFilter(String identifier) {
        FilterList fl = getFilterListForIdentifier(identifier);
        // For each active filter
        for (int i = 0; i < fl.size(); i++) {
            AbstractFilter filter = (AbstractFilter) fl.get(i);
            if (filter instanceof TrajectoryShapeFilter) {
                fl.remove((TrajectoryShapeFilter) filter);
            }
        }
        put(identifier, fl);
    }

    public void filterProbes(String identifier, Probes probes, boolean exclude) {
        FilterList fl = getFilterListForIdentifier(identifier);
        Set<Map.Entry<String, Probe>> entries = probes.entrySet();
        Iterator<Entry<String, Probe>> it = entries.iterator();
        while (it.hasNext()) {
            Entry<String, Probe> entry = it.next();
            Probe probe = entry.getValue();
            ProbeFilter filter = new ProbeFilter(probe.getID());
            if (exclude) {
                filter.exclude();
            }
            fl.add(filter);
        }
        put(identifier, fl);
    }

    public void filterPathways(String identifier, Pathways pathways, boolean exclude) {
        FilterList fl = getFilterListForIdentifier(identifier);
        Set<Map.Entry<Integer, Pathway>> pathESet = pathways.entrySet();
        Iterator<Map.Entry<Integer, Pathway>> it = pathESet.iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Pathway> entry = it.next();
            Pathway pathway = entry.getValue();
            PathwayFilter filter = new PathwayFilter(pathway);
            if (exclude) {
                filter.exclude();
            }
            fl.add(filter);
        }
        put(identifier, fl);
    }

    public void filterGOTerms(String identifier, GOTerms goTerms, boolean exclude) {
        FilterList fl = getFilterListForIdentifier(identifier);
        Set<Map.Entry<String, GOTerm>> pathESet = goTerms.entrySet();
        Iterator<Map.Entry<String, GOTerm>> it = pathESet.iterator();
        while (it.hasNext()) {
            Map.Entry<String, GOTerm> entry = it.next();
            GOTerm goTerm = entry.getValue();
            GOTermFilter filter = new GOTermFilter(goTerm);
            if (exclude) {
                filter.exclude();
            }
            fl.add(filter);
        }
        put(identifier, fl);
    }

    public void filterLabel(String identifier, Label label, boolean exclude) {
        FilterList fl = getFilterListForIdentifier(identifier);

        LabelFilter filter = new LabelFilter(label);
        if (exclude) {
            filter.exclude();
        }
        fl.add(filter);

        put(identifier, fl);
    }

    public void filterPathway(String identifier, Pathway pathway, boolean exclude) {
        FilterList fl = getFilterListForIdentifier(identifier);

        PathwayFilter filter = new PathwayFilter(pathway);
        if (exclude) {
            filter.exclude();
        }
        fl.add(filter);

        put(identifier, fl);
    }

    public void filterLabels(String identifier, Labels labels, boolean exclude) {
        FilterList fl = getFilterListForIdentifier(identifier);

        Set<Map.Entry<Integer, Label>> labelESet = labels.entrySet();
        Iterator<Map.Entry<Integer, Label>> it = labelESet.iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Label> entry = it.next();
            Label label = entry.getValue();
            LabelFilter filter = new LabelFilter(label);
            if (exclude) {
                filter.exclude();
            }
            fl.add(filter);
        }
        put(identifier, fl);
    }

    public void addFilter(String identifier, AbstractFilter filter) {
        FilterList filterList = get(identifier);
        if (filterList == null || filterList.size() == 0) {
            filterList = new FilterList();
        }
        filterList.add(filter);
        put(identifier, filterList);
    }

    public static ProbeFilter makeProbeIDFilter(String id, boolean exluded) {
        ProbeFilter filter = new ProbeFilter(id);
        if (exluded) {
            filter.exclude();
        }
        return filter;
    }//    public static LabelFilter makeAnnotationIDFilter(int id) {
//        LabelFilter filter = new LabelFilter(id);
//        return filter;
//    }
    public static final int blankShapeValue = -1111;

    public void removeFilters(String identifier) {
        put(identifier, new FilterList());
    // Un-load any loaded snap shot file
//        XMLSnapShotFactory snapFact = XMLSnapShotFactory.getUniqueInstance();
//        snapFact.unload();
    }

    public boolean removeFilter(String identifier, int filterIndex) {
        FilterList fl = getFilterListForIdentifier(identifier);
        if (fl != null && fl.size() > 0) {
            Iterator<IFilter> it = fl.iterator();
            while (it.hasNext()) {
                IFilter filt = it.next();
                if (filt.getListIndex() == filterIndex) {
                    fl.remove(filt);
                    return true;
                }
            }
        } else {
            System.out.println("ERROR: Removing filter index " + filterIndex + " from ArrayList " + fl);
        }
        return false;
    }

    /**
     * Creates an XML document representation of the filter list
     */
    public void getXML(String identifier, String fileName) {
        System.out.println("Creating filter SnapShot called: " + fileName);
        // Start
        DOMImplementation domImpl = new DOMImplementationImpl();
        Document doc = domImpl.createDocument(null, "SnapShot", null);
        Element root = doc.getDocumentElement();

        Element probes = doc.createElement("ProbeIDs");
        Element nodes = doc.createElement("NodeIDs");
        Element groupedProbes = doc.createElement("GroupedIDs");
        Element shapes = doc.createElement("Shapes");


//        XMLTrajectoryFactory fact = XMLTrajectoryFactory.getUniqueInstance();
//        TrajectoryDocument td = fact.getTrajectoryDocument();

        FilterList filterList = get(identifier);

        int filterCount = 0;

        Iterator filterIterator = filterList.iterator();
        while (filterIterator.hasNext()) {
            AbstractFilter filter = (AbstractFilter) filterIterator.next();
            if (filter instanceof ProbeFilter) {
                ProbeFilter specificFilter = (ProbeFilter) filter;
                Element filterElement = doc.createElement("Probe");
                filterElement.setAttribute("ID", "" + specificFilter.getAttributeID());
                filterElement.setAttribute("excluded", "" + specificFilter.isExcluded());
                filterElement.setAttribute("active", "" + specificFilter.isActive());
                probes.appendChild(filterElement);
            } else if (filter instanceof TrajNodeFilter) {
                TrajNodeFilter specificFilter = (TrajNodeFilter) filter;
                Element filterElement = doc.createElement("Node");
                filterElement.setAttribute("ID", "" + specificFilter.getAttributeID());
                filterElement.setAttribute("timePeriod", "" + specificFilter.getTimePeriod());
                filterElement.setAttribute("excluded", "" + specificFilter.isExcluded());
                filterElement.setAttribute("active", "" + specificFilter.isActive());
                nodes.appendChild(filterElement);
            } else if (filter instanceof MultiProbeFilter) {
                // Covers Pathway, Annoation and Cluster
                MultiProbeFilter specificFilter = (MultiProbeFilter) filter;
                Element filterElement = doc.createElement(specificFilter.getType());
                filterElement.setAttribute("ID", "" + specificFilter.getID());
                filterElement.setAttribute("excluded", "" + specificFilter.isExcluded());
                filterElement.setAttribute("active", "" + specificFilter.isActive());
                specificFilter.appendFilterSpecificAttributes(filterElement);
                groupedProbes.appendChild(filterElement);
            } else if (filter instanceof TrajectoryShapeFilter) {
                TrajectoryShapeFilter specificFilter = (TrajectoryShapeFilter) filter;
                Element filterElement = doc.createElement("Shape");

                int[][] shape = specificFilter.getShape();

//                int maxBin = td.getMaximumSubtractiveDegree();

//                for (int i = 0; i < shape.length; i++) {
//                    int[] timePeriod = shape[i];
//
//                    Element tpElement = doc.createElement("TimePeriod");
//                    tpElement.setAttribute("ID", String.valueOf(i + 1));
//
//                    for (int j = 0; j < timePeriod.length; j++) {
//                        int value = timePeriod[j];
//                        if (value != FilterManager.blankShapeValue) {
//
//                            Element cond = doc.createElement("Condition");
//                            cond.setAttribute("move", String.valueOf(maxBin - (timePeriod.length - j) + 1));
//                            tpElement.appendChild(cond);
//                        }
//                    }
//                    filterElement.appendChild(tpElement);
//                }

                filterElement.setAttribute("excluded", "" + specificFilter.isExcluded());
                filterElement.setAttribute("active", "" + specificFilter.isActive());
                shapes.appendChild(filterElement);
            }
            filterCount++;
        }

        // TODO: Can get traj and probe count from file + leafFiler...
//        Node rootNode = td.getRootNode();
//        NodeList trajectoryNodes = TrajectoryDocument.getTrajectories(rootNode);
//        int trajCount = trajectoryNodes.getLength();
//
//        Probes probeNodes = TrajectoryDocument.getProbes(rootNode);
//        int probeCount = probeNodes.size();
//
//        Element summary = doc.createElement("SnapShotSummary");
//        // Node count
//        Element nodeCountTotal = doc.createElement("ParentFile");
//        nodeCountTotal.setAttribute("Value", fact.getCurrentFile());
//        summary.appendChild(nodeCountTotal);
//        // Parent vote
//        Element parentFileFull = doc.createElement("ParentFileFull");
//        parentFileFull.setAttribute("Value", fact.getCurrentFileLocation());
//        summary.appendChild(parentFileFull);
        // File Name
//        Element fileNameElement = doc.createElement("File");
//        fileNameElement.setAttribute("Value", fileName);
//        summary.appendChild(fileNameElement);
//        // Probe count
//        Element probeCountTotal = doc.createElement("Probes");
//        probeCountTotal.setAttribute("Value", String.valueOf(probeCount));
//        summary.appendChild(probeCountTotal);
//        // Trajectory count
//        Element trajectoryCountTotal = doc.createElement("Trajectories");
//        trajectoryCountTotal.setAttribute("Value", String.valueOf(trajCount));
//        summary.appendChild(trajectoryCountTotal);
//        // Filter count
//        Element filterCountTotal = doc.createElement("Filters");
//        filterCountTotal.setAttribute("Value", String.valueOf(filterCount));
//        summary.appendChild(filterCountTotal);
//
//        root.appendChild(summary);
        root.appendChild(probes);
        root.appendChild(nodes);
        root.appendChild(groupedProbes);
        root.appendChild(shapes);

//        String filename = fact.getChildFileRoot() + "/" + fileName + ".xml";

//        XMLFileCreator.writeXmlFile(doc, filename);
    }
}
