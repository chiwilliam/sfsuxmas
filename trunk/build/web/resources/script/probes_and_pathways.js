function grow(identifier) {
    var attributeLink = document.getElementById(identifier);
    var attributeLinkFull = document.getElementById(identifier + "_full");
    var attributeGrow = document.getElementById(identifier + "_G");
    var attributeShrink = document.getElementById(identifier + "_S");
    
    if (attributeLink != null && attributeLinkFull != null) {
        attributeLink.style.display = "none";
        attributeLinkFull.style.display = "inline";
        if (attributeGrow != null && attributeShrink != null) {
            attributeGrow.style.display = "none";
            attributeShrink.style.display = "inline";
        }
    }
}
function shrink(identifier) {
    var attributeLink = document.getElementById(identifier);
    var attributeLinkFull = document.getElementById(identifier + "_full");
    var attributeGrow = document.getElementById(identifier + "_G");
    var attributeShrink = document.getElementById(identifier + "_S");
    
    if (attributeLink != null && attributeLinkFull != null) {
        attributeLink.style.display = "inline";
        attributeLinkFull.style.display = "none";
        if (attributeGrow != null && attributeShrink != null) {
            attributeGrow.style.display = "inline";
            attributeShrink.style.display = "none";
        }
    }
}


function get_probe_note_box (id) {
    var noteDiv = document.getElementById('probe_note_' + id);
    var toggle = document.getElementById('note_toggle_' + id);
    if (noteDiv != null) {
        noteDiv.style.display = 'inline';
        toggle.style.display = 'none';
    }
}

function save_probe_note_box (id) {
    var xhr = getRequest();
    var probe_note = document.getElementById('probe_note_text_' + id).value;
    xhr.onreadystatechange = function() {
        if(xhr.readyState  == 4) {
            if(xhr.status  == 200 || xhr.status  == 0) {
                var response = xhr.responseText;
                if (response != "1") {
                    probe_note = "Click to edit...";
                }
                var noteDiv = document.getElementById('probe_note_text_' + id);
                var toggle = document.getElementById('note_toggle_' + id);
                if (noteDiv != null) {
                    noteDiv.innerHTML = probe_note;
                }
                if (toggle != null) {
                    toggle.innerHTML = probe_note;
                }
                cancel_probe_note_box(id);
            }
            else {
                alert(xhr.responseText); 
            }
        }
    };
    xhr.open("GET", "../SAddProbeNote?probe_id=" + id + "&probe_note=" + encodeURIComponent(probe_note),  true);
    xhr.send(null);
}

function cancel_probe_note_box (id) {
    var noteDiv = document.getElementById('probe_note_' + id);
    var toggle = document.getElementById('note_toggle_' + id);
    if (noteDiv != null) {
        noteDiv.style.display = 'none';
        toggle.style.display = 'inline';
    }
}

function change_label_type() {
    var found_it;
    for (var i=0; i<document.batch_labeler_form.label_type.length; i++)  {
        if (document.batch_labeler_form.label_type[i].checked)  {
            found_it = document.batch_labeler_form.label_type[i].value;
        }
    }
    if (found_it == "existing_label") {
        // collapse new_label, open existing_label
        collapse_area('new_label');
        open_area('existing_label');
    } else {
        collapse_area('existing_label');
        open_area('new_label');
    }
    return;
}

function create_annotation() {
    var xhr = getRequest();
    var annotationName = document.getElementById('new_annotation_input').value;
    xhr.onreadystatechange = function() {
        if(xhr.readyState  == 4) {
            if(xhr.status  == 200 || xhr.status  == 0) {
                
            }
            else {
                alert(xhr.responseText); 
            }
        }
    };
    xhr.open("GET", "SNewAnnotation?new_annotation_input=" + annotationName,  true);
    xhr.send(null);
}

