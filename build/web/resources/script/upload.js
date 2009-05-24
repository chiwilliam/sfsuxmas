function window_onload_upload() {
    check_expression_data_name();
    populate_preview_table();
}

function preview_file() {
    var file_name_input = document.getElementById('file_name');
    var file_name = file_name_input.value;
    preview_samples(file_name);
}

function preview_samples(file_name) {
    document.getElementById('samples_preview').src = "file_sample_preview.jsp?fileName=" + file_name;
}

function check_expression_data_name() {
    var msg_duplicate = "Name already in use";
    var msg_empty = "You must specify a name";
    
    var error_message_space = document.getElementById("file_name_error");
    var submit_button = document.getElementById("upload_button");
    
    var file_name_input = document.getElementById('database_name');
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
            xhr.open("GET", "../SDatabaseNameOriginal?database_name=" + encodeURIComponent(file_name),  true);
            xhr.send(null);
        }
        else {
            error_message_space.innerHTML = msg_empty;
            error_message_space.style.display = "inline";
            submit_button.disabled = true;
        }
    }
}

function populate_preview_table() {
    var file_name_input = document.getElementById('file_name');
    if (file_name_input != null) {
        var file_name = file_name_input.value;
        var xhr;
        xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function() {
            if(xhr.readyState  == 4) {
                if(xhr.status  == 200 || xhr.status  == 0) {
                    var st = xhr.responseText;
                    var sampleCounter = 0;
                
                    var samples = st.split("|");
                
                    var selectHTML = "<select>";
                
                    for (var i = 0; i < samples.length; i++) {
                        var sample = samples[i];
                        selectHTML += "<option>" + sample + "</option>";
                        sampleCounter++;
                    }
                
                    selectHTML += "</select>";
                
                    document.getElementById("preview_samples").innerHTML = selectHTML;
                    document.getElementById("preview_sample_count").innerHTML = sampleCounter;
                }
                else {
                    alert(xhr.responseText); 
                }
            }
        };
        xhr.open("GET", "../SGetSamples?file_name=" + file_name,  true);
        xhr.send(null);
    }
}

function get_time_period_descriptors() {
    var selectionArea = document.getElementById('timePeriodCount');
    
    var prev_sample_count_div = document.getElementById("preview_sample_count");
    var previewSampleCount = 0;
    if (prev_sample_count_div != null) {
        previewSampleCount = prev_sample_count_div.innerHTML;        
    }
    
    var numberOfTimePeriods = selectionArea.value;
    
    var htmlCode = 
        "<p>Distribute your samples between the " + numberOfTimePeriods + " time periods</p>" +
        "<table class=\"time_period_data\">" +
        "<thead>" +
        "<tr>" +
        "<th />" +
        "<th># Samples</th>" +
        "<th>Description</th>" +
        "</tr>" +
        "</thead>" +
        "<tbody>";
    
    var samplesPerPeriod = Math.floor(previewSampleCount/numberOfTimePeriods);
    
    for (var i = 0; i < numberOfTimePeriods; i++) {
        var sampInTP = samplesPerPeriod;
        if (i + 1 == numberOfTimePeriods) {
            sampInTP = previewSampleCount - ((numberOfTimePeriods - 1) * samplesPerPeriod);
        }
        htmlCode += "<tr>" +
            "<th>" + (i + 1) + "</th>" + 
            "<td><input type=\"text\" name=\"timePeriodSampleCount" + i + "\" id=\"timePeriodSampleCount" + i + "\" style=\"width: 30px;\" value=\"" + sampInTP + "\" " + 
            "onChange=\"return updateTotal(" + numberOfTimePeriods + ");\" " +
            "onFocus=\"return updateTotal(" + numberOfTimePeriods + ");\" " +
            "onBlur=\"return updateTotal(" + numberOfTimePeriods + ");\" " +
            "onSelect=\"return updateTotal(" + numberOfTimePeriods + ");\" " +
            "onkeyup=\"return updateTotal(" + numberOfTimePeriods + ");\" " +
            " /></td>";
        htmlCode += "<td><input type=\"text\" name=\"timePeriodDescription" + i + "\" id=\"timePeriodDescription" + i + "\" width=\"50\" value=\"TP " + (i + 1) + "\" /></td>" +
            "</tr>";
    }
    
    htmlCode += "<tr>" + 
        "<th>Total:</th>" +
        "<td><input type=\"text\" name=\"sumSamples\" id=\"sumSamples\" style=\"width: 30px;\" value=\"" + previewSampleCount + "\" disabled /></td>" +
        "<td />" +
        "</tr>";
    
    
    htmlCode += 
        "</tbody>" +
        "</table";
    
    document.getElementById('timePeriodDescriptor').innerHTML = htmlCode;
    document.getElementById('intall_button').style.display =  "inline";
}

function updateTotal(numberOfTimePeriods) {
    var sum = 0;
    for (var i = 0; i < numberOfTimePeriods; i++) {
        var tpQuant = document.getElementById('timePeriodSampleCount' + i);
        var coun = parseInt(tpQuant.value);
        sum = sum + coun;
    }
    document.getElementById('sumSamples').value = sum;
}

function validate_upload() {
    creating();
    var name_input = document.getElementById('database_name');
    var name = name_input.value;
    
    
    var previewSampleCount = document.getElementById("preview_sample_count").innerHTML;
    var tpSampleCountInput = document.getElementById("sumSamples");
    var tpSampleCount = 0;
    if (tpSampleCountInput != null) {
        tpSampleCount = tpSampleCountInput.value;
    } else {
        error_creating("You have not specified time period information");
        return false;
    }
    
    if (previewSampleCount != tpSampleCount) {
        error_creating("The number of samples in the file does not match the number assigned to time periods");
        return false;
    }
    
    if (name.length > 0) {
        // Check it is a valid name
        var xhr;
        xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function() {
            if(xhr.readyState  == 4) {
                if(xhr.status  == 200 || xhr.status  == 0) {
                    var st = xhr.responseText;
                    if (st == "unique") {
                        return true;
                    }
                    else {
                        error_creating("The file name you have specified is either already in use, or not valid");
                        return false;
                    }
                }
                else {
                    alert(xhr.responseText); 
                    return false;
                }
            } else {
                alert(xhr.responseText); 
                return false;
            }
        };
        xhr.open("GET", "../SDatabaseNameOriginal?databaseName=" + name,  false);
        xhr.send(null);
        return true;
    } else {
        error_creating("Empty file name");
        return false;
    }
    
}


function toggle_preview() {
    var preview_area = document.getElementById("file_preview");
    if (preview_area.style.display=="block" || preview_area.style.display=="inline") {
        preview_area.style.display="none";
    } else {
        preview_area.style.display="inline";
    }
}


function creating() {
    document.getElementById('file_creation_message').innerHTML = "<div class=\"loading_container\"><p><b>Loading...</b></p><br /><img src=\"../resources/images/loading.gif\" /></div>";
}
function error_creating(message) {
    document.getElementById('file_creation_message').innerHTML = "<div class=\"error\">" + message + "</div>";
}