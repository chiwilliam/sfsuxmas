<%@page import="visualization.*"%>
<%@page import="database_management.*"%>
<%@page import="xml.*"%>
<%@page import="org.w3c.dom.Document"%>
<%@page import="Globals.*"%>

<style type="text/css">
    td.error, td.success, td.no_decision, td.warning {
        background-image: url();
    }
    td.error {
        background-color: red;
    }
    td.success {
        background-color: limegreen;
    }
    td.warning {
        background-color: yellow;
    }
</style>

<h2>Active data</h2>

<div class="sidebar_padding">
    <div id="sidebar_page_content">
        
        <%
            DatabaseDataAccess dda = new DatabaseDataAccess();
            boolean successfulConnection = dda.checkConnection();
            Database activeDB = DatabaseManager.getActiveDataBase();
            AnnotationDatabase activeADB = AnnotationDatabaseManager.getActiveDataBase();
            if (successfulConnection && activeDB != null) {
        %>
        <p>
            <table class="base_table">
                <tbody>
                    <tr class="odd">
                        <th>Database*</th>
                        <% if (activeDB != null) {%>
                        <td class="success">
                            <a href="../data/your_data.jsp"><%= activeDB.getName()%></a>
                        </td>
                        <% } else {%>
                        <td class="error">
                            <a href="../data/your_data.jsp">Load</a>
                        </td>
                        <%            }
                        %>
                    </tr>
                    <tr>
                        <th>Library*</th>
                        <%
            if (activeADB != null) {%>
                        <td class="success">
                            <a href="../data/your_data.jsp"><%= activeADB.getName()%></a>
                        </td>
                        <% } else {%>
                        <td class="warning">
                            <a href="../data/your_data.jsp">Load</a>
                        </td>
                        <%                            }
                        %>
                    </tr>
                    <tr>
                        <th>Trajectory File*</th>
                        <%
            XMLTrajectoryFactory trajFact = XMLTrajectoryFactory.getUniqueInstance();
            if (trajFact.getTrajectoryDocument() != null) {%>
                        <td class="success">
                            <a href="../data/your_data.jsp"><%= trajFact.getCurrentFile()%></a>
                        </td>
                        <%
                            trajFact = null;
                        } else {%>
                        <td class="error">
                            <a href="../data/your_data.jsp">Load</a>
                        </td>
                        <%                            }
                        %>
                    </tr>
                    <tr>
                        <th>SnapShot</th>
                        <%
            XMLSnapShotFactory snapShotFact = XMLSnapShotFactory.getUniqueInstance();
            if (snapShotFact.getDocument() != null) {%>
                        <td class="success">
                            <a href="../data/your_data.jsp"><%= snapShotFact.getCurrentFile()%></a>
                        </td>
                        <% } else {%>
                        <td class="warning">
                        </td>
                        <%                            }
                        %>
                    </tr>
                    <tr>
                        <th>Cluster</th>
                        <%
            XMLClusterFactory clustFact = XMLClusterFactory.getUniqueInstance();
            if (clustFact.getDocument() != null) {%>
                        <td class="success">
                            <a href="../data/your_data.jsp"><%= clustFact.getCurrentFile()%></a>
                        </td>
                        <% } else {%>
                        <td class="warning">
                        </td>
                        <% }%>
                    </tr>
                </tbody>
            </table>
        </p>
        <%
            }
        %>
    </div>
</div>