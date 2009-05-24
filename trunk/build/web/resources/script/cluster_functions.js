function window_onload_heatmap() {
    load_images();
}

function window_onload_clustering() {
    var xhr;
    xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if(xhr.readyState  == 4) {
            if(xhr.status  == 200 || xhr.status  == 0) {
                var st = xhr.responseText;
                var code = "";
                for (var i = 0; i < st; i++) {
                    code +=
                    "<h2>Cluster " + i + "</h2>" +
                    "<div style=\"text-align: center;\">" +
                    "<a href=\"javascript:void(0)\" onclick=\"return cluster_click(event, " + i + ")\">" + 
                    "<img src=\"../SGetCluster?number=" + i + "\" style=\"border: 0;\" /></a>" +
                    "</div>";
                }
                document.getElementById('cluster_space').innerHTML = code;
            }
            else {
                alert(xhr.responseText); 
            }
        }
    };
    xhr.open("GET", "../SGenerateHeatMaps",  true);
    xhr.send(null);
}

function cluster_click(evt, nodeID) {
    var yOffset = window.pageYOffset;
    var popupX =evt.clientX;
    var popupY =evt.clientY + yOffset;
    
    addClusterWindow("Inspect this cluster: ", nodeID, popupX, popupY);
}

function refresh_annotation_table() {
    for (var i = 1; i <= 4; i++) {
        document.getElementById('image' + i).scr = "";
    }
}