function window_onload_upload_integration() {
    check_library_database_name();
}

function check_library_database_name() {
    var msg_duplicate = "This name already exists";
    var msg_empty = "You must specify a name";
    
    var error_message_space = document.getElementById("data_name_error");
    
    var file_name_input = document.getElementById('database_name');
    var file_name = file_name_input.value;
    if (file_name.length > 0) {
        var xhr;
        xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function() {
            if(xhr.readyState  == 4) {
                if(xhr.status  == 200 || xhr.status  == 0) {
                    var st = xhr.responseText;
                    if (st == "unique") {
                        error_message_space.innerHTML = "";
                        error_message_space.style.display = "none";
                    }
                    else if (st == "duplicate") {
                        error_message_space.innerHTML = "";
                        error_message_space.style.display = "none";
                        //                        error_message_space.innerHTML = msg_duplicate;
                        //                        error_message_space.style.display = "inline";
                    }
                    else {
                        error_message_space.innerHTML = msg_empty;
                        error_message_space.style.display = "inline";
                    }
                }
                else {
                    alert(xhr.responseText); 
                }
            }
        };
        xhr.open("GET", "../SDatabaseNameOriginal?integrated&databaseName=" + file_name,  true);
        xhr.send(null);
    }
    else {
        error_message_space.innerHTML = msg_empty;
        error_message_space.style.display = "inline";
    }
}

function validate_library_upload() {
    creating_knowledge_library();
    var name_input = document.getElementById('database_name');
    var name = name_input.value;
    var probe_attribute_file_name = document.getElementById("file_name").value;
    if (probe_attribute_file_name.length <= 0) {
        error_creating_annotation("You need to specify a probe attribute file");
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
                        error_creating_annotation("The file name you have specified is either already in use, or not valid");
                        return false;
                    }
                }
            }
        };
        xhr.open("GET", "../SDatabaseNameOriginal?integrated&databaseName=" + name,  true);
        xhr.send(null);
    } else {
        error_creating_annotation("Empty file name");
        return false;
    }
}
function creating_knowledge_library() {
    document.getElementById('knowledge_library_creation_message').innerHTML = "<div class=\"loading_container\"><p><b>Loading...</b></p><br /><img src=\"../resources/images/loading.gif\" /></div>";
}

function validate_upload() {
    creating_annotation();
    
    var probe_attribute_file_name = document.getElementById("pathway_file_name").value;
    if (probe_attribute_file_name.length <= 0) {
        error_creating_annotation("You need to specify a probe attribute file");
        return false;
    }
    return true;
}


function creating_annotation() {
    document.getElementById('file_creation_message').innerHTML = "<div class=\"loading_container\"><p><b>Loading...</b></p><br /><img src=\"../resources/images/loading.gif\" /></div>";
}
function error_creating_annotation(message) {
    document.getElementById('file_creation_message').innerHTML = "<div class=\"error\">" + message + "</div>";
}