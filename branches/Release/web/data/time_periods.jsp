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

                TimePeriods timePeriods = db.getTimePeriods();
                int dataSize = timePeriods.size();
%>


<div class="col_header" onclick="toggle_area('time_periods');" id="time_periods_show_hide" title="Show/Hide">Time Periods</div>
<div class="indented_div">
    <div id="time_periods">
        <p class="block_summary">
            Time periods are composed of 1 or more samples, and are represented by a single, probe indexed expression vector derived from the median expression value for each probe.
        </p>
        <p>
            <form>
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
                        No time periods in this database
                    </div>
                    <%    } else {
                    %>
                    
                    <p class="table_summary">
                        There are <%= dataSize%> time periods in this Expression Data Set.
                    </p>
                    
                    <table class="window-large_quant_data">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Description</th>
                                <th>Number of Member Samples</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                        for (int i = 0; i < dataSize; i++) {
                            TimePeriod tp = timePeriods.get(i);
                            String rowClass = "odd";
                            if (i % 2 == 0) {
                                rowClass = "even";
                            }
                            %>
                            <tr class="<%= rowClass%>" id="time_period_row_<%= tp.getID()%>">
                                <td>
                                    <%= tp.getID()%>
                                </td>
                                <td>
                                    <%= tp.getDescription()%>
                                </td>
                                <td>
                                    <%= tp.getSampleCount()%>
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
            </form>
        </p>
    </div>
</div>
<%
            }
%>