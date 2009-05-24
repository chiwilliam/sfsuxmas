<%@page import="com.sfsu.xmas.dao.*"%>
<%@page import="com.sfsu.xmas.data_sets.*"%>
<%@page import="com.sfsu.xmas.data_structures.*"%>
<%@page import="com.sfsu.xmas.data_structures.expression.*"%>

<%
            String dataID = request.getParameter("data_id");


            int dataSetID = -1;
            try {
                dataSetID = Integer.parseInt(dataID);
            } catch (NumberFormatException ex) {
%>
<p class="error">
    Error: Non numeric or null data ID for attribute: "data_id"
</p>
<%            }

            if (dataSetID >= 0) {

                ExpressionDataSet db = ExpressionDataSetMultiton.getUniqueInstance().getDataSet(dataSetID, false);

                Samples samples = db.getSamples();
                int dataSize = samples.size();
%>


<div class="col_header" onclick="toggle_area('samples');" id="samples_show_hide" title="Show/Hide">Samples</div>
<div class="indented_div">
    <div id="samples">
        <p class="block_summary">
            A single sample captures an expression level for every probe represented on the microarray chip. 
            Generally, a number of samples are generated per time period to facilitate the notion of reproducibility.
            Sample are described completely by a unique name, a time period assignment and a vector of probe indexed expression values.
        </p>
        <p>
            <FORM>
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
                        No samples in this database
                    </div>
                    <%    } else {
                    %>
                    
                    <p class="table_summary">
                        There are <%= dataSize%> samples in this Expression Data Set.
                    </p>
                    
                    <table class="window-large_quant_data">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name/Description</th>
                                <th>Time Period</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                        for (int i = 0; i < dataSize; i++) {
                            Sample samp = samples.get(i);
                            String rowClass = "odd";
                            if (i % 2 == 0) {
                                rowClass = "even";
                            }
                            %>
                            <tr class="<%= rowClass%>" id="sample_row_<%= samp.getID()%>">
                                <td>
                                    <%= samp.getID()%>
                                </td>
                                <td>
                                    <%= samp.getDescription()%>
                                </td>
                                <td>
                                    <%= samp.getTimePeriod().getDescription()%>
                                </td>
                            </tr>
                            <%
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
                
                <div class="sidebar_tool_area">
                    <div class="sidebar_tool_area_bottom_line">
                        
                    </div>
                </div>
                
                <div class="corners_bar">
                    <div class="corners_bottom_left"></div>
                    <div class="corners_bottom_right"></div>
                    <div class="corners_connector_bar"></div>
                </div>
            </FORM>
        </p>
    </div>
</div>
<%
            }
%>