function de_annotate(probe_id, anno) {
    var xhr = getRequest();
    xhr.onreadystatechange = function() {
        if(xhr.readyState  == 4) {
            if(xhr.status  == 200 || xhr.status  == 0) {
                
            }
            else {
                alert(xhr.responseText); 
            }
        }
    };
    xhr.open("GET", "SDeassignProbeAnnotation?probe_id=" + probe_id + "&annotation_id=" + anno,  true);
    xhr.send(null);
}

function un_check(first, last, base_id) {
    for (var i = first; i < last; i++) {
        var checkBox = document.getElementById(base_id + i);
        if (checkBox.disabled == true) {
            
        } else {
            checkBox.checked = false;   
        }
    }
}

function check(first, last, base_id) {
    for (var i = first; i < last; i++) {
        var checkBox = document.getElementById(base_id + i);
        if (checkBox.disabled == true) {
            
        } else {
            checkBox.checked = true;   
        }
    }
}

function refresh_image() {
    var main_image = document.getElementById('main_image');
    if (main_image == null) {
        // Request from window
        main_image = parent.document.getElementById('main_image');
    }
    var bypassCaching = "ran_" + Math.random();
    main_image.src = "../SLoadViz?bypass=" + bypassCaching;
    done_loading_image();
}

function loading_image() {
    var main_image = document.getElementById('main_image');
    if (main_image == null) {
        main_image = parent.document.getElementById('main_image');
    }
    
    main_image.style.opacity = '0.5';
    main_image.style.MozOpacity = '0.5';
    main_image.style.filter = 'alpha(opacity=' + 0.5 + ')';
    
    var loading_area = document.getElementById('viz_loading_cont');
    if (loading_area == null) {
        loading_area = parent.document.getElementById('viz_loading_cont');
    }
    if (loading_area != null) {
        loading_area.style.display = 'inline';
        loading_area.style.opacity = '0.6';
        loading_area.style.MozOpacity = '0.6';
        loading_area.style.filter = 'alpha(opacity=' + 0.6 + ')';
        
        var loading_content = document.getElementById('loading_content');
        if (loading_content != null) {
            loading_content.style.opacity = '1';
            loading_content.style.MozOpacity = '1';
            loading_content.style.filter = 'alpha(opacity=' + 1 + ')';
        }
    }
}

function done_loading_image() {
    var main_image = document.getElementById('main_image');
    if (main_image == null) {
        main_image = parent.document.getElementById('main_image');
    }
    
    main_image.style.opacity = '1';
    main_image.style.MozOpacity = '1';
    main_image.style.filter = 'alpha(opacity=' + 1 + ')';
    
    var loading_area = document.getElementById('viz_loading_cont');
    if (loading_area == null) {
        loading_area = parent.document.getElementById('viz_loading_cont');
    }
    if (loading_area != null) {
        loading_area.style.display = 'none';
    }
}

function check_tag() {
    var tag_area = document.getElementById("label_id");
    if (tag_area != null) {
        if (tag_area.value == "X") {
            request_new_label();
            //            request_new_label_description();
            return false;
        }
    }
    return true;
}

function request_new_label() {
    var newTag = prompt('Enter a new label name:', '');
            
    // Try to create a new annotation
    // Should do a uniqueness check here
    // Create annotation:
    var xhr = getRequest();
    xhr.onreadystatechange = function() {
        if(xhr.readyState  == 4) {
            if(xhr.status  == 200 || xhr.status  == 0) {
                var resp = xhr.responseText;
                if (resp == "0") {
                    // Unsuccessful insert
                    var contin = confirm('Label already exists. Try again');
                    if (contin) {
                        askForNewAnnotation();
                    } else {
                        
                    }
                } else if (resp > 0) {
                    // Response is new ID
                    // Success insert
                    
                    // insert worked
                        
                    // Make system use this anno
                        
                    var tag_area = document.getElementById("label_id");
                    var numbOptions = tag_area.length;
                    var newOption = new Option(newTag, resp);
                    tag_area[numbOptions] = newOption;
                    tag_area.selectedIndex = numbOptions;
                            
                } else {
                    // No ID returned for anno desc
                }
            }
            else {
                alert(xhr.responseText); 
            }
        }
    };
    xhr.open("GET", "../SNewLabel?new_annotation_input=" + newTag, true);
    xhr.send(null);
}

