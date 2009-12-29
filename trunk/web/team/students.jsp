<%@ include file="../template/file_header.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title>XMAS &copy; 2007</title>
        
        <link rel="stylesheet" type="text/css" href="../resources/styles/main.css" />
        <link rel="stylesheet" type="text/css" href="../resources/styles/header.css" />
        <link rel="stylesheet" type="text/css" href="../resources/styles/sidebar.css" />
        
        <script type="text/javascript" src="../resources/script/main.js"></script>
        
    </head>
    
    <body onload="window_onload_team('students');">
        
        <%@ include file="../template/top_navigation.jsp" %>
        
        <div id="page_body">
            <div id="page_body_sub">
                
                <div id="sidebar_container">
                    <div id="sidebar">
                        
                    </div>
                </div>
                
                <div id="page_body_main">
                    
                    <h1>Team</h1>
                    
                    <%@ include file="../template/team_nav.jsp" %>
                    
                    <div id="page_body_content">
                        
                        <h2>Ben Dalziel</h2>
                        
                        <p><b>Email</b>: b.j.dalziel [at] gmail.com</p>
                        
                    </div>
                    <div id="page_body_content">

                        <h2>William Murad</h2>

                        <p><b>Email</b>: whemurad [at] sfsu.edu</p>

                    </div>
                </div>
            </div>
        </div>
        
        <%@ include file="../template/footer.jsp" %>
        
    </body>
    
</html>