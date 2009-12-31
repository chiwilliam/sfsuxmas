<%@page import="com.sfsu.xmas.dao.*"%>
<%@page import="com.sfsu.xmas.data_sets.*"%>
<%@page import="com.sfsu.xmas.session.*"%>
<%@page import="com.sfsu.xmas.servlet.*"%>
<%@page import="java.io.File"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.sfsu.xmas.trajectory_files.*"%>


<%
            ExpressionDataSet activeDatabase = SessionAttributeManager.getActivePrimaryExpressionDatabase(request);
// Check that an active database exists
            if (activeDatabase != null) {
                double maxExpression = activeDatabase.getMaximumExpression();
                double minExpression = activeDatabase.getMinimumExpression();
                double meanExpression = activeDatabase.getMeanExpression();

                int minBin = (int) Math.floor(minExpression);
                int maxBin = (int) Math.ceil(maxExpression);
                double range = (int) maxBin - minBin;
                String fmt = "0.00#";
                DecimalFormat df = new DecimalFormat(fmt);
%>
<p>
    <%
            if (activeDatabase != null) {
                int fileCount = 0;

                File[] l = activeDatabase.getTrajectoryFiles();
                if (l != null && l.length > 0) {
                    TrajectoryFile trajFile = SessionAttributeManager.getActiveTrajectoryFile(request);
    %>
    <table class="base_table" style="width: 95%;">
        <thead>
            <tr>
                <th>Load</th>
                <th>Name</th>
                <th>Type</th>
                <th>Bin Unit</th>
                <th>Traj' Count</th>
            </tr>
        </thead>
        <tbody>
            <%
            for (int i = 0; i < l.length; i++) {
                TrajectoryFile currentTD = TrajectoryFileFactory.getUniqueInstance().getFile(activeDatabase.getID(), l[i].getName());
                if (currentTD != null) {
                    String rowClass = "odd";
                    if (fileCount % 2 == 0) {
                        rowClass = "even";
                    }
                    String presCol = "Preserved";
                    if (!currentTD.isPreserved()) {
                        presCol = "Collapsed";
                    }


                    boolean isActiveDB = (trajFile != null && currentTD.getFileName().equals(trajFile.getFileName()));
                    String highlight = "";
                    String disabled = "";
                    String load = "Load";
                    if (isActiveDB) {
                        highlight = "highlighted";
                        disabled = " disabled ";
                        load = "Active";
                    }

            %>
            <tr class="<%= rowClass%>">
                <td class="<%= highlight%>">
                <input type="button" onclick="return load_file('<%=currentTD.getFileName() %>')" value="Load" <%= disabled%> />
                       </td>
                <td style="font-size: 1.1em;"><b><%= currentTD.getFileName()%></b></td>
                <td><%= presCol%> Trajectory File</td>
                <td><%= currentTD.getBinUnit()%></td>
                <td><%= currentTD.getNumberOfTrajectories()%></td>
                <%
                    fileCount++;
                %>
            </tr>
            <%
                }
            }
            if (fileCount <= 0) {
            %>
            <tr>
                <td colspan="4">No Files - please create one</td>
            </tr>
            <%            }
            %>
            
        </tbody>
    </table>
    <%
        } else {
    %>
    <div class="error">No files for active database</div>
    <%            }
    } else {
    %>
    <div class="error">No active database found</div>
    <%            }
    %>
    
</p>


<div class="col_header_collapsed" 
     onclick="toggle_area('file_creation_area');" 
     id="file_creation_area_show_hide"
     alt="Show/Hide"
     title="Show/Hide">Create a new file</div>
<div id="file_creation_area" style="display: none;">
    
    <% if (activeDatabase.hasData()) {%>
    
    Use the summary of your data below to help you define options for file creation:
    <img src="../SDataImage" />
    <form action="../SMakeXMLFile" method="POST" onsubmit="check_generate_file(1);">
        
        <p>
            <span style="color: blue;">Minimum</span>: <b><%= df.format(minExpression)%></b> || 
            <span style="color: black;">Mean</span>: <b><%= df.format(meanExpression)%></b> || 
            <span style="color: red;">Maximum</span>: <b><%= df.format(maxExpression)%></b>
        </p>
        
        <p>
            <table>
                <thead>
                    <tr>
                        <th>Attribute</th><th>Value</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Integer Expression Range</td><td><b><%= Math.ceil(range)%></b></td>
                    </tr>
                    <tr>
                        <td>Number of Bins</td><td><input id="bin_count" name="bin_count" type="text" size="5" value="<%= Math.ceil(range)%>"  disabled /></td>
                    </tr>
                    <tr>
                        <td>Bin Unit</td><td><input id="bin_unit" name="bin_unit" type="text" size="5" value="<%= Math.ceil(range) / Math.ceil(range)%>"
                                                        onfocus="updateBinUnit(<%= Math.ceil(range)%>);" 
                                                        onblur="updateBinUnit(<%= Math.ceil(range)%>);" 
                                                        onselect="updateBinUnit(<%= Math.ceil(range)%>);" 
                                                    onchange="updateBinUnit(<%= Math.ceil(range)%>);" /> - Use this number to customize your trajectory file</td>
                    </tr>
                </tbody>
            </table>
        </p>
        
        <div id="file_creation_message"></div>
        
        <p>
            <b>Name your file</b>
            <blockquote>
                <div class="entry">
                    <p>
                        <input type="text" name="file_name" id="file_name" size="20" value="" 
                               onchange="return check_trajectory_filename();" 
                               onblur="return check_trajectory_filename();" 
                               onkeyup="return check_trajectory_filename();" /> 
                        <span id="file_name_error" class="error">You must specify a name</span>
                    </p>
                </div>
            </blockquote>
        </p>
        
        <p style="text-align: center;">
            <INPUT TYPE="SUBMIT" VALUE="Create file" id="create_file_button" />
        </p>
    </form>
    
    <% } else {%>
    <div class="error">Corrupted data set loaded</div>
    <% }%>
    
</div>

<%
        } else {
%>
<p class="info_message">
    You do not have an active database. Please load one from the module above.
</p>
<%            }
%>