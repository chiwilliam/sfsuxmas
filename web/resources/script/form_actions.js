var http_request = false;

function makeRequest(url, parameters, return_url, results_id) {
    http_request = getRequest();
    http_request.onreadystatechange = function() {
        if (http_request.readyState == 4) {
            if (http_request.status == 200) {
                fill_div_with_request_response(return_url, results_id);
            } else {
                alert('There was a problem with the request.');
            }
        }
    }
    http_request.open('GET', url + parameters, true);
    http_request.send(null);
}

function make_pathway_action_request(url, parameters) {
    var ref_image = true;
    if (ref_image) {
        loading_image();
    }
    http_request = getRequest();
    http_request.onreadystatechange = function() {
        if (http_request.readyState == 4) {
            if (http_request.status == 200) {
                if (ref_image) {
                    refresh_image();
                    pathway_refresh('../modules/pathway_renderer.jsp');
                }
            } else {
                alert('There was a problem with the request.');
            }
        }
    }
    http_request.open('GET', url + parameters, true);
    http_request.send(null);
}

function make_go_term_action_request(url, parameters) {
    var ref_image = true;
    if (ref_image) {
        loading_image();
    }
    http_request = getRequest();
    http_request.onreadystatechange = function() {
        if (http_request.readyState == 4) {
            if (http_request.status == 200) {
                if (ref_image) {
                    refresh_image();
                    pathway_refresh('../modules/go_term_renderer.jsp');
                }
            } else {
                alert('There was a problem with the request.');
            }
        }
    }
    http_request.open('GET', url + parameters, true);
    http_request.send(null);
}

function make_label_action_request(url, parameters) {
    var ref_image = true;
    if (ref_image) {
        loading_image();
    }
    http_request = getRequest();
    http_request.onreadystatechange = function() {
        if (http_request.readyState == 4) {
            if (http_request.status == 200) {
                if (ref_image) {
                    refresh_image();
                    label_refresh('../modules/label_renderer.jsp');
                }
            } else {
                alert('There was a problem with the request.');
            }
        }
    }
    http_request.open('GET', url + parameters, true);
    http_request.send(null);
}

function make_probe_action_request(url, parameters, ref_image, side_bar_url) {
    if (ref_image) {
        loading_image();
    }
    http_request = getRequest();
    http_request.onreadystatechange = function() {
        if (http_request.readyState == 4) {
            if (http_request.status == 200) {
                if (ref_image) {
                    refresh_image();
                }
                if (side_bar_url != null) {
                    probe_refresh(side_bar_url);
                }
            } else {
                alert('There was a problem with the request.');
            }
        }
    }
    http_request.open('GET', url + parameters, true);
    http_request.send(null);
}

function un_label_probe(probe_id, label_id) {
    http_request = getRequest();
    http_request.onreadystatechange = function() {
        if (http_request.readyState == 4) {
            if (http_request.status == 200) {
                probe_refresh('../modules/probe_renderer.jsp?labels=true');
            } else {
                alert('There was a problem with the request.');
            }
        }
    }
    http_request.open('GET', '../SUnLabelProbe?probe_id=' + probe_id + '&label_id=' + label_id, true);
    http_request.send(null);
}

function submit_probe_list_action(action, first, last, actionType) {
    if (ensure_checked(first, last, 'probe_checkbox_')) {
        var okToProceed = true;
        if (actionType == "label_probes") {
            okToProceed = check_tag();
        }
        if (okToProceed) {
            var getstr = "?" + actionType + "&" + populate_checkbox_params(first, last, 'probe_checkbox_');
            var refresh_viz = true;
            var side_bar = '../modules/probe_renderer.jsp';
            if (actionType == "label_probes" || actionType == "filter_annotate_remove") {
                var label_name = document.getElementById('label_id');
                getstr += "&label_id=" + label_name.value;
                side_bar += "?labels=true";
                refresh_viz = false;
            }
            make_probe_action_request(action, getstr, refresh_viz, side_bar);
        }
    }
}

function submit_pathway_list_action(action, first, last, actionType) {
    if (ensure_checked(first, last, 'pathway_checkbox_')) {
        var getstr = "?" + actionType + "&" + populate_checkbox_params(first, last, 'pathway_checkbox_');
        make_pathway_action_request(action, getstr);
    }
}

function submit_go_term_list_action(action, first, last, actionType) {
    if (ensure_checked(first, last, 'go_term_checkbox_')) {
        var getstr = "?" + actionType + "&" + populate_checkbox_params(first, last, 'go_term_checkbox_');
        make_go_term_action_request(action, getstr);
    }
}

function submit_label_list_action(action, first, last, actionType) {
    if (ensure_checked(first, last, 'label_checkbox_')) {
        var getstr = "?" + encodeURIComponent(actionType) + "&" + populate_checkbox_params(first, last, 'label_checkbox_');
        make_label_action_request(action, getstr);
    }
}

function populate_checkbox_params(first, last, id_prefix) {
    var getstr = "";
    for (var i = first; i < last; i++) {
        var checkbox = document.getElementById(id_prefix + i);
        if (checkbox != null && checkbox.disabled == false && checkbox.checked) {
            if (getstr.length > 0) {
                getstr += "&"
            }
            getstr += encodeURIComponent(checkbox.name) + "=" + checkbox.value;
        }
        if (checkbox == null) {
            alert("First: " + first + ", Last: " + last + ", Prefix: " + id_prefix);
        }
    }
    return getstr;
}
   
function get(obj, submit_url, return_url, results_id) {
    
    navigation_loading(results_id);
    
    var getstr = "?";
    for (i=0; i<obj.childNodes.length; i++) {
        if (obj.childNodes[i].tagName == "INPUT") {
            if (obj.childNodes[i].type == "text") {
                getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
            }
            if (obj.childNodes[i].type == "checkbox") {
                if (obj.childNodes[i].checked) {
                    getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
                } else {
                    getstr += obj.childNodes[i].name + "=&";
                }
            }
            if (obj.childNodes[i].type == "radio") {
                if (obj.childNodes[i].checked) {
                    getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
                }
            }
        }   
        if (obj.childNodes[i].tagName == "SELECT") {
            var sel = obj.childNodes[i];
            getstr += sel.name + "=" + sel.options[sel.selectedIndex].value + "&";
        }
    }
    makeRequest(submit_url, getstr, return_url, results_id);
}