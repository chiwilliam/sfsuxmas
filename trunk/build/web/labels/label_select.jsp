<%@page import="com.sfsu.xmas.dao.*"%>
<%@page import="com.sfsu.xmas.data_sets.*"%>
<%@page import="com.sfsu.xmas.data_structures.knowledge.*"%>
<%@page import="com.sfsu.xmas.data_structures.knowledge.probe_data.*"%>
<%@page import="com.sfsu.xmas.session.SessionAttributeManager"%>
<%@page import="java.util.*"%>
<%

            KnowledgeDataSet labelKDB = SessionAttributeManager.getActiveKnowledgeLibrary(request);
            Labels labelLabels = null;
            if (labelKDB != null) {
                labelLabels = labelKDB.getLabels();
            }

%>
<select name="label_id" id="label_id" style="width: 110px;">
    <option value="X">New Label...</option>
    <%
            Set<Map.Entry<Integer, Label>> labelSet = labelLabels.entrySet();
            Iterator<Map.Entry<Integer, Label>> labIt = labelSet.iterator();
            while (labIt.hasNext()) {
                Map.Entry<Integer, Label> lab = labIt.next();
                Label realLabel = lab.getValue();
                %>
    <option value="<%= realLabel.getID() %>" style=""><%= realLabel.getName() %></option>
                <%
            }
    %>
</select>