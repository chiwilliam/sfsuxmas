<%@page import="com.sfsu.xmas.dao.*"%>
<%@page import="com.sfsu.xmas.data_sets.*"%>
<%@page import="com.sfsu.xmas.data_structures.knowledge.*"%>
<%@page import="com.sfsu.xmas.session.*"%>
<%@page import="com.sfsu.xmas.servlet.*"%>
<%@page import="java.io.File"%>
<%
            DataSetDAO diDataSetDA = DAOFactoryFactory.getUniqueInstance().getDatabaseDAOFactory().getDataSetDAO();
            DataSetList dbs = diDataSetDA.getKnowledgeDataSets();
            if (dbs != null && dbs.size() > 0) {
                boolean admin = SessionAttributeManager.isAdmin(request);
%>
<table class="base_table" style="width: 95%;">
    <thead>
        <tr>
            <th>Load</th>
            <th>ID</th>
            <th>Name</th>
            <th>Probe Data Types</th>
            <th>Probes with Data</th>
            <th>Labels</th>
            <th>Pathways</th>
            <th>GO Terms</th>
            <% if (admin) {%>
            <th />
            <% }%>
        </tr>
    </thead>
    <tbody>
        <%
    KnowledgeDataSet activeKDatabase = SessionAttributeManager.getActiveKnowledgeLibrary(request);
    for (int i = 0; i < dbs.size(); i++) {
        KnowledgeDataSet db = (KnowledgeDataSet) dbs.get(i);
        String currentDBName = db.getName();

        String rowClass = "odd";
        if (i % 2 == 0) {
            rowClass = "even";
        }
        %>
        <tr id="database_row_<%= i%>" class="<%= rowClass%>">
            <%
            boolean isActiveDB = (activeKDatabase != null && db.getID() == activeKDatabase.getID());
            String highlight = "";
            if (isActiveDB) {
                highlight = "highlighted";
            }
            %>
            <td class="<%= highlight%>" rowspan="2">
                <%
            if (isActiveDB) {
                %>
                <input type="button" onclick="return unload_knowledge_database( false )" value="Un-load" />
                <%                    } else {
                %>
                <input type="button" onclick="return load_knowledge_database('<%= db.getID()%>')" value="Load" />
                <%
            }
                %>
            </td>
            <td>
                <%= db.getID()%>
            </td>
            <td style="font-size: 1.1em;">
                <b><a href="../data/knowledge_database.jsp?data_id=<%= db.getID()%>" style="text-decoration: none;"><%= db.getName()%></a></b>
            </td>
            <td>
                <%= db.getNumberOfProbeAttributes()%>
            </td>
            <td>
                <%= db.getNumberOfProbesWithData()%>
            </td>
            <td>
                <%= db.getNumberOfLabels() %>
            </td>
            <td>
                <%= db.getNumberOfPathways() %>
            </td>
            <td>
                <%= db.getNumberOfGOTerms() %>
            </td>
            <% if (admin) {%>
            <td rowspan="2">
                <a href="javascript: void(0);" onclick="verify_delete('<%= "../SDropDatabase?database_id=" + String.valueOf(db.getID()) + "&library=true"%>', true);">Delete</a>
            </td>
            <% }%>
        </tr>
        <tr class="<%= rowClass%>">
            <td colspan="7">
                <span style="float: right;">Creation Date: <i><b><%= db.getCreationDate()%></b></i></span>
                <b>Description:</b><br />
                <%= db.getDescription()%>
            </td>
        </tr>
        <%
    }

        %>
    </tbody>
</table>
<%
} else {
%>
<p class="info_message">
    No Knowledge Libraries found. Please try refreshing this page.
</p>
<%            }
%>