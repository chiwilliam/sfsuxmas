function window_onload_snapshot() {
    save_filter_file();
}

function save_filter_file() {
    var xhr = getRequest();
    xhr.onreadystatechange = function() {
        if(xhr.readyState  == 4) {
            if(xhr.status  == 200 || xhr.status  == 0) {
                document.getElementById('file_list_holder').innerHTML = "<p>SUCCESS</p>";
            }
            else {
                alert(xhr.responseText); 
            }
        }
    };
    xhr.open("GET", "../SFilterSnapShot",  true);
    xhr.send(null);
}