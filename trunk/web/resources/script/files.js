function window_onload_files() {
    check_trajectory_filename();
    refresh_file_table();
}

//function refresh_file_table() {
//    if (document.getElementById('file_list_holder') != null) {
//        var xhr = getRequest();
//        xhr.onreadystatechange = function() {
//            if(xhr.readyState  == 4) {
//                if(xhr.status  == 200 || xhr.status  == 0) {
//                    var st = xhr.responseText;
//                    document.getElementById('file_list_holder').innerHTML = st;
//                    refresh_filter_summary_table('file_list_holder', 'fileTree');
//                }
//                else {
//                    alert(xhr.responseText); 
//                }
//            }
//        };
//        xhr.open("GET", "../SGetTrajectoryFiles",  true);
//        xhr.send(null);
//    }
//}

function refresh_file_table() {
    loading_file_table();
    if (document.getElementById('trajectory_file_table') != null) {
        var xhr = getRequest();
        xhr.onreadystatechange = function() {
            if(xhr.readyState  == 4) {
                if(xhr.status  == 200 || xhr.status  == 0) {
                    var st = xhr.responseText;
                    document.getElementById('trajectory_file_table').innerHTML = st;
                }
                else {
                    alert(xhr.responseText); 
                }
            }
        };
        xhr.open("GET", "../data/files.jsp",  true);
        xhr.send(null);
    }
}

function loading_file_table() {
    document.getElementById('trajectory_file_table').innerHTML = "<div class=\"loading_container\"><p><b>Loading...</b></p><br /><img src=\"../resources/images/loading.gif\" /></div>";
}

function refresh_snapshot_table() {
    if (document.getElementById('file_list_holder') != null) {
        var xhr = getRequest();
        xhr.onreadystatechange = function() {
            if(xhr.readyState  == 4) {
                if(xhr.status  == 200 || xhr.status  == 0) {
                    var st = xhr.responseText;
                    document.getElementById('file_list_holder').innerHTML = st;
                    refresh_filter_summary_table('file_list_holder', 'fileTree');
                }
                else {
                    alert(xhr.responseText); 
                }
            }
        };
        xhr.open("GET", "../SGetSnapShots",  true);
        xhr.send(null);
    }
}

function refresh_cluster_table() {
    if (document.getElementById('file_list_holder') != null) {
        var xhr = getRequest();
        xhr.onreadystatechange = function() {
            if(xhr.readyState  == 4) {
                if(xhr.status  == 200 || xhr.status  == 0) {
                    var st = xhr.responseText;
                    document.getElementById('file_list_holder').innerHTML = st;
                    refresh_filter_summary_table('file_list_holder', 'fileTree');
                }
                else {
                    alert(xhr.responseText); 
                }
            }
        };
        xhr.open("GET", "../SGetClusters",  true);
        xhr.send(null);
    }
}

function check_generate_file(type) {
    var file_name_input = document.getElementById('file_name');
    var file_name = file_name_input.value;
    
    if (file_name != null && file_name != "") {
        // Check it is a valid name
        var xhr = getRequest();
        xhr.onreadystatechange = function() {
            if(xhr.readyState  == 4) {
                if(xhr.status  == 200 || xhr.status  == 0) {
                    var st = xhr.responseText;
                    if (st == "unique") {
                        creating();
                        return true;
                    }
                    else {
                        error_creating("Duplicate or no file name - please specify a new file name");
                        return false;
                    }
                }
                else {
                    alert(xhr.responseText); 
                    return false;
                }
            } else {
                return false;
            }
        };
        xhr.open("GET", "../STrajectoryFileNameOriginal?file_name=" + encodeURIComponent(file_name),  false);
        xhr.send(null);
        return true;
    } else {
        error_creating("Duplicate or no file name - please specify a new file name");
        return false;
    }
}

function updateBinUnit(expressionRange) {
    var binUnit = document.getElementById("bin_unit");
    var binCount = document.getElementById("bin_count");
    binCount.value = Math.ceil(expressionRange/binUnit.value);
    
}

function updateK(maxK) {
    var k = document.getElementById("kmeansk");
    if (parseInt(k.value) > maxK) {
        k.value = maxK;
    }
}

function load_file(fileName) {
    loading();
    var xhr = getRequest();
    xhr.onreadystatechange = function() {
        if(xhr.readyState  == 4) {
            if(xhr.status  == 200 || xhr.status  == 0) {
                var response = xhr.responseText;
                done_loading(response);
                // done_loading("File Load Success: <a href=\"../visualization/visualization.jsp\" target=\"_parent\">REFRESH VISUALIZATION</a>");
                refresh_file_table();
            }
            else {
                alert(xhr.responseText); 
            }
        }
    };
    xhr.open("GET", "../SLoadTrajectoryFile?file_name=" + fileName,  true);
    xhr.send(null);
}

