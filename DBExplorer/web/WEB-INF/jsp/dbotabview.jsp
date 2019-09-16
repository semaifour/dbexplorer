<%@ page import="com.silrais.webtoolkit.util.HttpUtil"%>
<%@ page import="com.silrais.toolkit.util.SimpleUtil"%>
<%@ page import="s3tool.jdog.web.util.DBOContext"%>
<html><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/jdog.css">
<style type="text/css">
/*margin and padding on body element
  can introduce errors in determining
  element position and are not recommended;
  we turn them off as a foundation for YUI
  CSS treatments. */
body {
	margin:0;
	padding:0;
}

#banner{
background: #708090;
height: 75px; /*Height of top section*/
}

.innertune {
margin: 0; /*Margins for inner DIV inside each column (to provide padding)*/
margin-top: 0;
}
</style>

<link rel="stylesheet" type="text/css" href="yui/fonts/fonts-min.css" />
<link rel="stylesheet" type="text/css" href="yui/tabview/assets/skins/sam/tabview.css" />
<script type="text/javascript" src="yui/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="yui/element/element-beta-min.js"></script>

<script type="text/javascript" src="yui/tabview/tabview-min.js"></script>
<!--there is no custom header content for this example-->


   <%
   		int height = HttpUtil.getIntParam(request, "height");
   		int width  = HttpUtil.getIntParam(request, "width");
        if (height <= 0) {
            height = 800;
        }
        if (width <= 0 ) {
            width = 1200;
        }

    String actvtab = (String) request.getAttribute("actvtab");
    DBOContext dbctx = new DBOContext();
    dbctx.setContext(request);
    String dbStr = dbctx.getHttpParamString();

    String catalog = dbctx.getCatalog(); 
    String schema = dbctx.getSchema();
    String table  = dbctx.getOname();
    String otyp   = dbctx.getOtype();


    
    if (SimpleUtil.isnull(actvtab)) {
        actvtab = "NA";
    }
   %>
   

<script type="text/javascript">

/***********************************************
* IFrame SSI script II- © Dynamic Drive DHTML code library (http://www.dynamicdrive.com)
* Visit DynamicDrive.com for hundreds of original DHTML scripts
* This notice must stay intact for legal use
***********************************************/

//Input the IDs of the IFRAMES you wish to dynamically resize to match its content height:
//Separate each ID with a comma. Examples: ["myframe1", "myframe2"] or ["myframe"] or [] for none:
<%	if (!"PROC".equals(otyp)) { %>
var iframeids=["cols-iframe", "data-iframe", "relationships-iframe", "indexes-iframe"]
<% } else { %>
var iframeids=["cols-iframe", "script-iframe"]
<% } %>
               

//Should script hide iframe from browsers that don't support this script (non IE5+/NS6+ browsers. Recommended):
var iframehide="yes"

var getFFVersion=navigator.userAgent.substring(navigator.userAgent.indexOf("Firefox")).split("/")[1]
var FFextraHeight=parseFloat(getFFVersion)>=0.1? 16 : 0 //extra height in px to add to iframe in FireFox 1.0+ browsers
var FFextraWidth=parseFloat(getFFVersion)>=0.1? 20 : 0 //extra height in px to add to iframe in FireFox 1.0+ browsers

var myWindowWidth = 0, myWindowHeight = 0;

function resizeCaller() {

    getWindowSize();

    var dyniframe=new Array()

    for (i=0; i<iframeids.length; i++){
            if (document.getElementById)
            resizeIframe(iframeids[i])
            //reveal iframe for lower end browsers? (see var above):
            if ((document.all || document.getElementById) && iframehide=="no"){
                var tempobj=document.all? document.all[iframeids[i]] : document.getElementById(iframeids[i])
                tempobj.style.display="block"
            }
    }
}

