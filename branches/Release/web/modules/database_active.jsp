<%@page import="com.sfsu.xmas.dao.*"%>
<%@page import="com.sfsu.xmas.data_sets.*"%>
<%@page import="com.sfsu.xmas.session.*"%>
<%@page import="com.sfsu.xmas.servlet.*"%>
<%@page import="com.sfsu.xmas.trajectory_files.*"%>

<%
     //       if (DatabaseConnectionManager.isConnectionLive()) {
                ExpressionDataSet eDB = SessionAttributeManager.getActivePrimaryExpressionDatabase(request);
                if (eDB == null) {
%>
<div class="error">No active Expression Database</div>
<%    } else {
%>
<div class="success">Active Expression Database:<br />ID=<%= eDB.getID()%>, NAME:"<i><%= eDB.getName()%></i>"</div>
<%
    }
%>

<%
    ExpressionDataSet seDB = SessionAttributeManager.getActiveSecondaryExpressionDatabase(request);
    if (seDB == null) {
%>
<div class="error">No active Secondary Expression Database</div>
<%    } else {
%>
<div class="success">Active Secondary Expression Database:<br />ID=<%= seDB.getID()%>, NAME:"<i><%= seDB.getName()%></i>"</div>
<%
    }
%>

<%
    KnowledgeDataSet activeLibrary = SessionAttributeManager.getActiveKnowledgeLibrary(request);
    if (activeLibrary == null) {
%>
<div class="error">No active Knowledge Library</div>
<%} else {
%>
<div class="success">Active Knowledge Library:<br />ID=<%= activeLibrary.getID()%>, NAME:"<i><%= activeLibrary.getName()%></i>"</div>
<%
    }
%>

<%
    TrajectoryFile td = SessionAttributeManager.getActiveTrajectoryFile(request);
    if (td == null) {
%>
<div class="error">No active Trajectory File</div>
<%} else {
%>
<div class="success">Active Trajectory File: "<i><%= td.getFileName()%></i>"</div>
<%
    }
%>

<%
       //     }
%>



<%
if (SessionAttributeManager.isPreserved(request)) {
%>
<div class="success">Expression data is preserved</div>
<%} else {
%>
<div class="success">Expression data is collapsed</div>
<%
    }
%>

<%
if (SessionAttributeManager.isPrimaryExpressionDatabaseActive(request)) {
%>
<div class="success">The primary expression database is active</div>
<%} else {
%>
<div class="success">The secondary expression database is active</div>
<%
    }
%>

<%
if (SessionAttributeManager.isAdmin(request)) {
%>
<div class="success">You are recognized as an administrator</div>
<%} else {
%>
<div class="error">You are not an administrator</div>
<%
    }
%>
