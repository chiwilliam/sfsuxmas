function updateQuantityOfTrajectories(min, max, tpCount) {
    updateMaxQuantitiesOfTrajectories(min);
    var xhr = getRequest();
    xhr.onreadystatechange = function() {
        if(xhr.readyState  == 4) {
            if(xhr.status  == 200 || xhr.status  == 0) {
                // xhr.responseText;
                var timeperiods = xhr.responseText.split("|");
                // For each time period:
                for (var i = 0; i < timeperiods.length; i++) {
                    var timePeriod = timeperiods[i];
                    var trajCounts = timePeriod.split(",");
                    for (var j = 0; j < trajCounts.length; j++) {
                        var count = trajCounts[j];
                        var check_identifier = "";
                        var span_identifier = "";
                        var check_cell = "";
                        if (count == 0) {
                            // Empty traj:
                            check_identifier = "traj_check_" + (j + min) + "_" + (i+1);
                            span_identifier = "traj_span_" + (j + min) + "_" + (i+1);
                            document.getElementById(check_identifier).disabled = true;
                            
                            
                            var maxIdentifier = "traj_span_" + (j + min) + "_" + (i+1) + "_max";
                            if (document.getElementById(maxIdentifier).innerHTML.length > 0) {
                                document.getElementById(span_identifier).innerHTML = "0";
                            }
                            else {
                                document.getElementById(span_identifier).innerHTML = "";
                            }
                            
                            
                            check_cell = "traj_filter_" + (j + min) + "_" + (i+1);
                            document.getElementById(check_cell).className = "disabled";
                        }
                        else {
                            
                            check_cell = "traj_filter_" + (j + min) + "_" + (i+1);
                            //                            if (document.getElementById(check_cell).className != "highlighted") {
                    
                            check_identifier = "traj_check_" + (j + min) + "_" + (i+1);
                            document.getElementById(check_identifier).disabled = false;
                            //                            }
                            
                            span_identifier = "traj_span_" + (j + min) + "_" + (i+1);
                            document.getElementById(span_identifier).innerHTML = count;
                        }
                    }
                }
            }
            else {
                alert(xhr.responseText); 
            }
        }
    }
    var url = "../SGetPossibleTrajectories" + getCheckedBoxes(min, max, tpCount);
    xhr.open("GET", url,  true);
    xhr.send(null);
}

function updateMaxQuantitiesOfTrajectories(min) {
    var xhr = getRequest();
    xhr.onreadystatechange = function() {
        if(xhr.readyState  == 4) {
            if(xhr.status  == 200 || xhr.status  == 0) {
                // xhr.responseText;
                var timeperiods = xhr.responseText.split("|");
                // For each time period:
                for (var i = 0; i < timeperiods.length; i++) {
                    var timePeriod = timeperiods[i];
                    var trajCounts = timePeriod.split(",");
                    for (var j = 0; j < trajCounts.length; j++) {
                        var count = trajCounts[j];
                        var identifier;
                        if (count == 0) {
                            // Empty traj:
                            identifier = "traj_span_" + (j + min) + "_" + (i+1) + "_max";
                            document.getElementById(identifier).innerHTML = "";
                        }
                        else {
                            identifier = "traj_span_" + (j + min) + "_" + (i+1) + "_max";
                            document.getElementById(identifier).innerHTML = count;
                        }
                    }
                }
            }
            else {
                alert(xhr.responseText); 
            }
        }
    }
    var url = "../SGetPossibleTrajectories?max";
    xhr.open("GET", url,  true);
    xhr.send(null);
}


function getCheckedBoxes(min, max, tpCount) {
    var checkedBoxes = "";
    for (var i = 1; i <= tpCount; i++) {
        for (var j = min; j <= max; j++) {
            var checkName = "traj_check_" + j + "_" + i;
            var check = document.getElementById(checkName);
            var check_cell = "traj_filter_" + j + "_" + i;
            if (check.checked != 0) {
                if (checkedBoxes == "") {
                    checkedBoxes = "?";
                }
                else {
                    checkedBoxes = checkedBoxes + "&";
                }
                checkedBoxes = checkedBoxes + "traj_check_" + j + "_" + i;
                document.getElementById(check_cell).className = "highlighted";
            } else {
                document.getElementById(check_cell).className = "";
            }
        }
    }
    return checkedBoxes;
}








function filter_by_node_trajectories(max, minBin, maxBin, numberofTimePeriods) {
    loading_image();
    var xhr = getRequest();
    xhr.onreadystatechange = function() {
        if(xhr.readyState  == 4) {
            if(xhr.status  == 200 || xhr.status  == 0) {
                refresh_image();
                make_nav_request_traj("../modules/filter_trajectory_sub.jsp", minBin, maxBin, numberofTimePeriods);
            }
            else {
                alert(xhr.responseText); 
            }
        }
    }
    var url = "../SFilterManipulator?filterType=7&traj_count=" + max + "&" + get_checked_trajectories(max);
    xhr.open("GET", url,  true);
    xhr.send(null);
}

function get_checked_trajectories ( max ) {
    var traj_index = 0;
    
    var checkedBoxes = "";
    for (var i = 0; i < max; i++) {
        var checkName = "node_check_" + i;
        var check = document.getElementById(checkName);
        
        if (check != null && check.checked != 0) {
            if (checkedBoxes != "") {
                checkedBoxes = checkedBoxes + "&";
            }
            checkedBoxes = checkedBoxes + "node_" + traj_index + "=" + check.value;
            traj_index++;
        }
    }
    if (traj_index > 0) {
        checkedBoxes = checkedBoxes + "&check_count=" + traj_index;
    }
    return checkedBoxes;
}
