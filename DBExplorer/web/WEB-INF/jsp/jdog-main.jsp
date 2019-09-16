<%@ page import="com.silrais.webtoolkit.util.HttpUtil"%>
<%@ page import="com.silrais.toolkit.util.SimpleUtil"%>
<%
        String ds = request.getParameter("ds");
		String dsDispName1 = ds != null ? ds : "Home";
		String dsDispName2 = ds != null ? ds : "NONE";
		
%>
<html><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>Magit DBExplorer : <%=dsDispName1%> </title>
<link rel="stylesheet" type="text/css" href="css/jdog.css">

<style type="text/css">
body {
	margin:0;
	padding:0;
	font-size:12px;
}

.banner{
	background-color:transparent;
	background-image:url(image/jdog-banner.gif);
	background-attachment: fixed;
	background-repeat: repeat;
	height: 175px; /*Height of top section*/
	width:100%;
}
.trailer {
	position: absolute;
	bottom: 2px;
	left: 10px;
	align: center;
}
.trailer * {
	padding-bottom: 0;
	margin-bottom: 0;
	line-height: 14px;
}

.jdog_title {
	margin: 10px;
	font-size:12px;
	align:center;
}


</style>

<link rel="stylesheet" type="text/css" href="yui/fonts/fonts-min.css" />
<link rel="stylesheet" type="text/css" href="yui/tabview/assets/skins/sam/tabview.css" />
<script type="text/javascript" src="yui/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="yui/element/element-beta-min.js"></script>

<script type="text/javascript" src="yui/tabview/tabview-min.js"></script>
<!--there is no custom header content for this example-->

<script type="text/javascript">

/***********************************************
* IFrame SSI script II- © Dynamic Drive DHTML code library (http://www.dynamicdrive.com)
* Visit DynamicDrive.com for hundreds of original DHTML scripts
* This notice must stay intact for legal use
***********************************************/

//Input the IDs of the IFRAMES you wish to dynamically resize to match its content height:
//Separate each ID with a comma. Examples: ["myframe1", "myframe2"] or ["myframe"] or [] for none:
//var iframeids=["home-iframe", "dss-iframe", "browser-iframe", "executor-iframe", "dba-iframe", "custom-iframe", "metadata-iframe"]
var iframeids=["home-iframe","dss-iframe", "browser-iframe", "executor-iframe", "custom-iframe", "metadata-iframe"]

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
        currentfr.height = myWindowHeight - 80;
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


<body class="yui-skin-sam" onresize="resizeCaller();">

   <%
   		int height = HttpUtil.getIntAttrB4Param(request, "height");
   		int width  = HttpUtil.getIntAttrB4Param(request, "width");
        if (height <= 0) {
            height = 800;
        }
        if (width <= 0 ) {
            width = 1200;
        }
          
   %>
<div class="banner">
<div id="jdogtabs" class="yui-navset yui-navset-top">

<table width="100%" cellspacing="0" cellpadding="0">
<!--  
<tr>
	<td class="yui-nav" width="5%" align="center" rowspan="2">  
	&nbsp;
	</td>

	<td width="70%" align="center">&nbsp;</td>

	<td class="yui-nav"  width="25%" align="center" rowspan="2"> 
 		<p><span class="jdog_title"><font color="white">S3DBExplorer: a simple, sensible & smart database explorer </font> </span><p>
 		<p><font color="white" size="1">Now exploring... User@192.168.1.00:gserv1 </font> </span></p>
 		
	</td>
</tr>
-->
<tr>
	<td align="center" NOWRAP>
		<ul class="yui-nav">
			<li class="selected"><a href="#home"><em>Home</em></a></li>
			<li><a href="#dss"><em>Data Sources</em></a></li>
	     <%  
	       if (!SimpleUtil.isnull(ds)) {
		 %>
			<li><a href="#browser"><em>DB Browser</em></a></li>
			<li><a href="#executor"><em>Query Executor</em></a></li>
			<!--  
			<li><a href="#dba"><em>DBA Tools</em></a></li>
			-->
	
			<li><a href="#custom"><em>Custom Views</em></a></li>
			<li><a href="#metadata"><em>DB Metadata</em></a></li>
		<% } %>
	</ul>
	</td>
	<td class="yui-nav"  align="center" valign="bottom"> 
 		<p><span class="jdog_title"><font color="white">Datasource Name: [<%=dsDispName2%>]</font> </span><p>
	</td>

</tr>
</table>

<div class="yui-content">

<div id="home"><iframe name="home" id="home-iframe"
	width="<%=width%>" height="<%=height%>"
	src="exejsp.do?jsp2fwd2=jdog-home"></iframe></div>

<div id="dss"><iframe name="dss" id="dss-iframe"
	width="<%=width%>" height="<%=height%>"
	src="exejsp.do?jsp2fwd2=datasource-frame&ds=<%=ds%>"></iframe></div>
<%  
	if (!SimpleUtil.isnull(ds)) {
%>

<div id="browser" class="yui-hidden"><iframe name="browser"
	id="browser-iframe" width="<%=width%>" height="<%=height%>"
	src="exejsp.do?jsp2fwd2=db-browser&ds=<%=ds%>"></iframe></div>

<div id="executor" class="yui-hidden"><iframe name="executor"
	id="executor-iframe" width="<%=width%>" height="<%=height%>"
	src="exejsp.do?jsp2fwd2=query-executor&ds=<%=ds%>"></iframe></div>
<!-- 
<div id="dba" class="yui-hidden"><iframe name="dba"
	id="dba-iframe" width="<%=width%>" height="<%=height%>"
	src="exejsp.do?jsp2fwd2=db-admin&ds=<%=ds%>"></iframe></div>
-->
<div id="custom" class="yui-hidden"><iframe name="dba"
	id="custom-iframe" width="<%=width%>" height="<%=height%>"
	src="exejsp.do?jsp2fwd2=custom-view&ds=<%=ds%>"></iframe></div>
 
<div id="metadata" class="yui-hidden"><iframe name="metadata"
	id="metadata-iframe" width="<%=width%>" height="<%=height%>"
	src="showdbmdlist.do?ds=<%=ds%>"></iframe></div>
<%
	}
%>
</div>
</div>
</div>
<script>
(function() {
    var tabView = new YAHOO.widget.TabView('jdogtabs');
})();
</script>

<div class="trailer">
		<p>Magit DBExplorer: simple and smart database objects explorer&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;Magit Software Solutions Inc. Copyright Reserved 2012  </p>
</div>

</body></html>