function populate_pcad_data(first, last) {
    for (var i = first; i < last; i++) {
        var checkBox = document.getElementById('probe_checkbox_' + i);
        if (checkBox != null) {
            var probe_id = checkBox.value;
            if (probe_id != null && probe_id != '') {
                make_pcad_request(probe_id);
            }
        }
    }
}

function make_pcad_request(probe_id) {
    var pcadDataCell = document.getElementById('probe_cell_' + probe_id + '_sub_pcad');
    if (pcadDataCell != null) {
        var xhr = getRequest();
        xhr.onreadystatechange = function() {
            if(xhr.readyState  == 4) {
                if(xhr.status  == 200 || xhr.status  == 0) {
                    var resp = xhr.responseText;
                    pcadDataCell.innerHTML = resp;
                }
                else {
                    alert(xhr.responseText); 
                }
            }
        };
        xhr.open("GET", "../SGetPCADData?probe_id=" + probe_id, true);
        xhr.send(null);
    }
}

function ensure_checked(firstInd, lastInd, identifier) {
    var messageBox = document.getElementById('sidebar_messages');
    
    if (messageBox != null) {
        messageBox.className = "";
        messageBox.innerHTML = "";
    } else {
        // alert("No message box");
    }
    
    var currentTime = new Date();
    var hours = currentTime.getHours();
    var minutes = currentTime.getMinutes();
    var seconds = currentTime.getSeconds();
    if (minutes < 10){
        minutes = "0" + minutes
    }
    if (seconds < 10){
        seconds = "0" + seconds
    }
    var timeString = hours + ":" + minutes + ":" + seconds + " ";
    if(hours > 11){
        timeString = timeString + "PM";
    } else {
        timeString = timeString + "AM";
    }

    timeString = "<span style=\"float: right; color: gray; font-size: 8px;\">" + timeString + "</span>";
    
    
    for (var i = firstInd; i < lastInd; i++) {
        var checkBox = document.getElementById(identifier + i);
        if (checkBox != null && checkBox.disabled == false && checkBox.checked == true) {
            return true;
        }
    }
    if (messageBox != null) {
        messageBox.className = "error";
        messageBox.innerHTML = timeString + "Nothing new selected to filter";
    }
    return false;
}

function highlightProbe(probe_id, button_id, original_class) {
    var baseUrl = '../resources/images/';
    loading_image();
    var xhr = getRequest();
    xhr.onreadystatechange = function() {
        if(xhr.readyState  == 4) {
            if(xhr.status  == 200 || xhr.status  == 0) {
                var row = document.getElementById('probe_row_' + probe_id);
                var row_sub = document.getElementById('probe_row_' + probe_id + '_sub');
                var row_sub_pcad = document.getElementById('probe_row_' + probe_id + '_sub_pcad');
                var button = document.getElementById('probe_button_' + button_id);
                if (xhr.responseText == 1) {
                    if (row != null) {
                        row.className = "highlighted";
                    }
                    if (row_sub != null) {
                        row_sub.className = "highlighted";
                    }
                    if (row_sub_pcad != null) {
                        row_sub_pcad.className = "highlighted";
                    }
                    button.src = baseUrl + "high.png";
                } else {
                    if (row != null) {
                        row.className = original_class;
                    }
                    if (row_sub != null) {
                        row_sub.className = original_class;
                    }
                    if (row_sub_pcad != null) {
                        row_sub_pcad.className = original_class;
                    }
                    button.src = baseUrl + "unhigh.png";
                }
                refresh_image();
            }
            else {
                alert(xhr.responseText); 
            }
        }
    };
    xhr.open("GET", "../SHighlightProbes?probe_id=" + probe_id, true);
    xhr.send(null);
}

