function wol_top_nav(pageID) {
    make_element_current("top_nav_" + pageID);
}
function wol_top_sub_nav(pageID) {
    make_element_current("top_sub_nav_" + pageID);
}

function wol_upload_sub_nav(pageID) {
    make_element_current("upload_sub_nav_" + pageID);
}

function make_element_current(element_name) {
    var element = document.getElementById(element_name);
    if (element != null) {
        element.className = "current";
        return;
    }
    alert("Element not found on page, with name = '" + element_name + "'");
}

function load_latest_news(element_name) {
    if (document.getElementById(element_name) != null) {
        document.getElementById(element_name).innerHTML = "<div class=\"loading_container\"><p><b>Loading...</b></p><br /><img src=\"../resources/images/loading.gif\" /></div>";
        var xhr = getRequest();
        xhr.onreadystatechange = function() {
            if(xhr.readyState  == 4) {
                if(xhr.status  == 200 || xhr.status  == 0) {
                    var st = xhr.responseText;
                    document.getElementById(element_name).innerHTML = st;
                }
                else {
                    alert(xhr.responseText); 
                }
            }
        };
        xhr.open("GET", "../SGetLatestUpdates",  true);
        xhr.send(null);
    }
}

function load_cookie_test() {
    var element_name = "cookie_test";
    if (document.getElementById(element_name) != null) {
        document.getElementById(element_name).innerHTML = "<div class=\"loading_container\"><p><b>Loading...</b></p><br /><img src=\"../resources/images/loading.gif\" /></div>";
        var xhr = getRequest();
        xhr.onreadystatechange = function() {
            if(xhr.readyState  == 4) {
                if(xhr.status  == 200 || xhr.status  == 0) {
                    var st = xhr.responseText;
                    document.getElementById(element_name).innerHTML = st;
                }
                else {
                    alert(xhr.responseText); 
                }
            }
        };
        xhr.open("GET", "../TestForCookies",  true);
        xhr.send(null);
    }
} 

function toggle_area(area_id) {
    var area = document.getElementById(area_id);
    var toggle = document.getElementById(area_id + '_show_hide');
    if (area !=null && toggle != null) {
        if (area.style.display != "none") {
            area.style.display = "none";
            if (toggle != null) {
                toggle.className = "col_header_collapsed";
            }
        } else {
            area.style.display = "inline";
            if (toggle != null) {
                toggle.className = "col_header";
            }
        }
    } else {
        alert("Area (" + area_id + ") or toggle (" + area_id + '_show_hide' + ") null");
    }
}

function collapse_area(area_id) {
    var area = document.getElementById(area_id);
    var toggle = document.getElementById(area_id + '_show_hide');
    if (area != null) {
        area.style.display = "none";
        if (toggle != null) {
            toggle.className = "col_header_collapsed";
        }
    }
}

function open_area(area_id) {
    var area = document.getElementById(area_id);
    var toggle = document.getElementById(area_id + '_show_hide');
    if (area != null) {
        area.style.display = "inline";
        if (toggle != null) {
            toggle.className = "col_header";
        }
    }
}

function window_onload_data(pageID) {
    reset_nav("a", "top_sub_nav_", "");
    var div = document.getElementById("top_sub_nav_" + pageID);
    if (div != null) {
        div.className = "current";   
    }
}

function window_onload_data_source(pageID) {
    reset_nav("li", "source_switcher_", "");
    var div = document.getElementById("source_switcher_" + pageID);
    if (div != null) {
        div.className = "current";
    }
}

function window_onload_sub_sub(pageID) {
    document.getElementById("sub_nav_col_" + pageID).className = "current";
}

function window_onload_window(pageID) {
    document.getElementById("window_nav_" + pageID).className = "current";
}

function window_onload_trajectory(pageID) {
    document.getElementById("traj_" + pageID).className = "current";
}

function window_onload_probes(pageID) {
    document.getElementById("probe_" + pageID).className = "current";
}

function window_onload_pathways(pageID) {
    document.getElementById("pathway_" + pageID).className = "current";
}

function window_onload_annotations(pageID) {
    document.getElementById("annotations_" + pageID).className = "current";
}

function window_onload_team(pageID) {
    document.getElementById("team_" + pageID).className = "current";
}

function window_onload_file_preview(pageID) {
    document.getElementById("file_sub_nav_" + pageID).className = "current";
}

function window_onload_int_data(pageID) {
    document.getElementById("int_data_sub_nav_" + pageID).className = "current";
}


function reset_nav(tag_name, div_prefix, classes) {
    var elements = document.getElementsByTagName(tag_name);
    for (var i = 0; i < elements.length; i++) {
        var element = elements[i];
        if (element.id.substr(0, div_prefix.length) == div_prefix) {
            element.className = classes;
        }
    }
}

function refresh_filter_summary_table(holer_div, tree_name) {
    if (document.getElementById(holer_div) != null) {
        new Zapatec.Tree({
            tree: tree_name,
            highlightSelectedNode : false,
            expandOnIconClick : false,
            theme : "default"
        });
    }
}
    
    
            
function getRequest() {
    var xmlHttp;
    try {
        // Firefox, Opera 8.0+, Safari
        xmlHttp=new XMLHttpRequest();
    }
    catch (e) {
        // Internet Explorer
        try {
            xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
        }
        catch (e) {
            try {
                xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
            }
            catch (e) {
                alert("Your browser does not support AJAX!");
                return null;
            }
        }
    }
    return xmlHttp;
}