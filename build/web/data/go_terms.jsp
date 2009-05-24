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

                GOTerms goTerms = db.getGOTerms();
                int dataSize = 0;
                if (goTerms != null) {
                    dataSize = goTerms.size();
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
        No GO Terms in this Knowledge Library
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
        There <%= isAre%> <%= dataSize%> GO terms<%= ess%> in this library.
    </p>
    
    <table class="window-large_quant_data">
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th># Member Probes</th>
            </tr>
        </thead>
        <tbody>
            <%

        int entryIndex = 0;

        Set<Map.Entry<String, GOTerm>> pathESet = goTerms.entrySet();
        Iterator<Map.Entry<String, GOTerm>> it = pathESet.iterator();
        while (it.hasNext()) {
            Map.Entry<String, GOTerm> entry = it.next();
            GOTerm goTerm = entry.getValue();
            String rowClass = "odd";
            if (entryIndex % 2 == 0) {
                rowClass = "even";
            }
            %>
            <tr class="<%= rowClass%>" id="label_row_<%= goTerm.getStringID()%>">
                <td>
                    <%= goTerm.getStringID()%>
                </td>
                <td>
                    <%= goTerm.getName()%>
                </td>
                <td>
                    <%= goTerm.getDescription()%>
                </td>
                <td>
                    <%= goTerm.getNumberOfMembers()%>
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