
<div class="list_page_nav_bar">
    <%
            if (pageNumber - 1 > 0) {
    %><a href="<%= basePageNavURL %>1" class="page_link">&lt; First</a> <a href="<%= basePageNavURL %><%= pageNumber - 1 %>" class="page_link">&lt; Previous</a> <%
    } else {
    %><span class="page_link_no_link">&lt; First</span> <span class="page_link_no_link">&lt; Previous</span><%
            }
            
            %>
            <span class="page_link_no_link"><%= pageNumber %> of <%= maxPageCount %></span>
            <%
            
            if (pageNumber + 1 <= maxPageCount) {
    %> <a href="<%= basePageNavURL %><%= pageNumber + 1 %>" class="page_link">Next &gt;</a> <a href="<%= basePageNavURL %><%= maxPageCount %>" class="page_link">Last &gt;</a><%
    } else {
    %><span class="page_link_no_link">Next &gt;</span> <span class="page_link_no_link">Last &gt;</span><%
            }
    %>
</div>
<div class="list_page_nav_bar">
    <b>Pages</b>: <%
            for (int j = minPage; j <= maxPage; j++) {
                if (j == minPage && 1 < minPage) {
    %>
    <span class="page_link_no_link">...</span>
    <%
        }
        if (j != pageNumber) {
    %>
    <a href="<%= basePageNavURL %><%= j %>" class="page_link"><%= j %></a> 
    <%
    } else {
    %>
    <span class="page_link_no_link"><b><%= j %></b></span>
    <%
        }
        if (j >= maxPage && j < maxPageCount) {
    %>
    <span class="page_link_no_link">...</span>
    <%
                }
            }%>
</div>