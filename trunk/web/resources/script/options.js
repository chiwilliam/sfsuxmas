function window_onload_options() {
    check_root_dir();
    check_data_dir();
    check_annotations_dir();
}

function generic_check(inputName, servlet) {
    var root_dir = document.getElementById(inputName);
    var data_msg = document.getElementById(inputName + '_msg');
    if (root_dir != null && root_dir.value.length > 0) {
        var name = root_dir.value;
        var xhr;
        xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function() {
            if(xhr.readyState  == 4) {
                if(xhr.status  == 200 || xhr.status  == 0) {
                    var st = xhr.responseText;
                    if (st == "0") {
                        data_msg.className = "error";
                        data_msg.innerHTML = "Invalid";
//                        data_msg.style.display = "inline";
                    }
                    else {
                        data_msg.className = "success";
                        data_msg.innerHTML = "Valid";
//                        data_msg.style.display = "none";
                    }
                }
                else {
                    alert(xhr.responseText); 
                }
            }
        };
        xhr.open("GET", "../" + servlet + "?rootName=" + encodeURIComponent(name),  true);
        xhr.send(null);
    }
    else {
        data_msg.className = "error";
        data_msg.innerHTML = "Empty directory specification";
        data_msg.style.display = "inline";
    }
}

function check_root_dir() {
    generic_check('root_dir', 'SCheckDir');
}
function check_data_dir() {
    generic_check('expression_data_dir', 'SCheckDataDir');
}

function check_annotations_dir() {
    generic_check('knowledge_dir', 'SCheckAnnotationDir');
}