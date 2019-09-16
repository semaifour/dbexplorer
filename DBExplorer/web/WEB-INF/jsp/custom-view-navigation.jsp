<%@ include file="/WEB-INF/jsp/include.jsp" %>

<%@ page import="java.util.Map" %>
<%@ page import="com.silrais.toolkit.dataset.SimpleDataSet"%>
<%@ page import="com.silrais.toolkit.dataset.SimpleRow"%>
<%@ page import="com.silrais.toolkit.util.SimpleUtil"%>
<%@ page import="com.silrais.webtoolkit.util.HttpUtil"%>

<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">

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
</style>

<link rel="stylesheet" type="text/css" href="css/jdog.css">
<link rel="stylesheet" type="text/css" href="yui/fonts/fonts-min.css" />
<link rel="stylesheet" type="text/css" href="yui/treeview/assets/skins/sam/treeview.css" />
<link rel="stylesheet" type="text/css" href="yui/container/assets/skins/sam/container.css" />
<script type="text/javascript" src="yui/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="yui/treeview/treeview-min.js"></script>
<script type="text/javascript" src="yui/container/container-min.js"></script>


<!--begin custom header content for this example-->
<style>
    #treeDiv1 {background: #ffffff; padding:1em;font-size=15px; font-family : Calibri, Verdana, Arial, Helvetica, sans-serif; }
</style>
<!--end custom header content for this example-->

</head>

<body class="yui-skin-sam">

<%
    Map model = (Map) request.getAttribute("model");
    SimpleDataSet ds = (SimpleDataSet) model.get("cvnavlist");
    int topNodeCount = ds.size();
    SimpleRow row = null, optrow=null;
    String targetFrame = request.getParameter("targetframe");
	String dsname = request.getParameter("ds");
	String cvStaticParams = HttpUtil.toHttpQueryString("ds", dsname, "otyp", "CV");
%>    

<table width=100%>
    <tr class="header">
        <th> <%=request.getParameter("title")%> </th>
    </tr>
</table>
<div id="treeDiv1"></div>
<script type="text/javascript">

//global variable to allow console inspection of tree:
var tree;

//anonymous function wraps the remainder of the logic:
(function() {

    var tt, contextElements = [];
    
    function OptionItem(id,name) {
        this._optionId = id;
        this._optionName = name;
    }

    OptionItem.prototype._optionId;
    OptionItem.prototype._optionName;

    OptionItem.prototype.getId = function () {
        return this._optionId;
    }

    OptionItem.prototype.getName = function () {
        return this._optionName;
    }

    var topNodeList = new Array();
    var topNodeOptList = new Array();
    var tmpOptionItem;

    <%

    SimpleDataSet optList = null; 
    int optsize = 0;
    String optVarName = "", tipVarName = "", urlVarName="";
    for (int i=1; i < topNodeCount; i++) {
        row = ds.getDataRow(i); 
        out.println("\ttopNodeList["+ (i-1) + "] = \""   + row.get(2) + "\";");
        optVarName = "" + row.get(1) + i; 
        out.println("\tvar " + optVarName + " = new Array();");
        optList = (SimpleDataSet)row.get(3);
        optsize = optList.size(); 
        for (int j=1; j < optsize; j++) {
            optrow = optList.getDataRow(j); 
            out.println("\ttmpOptionItem = new OptionItem(\""+optrow.get(1)+"\",\"" + optrow.get(2) + "\");");
            out.println("\t" + optVarName + "["+ (j-1) + "] = tmpOptionItem;");
        }
        out.println("\ttopNodeOptList["+ (i-1) + "] = "   + optVarName + ";");
    } 
    %>

	//function to initialize the tree:
    function treeInit() {
        buildTopNodeTree();
    }
    
    
	//Function  creates the tree and adds options for each top node 
    function buildTopNodeTree() {
	
		//instantiate the tree:
        tree = new YAHOO.widget.TreeView("treeDiv1");

        for (var i = 0; i < topNodeList.length; i++) {
            var o = {
                label: topNodeList[i], 
                title: topNodeList[i] 
            };
            var tmpNode = new YAHOO.widget.TextNode(o, tree.getRoot(), false);
            
            buildNodeOptions(tmpNode, i, topNodeList[i]);
        }

		//The tree is not created in the DOM until this method is called:
        tree.draw();
    }

	//function builds 10 children for the node you pass in:
    function buildNodeOptions(node, idx, parentName) {
            var optList = topNodeOptList[idx];
            for ( var i = 0; i < optList.length; i++ ) {
                var o = {
                    label: optList[i].getName(),
                    title: optList[i].getName(),
                    href: "showcv.do?cvid=" + optList[i].getId()+"&cvname="+parentName+" >> "+optList[i].getName()+"&<%=cvStaticParams%>",
                    target: "<%=targetFrame%>"
                };
                var tmpNode = new YAHOO.widget.TextNode(o, node, false);
            }
    }

	//Add an onDOMReady handler to build the tree when the document is ready
    YAHOO.util.Event.onDOMReady(treeInit);

})();

</script>
</body>
</html>
