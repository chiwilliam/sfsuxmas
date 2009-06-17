<%@page import="com.sfsu.xmas.dao.*"%>
<%@page import="com.sfsu.xmas.data_sets.*"%>
<%@page import="com.sfsu.xmas.session.*"%>
<%@page import="com.sfsu.xmas.data_structures.expression.*"%>
<%@page import="com.sfsu.xmas.session.*"%>
<%@page import="com.sfsu.xmas.trajectory_files.*"%>

<%
            ExpressionDataSet aDB = SessionAttributeManager.getActivePrimaryExpressionDatabase(request);
            ExpressionDataSet sDB = SessionAttributeManager.getActiveSecondaryExpressionDatabase(request);
            TrajectoryFile td = SessionAttributeManager.getActiveTrajectoryFile(request);
            if (aDB != null && sDB != null) {
%>
<style type="text/css">
    .line {
        border-bottom: 1px solid #9999FF;
        clear: left;
        margin-bottom: 10px;
        margin-top: 10px;
    }
    
    #viz_navigation {
        margin-top: 10px;
    }
    #viz_navigation ul {
        margin: 0 0 0 10px;
        padding: 0;
        list-style: none;
        float: left;
    }
    #viz_navigation li {
        font-size: 1.0em;
        float: left;
        margin: 0 5px 0 0;
        background-color: #FFFFFF;
    }
    #viz_navigation a {
        color: #003399;
        display: block;
        padding: 2px 5px;
        text-decoration: none;
        border-right: solid #9999FF 1px;
        border-top: solid #9999FF 1px;
        border-left: solid #9999FF 1px;
    }
    #viz_navigation a:hover {
        background-color: #F0F0F0;
    }
    #viz_navigation .current a:hover {
        background-color: #BFCFFF;
    }
    #viz_navigation .current {
        font-weight: bold;
        background-color: #9999FF;
    }
</style>

<%
    String hidden = "inline";
    if (!SessionAttributeManager.isProfileVisualization(request)){
        hidden = "none;";
    };
    String subtractiveButtonText = "Subtractive Analysis";
    if (SessionAttributeManager.isSubtractive(request)){
        subtractiveButtonText = "Undo Subtraction";
    };
    String buttonText = "Compare Primary to Secondary";
    if (SessionAttributeManager.isComparative(request)){
        buttonText = "Stop Comparison";
    };
    String dataselectorButtonText = "View Secondary Dataset";
    if (SessionAttributeManager.isDataSelector(request)){
        dataselectorButtonText = "View Primary Dataset";
    };
%>

<div style="font-size: 11px; float: right; display: <%= hidden%>;">
    <b>Primary &quot;<i><%= aDB.getName()%></i>&quot;</b>: Showing <b><%= aDB.getProbes(td.getProbes(SessionAttributeManager.getSessionID(request)), true).size()%></b> of <%= aDB.getNumberOfProbes()%> probes <br />
    <b>Secondary &quot;<i><%= sDB.getName()%></i>&quot;</b>: Shares <b><%= sDB.getProbes(td.getProbes(SessionAttributeManager.getSessionID(request)), true).size()%></b> probes with this analysis 
</div>
<div style="margin: 10px 0; display: <%= hidden%>;">
    <input type="button" onclick="image_type_switch('../SVisualizationManipulator?comparative', 'comparative');" id="button_comparative" value="<%= buttonText%>" />
    <input type="button" onclick="image_type_switch('../SVisualizationManipulator?subtractive', 'subtractive');" id="button_subtractive" value="<%= subtractiveButtonText%>" />
    <input type="button" onclick="image_type_switch('../SVisualizationManipulator?dataselector', 'dataselector');" id="button_dataselector" value="<%= dataselectorButtonText%>" />
</div>

<% if (false) {%>
<div id="viz_navigation" style="height: 20px;">
    <ul>
        <li id="source_switcher_primary"><a href="javascript: void(0);" onclick="image_type_switch(<%= "../SVisualizationManipulator?" + SessionAttributes.FOCAL_DATABASE + "=primary" %>, null, false); window_onload_data_source('primary')"><b><%= aDB.getName()%></b></a></li>
        <li id="source_switcher_secondary"><a href="javascript: void(0);" onclick="image_type_switch(<%= "../SVisualizationManipulator?" + SessionAttributes.FOCAL_DATABASE + "=secondary"%>, null, false); window_onload_data_source('secondary')"><b><%= sDB.getName()%></b></a></li>
    </ul>
</div>
<% }%>
<div class="line"> </div>

<%            }
%>