<%@page import="xml.*"%>
<%@page import="org.w3c.dom.*"%>

        
<h2>Clusters</h2>

<div class="sidebar_padding">
    <div id="sidebar_page_content">
        
        <form action="../SCalculateClusters" method="POST" target="_parent">
            
            <p>
                <b>Cluster</b>
                <br />
                Use this tool to group probes in your current analysis based on their similarity
                
            </p>
            
            <p>
                <b>Choose a value of K</b>
                <br />
                <p>
                    <blockquote>
                        <div class="entry">
                            <p>
                                <select name="kValue" style="width: 50px;">
                                    <%
            for (int i = 1; i <= 40; i++) {
                                    %>
                                    <option value="<%= i%>"><%= i%></option>
                                    <%
            }
                                    %>
                                </select>
                            </p>
                        </div>
                    </blockquote>
                </p>
            </p>
            
            <p>
                <b>Choose a distance metric</b>
                <br />
                <p>
                    <blockquote>
                        <div class="entry">
                            <p>
                                <select name="metric" style="width: 150px;">
                                    <option value="euclidean">Euclidean Distance</option>
                                    <option value="correlation">Correlation</option>
                                </select>
                            </p>
                        </div>
                    </blockquote>
                </p>
            </p>
            
            <p>
                <b>Choose a name for this clustering</b>
                <br />
                <p>
                    <blockquote>
                        <div class="entry">
                            <p>
                                <input type="text" name="cluster_name" id="cluster_name" size="20" value="" />
                            </p>
                        </div>
                    </blockquote>
                </p>
            </p>
            
            <p style="text-align: center;">
                <input TYPE="SUBMIT" VALUE="Okay, Cluster!" />
            </p>
            
        </form>
        
        <h3>Load an existing Cluster</h3>
        
        <p>Here are the Cluters you have created for this SnapShot so far:</p>
        
        <div id="file_load_message"></div>
        
        <div id="file_list_holder"><img src="../resources/images/loading.gif" /></div>
        
    </div>
</div>