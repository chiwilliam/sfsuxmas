<%@page import="com.sfsu.xmas.kegg.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    
    <frameset rows="102, *" border="0">
        <frame src="../pathways/top_navigation.jsp" />
        <!-- <frame src="../pathways/top_navigation.jsp" /> -->
        
        <frame src="<%= PathwayAccessor.getPathwayURLForProbes(null)%>" />
    </frameset>
    
</html>