function load_filters(fileName) {
    loading();
    var xhr = getRequest();
    xhr.onreadystatechange = function() {
        if(xhr.readyState  == 4) {
            if(xhr.status  == 200 || xhr.status  == 0) {
                done_loading("Filter SnapShot Load Success: <a href=\"../visualization/visualization.jsp\" target=\"_parent\">REFRESH VISUALIZATION</a>");
                refresh_file_table();
            }
            else {
                alert(xhr.responseText); 
            }
        }
    };
    xhr.open("GET", "../SLoadFilterSnapShot?fileName=" + fileName,  true);
    xhr.send(null);
}

function load_cluster(fileName) {
    loading();
    var xhr = getRequest();
    xhr.onreadystatechange = function() {
        if(xhr.readyState  == 4) {
            if(xhr.status  == 200 || xhr.status  == 0) {
                done_loading("Cluster Load Success: <a href=\"../visualization/visualization_cluster.jsp\" target=\"_parent\">REFRESH VISUALIZATION</a>");
                refresh_file_table();
            }
            else {
                alert(xhr.responseText); 
            }
        }
    };
    xhr.open("GET", "../SLoadCluster?fileName=" + fileName,  true);
    xhr.send(null);
}



function check_trajectory_filename() {
    var msg_duplicate = "Name already in use";
    var msg_empty = "You must specify a name";
    
    var error_message_space = document.getElementById("file_name_error");
    var submit_button = document.getElementById("create_file_button");
    
    var file_name_input = document.getElementById('file_name');
    if (file_name_input != null) {
        var file_name = file_name_input.value;
        if (file_name.length > 0) {
            var xhr = getRequest();
            xhr.onreadystatechange = function() {
                if(xhr.readyState  == 4) {
                    if(xhr.status  == 200 || xhr.status  == 0) {
                        var st = xhr.responseText;
                        if (st == "unique") {
                            error_message_space.innerHTML = "";
                            error_message_space.style.display = "none";
                            submit_button.disabled = false;
                        }
                        else if (st == "duplicate") {
                            error_message_space.innerHTML = "";
                            error_message_space.style.display = "none";
                            error_message_space.innerHTML = msg_duplicate;
                            error_message_space.style.display = "inline";
                            submit_button.disabled = true;
                        }
                        else {
                            error_message_space.innerHTML = msg_empty;
                            error_message_space.style.display = "inline";
                            submit_button.disabled = true;
                        }
                    }
                    else {
                        alert(xhr.responseText); 
                    }
                }
            };
            xhr.open("GET", "../STrajectoryFileNameOriginal?file_name=" + encodeURIComponent(file_name),  true);
            xhr.send(null);
        }
        else {
            error_message_space.innerHTML = msg_empty;
            error_message_space.style.display = "inline";
            submit_button.disabled = true;
        }
    }
}





function check_capture_filename() {
    var msg_empty = "You must specify a name";
    
    var error_message_space = document.getElementById("file_name_error");
    var submit_button = document.getElementById("create_capture_button");
    
    var file_name_input = document.getElementById('capture_name');
    if (file_name_input != null) {
        var file_name = file_name_input.value;
        if (file_name.length > 0) {
            error_message_space.innerHTML = "";
            error_message_space.style.display = "none";
            submit_button.disabled = false;
        }
        else {
            error_message_space.innerHTML = msg_empty;
            error_message_space.style.display = "inline";
            submit_button.disabled = true;
        }
    }
}

function loading() {
    document.getElementById('file_load_message').innerHTML = "<div class=\"loading_container\"><p><b>Loading...</b></p><br /><img src=\"../resources/images/loading.gif\" /></div>";
}

function done_loading(message) {
    document.getElementById('file_load_message').innerHTML = message;
}

function done_creating(message) {
    document.getElementById('file_creation_message').innerHTML = "<div class=\"success\">" + message + "</div>";
}
function creating() {
    document.getElementById('file_creation_message').innerHTML = "<div class=\"loading_container\"><p><b>Loading...</b></p><br /><img src=\"../resources/images/loading.gif\" /></div>";
}
function error_creating(message) {
    document.getElementById('file_creation_message').innerHTML = "<div class=\"error\">" + message + "</div>";
}