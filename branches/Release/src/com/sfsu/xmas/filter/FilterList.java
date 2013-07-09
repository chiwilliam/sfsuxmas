package com.sfsu.xmas.filter;

import com.sfsu.xmas.dao.CapturedAnalysisDAO;
import filter.*;
import java.util.ArrayList;
import java.util.Iterator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class FilterList extends ArrayList<IFilter> {

    // TODO: shouldn't require these here
    protected static final String mStringAnd = " and ";
    protected static final String mStringOr = " or ";

    public void excludeAll() {
        FilterList fl = getActiveFilters();
        // For each active filter
        Iterator<IFilter> it = fl.iterator();
        while (it.hasNext()) {
            ((AbstractFilter) it.next()).exclude();
        }
    }

    public TrajectoryShapeFilter getCurrentTrajectoryFilter() {
        FilterList fl = getActiveFilters();
        // For each active filter
        for (int i = 0; i < fl.size(); i++) {
            AbstractFilter filter = (AbstractFilter) fl.get(i);
            if (filter instanceof TrajectoryShapeFilter) {
                return (TrajectoryShapeFilter) filter;
            }
        }
        return null;
    }

    /**
     * 
     * @return - A string representastion of an XPath which can be applied to a TrajectoryDocument
     */
    public String getFullXPath(int numberOfTimePeriods) {
        StringBuffer filterXPath = new StringBuffer();
        filterXPath.append(getTrajectoryXPathSnippet(numberOfTimePeriods));
        filterXPath.append(getProbeIDXPathSnippet());

        return filterXPath.toString();
    }

    public String getTrajectoryXPathSnippet(int numberOfTimePeriods) {
        StringBuffer filterXPath = new StringBuffer();
        filterXPath.append(".");
//        int lowestFilterLevel = 0;
//        boolean filterInPreviousLevel = true;
        // Make this scale to samples
        for (int j = 1; j <= numberOfTimePeriods; j++) {
            String nodeAttributeConditions = "";
            if (j > 0) {
                nodeAttributeConditions = getXPathForNode(j);
            }
            if (nodeAttributeConditions.length() > 0) {
                filterXPath.append("/TP" + j);

                filterXPath.append("[" + nodeAttributeConditions + "]");
            } else {
                filterXPath.append("/TP" + j);
            }
        }
        return filterXPath.toString();
    }

    public String getTrajectoryXPath(int numberOfTimePeriods) {
        return getFullXPath(numberOfTimePeriods) + "/..";
    }

    public Node toXML(Document doc) {
        Element filters = doc.createElement(CapturedAnalysisDAO.XML_KEY_FILTERS);

        FilterList fl = getProbeFilters();
        Element probes = doc.createElement(CapturedAnalysisDAO.XML_KEY_PROBES);
        boolean probesActive = false;
        for (int i = 0; i < fl.size(); i++) {
            ProbeFilter filter = (ProbeFilter) fl.get(i);
            if (filter.isActive()) {
                probesActive = true;
                probes.appendChild(filter.toXML(doc));
            }
        }
        if (probesActive) {
            filters.appendChild(probes);
        }

        fl = getLabelFilters();
        Element labels = doc.createElement(CapturedAnalysisDAO.XML_KEY_LABELS);
        boolean labelsActive = false;
        for (int i = 0; i < fl.size(); i++) {
            LabelFilter filter = (LabelFilter) fl.get(i);
            if (filter.isActive()) {
                labelsActive = true;
                labels.appendChild(filter.toXML(doc));
            }
        }
        if (labelsActive) {
            filters.appendChild(labels);
        }

        fl = getPathwayFilters();
        Element pathways = doc.createElement(CapturedAnalysisDAO.XML_KEY_PATHWAYS);
        boolean pathwaysActive = false;
        for (int i = 0; i < fl.size(); i++) {
            PathwayFilter filter = (PathwayFilter) fl.get(i);
            if (filter.isActive()) {
                pathwaysActive = true;
                pathways.appendChild(filter.toXML(doc));
            }
        }
        if (pathwaysActive) {
            filters.appendChild(pathways);
        }

        fl = getTrajectoryShapeFilters();
        Element shape = doc.createElement("shape");
        boolean shapeActive = false;
        for (int i = 0; i < fl.size(); i++) {
            TrajectoryShapeFilter filter = (TrajectoryShapeFilter) fl.get(i);
            if (filter.isActive()) {
                shapeActive = true;
                shape.appendChild(filter.toXML(doc));
            }
        }
        if (shapeActive) {
            filters.appendChild(shape);
        }
        return filters;
    }

    /**
     * 
     * @param j - The time period of the node
     * @return String representation of the XPath filter to be applied to the given node
     */
    private String getXPathForNode(int timePeriod) {
        StringBuffer nodeAttributeConditions = new StringBuffer();

        // Node ID conditions
        String nodeFilters = getNodeIDFiltersForTimePeriod(timePeriod);
        // Node value conditions
        String shapeFilters = getTrajectoryShapeFiltersForTimePeriod(timePeriod);

        if (nodeFilters.length() > 0 && shapeFilters.length() > 0) {
            // Both types of filter
            nodeAttributeConditions.append("(" + nodeFilters + ") and (" + shapeFilters + ")");
        } else if (nodeFilters.length() > 0 && shapeFilters.length() <= 0) {
            // Only node ID filter(s)
            nodeAttributeConditions.append("(" + nodeFilters + ")");
        } else if (nodeFilters.length() == 0 && shapeFilters.length() > 0) {
            // Only value condition(s)
            nodeAttributeConditions.append("(" + shapeFilters + ")");
        }

        return nodeAttributeConditions.toString();
    }

    public FilterList getProbeFilters() {
        FilterList probeFilters = new FilterList();
        FilterList fl = getActiveFilters();
        // For each active filter
        for (int i = 0; i < fl.size(); i++) {
            AbstractFilter absFilter = (AbstractFilter) fl.get(i);
            if (absFilter instanceof ProbeFilter) {
                ProbeFilter filter = (ProbeFilter) absFilter;
                filter.setListIndex(i);
                probeFilters.add(filter);
            }
        }
        return probeFilters;
    }

    /**
     * 
     * @return An ArrayList of probe IDs for all the probes isolated by this FilterList
     */
    public ArrayList<String> getProbeFilterIDs() {
        ArrayList<String> filteredProbeIDs = new ArrayList<String>();

        for (int i = 0; i < size(); i++) {
            AbstractFilter filter = (AbstractFilter) super.get(i);
            // Only interested in active filters
            if (filter.isActive() && !filter.isExcluded()) {
                if (filter instanceof ProbeFilter) {
                    ProbeFilter probeFilter = (ProbeFilter) filter;
                    filteredProbeIDs.add(probeFilter.getAttributeID());
                }
            }
        }
        return filteredProbeIDs;
    }

    public FilterList getLabelFilters() {
        FilterList labelFilters = new FilterList();
        FilterList fl = getActiveFilters();
        // For each active filter
        for (int i = 0; i < fl.size(); i++) {
            AbstractFilter absFilter = (AbstractFilter) fl.get(i);
            if (absFilter instanceof LabelFilter) {
                LabelFilter filter = (LabelFilter) absFilter;
                filter.setListIndex(i);
//                if (!filter.isExcluded()) {
                labelFilters.add(filter);
//                }
            }
        }
        return labelFilters;
    }

    public ArrayList<Integer> getFilteredLabels() {
        ArrayList<Integer> labelIDs = new ArrayList<Integer>();
        FilterList labelFilters = getLabelFilters();
        // For each active filter
        Iterator<IFilter> it = labelFilters.iterator();
        while (it.hasNext()) {
            AbstractFilter absFilter = (AbstractFilter) it.next();
            if (absFilter instanceof LabelFilter) {
                LabelFilter filter = (LabelFilter) absFilter;
//                if (!filter.isExcluded()) {
                labelIDs.add(filter.getLabel().getID());
//                }
            }
        }
        return labelIDs;
    }

    public FilterList getPathwayFilters() {
        FilterList probeFilters = new FilterList();
        FilterList fl = getActiveFilters();
        // For each active filter
        for (int i = 0; i < fl.size(); i++) {
            AbstractFilter absFilter = (AbstractFilter) fl.get(i);
            if (absFilter instanceof PathwayFilter) {
                PathwayFilter filter = (PathwayFilter) absFilter;
                filter.setListIndex(i);
//                if (!filter.isExcluded()) {
                probeFilters.add(filter);
//                }
            }
        }
        return probeFilters;
    }

    public FilterList getGOTermFilters() {
        FilterList goTermFilters = new FilterList();
        FilterList fl = getActiveFilters();
        // For each active filter
        for (int i = 0; i < fl.size(); i++) {
            AbstractFilter absFilter = (AbstractFilter) fl.get(i);
            if (absFilter instanceof GOTermFilter) {
                GOTermFilter filter = (GOTermFilter) absFilter;
                filter.setListIndex(i);
//                if (!filter.isExcluded()) {
                goTermFilters.add(filter);
//                }
            }
        }
        return goTermFilters;
    }

    public ArrayList<Integer> getFilteredPathways() {
        ArrayList<Integer> pathwayIDs = new ArrayList<Integer>();
        FilterList pathFilters = getPathwayFilters();
        // For each active filter
        Iterator<IFilter> it = pathFilters.iterator();
        while (it.hasNext()) {
            AbstractFilter absFilter = (AbstractFilter) it.next();
            if (absFilter instanceof PathwayFilter) {
                PathwayFilter filter = (PathwayFilter) absFilter;
//                if (!filter.isExcluded()) {
                pathwayIDs.add(filter.getLabel().getID());
//                }
            }
        }
        return pathwayIDs;
    }

    public ArrayList<String> getFilteredGOTerms() {
        ArrayList<String> goTermIDs = new ArrayList<String>();
        FilterList goTermFilters = getGOTermFilters();
        // For each active filter
        Iterator<IFilter> it = goTermFilters.iterator();
        while (it.hasNext()) {
            AbstractFilter absFilter = (AbstractFilter) it.next();
            if (absFilter instanceof GOTermFilter) {
                GOTermFilter filter = (GOTermFilter) absFilter;
//                if (!filter.isExcluded()) {
                goTermIDs.add(filter.getLabel().getStringID());
//                }
            }
        }
        return goTermIDs;
    }

    public String getProbeFiltersString() {
        StringBuffer nodeAttributeConditionsInclude = new StringBuffer();
        StringBuffer nodeAttributeConditionsExclude = new StringBuffer();

        int includedIndex = 0;
        int excludedIndex = 0;

        FilterList fl = getActiveFilters();
        // For each active filter
        for (int i = 0; i < fl.size(); i++) {
            AbstractFilter filter = (AbstractFilter) fl.get(i);
            if (filter instanceof ProbeFilter) {
                ProbeFilter probeFilter = (ProbeFilter) filter;
                if (probeFilter.isExcluded()) {
                    if (excludedIndex > 0) {
                        nodeAttributeConditionsExclude.append(mStringAnd);
                    }
                    nodeAttributeConditionsExclude.append(probeFilter.getXPath());
                    excludedIndex++;
                } else {
                    if (includedIndex > 0) {
                        nodeAttributeConditionsInclude.append(mStringOr);
                    }
                    nodeAttributeConditionsInclude.append(probeFilter.getXPath());
                    includedIndex++;
                }
            } else if (filter instanceof PathwayFilter) {
                PathwayFilter pf = (PathwayFilter) filter;
                FilterList pathwayProbeFilterList = pf.getFilterList();
                if (pf.isExcluded()) {
                    if (excludedIndex > 0) {
                        nodeAttributeConditionsExclude.append(mStringAnd);
                    }
                    nodeAttributeConditionsExclude.append(pathwayProbeFilterList.getProbeFiltersString());
                    excludedIndex++;
                } else {
                    if (includedIndex > 0) {
                        nodeAttributeConditionsInclude.append(mStringOr);
                    }
                    nodeAttributeConditionsInclude.append(pathwayProbeFilterList.getProbeFiltersString());
                    includedIndex++;
                }
            } else if (filter instanceof LabelFilter) {
                LabelFilter pf = (LabelFilter) filter;
                FilterList labelFilterList = pf.getFilterList();
                if (pf.isExcluded()) {
                    if (excludedIndex > 0) {
                        nodeAttributeConditionsExclude.append(mStringAnd);
                    }
                    nodeAttributeConditionsExclude.append(labelFilterList.getProbeFiltersString());
                    excludedIndex++;
                } else {
                    if (includedIndex > 0) {
                        nodeAttributeConditionsInclude.append(mStringOr);
                    }
                    nodeAttributeConditionsInclude.append(labelFilterList.getProbeFiltersString());
                    includedIndex++;
                }
            } else if (filter instanceof ClusterFilter) {
                ClusterFilter pf = (ClusterFilter) filter;
                FilterList clusterProbeFilterList = pf.getFilterList();
                if (pf.isExcluded()) {
                    if (excludedIndex > 0) {
                        nodeAttributeConditionsExclude.append(mStringAnd);
                    }
                    nodeAttributeConditionsExclude.append(clusterProbeFilterList.getProbeFiltersString());
                    excludedIndex++;
                } else {
                    if (includedIndex > 0) {
                        nodeAttributeConditionsInclude.append(mStringOr);
                    }
                    nodeAttributeConditionsInclude.append(clusterProbeFilterList.getProbeFiltersString());
                    includedIndex++;
                }
            }
        }

        if (nodeAttributeConditionsExclude.length() > 0) {
            nodeAttributeConditionsExclude = new StringBuffer(
                    "(" +
                    nodeAttributeConditionsExclude.toString() +
                    ")");
        }

        if (nodeAttributeConditionsInclude.length() > 0) {
            nodeAttributeConditionsInclude = new StringBuffer(
                    "(" +
                    nodeAttributeConditionsInclude.toString() +
                    ")");
        }

        if (nodeAttributeConditionsExclude.length() > 0 && nodeAttributeConditionsInclude.length() > 0) {
            return nodeAttributeConditionsExclude.toString() + mStringAnd + nodeAttributeConditionsInclude.toString();
        } else {
            return nodeAttributeConditionsExclude.toString() + nodeAttributeConditionsInclude.toString();
        }
    }

    /**
     * 
     * @return An ArrayList of probe IDs for all the probes isolated by this FilterList
     */
    public ArrayList<String> getProbeIsolationFilters() {
        ArrayList<String> filteredProbeIDs = getProbeFilterIDs();

        if (filteredProbeIDs != null && filteredProbeIDs.size() > 0) {
            ArrayList<String> filtersToRemove = new ArrayList<String>();
            for (int i = 0; i < size(); i++) {
                AbstractFilter filter = (AbstractFilter) super.get(i);
                // Only interested in active filters
                if (filter.isActive() && !filter.isExcluded()) {
                    if (filter instanceof MultiProbeFilter) {
                        MultiProbeFilter multiProbeFilter = (MultiProbeFilter) filter;
                        FilterList multiProbeFilterList = multiProbeFilter.getFilterList();
                        ArrayList<String> multiProbeIDs = multiProbeFilterList.getProbeIsolationFilters();

                        Iterator<String> it = filteredProbeIDs.iterator();
                        while (it.hasNext()) {
                            String id = it.next();
                            if (!multiProbeIDs.contains(id)) {
                                // Mullti Data Struct doesn't contain isolated probe, remove
                                filtersToRemove.add(id);
                            }
                        }
                    }
                }
            }

            Iterator<String> it = filtersToRemove.iterator();
            while (it.hasNext()) {
                String id = it.next();
                if (filteredProbeIDs.contains(id)) {
                    // Mullti Data Struct doesn't contain isolated probe, remove
                    filteredProbeIDs.remove(id);
                }
            }
        } else {
            // No probe filters
            filteredProbeIDs = new ArrayList<String>();

            ArrayList<String> filtersToRemove = new ArrayList<String>();
            for (int i = 0; i < size(); i++) {
                AbstractFilter filter = (AbstractFilter) super.get(i);
                // Only interested in active filters
                if (filter.isActive() && !filter.isExcluded()) {
                    if (filter instanceof MultiProbeFilter) {
                        MultiProbeFilter multiProbeFilter = (MultiProbeFilter) filter;
                        if (filteredProbeIDs.size() <= 0) {
                            // First multi probe we've encountered
                            filteredProbeIDs = multiProbeFilter.getFilterList().getProbeIsolationFilters();
                        } else {
                            // We already have a multi probe
                            ArrayList<String> multiProbeIDs = multiProbeFilter.getFilterList().getProbeIsolationFilters();
                            Iterator<String> it = filteredProbeIDs.iterator();
                            while (it.hasNext()) {
                                String id = it.next();
                                if (!multiProbeIDs.contains(id)) {
                                    // Mullti Data Struct doesn't contain isolated probe, remove
                                    filtersToRemove.add(id);
                                }
                            }
                        }
                    }
                }
            }
            Iterator<String> it = filtersToRemove.iterator();
            while (it.hasNext()) {
                String id = it.next();
                if (filteredProbeIDs.contains(id)) {
                    // Mullti Data Struct doesn't contain isolated probe, remove
                    filteredProbeIDs.remove(id);
                }
            }
        }
        return filteredProbeIDs;
    }

    /**
     * 
     * @return An ArrayList of probe IDs for all the probes exluded by this FilterList
     */
    public ArrayList<String> getProbeExclusionFilters() {
        ArrayList<String> filteredProbeIDs = new ArrayList<String>();

        for (int i = 0; i < size(); i++) {
            AbstractFilter filter = (AbstractFilter) super.get(i);
            // Only interested in active filters
            if (filter.isActive() && filter.isExcluded()) {
                if (filter instanceof ProbeFilter) {
                    ProbeFilter probeFilter = (ProbeFilter) filter;
                    filteredProbeIDs.add(probeFilter.getAttributeID());
                } else if (filter instanceof MultiProbeFilter) {
                    MultiProbeFilter multiProbeFilter = (MultiProbeFilter) filter;
                    FilterList multiProbeFilterList = multiProbeFilter.getFilterList();
                    filteredProbeIDs.addAll(multiProbeFilterList.getProbeExclusionFilters());
                }
            }
        }
        return filteredProbeIDs;
    }

    public boolean hasTrajFileBasedFilters() {
        FilterList fl = getActiveFilters();
        // For each active filter
        for (int i = 0; i < fl.size(); i++) {
            AbstractFilter filter = (AbstractFilter) fl.get(i);
            if (filter instanceof TrajectoryShapeFilter || filter instanceof TrajNodeFilter) {
                return true;
            }
        }
        return false;
    }

    public boolean hasProbeFilters() {
        FilterList fl = getActiveFilters();
        // For each active filter
        for (int i = 0; i < fl.size(); i++) {
            AbstractFilter filter = (AbstractFilter) fl.get(i);
            if (filter instanceof ProbeFilter) {
                return true;
            } else if (filter instanceof MultiProbeFilter) {
                return true;
            }
        }
        return false;
    }

    private String getNodeIDFiltersForTimePeriod(int j) {
        StringBuffer nodeAttributeConditions = new StringBuffer();

        int filtersOfThisType = 0;

        FilterList fl = getActiveFilters();
        // For each active filter
        for (int i = 0; i < fl.size(); i++) {
            AbstractFilter filter = (AbstractFilter) fl.get(i);
            if (filter instanceof TrajNodeFilter) {
                TrajNodeFilter trajNodeFilter = (TrajNodeFilter) filter;
                // Each TrajNodeFilter is assigned a time period to which it applies
                if (trajNodeFilter.getTimePeriod() == j) {
                    if (filtersOfThisType > 0) {
                        nodeAttributeConditions.append(mStringOr);
                    }
                    nodeAttributeConditions.append(trajNodeFilter.getXPath());
                    filtersOfThisType++;
                }
            }
        }
        return nodeAttributeConditions.toString();
    }

    private synchronized String getTrajectoryShapeFiltersForTimePeriod(int timePeriod) {
        StringBuffer nodeAttributeConditions = new StringBuffer();

        int numberOfConditions = 0;

        for (int i = 0; i < size(); i++) {
            AbstractFilter filter = (AbstractFilter) super.get(i);
            if (filter.isActive()) {
//                if (filter instanceof TrajectoryMagnitudeShapeFilter) {
//                    if (numberOfConditions > 0) {
//                        nodeAttributeConditions.append(mStringAnd);
//                    }
//                    TrajectoryMagnitudeShapeFilter trajNodeFilter = (TrajectoryMagnitudeShapeFilter) filter;
//                    String filtX = trajNodeFilter.getXPathForTimePeriod(timePeriod);
//                    if (filtX != null && filtX.length() > 0) {
//                        nodeAttributeConditions.append(filtX);
//                        numberOfConditions++;
//                    }
                if (filter instanceof TrajectoryShapeFilter) {
                    TrajectoryShapeFilter trajNodeFilter = (TrajectoryShapeFilter) filter;
                    String filtX = trajNodeFilter.getXPathForTimePeriod(timePeriod);
                    if (filtX != null && filtX.length() > 0) {
                        if (numberOfConditions > 0) {
                            nodeAttributeConditions.append(mStringAnd);
                        }
                        nodeAttributeConditions.append(filtX);
                        numberOfConditions++;
                    }
                }

            }
        }
        return nodeAttributeConditions.toString();
    }

    public MatchingProbesForFilterSet getIncludedProbeFiltersByIDs() {
        ArrayList<String> probes = getProbeIsolationFilters();
        MatchingProbesForFilterSet filteredProbes = new MatchingProbesForFilterSet();
        if (probes.size() > 0) {
            filteredProbes.add(probes);
        }
//        ArrayList<Probes> includedProbesets = getIncludedProbeFilters();
//        MatchingProbesForFilterSet includedPs = new MatchingProbesForFilterSet();
//        for (int ind = 0; ind < includedProbesets.size(); ind++) {
//            ArrayList<String> filterPIDs = new ArrayList<String>();
//            Probes filterProbes = includedProbesets.get(ind);
//            Set<String> keys = filterProbes.keySet();
//            Iterator<String> it = keys.iterator();
//            while (it.hasNext()) {
//                filterPIDs.add(it.next());
//            }
//            if (filterPIDs.size() > 0) {
//                includedPs.add(filterPIDs);
//            }
//        }
//        return includedPs;
        return filteredProbes;
    }

    public MatchingProbesForFilterSet getExcludedProbeFiltersByIDs() {
        ArrayList<String> probes = getProbeExclusionFilters();
        MatchingProbesForFilterSet filteredProbes = new MatchingProbesForFilterSet();
        if (probes.size() > 0) {
            filteredProbes.add(probes);
        }
//        for (int ind = 0; ind < excludedProbesets.length; ind++) {
//            ArrayList<String> filterPIDs = new ArrayList<String>();
//            Probes filterProbes = (Probes) excludedProbesets[ind];
//            for (int i = 0; i < filterProbes.size(); i++) {
//                filterPIDs.add(((Probe) filterProbes.get(i)).getID());
//            }
//            if (filterPIDs.size() > 0) {
//                excludedPs.add(filterPIDs);
//            }
//        }
        return filteredProbes;
    }

    public String getProbeIDXPathSnippet() {
        String path = "/Probe";
//        String geneIDFilters = getProbeFiltersString();
//        if (geneIDFilters.length() != 0) {
//            path += "[" + geneIDFilters + "]";
//        }
//        String leafFilters = getLeafFilters();
//        if (leafFilters.length() != 0) {
//            path += "/*[" + leafFilters + "]/..";
//        }

        return path;
    }

    private FilterList getActiveFilters() {
        FilterList fl = new FilterList();
        for (int i = 0; i < size(); i++) {
            AbstractFilter filter = (AbstractFilter) super.get(i);
            if (filter.isActive()) {
                fl.add(filter);
            }
        }
        return fl;
    }

    public FilterList getTrajectoryNodeFilters() {
        FilterList fl = new FilterList();
        FilterList allFilters = getActiveFilters();
        for (int i = 0; i < allFilters.size(); i++) {
            AbstractFilter absFilter = (AbstractFilter) allFilters.get(i);
            if (absFilter instanceof TrajNodeFilter) {
                absFilter.setListIndex(i);
                fl.add((TrajNodeFilter) absFilter);
            }
        }
        return fl;
    }

    public FilterList getTrajectoryShapeFilters() {
        FilterList fl = new FilterList();
        FilterList allFilters = getActiveFilters();
        for (int i = 0; i < allFilters.size(); i++) {
            AbstractFilter filt = (AbstractFilter) allFilters.get(i);
            if (filt instanceof TrajectoryShapeFilter) {
                filt.setListIndex(i);
                fl.add((TrajectoryShapeFilter) filt);
            }
        }
        return fl;
    }

//    public ArrayList<Integer> getFilteredPathways() {
//        ArrayList<Integer> fl = new ArrayList<Integer>();
//        FilterList allFilters = getActiveFilters();
//        for (int i = 0; i < allFilters.size(); i++) {
//            IFilter filt = (IFilter) allFilters.get(i);
//            if (filt instanceof PathwayFilter) {
//                PathwayFilter pfilt = (PathwayFilter) filt;
//                fl.add(pfilt.getID());
//            }
//        }
//        return fl;
//    }
    public void toXML() {
    }
}
