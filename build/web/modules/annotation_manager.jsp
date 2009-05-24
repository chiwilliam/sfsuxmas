<%@page import="annotations.*"%>
<%@page import="data_structures.*"%>

<!--
START SIDEBAR MENU
-->
<div class="corners_bar">
    <div class="corners_top_left"></div>
    <div class="corners_top_right"></div>
    <div class="corners_connector_bar"></div>
</div>
<div class="sidebar_tool_area">
    <div class="sidebar_tool_area_top_line">
        Annotation manager
    </div>
</div>
<div class="corners_bar">
    <div class="corners_connector_bar_full"></div>
</div>
<!--
END SIDEBAR MENU
-->



<div class="sidebar_content_holder">
    <div class="corners_bar">
        <div class="corners_inner_top_left"></div>
        <div class="corners_inner_top_right"></div>
        <div class="corners_inner_connector_bar"></div>
    </div>
    <%



            AnnotationDataAccess ada = new AnnotationDataAccess();
            Annotations annotations = ada.getAnnotations();
            if (annotations == null || annotations.size() <= 0) {
    %>
    <div class="no_data">
        No data
    </div>
    <%            } else {
    %>
    <table class="window-large_quant_data">
        <thead>
            <tr>
                <th>ID</th>
                <th>Description</th>
                <th>Representation</th>
                <th />
                <th />
            </tr>
        </thead>
        <tbody>
            <%
                    for (int i = 0; i < annotations.size(); i++) {
                        Annotation annotation = (Annotation) annotations.get(i);
                        String rowClass = "odd";
                        if (i % 2 == 0) {
                            rowClass = "even";
                        }
            %> 
            <tr class="<%= rowClass%>">
                <td>
                    <%= annotation.getID()%>
                </td>
                <td>
                    <a href="../SFilterManipulator?filterType=3&annotationID=<%= annotation.getID()%>" target="_parent"><%= annotation.getDescription()%></a>
                </td>
                <td>
                    <%= annotation.getTotalCardinality()%>
                </td>
                <td>
                    <%
                if (annotation.getTotalCardinality() > 0) {
                    %>
                    <a href="../SEmptyAnnotation?id=<%= annotation.getID()%>">Empty</a>
                    <%
                }
                    %>
                </td>
                <td>
                    <a href="../SRemoveAnnotation?id=<%= annotation.getID()%>">X</a>
                </td>
            </tr>
            <%            }
            %>
            
        </tbody>
    </table>
    <%
            }
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