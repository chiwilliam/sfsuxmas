var nav_container = 'sidebar_window';
var nav_message = 'sidebar_window_message';
var nav_container_content = 'sidebar_page_content';
var sub_nav_but = 'sub_nav_';
var probe_area = "sidebar_probe_area";
var window_probe_area = "window_probe_area";
var pathway_area = 'sidebar_pathway_area';
var label_area = 'sidebar_label_area';

function navigation_loading(div_id) {
    var loading_area = document.getElementById(div_id);
    if (loading_area != null) {
        document.getElementById(div_id).innerHTML = "<div class=\"loading_container\"><p><b>Loading...</b></p><br /><img src=\"../resources/images/loading.gif\" /></div>";
    }
}

function make_nav_request(url) {
    navigation_loading(nav_container);
    var xmlHttp = getRequest();
    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState==4 && xmlHttp.status==200) {
            document.getElementById(nav_container).innerHTML = xmlHttp.responseText;
            refresh_filter_summary_table('_filter_list_holder', '_FilterTree');
            refresh_filter_summary_table('probe_filter_list_holder', 'probe_FilterTree');
            refresh_filter_summary_table('pathway_filter_list_holder', 'pathway_FilterTree');
            refresh_filter_summary_table('labels_filter_list_holder', 'labels_FilterTree');
            refresh_filter_summary_table('trajectory_filter_list_holder', 'trajectory_FilterTree');
            refresh_filter_summary_table('node_filter_list_holder', 'node_FilterTree');
            populate_pcad_data(0, 50);
        }
    }
    xmlHttp.open("GET", url, true);
    xmlHttp.setRequestHeader("Cache-Control", "no-cache");
    xmlHttp.send(null);
}

function make_nav_request_traj(url, minBin, maxBin, numberofTimePeriods) {
    navigation_loading(nav_container);
    var xmlHttp = getRequest();
    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState==4 && xmlHttp.status==200) {
            document.getElementById(nav_container).innerHTML = xmlHttp.responseText;
            updateQuantityOfTrajectories(minBin, maxBin, numberofTimePeriods);
            refresh_filter_summary_table('filter_list_holder', 'filterTree');
        }
    }
    xmlHttp.open("GET", url, true);
    xmlHttp.setRequestHeader("Cache-Control", "no-cache");
    xmlHttp.send(null);
}

function make_sub_nav_request(url) {
    navigation_loading(nav_container_content);
    var xmlHttp = getRequest();
    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState==4 && xmlHttp.status==200) {
            document.getElementById(nav_container_content).innerHTML = xmlHttp.responseText;
        }
    }
    xmlHttp.open("GET", url, true);
    xmlHttp.setRequestHeader("Cache-Control", "no-cache");
    xmlHttp.send(null);
}

function probe_refresh(url) {
    return probe_refresh(url, false, -1)
}

function probe_refresh(url, window, divID) {
    if (window) {
        make_ajax_window_request(url, divID, null);
    } else {
        navigation_loading(probe_area);
        var xmlHttp = getRequest();
        xmlHttp.onreadystatechange = function() {
            if (xmlHttp.readyState==4 && xmlHttp.status==200) {
                if (document.getElementById(probe_area) != null) {
                    document.getElementById(probe_area).innerHTML = xmlHttp.responseText;
                    populate_pcad_data(0, 50);
                }
            }
        }
        xmlHttp.open("GET", url, true);
        xmlHttp.setRequestHeader("Cache-Control", "no-cache");
        xmlHttp.send(null);
    }
}

function pathway_refresh(url) {
    navigation_loading(pathway_area);
    var xmlHttp = getRequest();
    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState==4 && xmlHttp.status==200) {
            document.getElementById(pathway_area).innerHTML = xmlHttp.responseText;
        }
    }
    xmlHttp.open("GET", url, true);
    xmlHttp.setRequestHeader("Cache-Control", "no-cache");
    xmlHttp.send(null);
}

function label_refresh(url) {
    navigation_loading(label_area);
    var xmlHttp = getRequest();
    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState==4 && xmlHttp.status==200) {
            document.getElementById(label_area).innerHTML = xmlHttp.responseText;
        }
    }
    xmlHttp.open("GET", url, true);
    xmlHttp.setRequestHeader("Cache-Control", "no-cache");
    xmlHttp.send(null);
}

function fill_div_with_request_response(url, results_id) {
    var xmlHttp = getRequest();
    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState==4 && xmlHttp.status==200) {
            document.getElementById(results_id).innerHTML = xmlHttp.responseText;
        }
    }
    xmlHttp.open("GET", url, true);
    xmlHttp.setRequestHeader("Cache-Control", "no-cache");
    xmlHttp.send(null);
}

function make_sub_nav_request(url, button_id) {
    navigation_loading(nav_container_content);
    var xmlHttp = getRequest();
    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState==4 && xmlHttp.status==200) {
            document.getElementById(nav_container_content).innerHTML = xmlHttp.responseText;
            var elements = document.getElementsByTagName('a');
            for (var i = 0; i < elements.length; i++) {
                if (elements[i].id.substr(0,sub_nav_but.length) == sub_nav_but) {
                    document.getElementById(elements[i].id).className = "button";
                }
            }
            document.getElementById(sub_nav_but + button_id).className = "button current";
        }
    }
    xmlHttp.open("GET", url, true);
    xmlHttp.setRequestHeader("Cache-Control", "no-cache");
    xmlHttp.send(null);
}
            
function handleResponse() {
}
