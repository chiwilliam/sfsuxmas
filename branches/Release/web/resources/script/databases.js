function load_expression_database(database_id, secondary) {
    loading_expression_database();
    var xhr = getRequest();
    xhr.onreadystatechange = function() {
        if(xhr.readyState  == 4) {
            if(xhr.status  == 200 || xhr.status  == 0) {
                var response = xhr.responseText;
                done_loading_expression_database(response);
                refresh_expression_databases();
                refresh_file_table();
            }
            else {
                alert(xhr.responseText); 
            }
        }
    };
    var secondary_string = "";
    if (secondary) {
        secondary_string = "&secondary=true";
    }
    xhr.open("GET", "../SLoadExpressionDatabase?database_id=" + database_id + secondary_string,  true);
    xhr.send(null);
}

function load_knowledge_database(database_id) {
    loading_knowledge_database();
    var xhr = getRequest();
    xhr.onreadystatechange = function() {
        if(xhr.readyState  == 4) {
            if(xhr.status  == 200 || xhr.status  == 0) {
                var response = xhr.responseText;
                done_loading_knowledge_database(response);
                refresh_knowledge_databases();
            }
            else {
                alert(xhr.responseText); 
            }
        }
    };
    xhr.open("GET", "../SLoadKnowledgeDatabase?database_id=" + database_id,  true);
    xhr.send(null);
}

function refresh_knowledge_databases() {
    loading_knowledge_table();
    if (document.getElementById('knowledge_data_table') != null) {
        var xhr = getRequest();
        xhr.onreadystatechange = function() {
            if(xhr.readyState  == 4) {
                if(xhr.status  == 200 || xhr.status  == 0) {
                    var st = xhr.responseText;
                    document.getElementById('knowledge_data_table').innerHTML = st;
                }
                else {
                    alert(xhr.responseText); 
                }
            }
        };
        xhr.open("GET", "../data/databases_integrated.jsp",  true);
        xhr.send(null);
    }
}

function loading_knowledge_table() {
    document.getElementById('knowledge_data_table').innerHTML = "<div class=\"loading_container\"><p><b>Loading...</b></p><br /><img src=\"../resources/images/loading.gif\" /></div>";
}

function refresh_expression_databases() {
    loading_expression_table();
    if (document.getElementById('expression_data_table') != null) {
        var xhr = getRequest();
        xhr.onreadystatechange = function() {
            if(xhr.readyState  == 4) {
                if(xhr.status  == 200 || xhr.status  == 0) {
                    var st = xhr.responseText;
                    document.getElementById('expression_data_table').innerHTML = st;
                }
                else {
                    alert(xhr.responseText); 
                }
            }
        };
        xhr.open("GET", "../data/databases.jsp",  true);
        xhr.send(null);
    }
}

function loading_expression_table() {
    document.getElementById('expression_data_table').innerHTML = "<div class=\"loading_container\"><p><b>Loading...</b></p><br /><img src=\"../resources/images/loading.gif\" /></div>";
}

function unload_expression_database(secondary) {
    loading_expression_database();
    var xhr = getRequest();
    xhr.onreadystatechange = function() {
        if(xhr.readyState  == 4) {
            if(xhr.status  == 200 || xhr.status  == 0) {
                var response = xhr.responseText;
                done_loading_expression_database(response);
                refresh_expression_databases();
                refresh_file_table();
            }
            else {
                alert(xhr.responseText); 
            }
        }
    };
    var secondary_string = "";
    if (secondary) {
        secondary_string = "secondary=true";
    }
    xhr.open("GET", "../SUnloadExpressionDatabase?"+ secondary_string,  true);
    xhr.send(null);
}

function unload_knowledge_database() {
    loading_knowledge_database();
    var xhr = getRequest();
    xhr.onreadystatechange = function() {
        if(xhr.readyState  == 4) {
            if(xhr.status  == 200 || xhr.status  == 0) {
                var response = xhr.responseText;
                done_loading_knowledge_database(response);
                refresh_knowledge_databases()();
            }
            else {
                alert(xhr.responseText); 
            }
        }
    };
    xhr.open("GET", "../SUnloadKnowledgeDatabase?",  true);
    xhr.send(null);
}

function toggle_file_creation() {
    var area = document.getElementById('file_creation_area');
    if (area.style.display != "none") {
        area.style.display = "none";
    } else {
        area.style.display = "inline";
    }
}

function toggle_library_area() {
    var area = document.getElementById('your_data_libraries');
    if (area.style.display != "none") {
        area.style.display = "none";
    } else {
        area.style.display = "inline";
    }
}

function toggle_files_area() {
    var area = document.getElementById('your_data_files');
    if (area.style.display != "none") {
        area.style.display = "none";
    } else {
        area.style.display = "inline";
    }
}

function verify_delete(url, isLibrary) {
    var answer = confirm("Are you sure you want to delete this Data Set?")
    if (answer){
        var xhr;
        xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function() {
            if(xhr.readyState  == 4) {
                if(xhr.status  == 200 || xhr.status  == 0) {
                    var response = xhr.responseText;
                    if (isLibrary) {
                        refresh_knowledge_databases();
                        done_loading_knowledge_database(response);
                    } else {
                        refresh_expression_databases();
                        refresh_file_table();
                        done_loading_expression_database(response);
                    }
                    
                }
                else {
                    alert(xhr.responseText); 
                }
            }
        };
        xhr.open("GET", url,  true);
        xhr.send(null);
    }
    else{
        
    }
}

function loading_expression_database() {
    document.getElementById('expression_database_load_message').innerHTML = "<div class=\"loading_container\"><p><b>Loading...</b></p><br /><img src=\"../resources/images/loading.gif\" /></div>";
}

function loading_knowledge_database() {
    document.getElementById('knowledge_database_load_message').innerHTML = "<div class=\"loading_container\"><p><b>Loading...</b></p><br /><img src=\"../resources/images/loading.gif\" /></div>";
}

function done_loading_expression_database(message) {
    document.getElementById('expression_database_load_message').innerHTML = message;
}

function done_loading_knowledge_database(message) {
    document.getElementById('knowledge_database_load_message').innerHTML = message;
}