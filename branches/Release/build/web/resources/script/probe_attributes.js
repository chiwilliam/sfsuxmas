function add_or_update_attribute_link(attribute_id, data_id) {
    var xhr;
    xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if(xhr.readyState  == 4) {
            if(xhr.status  == 200 || xhr.status  == 0) {
                var st = xhr.responseText;
                
                var message_div = document.getElementById("link_assignment_div");
                if (message_div != null) {
                    message_div.innerHTML = st;
                } else {
                    alert("Error message div not found");
                }
                refresh_probe_data_types(data_id);
            }
            else {
                alert(xhr.responseText); 
            }
        }
    };
    var link = document.getElementById('probe_data_type_link_' + attribute_id).value;
    xhr.open("GET", "../SAddAttributeLink?attribute_id=" + encodeURIComponent(attribute_id) + "&data_id=" + encodeURIComponent(data_id) + "&attribute_link=" + encodeURIComponent(link),  true);
    //alert("../SAddAttributeLink?data_type_name=" + encodeURIComponent(attribute) + "&database_name=" + encodeURIComponent(database_name) + "&data_type_link=" + encodeURIComponent(link));
    xhr.send(null);
}

function refresh_probe_data_types(data_id) {
    if (document.getElementById('probe_data_types') != null) {
        var xhr = getRequest();
        xhr.onreadystatechange = function() {
            if(xhr.readyState  == 4) {
                if(xhr.status  == 200 || xhr.status  == 0) {
                    var st = xhr.responseText;
                    document.getElementById('probe_data_types').innerHTML = st;
                }
                else {
                    alert(xhr.responseText); 
                }
            }
        };
        xhr.open("GET", "../data/probe_data_types.jsp" + "?data_id=" + data_id,  true);
        xhr.send(null);
    }
}

function deleteLinkAssociation(attribute, database_name) {
    var xhr;
    xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if(xhr.readyState  == 4) {
            if(xhr.status  == 200 || xhr.status  == 0) {
                var st = xhr.responseText;
                
                var table = document.getElementById("attri_link_table");
                var attRow = document.getElementById("attribute_link_row_" + attribute);
                
                table.deleteRow(attRow.rowIndex);
                
            }
            else {
                alert(xhr.responseText); 
            }
        }
    };
    xhr.open("GET", "../SDeleteAttributeLink?attribute_name=" + attribute + "&database_name=" + database_name,  true);
    xhr.send(null);
}