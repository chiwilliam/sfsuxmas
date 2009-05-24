<%@page import="com.sfsu.xmas.dao.*"%>
<%@page import="com.sfsu.xmas.data_sets.*"%>
<%@page import="com.sfsu.xmas.session.*"%>
<%@page import="com.sfsu.xmas.capture.*"%>
<%@page import="java.io.*"%>

<div class="sidebar_header">Analysis Capture</div>

<div class="sidebar_padding">
    <div id="sidebar_messages">
        
    </div>
    <div id="sidebar_page_content">
        
        <p>This tool enable you to capture the state of your current analysis so that you can return to this point.</p>
        
        
        <div class="col_header" onclick="toggle_area('capture');" id="capture_show_hide">Capture the current analysis</div>
        <div class="indented_div">
            <div id="capture">
                <FORM ACTION="../SCaptureAnalysis" METHOD="POST">
                    <table>
                        <tr>
                            <td>Analysis Name:</td>
                            <td>
                                <input type="text" name="capture_name" id="capture_name" size="20" value="" 
                                       onchange="return check_capture_filename();" 
                               onblur="return check_capture_filename();" 
                               onkeyup="return check_capture_filename();" />
                            </td>
                        </tr>
                        <tr valign="top">
                            <td>Analysis Description:</td>
                            <td>
                                <textarea name="capture_description" id="capture_description" cols="26" rows="4" wrap="virtual"></textarea>
                            </td>
                        </tr>
                        <tr valign="top">
                            <td />
                            <td>
                                <INPUT TYPE="SUBMIT" name="create_capture_button" id="create_capture_button" VALUE="Capture" disabled />
                            </td>
                        </tr>
                        <tr valign="top">
                            <td />
                            <td>
                                <span id="file_name_error" class="error">You must specify a name</span>
                            </td>
                        </tr>
                    </table>
                </FORM>
            </div>
        </div>
        
        <div class="col_header" onclick="toggle_area('capture_load');" id="capture_load_show_hide">Load an existing analysis</div>
        <div class="indented_div">
            
            <div>
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
            ExpressionDataSet eDB = SessionAttributeManager.getActivePrimaryExpressionDatabase(request);

            File[] files = eDB.getCapturedAnalyses();
            if (files != null && files.length > 0) {
                    %>
                    <table class="window-large_quant_data">
                        <thead>
                            <th>Name</th>
                            <th>Data/Viz</th>
                            <th>Description</th>
                            <th />
                        </thead>
                        <tbody>
                            <%
                        for (int i = 0; i < files.length; i++) {
                            String rowClass = "odd";
                            if (i % 2 == 0) {
                                rowClass = "even";
                            }
                            CapturedAnalysisDAO caD = CapturedAnalysisFileFactory.getUniqueInstance().getFile(eDB.getID(), files[i].getName());
                            if (caD != null) {
                                ExpressionDataSet eDBForFile = ExpressionDataSetMultiton.getUniqueInstance().getDataSet(caD.getExpressionDatabaseName(), false);
                                KnowledgeDataSet kDBForFile = KnowledgeDataSetFactory.getUniqueInstance().getDataSet(caD.getKnowledgeDatabaseName(), false);
                            %>
                            <tr class="<%= rowClass%>" valign="top">
                                <td><%= caD.getFileName()%></td>
                                <td>
                                    <b>E</b>:<%= eDBForFile.getName()%><br />
                                    <b>K</b>:<%= kDBForFile.getName()%><br />
                                    <b>T</b>:<%= caD.getTrajectoryFileName()%><br />
                                    <b>V</b>:<%= caD.getVisualizationType()%>
                                </td>
                                <td><%= caD.getDescription()%></td>
                                <td><a href="../SLoadAnalysis?file_name=<%= caD.getFileName()%>">Load</a></td>
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
                    <div class="no_data">
                        No Files
                    </div>
                    <%            }
                    %>
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
            </div>
            
            <div>
                <p style="font-style: italic; font-size: 0.8em;">
                    <b>E</b>: Expression Data Set name<br />
                    <b>K</b>: Knowledge Library name<br />
                    <b>T</b>: Trajectory File name<br />
                    <b>V</b>: Visualization type<br />
                </p>
            </div>
            
        </div>
        
    </div>
</div>