<%@page import="com.sfsu.xmas.dao.*"%>
<%@page import="com.sfsu.xmas.data_sets.*"%>
<%@page import="xml.*"%>
<%@page import="visualization.*"%>
<%@page import="com.sfsu.xmas.session.SessionAttributeManager"%>
<%@page import="com.sfsu.xmas.highlight.*"%>

<div class="sidebar_header">Highlighted Data</div>

<div class="sidebar_padding">
    
    <div class="corners_bar">
        <div class="corners_top_left"></div>
        <div class="corners_top_right"></div>
        <div class="corners_connector_bar"></div>
    </div>
    <div class="sidebar_tool_area">
        <div class="sidebar_tool_area_content">
            <div class="corners_bar">
                <div class="corners_inner_top_left"></div>
                <div class="corners_inner_top_right"></div>
                <div class="corners_inner_connector_bar"></div>
            </div>
            <div class="sidebar_tool_buttons">
                <form action="../SRemoveHighlights">
                    <input type="submit" value="Stop Highlighting Data" />
                </form>
            </div>
            <div class="corners_bar">
                <div class="corners_inner_bottom_left"></div>
                <div class="corners_inner_bottom_right"></div>
                <div class="corners_inner_connector_bar"></div>
            </div>
        </div>
    </div>
    <div class="corners_bar">
        <div class="corners_bottom_left"></div>
        <div class="corners_bottom_right"></div>
        <div class="corners_connector_bar"></div>
    </div>
    
    
    <div class="sidebar_block_header">Highlighted Probes</div>
    <p>
        <jsp:include page="../modules/probe_renderer.jsp">
            <jsp:param name="highlighted" value="true" />
        </jsp:include>
    </p>
    
    <%
            KnowledgeDataSet kDB = SessionAttributeManager.getActiveKnowledgeLibrary(request);
            if (kDB != null) {
                if (HighlightManager.getUniqueInstance().getHighlightedPathways(SessionAttributeManager.getSessionID(request)).size() > 0) {
    %>
    
    <div class="sidebar_block_header">Highlighted Pathways</div>
    <p>
        <jsp:include page="../modules/pathway_renderer.jsp">
            <jsp:param name="highlighted" value="true" />
        </jsp:include>
    </p>
    
    <%        }
    if (HighlightManager.getUniqueInstance().getHighlightedAnnotations(SessionAttributeManager.getSessionID(request)).size() > 0) {
    %>
    
    <div class="sidebar_block_header">Highlighted Annotations</div>
    <p>
        <jsp:include page="../modules/label_renderer.jsp">
            <jsp:param name="highlighted" value="true" />
        </jsp:include>
    </p>
    
    <%                }
    if (HighlightManager.getUniqueInstance().getHighlightedGOTerms(SessionAttributeManager.getSessionID(request)).size() > 0) {
    %>
    
    <div class="sidebar_block_header">Highlighted GO Terms</div>
    <p>
        <jsp:include page="../modules/go_term_renderer.jsp">
            <jsp:param name="highlighted" value="true" />
        </jsp:include>
    </p>
    
    <%                }
            }%>
    
</div>