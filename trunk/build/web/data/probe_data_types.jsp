<%@page import="com.sfsu.xmas.dao.*"%>
<%@page import="com.sfsu.xmas.data_sets.*"%>
<%@page import="com.sfsu.xmas.data_structures.*"%>
<%@page import="com.sfsu.xmas.data_structures.expression.*"%>
<%@page import="com.sfsu.xmas.data_structures.knowledge.*"%>
<%@page import="com.sfsu.xmas.data_structures.knowledge.probe_data.*"%>
<%@page import="java.util.*"%>


<p class="block_summary">
    Probe data types contain supplementary information about probes such as descriptions, identifiers and chromosome locations. Every probe data type can be assigned a link pattern, which activates probe data type values within the interface, providing entry points to analysis within other online resources.
</p>
<p class="block_summary">
    Probe Data Type link patterns must contain the following wildcard string '<b style="font-family: courier;"><%= ProbeDataTypes.PROBE_DATA_TYPE_VALUE_IDENTIFIER%></b>', which will be replaced by actual probe data type values.
</p>

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

                ProbeDataTypes pDTs = db.getProbeDataTypes();
                int dataSize = 0;
                if (pDTs != null) {
                    dataSize = pDTs.size();
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
        No data types in this Knowledge Library
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
        There <%= isAre%> <%= dataSize%> data type<%= ess%> in this library.
    </p>
    
    <table class="window-large_quant_data">
        <thead>
            <tr>
                <th>Name</th>
                <th>Link Pattern</th>
                <th />
            </tr>
        </thead>
        <tbody>
            <%

        int entryIndex = 0;

        Iterator<Integer> it = pDTs.keySet().iterator();
        while (it.hasNext()) {
            int attributeID = it.next();
            ProbeDataType type = pDTs.get(attributeID);

            String rowClass = "odd";
            if (entryIndex % 2 == 0) {
                rowClass = "even";
            }

            String link = "http://";
            boolean isLinked = type.isLinked();
            if (isLinked) {
                link = type.getLink();
            }

            %>
            <tr class="<%= rowClass%>">
                <td>
                    <input type="text" name="probe_data_type" disabled value="<%= type.getAttribute()%>" />
                </td>
                <td>
                    <input type="text" name="probe_data_type_link_<%= type.getID()%>" id="probe_data_type_link_<%= type.getID()%>" size="35" value="<%= link%>" />
                </td>
                <td>
                    <%
                        if (isLinked) {
                    %>
                    <a href="javascript:void(0)" onclick="return add_or_update_attribute_link( '<%= type.getID()%>', '<%= db.getID()%>' )" class="button"><span>Update Link</span></a>
                    <%                    } else {
                    %>
                    <a href="javascript:void(0)" onclick="return add_or_update_attribute_link( '<%= type.getID()%>', '<%= db.getID()%>' )" class="button"><span>Assign Link</span></a>
                    <%                            }
                    %>
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