function highlightPathway(pathway_id, button_id, original_class) {
    var baseUrl = '../resources/images/';
    loading_image();
    var xhr = getRequest();
    xhr.onreadystatechange = function() {
        if(xhr.readyState  == 4) {
            if(xhr.status  == 200 || xhr.status  == 0) {
                var row = document.getElementById('pathway_row_' + pathway_id);
                var row_sub = document.getElementById('pathway_row_' + pathway_id + '_sub');
                var button = document.getElementById('pathway_button_' + button_id);
                if (xhr.responseText == 1) {
                    row.className = "highlighted";
                    if (row_sub != null) {
                        row_sub.className = "highlighted";
                    }
                    button.src = baseUrl + "high.png";
                } else {
                    row.className = original_class;
                    if (row_sub != null) {
                        row_sub.className = original_class;
                    }
                    button.src = baseUrl + "unhigh.png";
                }
                refresh_image();
            }
            else {
                alert(xhr.responseText); 
            }
        }
    };
    xhr.open("GET", "../SHighlightPathway?pathway_id=" + encodeURIComponent(pathway_id), true);
    xhr.send(null);
}

function highlightGOTerm(go_term_id, button_id, original_class) {
    var baseUrl = '../resources/images/';
    loading_image();
    var xhr = getRequest();
    xhr.onreadystatechange = function() {
        if(xhr.readyState  == 4) {
            if(xhr.status  == 200 || xhr.status  == 0) {
                var row = document.getElementById('go_term_row_' + go_term_id);
                var row_sub = document.getElementById('go_term_row_' + go_term_id + '_sub');
                var button = document.getElementById('go_term_button_' + button_id);
                if (xhr.responseText == 1) {
                    row.className = "highlighted";
                    if (row_sub != null) {
                        row_sub.className = "highlighted";
                    }
                    button.src = baseUrl + "high.png";
                } else {
                    row.className = original_class;
                    if (row_sub != null) {
                        row_sub.className = original_class;
                    }
                    button.src = baseUrl + "unhigh.png";
                }
                refresh_image();
            }
            else {
                alert(xhr.responseText); 
            }
        }
    };
    xhr.open("GET", "../SHighlightGOTerm?go_term_id=" + encodeURIComponent(go_term_id), true);
    xhr.send(null);
}

function highlightAnnotation(annotation_id, button_id, original_class) {
    var baseUrl = '../resources/images/';
    loading_image();
    var xhr = getRequest();
    xhr.onreadystatechange = function() {
        if(xhr.readyState  == 4) {
            if(xhr.status  == 200 || xhr.status  == 0) {
                var row = document.getElementById('annotation_row_' + annotation_id);
                var row_sub = document.getElementById('annotation_row_' + annotation_id + '_sub');
                var button = document.getElementById('annotation_button_' + button_id);
                if (xhr.responseText == 1) {
                    row.className = "highlighted";
                    if (row_sub != null) {
                        row_sub.className = "highlighted";
                    }
                    button.src = baseUrl + "high.png";
                } else {
                    row.className = original_class;
                    if (row_sub != null) {
                        row_sub.className = original_class;
                    }
                    button.src = baseUrl + "unhigh.png";
                }
                refresh_image();
            }
            else {
                alert(xhr.responseText); 
            }
        }
    };
    xhr.open("GET", "../SHighlightAnnotation?annotation_id=" + encodeURIComponent(annotation_id), true);
    xhr.send(null);
}



function highlightTrajCell(node_id) {
    var cell = document.getElementById('node_cell_' + node_id);
    var check = document.getElementById('node_check_' + node_id);
    if (!check.checked) {
        cell.className = "traj_normal";
    }
    else
    {
        cell.className = "traj_selected";
    }
}

function toggle_trajectory_group(identifier) {
    var area = document.getElementById("trajectory_group_" + identifier);
    if (area.style.display != "none") {
        area.style.display = "none";
    } else {
        area.style.display = "inline";
    }
}
