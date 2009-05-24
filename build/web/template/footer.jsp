<%@page import="com.sfsu.xmas.util.*"%>
<style type="text/css">
    .footer {
        height: 100px;
        border-top: 1px solid #222;
        border-bottom: 2px solid #222;
        background-color: #CCC;
        clear: both;
    }
    .footer_sub {
        margin-left: auto;
        margin-right: auto;
        width: 980px;
    }
    .footer_content {
        color: #222;
        padding: 10px 0;
        font-size: 11px;
    }
    .footer_content a {
        font-weight: bold;
        text-decoration: none;  
    }
</style>

        <div class="footer">
            <div class="footer_sub">
                <div class="footer_content">
                    <div style="float: left;">
                        Copyright &copy; 2009. All rights reserved | Contact: b.j.dalziel [at] gmail [dot] com<br>
                        <i>* Beta release - please <a href="https://sourceforge.net/forum/forum.php?forum_id=815038" target="_blank">report bugs</a> and come back for updates</i><br>&nbsp;<br>
                        <a href="../home/index.jsp">Home</a> | 
                        <a href="https://sourceforge.net/projects/xmassfsu/" target="_blank">SourceForge Project Page</a> | 
                        <a href="https://sourceforge.net/forum/forum.php?forum_id=815038" target="_blank">Support Forum</a>
                    </div>
                    <div style="text-align: right;">
                        <a href="http://tintin.sfsu.edu/" target="_blank"><img src="../resources/images/biocomputing.png" style="height: 50px; margin-right: 50px; margin-top: 15px;" alt="Biocomputing & Media Research Group"></a>
                        <a href="http://dentistry.ucsf.edu/fisherlab/index.html" target="_blank"><img src="../resources/images/ucsf_logo.png" style="height: 50px; margin-right: 50px; margin-top: 15px;" alt=""></a>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
try {
var pageTracker = _gat._getTracker("UA-<%= GoogleAnalytics.GOOGLE_ANALYTICS_CODE %>");
pageTracker._trackPageview();
} catch(err) {}</script>
      