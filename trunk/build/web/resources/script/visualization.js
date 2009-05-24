var divCount = 0;

function window_onload_visualization() {
    ref_viz();
}

function image_type_switch(url, button_name, loading) {
    if (loading == null || loading) {
        loading_image();
    }
    var xhr = getRequest();
    xhr.onreadystatechange = function() {
        if(xhr.readyState  == 4) {
            if(xhr.status  == 200 || xhr.status  == 0) {
                var st = xhr.responseText;
                
                
                ref_viz();
                var sourceButs = document.getElementById('source_buts');
                if (button_name != null && button_name != 'trajectory' && button_name != 'profile' && button_name != 'comparative') {
                    document.getElementById('button_collapsed').disabled = false;
                    document.getElementById('button_collapsed').style.cursor = 'pointer';
                    document.getElementById('button_collapsed').style.cursor = 'hand';
                    document.getElementById('button_preserved').disabled = false;
                    document.getElementById('button_preserved').style.cursor = 'pointer';
                    document.getElementById('button_preserved').style.cursor = 'hand';
                    document.getElementById('button_' + button_name).disabled = true;
                    document.getElementById('button_' + button_name).style.cursor = '';
                    document.getElementById('button_' + button_name).style.cursor = '';
                } else if (button_name == 'trajectory' || button_name == 'profile' || button_name == 'comparative') {
                    // Show button
                    if (sourceButs != null) {
                        var xhr2 = getRequest();
                        xhr2.onreadystatechange = function() {
                            if(xhr2.readyState  == 4) {
                                if(xhr2.status  == 200 || xhr2.status  == 0) {
                                    var st = xhr2.responseText;
                                    sourceButs.innerHTML = st;
                                }
                                else {
                                    alert(xhr2.responseText); 
                                }
                            }
                        };
                        xhr2.open("GET", "../visualization/sub_nav_image_source.jsp",  true);
                        xhr2.send(null);
                    }
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



function ref_viz() {
    var xhr1 = getRequest();
    xhr1.onreadystatechange = function() {
        if(xhr1.readyState  == 4) {
            if(xhr1.status  == 200 || xhr1.status  == 0) {
                var st = xhr1.responseText;
                if (st != "0") {
                    var main_image = document.getElementById('main_image');
                    var bypassCaching = "ran_" + Math.random();
                    main_image.src = "../SLoadViz?bypass=" + bypassCaching;

                    var vizContainer = document.getElementById('image_map');
                    if (vizContainer != null) {
                        var xhr = getRequest();
                        xhr.onreadystatechange = function() {
                            if(xhr.readyState  == 4) {
                                if(xhr.status  == 200 || xhr.status  == 0) {
                                    var st = xhr.responseText;
                                    document.getElementById('image_map').innerHTML = st;
                                    var main_image = document.getElementById('main_image');
                                    var current_map = main_image.useMap;
                                    main_image.useMap = "";
                                    main_image.useMap = current_map;
                                    done_loading_image();
                                }
                                else {
                                    alert(xhr.responseText); 
                                }
                            }
                        };
                        xhr.open("GET", "../SImageMap?bypass=" + bypassCaching,  true);
                        xhr.send(null);
                    } 
                } else {
                        
                }
            }
            else {
                alert(xhr1.responseText); 
            }
        }
    };
    xhr1.open("GET", "../SGetImageLoadKey",  true);
    xhr1.send(null);
    
}

function refresh_vizualization() {
    var xhr = getRequest();
    xhr.onreadystatechange = function() {
        if(xhr.readyState  == 4) {
            if(xhr.status  == 200 || xhr.status  == 0) {
                var st = xhr.responseText;
                document.getElementById('image_map').innerHTML = st;
                var main_image = document.getElementById('main_image');
                var bypassCaching = "ran_" + Math.random();
                main_image.src = "../SPopulateVisualization?bypass=" + bypassCaching;
                var current_map = main_image.useMap;
                main_image.useMap = "";
                main_image.useMap = current_map;
                done_loading_image();
            }
            else {
                alert(xhr.responseText); 
            }
        }
    };
    var bypassCaching = "ran_" + Math.random();
    xhr.open("GET", "../SImageMap?bypass=" + bypassCaching,  true);
    xhr.send(null);
}

function refresh_and_rebuild_image() {
    var xhr = getRequest();
    xhr.onreadystatechange = function() {
        if(xhr.readyState  == 4) {
            if(xhr.status  == 200 || xhr.status  == 0) {
                refresh_vizualization();
            }
            else {
                alert(xhr.responseText); 
            }
        }
    };
    var bypassCaching = "ran_" + Math.random();
    xhr.open("GET", "../SLoadViz?bypass=" + bypassCaching,  true);
    xhr.send(null);
}

function lc(evt, timePeriod, nodeID) {
    var yOffset = window.pageYOffset;
    var popupX =evt.clientX;
    var popupY =evt.clientY + yOffset;
    
    addWindow("../visualization/node_window.jsp", null, nodeID, timePeriod, popupX, popupY);
}

function enc(evt, timePeriod, probeID) {
    var yOffset = window.pageYOffset;
    var popupX =evt.clientX;
    var popupY =evt.clientY + yOffset;
    
    timePeriod++;
    
    addWindow("../visualization/exact_window.jsp", probeID, null, timePeriod, popupX, popupY);
}

function loading_new_ajax_window_page(div_id) {
    document.getElementById("popout_content_" +  div_id).src = "../visualization/loading.jsp";
}

function make_ajax_window_request(url, div_id, button) {
    var iFrameWindow = window.frames['popout_content_' + div_id];
    if (iFrameWindow == null) {
        // Request from inside window frame
        iFrameWindow = parent.window.frames['popout_content_' + div_id];
    }
    
    iFrameWindow.document.getElementById('iframe_content').style.display = "none";
    iFrameWindow.document.getElementById('loading_container').style.display = "inline";
    
    var pop_cont = document.getElementById("popout_content_" +  div_id);
    if (pop_cont == null) {
        // Request from inside window frame
        pop_cont = parent.document.getElementById("popout_content_" +  div_id);
    }
    pop_cont.src = url;
    
    var a_tags = document.getElementsByTagName('a');
    
    if (button != null) {
        // DOn't worry about origin - always haas to be from parent
        var button_id_prefix = div_id + '_window_nav_';
    
        for (var i = 0; i < a_tags.length; i++) {
            var tag = a_tags[i];
            if (tag.id.substring(0, button_id_prefix.length) == button_id_prefix) {
                tag.className = "";
            }
        }
        document.getElementById(button_id_prefix + button).className = "current";
    }
}

function addClusterWindow(sTitle, nodeID, posX, posY){
    var divTarget = document.getElementById('divWindows');
    
    var divInner = document.createElement('div');
    divCount += 1; 
    var divRootID = "divRoot" + divCount;
    var divhandleID = "divhandle" + divCount;
    divInner.setAttribute("id",divRootID);
    divInner.setAttribute("class","root");
    divInner.setAttribute("className","root");
    
    var title = sTitle.substring(0,50);
    
    var sHtml = "<div>" +
        getTitle(title) +
        "<div class=\"ajaxWindowBody\">" +
        "<iframe class=\"ajaxWindowFrame\" src=\"cluster_summary.jsp?nodeID=" + nodeID + "&divID=" + divCount + "\"></iframe>" +
        "</div>" +
        "</div>";
    
    divInner.innerHTML=sHtml;
    
    divTarget.appendChild(divInner);
    
    divInner.style.top = posY + "px";
    divInner.style.left = posX + "px";
    
    startDrag(divhandleID,divRootID);
    return true;
}

function addWindow(url, probeID, nodeID, timePeriod, posX, posY){
    divCount += 1; 
    var divTarget = document.getElementById('divWindows');
    
    var urlParams = "?probeID=" + probeID + "&nodeID=" + nodeID + "&timePeriod=" + timePeriod + "&divID=" + divCount;
    
    var divInner = document.createElement('div');
    var divRootID = "divRoot" + divCount;
    var divhandleID = "divhandle" + divCount;
    divInner.setAttribute("id",divRootID);
    divInner.setAttribute("class","root");
    divInner.setAttribute("className","root");
    
    var xhr = getRequest();
    xhr.onreadystatechange = function() {
        if(xhr.readyState  == 4) {
            if(xhr.status  == 200 || xhr.status  == 0) {
                var st = xhr.responseText;
                divInner.innerHTML=st;
                startDrag(divhandleID,divRootID);
            }
            else {
                alert(xhr.responseText); 
            }
        }
    };
    xhr.open("GET", url + urlParams,  true);
    xhr.send(null);
    
    divInner.innerHTML = "<div style=\"text-align: center; padding: 10px;\"><p><b>Loading...</b></p><br /><img src=\"../resources/images/loading.gif\" /></div>";
    
    divTarget.appendChild(divInner);
    
    divInner.style.top = posY + "px";
    divInner.style.left = posX + "px";
    
    return true;
}

function updateHighlightedButton(tabNumber, type) {
    // Reset existing buttons
    for (var i = 0; i <= 10; i++) {
        var idName = type + i;
        var clickedTab = document.getElementById(idName);
        if (clickedTab != null) {
            clickedTab.className = "";
        }
    }
    // Highlight clicked one
    iidName = type + tabNumber;
    clickedTab = document.getElementById(idName);
    clickedTab.className = "current";
}

function getTitle(title) {
    var divRootID = "divRoot" + divCount;
    var divhandleID = "divhandle" + divCount;
    var sHtml = "<div>" +
        "<div class=\"cancel_button\"><a href=\"javascript:void(0)\" onclick=\"return closeWindow('" + divRootID + "')\"/>X</a></div>" + 
        "<div id=\"" + divhandleID + "\" class=\"titlebar\">" + title + "</div>";
    return sHtml;
}

function getCancelButton() {
    var divRootID = "divRoot" + divCount;
    var codeClose = "<a href=\"javascript:void(0)\" onclick=\"return closeWindow('" + divRootID + "')\"/>Cancel</a>";
    var sHtml = "<div class=\"cancelbar\">" + codeClose + "</div>";
    return sHtml;
}

function getBasicWindowHandle(title) {
    var divRootID = "divRoot" + divCount;
    var divhandleID = "divhandle" + divCount;
    var codeClose = "<img src=\"images/winClose.gif\" style=\"cursor:pointer\" onclick=\"return closeWindow('" + divRootID + "')\"/>";
    var sHtml = "<div>" +
        "<div id=\"" + divhandleID + "\" class=\"handle\">" +
        "<table class=\".windowTtile\">" +
        "<tr>" +
        "<td width=\"100%\" nowrap=\"true\">" + title + "</td>" +
        "<td>" + codeClose + "</td>" +
        "</tr>" +
        "</table>" +
        "</div>";
    return sHtml;
}

function startDrag(handleName,rootName){
    var thehandle = document.getElementById(handleName);
    var theRoot   = document.getElementById(rootName);
    Drag.init(thehandle, theRoot);
}
function closeWindow(windowID){
    var objChild = document.getElementById(windowID);
    var objParent = document.getElementById(windowID).parentNode; 
    objParent.removeChild(objChild); 
}
function minimizeWindow(windowID){
    var objWin = document.getElementById(windowID);
    objWin.setAttribute("class","rootMin");
    objWin.setAttribute("className","rootMin");
}
function maximizeWindow(windowID){
    var objWin = document.getElementById(windowID);
    objWin.setAttribute("class","root");
    objWin.setAttribute("className","root");   
}
function sideBarSwitch(url, tabNumber) {
    document.getElementById("sidebar_frame").src = url;
    updateHighlightedButton(tabNumber, "sidebarTab");
}



/**************************************************
 * dom-drag.js
 * 09.25.2001
 * www.youngpup.net
 * Script featured on Dynamic Drive (http://www.dynamicdrive.com) 12.08.2005
 **************************************************
 * 10.28.2001 - fixed minor bug where events
 * sometimes fired off the handle, not the root.
 **************************************************/

var Drag = {

    obj : null,

    init : function(o, oRoot, minX, maxX, minY, maxY, bSwapHorzRef, bSwapVertRef, fXMapper, fYMapper)
    {
        o.onmousedown	= Drag.start;

        o.hmode			= bSwapHorzRef ? false : true ;
        o.vmode			= bSwapVertRef ? false : true ;

        o.root = oRoot && oRoot != null ? oRoot : o ;

        if (o.hmode  && isNaN(parseInt(o.root.style.left  ))) o.root.style.left   = "0px";
        if (o.vmode  && isNaN(parseInt(o.root.style.top   ))) o.root.style.top    = "0px";
        if (!o.hmode && isNaN(parseInt(o.root.style.right ))) o.root.style.right  = "0px";
        if (!o.vmode && isNaN(parseInt(o.root.style.bottom))) o.root.style.bottom = "0px";

        o.minX	= typeof minX != 'undefined' ? minX : null;
        o.minY	= typeof minY != 'undefined' ? minY : null;
        o.maxX	= typeof maxX != 'undefined' ? maxX : null;
        o.maxY	= typeof maxY != 'undefined' ? maxY : null;

        o.xMapper = fXMapper ? fXMapper : null;
        o.yMapper = fYMapper ? fYMapper : null;

        o.root.onDragStart	= new Function();
        o.root.onDragEnd	= new Function();
        o.root.onDrag		= new Function();
    },

    start : function(e)
    {
        var o = Drag.obj = this;
		
        //axo
        this.style.cursor="move";
			
        e = Drag.fixE(e);
        var y = parseInt(o.vmode ? o.root.style.top  : o.root.style.bottom);
        var x = parseInt(o.hmode ? o.root.style.left : o.root.style.right );
        o.root.onDragStart(x, y);

        o.lastMouseX	= e.clientX;
        o.lastMouseY	= e.clientY;

        if (o.hmode) {
            if (o.minX != null)	o.minMouseX	= e.clientX - x + o.minX;
            if (o.maxX != null)	o.maxMouseX	= o.minMouseX + o.maxX - o.minX;
        } else {
            if (o.minX != null) o.maxMouseX = -o.minX + e.clientX + x;
            if (o.maxX != null) o.minMouseX = -o.maxX + e.clientX + x;
        }

        if (o.vmode) {
            if (o.minY != null)	o.minMouseY	= e.clientY - y + o.minY;
            if (o.maxY != null)	o.maxMouseY	= o.minMouseY + o.maxY - o.minY;
        } else {
            if (o.minY != null) o.maxMouseY = -o.minY + e.clientY + y;
            if (o.maxY != null) o.minMouseY = -o.maxY + e.clientY + y;
        }

        document.onmousemove	= Drag.drag;
        document.onmouseup		= Drag.end;

        return false;
    },

    drag : function(e)
    {
        e = Drag.fixE(e);
        var o = Drag.obj;

        var ey	= e.clientY;
        var ex	= e.clientX;
        var y = parseInt(o.vmode ? o.root.style.top  : o.root.style.bottom);
        var x = parseInt(o.hmode ? o.root.style.left : o.root.style.right );
        var nx, ny;

        if (o.minX != null) ex = o.hmode ? Math.max(ex, o.minMouseX) : Math.min(ex, o.maxMouseX);
        if (o.maxX != null) ex = o.hmode ? Math.min(ex, o.maxMouseX) : Math.max(ex, o.minMouseX);
        if (o.minY != null) ey = o.vmode ? Math.max(ey, o.minMouseY) : Math.min(ey, o.maxMouseY);
        if (o.maxY != null) ey = o.vmode ? Math.min(ey, o.maxMouseY) : Math.max(ey, o.minMouseY);

        nx = x + ((ex - o.lastMouseX) * (o.hmode ? 1 : -1));
        ny = y + ((ey - o.lastMouseY) * (o.vmode ? 1 : -1));

        if (o.xMapper)		nx = o.xMapper(y)
        else if (o.yMapper)	ny = o.yMapper(x)

        Drag.obj.root.style[o.hmode ? "left" : "right"] = nx + "px";
        Drag.obj.root.style[o.vmode ? "top" : "bottom"] = ny + "px";
        Drag.obj.lastMouseX	= ex;
        Drag.obj.lastMouseY	= ey;

        Drag.obj.root.onDrag(nx, ny);
        return false;
    },

    end : function()
    {
        document.onmousemove = null;
        document.onmouseup   = null;
		
        //axo
        Drag.obj.style.cursor="default";
		
        Drag.obj.root.onDragEnd(	parseInt(Drag.obj.root.style[Drag.obj.hmode ? "left" : "right"]), 
        parseInt(Drag.obj.root.style[Drag.obj.vmode ? "top" : "bottom"]));
        Drag.obj = null;
    },

    fixE : function(e)
    {
        if (typeof e == 'undefined') e = window.event;
        if (typeof e.layerX == 'undefined') e.layerX = e.offsetX;
        if (typeof e.layerY == 'undefined') e.layerY = e.offsetY;
        return e;
    }
};