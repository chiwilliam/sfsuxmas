<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.sfsu.xmas.globals.*"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.sfsu.xmas.util.*"%>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title>Redirecting to XMAS Homepage - <%= SiteGlobals.SITE_PAGE_TITLE%></title>
        
        <script type="text/javascript" language="JavaScript">
            <!--
            setTimeout("top.location.href = 'home/'",0);
            //-->
        </script>
        <style>
            body {
                 font-family: arial;
            }
            h1 {
                 color: #FF9900;
            }
        </style>
    </head>
    <body>
        <h1>XMAS: Redirect...</h1>
        <p>
            If this page does not automatically redirect, please follow this link to the <a href="home/">home page</a>.
        </p>
        <p>
            XMAS requires both JavaScript and Cookies to be enabled, and works best in the <a target="_blank" href="http://www.mozilla.com/en-US/firefox/">Firefox</a> web browser.
        </p>
<script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
try {
var pageTracker = _gat._getTracker("UA-<%= GoogleAnalytics.GOOGLE_ANALYTICS_CODE %>");
pageTracker._trackPageview();
} catch(err) {}</script>

    </body>
</html>
