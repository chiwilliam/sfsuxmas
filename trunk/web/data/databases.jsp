<%@page import="com.sfsu.xmas.dao.*"%>
<%@page import="com.sfsu.xmas.data_sets.*"%>
<%@page import="com.sfsu.xmas.session.*"%>
<%@page import="com.sfsu.xmas.servlet.*"%>
<%@page import="java.io.File"%>

<%
            DataSetDAO dataSetDA = new DataSetDAO();
            DataSetList databasesDatabase = dataSetDA.getExpressionDataSets();
            if (databasesDatabase != null && databasesDatabase.size() > 0) {

                boolean admin = SessionAttributeManager.isAdmin(request);
%>
<table class="base_table" style="width: 95%;">
    <thead>
        <tr>
            <th colspan="2">Load</th>
            <th rowspan="2"><u>ID:</u></th>
            <th rowspan="2">Name</th>
            <th rowspan="2">Time Periods</th>
            <th rowspan="2">Samples</th>
            <th rowspan="2">Probes/Genes</th>
            <% if (admin) {%>
            <th rowspan="2" colspan="2" />
            <% }%>
        </tr>
        <tr>
            <th>Primary</th>
            <th>Secondary</th>
        </tr>
    </thead>
    <tbody>
        <%


    ExpressionDataSet activeDatabase = SessionAttributeManager.getActivePrimaryExpressionDatabase(request);

    ExpressionDataSet secondaryDatabase = SessionAttributeManager.getActiveSecondaryExpressionDatabase(request);

    // Database databasesSecondaryDB = DatabaseManager.getActiveSecondaryDataBase();
    for (int i = 0; i < databasesDatabase.size(); i++) {
        ExpressionDataSet db = (ExpressionDataSet) (databasesDatabase.get(i));

        if (db != null) {
            String rowClass = "odd";
            if (i % 2 == 0) {
                rowClass = "even";
            }

            boolean isActiveDB = activeDatabase != null && db.getID() == activeDatabase.getID();
            String highlight = "";
            if (isActiveDB) {
                highlight = "highlighted";
            }
        %>
        <tr id="database_row_<%= i%>" class="<%= rowClass%>">
            <td class="<%= highlight%>" rowspan="2">
                <%
                    if (isActiveDB) {
                %>
                <input type="button" onclick="return unload_expression_database( false )" value="Un-load" />
                <%                    } else {
                %>
                <input type="button" onclick="return load_expression_database('<%= db.getID()%>', false)" value="Load" />
                <%
                    }
                %>
            </td>
            <%
                    boolean isSecondaryDB = (secondaryDatabase != null && db.getID() == secondaryDatabase.getID());
                    String secondaryHighlight = "";
                    if (isSecondaryDB) {
                        secondaryHighlight = "highlighted";
                    }
            %>
            <td class="<%= secondaryHighlight%>" rowspan="2">
                <%
                if (isSecondaryDB) {
                %>
                <input type="button" onclick="return unload_expression_database( true )" value="Un-load" />
                <%                    } else {
                %>
                <input type="button" onclick="return load_expression_database('<%= db.getID()%>', true)" value="Load" />
                <%
                }
                %>
            </td>
            <td>
                <%= db.getID()%>
            </td>
            <td style="font-size: 1.1em;">
                <b><a href="../data/expression_database.jsp?data_id=<%= db.getID()%>" style="text-decoration: none;"><%= db.getName()%></a></b>
            </td>
            <td>
                <%= db.getNumberOfTimePeriods()%>
            </td>
            <td>
                <%= db.getNumberOfSamples()%>
            </td>
            <td>
                <%= db.getNumberOfProbes()%>
            </td>
            <% if (admin) {%>
            <td rowspan="2">
                <a href="javascript: void(0);" onclick="verify_delete('../SDropDatabase?database_id=<%= String.valueOf(db.getID())%>', false);">Delete</a>
            </td>
            <% }%>
        </tr>
        <tr class="<%= rowClass%>">
            <td colspan="5">
                <span style="float: right;">Creation Date: <i><b><%= db.getCreationDate()%></b></i></span>
                <b>Description:</b><br />
                <%= db.getDescription()%>
            </td>
        </tr>
        <%
        }
    }
        %>
    </tbody>
</table>

<%
} else {
%>
<p class="info_message">
    No Expression Data Sets found. Please try refreshing this page.
</p>
<%            }
%>