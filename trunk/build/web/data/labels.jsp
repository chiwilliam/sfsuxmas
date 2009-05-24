<%@page import="com.sfsu.xmas.dao.*"%>
<%@page import="com.sfsu.xmas.data_sets.*"%>
<%@page import="com.sfsu.xmas.data_structures.*"%>
<%@page import="com.sfsu.xmas.data_structures.expression.*"%>
<%@page import="com.sfsu.xmas.data_structures.knowledge.*"%>
<%@page import="java.util.*"%>

<%
            int dataSetID = -1;
            try {
                dataSetID = Integer.parseInt(request.getParameter("data_id"));
            } catch (NumberFormatException ex) {
%>
<p class="error">
    Error: Non numeric or null data ID for attribute: "data_id"
</p>
<%            }
            if (dataSetID >= 0) {
                KnowledgeDataSet db;
                db = KnowledgeDataSetFactory.getUniqueInstance().getDataSet(dataSetID, true);

                Labels labels = db.getLabels();
                int dataSize = 0;
                if (labels != null) {
                    dataSize = labels.size();
                }
%>

<div class="corners_bar">
    <div class="corners_top_left"></div>
    <div class="corners_top_right"></div>
    <div class="corners_connector_bar"></div>
</div>

<div class="sidebar_content_holder">
    <div class="corners_bar">
        <div class="corners_inner_top_left"></div>
        <div class="corners_inner_top_right"></div>
        <div class="corners_inner_connector_bar"></div>
    </div>
    
    
    <%
    if (dataSize <= 0) {
    %>
    <div class="no_data">
        No Labels in this Knowledge Library
    </div>
    <%    } else {
        String ess = "s";
        String isAre = "are";
        if (dataSize == 1) {
            ess = "";
            isAre = "is";
        }
    %>
    
    <p class="table_summary">
        There <%= isAre%> <%= dataSize%> label<%= ess%> in this library.
    </p>
    
    <table class="window-large_quant_data">
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Description</th>
                <th># Member Probes</th>
            </tr>
        </thead>
        <tbody>
            <%

        int entryIndex = 0;

        Set<Map.Entry<Integer, Label>> labelESet = labels.entrySet();
        Iterator<Map.Entry<Integer, Label>> it = labelESet.iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Label> entry = it.next();
            Label lab = entry.getValue();
            String rowClass = "odd";
            if (entryIndex % 2 == 0) {
                rowClass = "even";
            }
            %>
            <tr class="<%= rowClass%>" id="label_row_<%= lab.getID()%>">
                <td>
                    <%= lab.getID()%>
                </td>
                <td>
                    <%= lab.getName()%>
                </td>
                <td>
                    <%= lab.getDescription()%>
                </td>
                <td>
                    <%= lab.getNumberOfMembers()%>
                </td>
            </tr>
            <%
            entryIndex++;
        }%>
        </tbody>
    </table>
    <% }%>
    <div class="corners_bar">
        <div class="corners_inner_bottom_left"></div>
        <div class="corners_inner_bottom_right"></div>
        <div class="corners_inner_connector_bar"></div>
    </div>
</div>
<div class="corners_bar">
    <div class="corners_bottom_left"></div>
    <div class="corners_bottom_right"></div>
    <div class="corners_connector_bar"></div>
</div>
<%
            }
%>