function resizeIframe(frameid){
    var currentfr=document.getElementById(frameid)
    

    if( typeof(myWindowWidth) == 'number' && myWindowWidth > 0 ) {
        currentfr.width = myWindowWidth - 20;
    }
    if( typeof(myWindowHeight) == 'number' && myWindowHeight > 0 ) {
        currentfr.height = myWindowHeight - 60;
    }

        if (currentfr.addEventListener)
            currentfr.addEventListener("load", readjustIframe, false)
        else if (currentfr.attachEvent){
            currentfr.detachEvent("onload", readjustIframe) // Bug fix line
            currentfr.attachEvent("onload", readjustIframe)
        }
}

function readjustIframe(loadevt) {
    var crossevt=(window.event)? event : loadevt
    var iframeroot=(crossevt.currentTarget)? crossevt.currentTarget : crossevt.srcElement
    if (iframeroot)
        resizeIframe(iframeroot.id);
}

function loadintoIframe(iframeid, url){
    if (document.getElementById)
        document.getElementById(iframeid).src=url
}


function getWindowSize() {
    if( typeof( window.innerWidth ) == 'number' ) {
        //Non-IE
        myWindowWidth = window.innerWidth ;
        myWindowHeight = window.innerHeight ;
    } else if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
        //IE 6+ in 'standards compliant mode'
        myWindowWidth = document.documentElement.clientWidth ;
        myWindowHeight = document.documentElement.clientHeight ;
   } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
        //IE 4 compatible
        myWindowWidth = document.body.clientWidth ;
        myWindowHeight = document.body.clientHeight ;
    }
    //window.alert( 'Width = ' + myWindowWidth );
    //window.alert( 'Height = ' + myWindowHeight );
}


if (window.addEventListener)
    window.addEventListener("load", resizeCaller, false)
else if (window.attachEvent)
    window.attachEvent("onload", resizeCaller)
else
    window.onload=resizeCaller

</script>

</head>
<body class="yui-skin-sam" onresize="resizeCaller();">

<div id="dbotabview" class="yui-navset">
    <center>
    <ul class="yui-nav">
        <li class="selected"><a href="#cols"><em>Columns</em></a></li>
        <%	if (!"PROC".equalsIgnoreCase(otyp)) { %>
            
        <li><a href="#data"><em>Data</em></a></li>
        <li><a href="#relationships"><em>Relationships</em></a></li>
        <li><a href="#indexes"><em>Indexes</em></a></li>
		<% } else { %>
		<li><a href="#script"><em>Script</em></a></li>
		<% } %>
    </ul>   
    </center> 
    <div class="yui-content">
        <div id="cols">
            <iframe name="cols"  id="cols-iframe" width="<%=width%>" height="<%=height%>"  src="showcoldtl.do?<%=dbStr%>" border="0"></iframe>
        </div>
    <%	if (!"PROC".equalsIgnoreCase(otyp)) { %>
        <div id="data" class="yui-hidden" >
            <iframe name="data"  id="data-iframe" width="<%=width%>" height="<%=height%>"  src="showtabdata.do?<%=dbStr%>" border="0"></iframe>
        </div>
    
        <div id="relationships" class="yui-hidden" >
            <iframe name="relationships"   id="relationships-iframe" width="<%=width%>" height="<%=height%>"  src="showrefkeylists.do?<%=dbStr%>"></iframe>
        </div>
    
        <div id="indexes" class="yui-hidden" >
            <iframe name="indexes"  id="indexes-iframe" width="<%=width%>" height="<%=height%>"  src="showidxlist.do?<%=dbStr%>"></iframe>
        </div>
  <% } else { %>
  
        <div id="script" class="yui-hidden" >
            <iframe name="script"  id="script-iframe" width="<%=width%>" height="<%=height%>"  src="showidxlist.do?<%=dbStr%>"></iframe>
        </div>
  <% } %>
        
    
    </div>
</div>
<script>
(function() {
    var tabView = new YAHOO.widget.TabView('dbotabview');
})();
</script>


</body></html>
