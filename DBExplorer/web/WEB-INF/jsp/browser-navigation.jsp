<%@ page import="java.util.Map" %>
<%@ page import="com.silrais.toolkit.dataset.SimpleDataSet"%>
<%@ page import="com.silrais.toolkit.dataset.SimpleRow"%>
<%@ page import="com.silrais.toolkit.util.SimpleUtil"%>

<%
    Map model = (Map) request.getAttribute("model");
    SimpleDataSet dataset = (SimpleDataSet) model.get("dblist");
    int colCount = dataset.getColumns().length;
    
    //we need to determine whether we need use value at col 2 as dbname name or catalog name  
    String col2HttpParamName = "sch"; //by defulat
    if (colCount == 3) {
    	//i.e there only three cols in the datasaet
    	//which means 
    	//col1 is REC_ID
    	//col 2 is dbname
    	//col 3 is CATALOG
    	//so, col2 value is to be used as "schama" so do use the http-parameter "sch" to pass it around
    	col2HttpParamName="sch";
    } else if (colCount == 2) {
      	//i.e there only three cols in the datasaet
    	//which means 
    	//col1 is REC_ID
    	//col 2 is CATALOG
    	//so, col 2 value is to be used as "catalog" so do use the http-parameter "cat" to pass it around
    	col2HttpParamName = "cat";
    } else {
    	//this should not happen
    }
    String catalog = request.getParameter("cat");
%>

<html>
<head>

<style type="text/css">
body {
	margin:0;
	padding:0;
}
</style>

<link rel="stylesheet" type="text/css" href="css/jdog.css">
<link rel="stylesheet" type="text/css" href="yui/fonts/fonts-min.css" />
<link rel="stylesheet" type="text/css" href="yui/treeview/assets/skins/sam/treeview.css" />
<script type="text/javascript" src="yui/yahoo/yahoo-min.js"></script>
<script type="text/javascript" src="yui/event/event-min.js"></script>
<script type="text/javascript" src="yui/connection/connection-min.js"></script>
<script type="text/javascript" src="yui/treeview/treeview-min.js"></script>


<!--begin custom header content for this example-->
<style>
    #treeDiv1 {background: #ffffff;margin-top:1em; padding:1em; min-height:7em; padding:1em;font-size=15px; font-family : Calibri, Verdana, Arial, Helvetica, sans-serif; }
</style>
<!--end custom header content for this example-->


</head>

<body class=" yui-skin-sam">

<%

    String targetFrame = "dbbcontentframe"; //(String) request.getAttribute("targetframe");
    String dbname="ORADBA_DEV";
    String[] dboCategories = {"Table", "View", "Proc"}; //(String[]) request.getAttribute("navtopitems");
%>    

<div id="treeDiv1"></div>
    
<script type="text/javascript">

YAHOO.example.treeExample = function() {

    var tree, currentIconMode;

        function loadNodeData(node, fnLoadComplete)  {
            
            //We'll load node data based on what we get back when we
            //use Connection Manager topass the text label of the 
            //expanding node to the Yahoo!
            //Search "related suggestions" API.  Here, we're at the 
            //first part of the request -- we'll make the request to the
            //server.  In our success handler, we'll build our new children
            //and then return fnLoadComplete back to the tree.
            
            //Get the node's label and urlencode it; this is the word/s
            //on which we'll search for related words:
            var nodeLabel = encodeURI(node.label);
            var dbname = encodeURI(node.title);
            //prepare URL for XHR request:
            //
            //var sUrl = "assets/ysuggest_proxy.php?query=" + nodeLabel;

            var sUrl ="getdbolist.do?ds=<%=request.getParameter("ds")%>&<%=col2HttpParamName%>="+dbname+"&otype="+nodeLabel; 

            //prepare our callback object
            var callback = {
            
                //if our XHR call is successful, we want to make use
                //of the returned data and create child nodes.
                success: function(oResponse) {
                    YAHOO.log("XHR transaction was successful.", "info", "example");
                    //YAHOO.log(oResponse.responseText);
                    var oResults = eval("(" + oResponse.responseText + ")");
                    //if((oResults.ResultSet.Result) && (oResults.ResultSet.Result.length)) {
                    if((oResults.ResultSet) && (oResults.ResultSet.length)) {
                        //Result is an array if more than one result, string otherwise
                        if(YAHOO.lang.isArray(oResults.ResultSet)) {
                            for (var i=0, j=oResults.ResultSet.length; i<j; i++) {
                                var o = {
                                    label: oResults.ResultSet[i].RowArray[2],
                                           href: "showcoldtl.do?ds=<%=request.getParameter("ds")%>&<%=col2HttpParamName%>="+dbname+"&oname="+oResults.ResultSet[i].RowArray[2]+"&otype="+nodeLabel,
                                           target: "<%=targetFrame%>"
                                };
                                var tmpNode = new YAHOO.widget.TextNode(o, node, false);
                                tmpNode.isLeaf = true;
                            }
                        } else {
                            //there is only one result; comes as string:
                            var tempNode = new YAHOO.widget.TextNode(oResults.ResultSet[i].RowArray[2], node, false)
                        }
                    }
                    
                    //When we're done creating child nodes, we execute the node's
                    //loadComplete callback method which comes in via the argument
                    //in the response object (we could also access it at node.loadComplete,
                    //if necessary):
                    oResponse.argument.fnLoadComplete();
                },
                
                //if our XHR call is not successful, we want to
                //fire the TreeView callback and let the Tree
                //proceed with its business.
                failure: function(oResponse) {
                    YAHOO.log("Failed to process XHR transaction.", "info", "example");
                    oResponse.argument.fnLoadComplete();
                },
                
                //our handlers for the XHR response will need the same
                //argument information we got to loadNodeData, so
                //we'll pass those along:
                argument: {
                    "node": node,
                    "fnLoadComplete": fnLoadComplete
                },
                
                //timeout -- if more than 7 seconds go by, we'll abort
                //the transaction and assume there are no children:
                timeout: 7000
            };
            
            //With our callback object ready, it's now time to 
            //make our XHR call using Connection Manager's
            //asyncRequest method:
            YAHOO.util.Connect.asyncRequest('GET', sUrl, callback);
        }

        function buildTree() {
           //create a new tree:
           tree = new YAHOO.widget.TreeView("treeDiv1");
           
           //turn dynamic loading on for entire tree:
           //tree.setDynamicLoad(loadNodeData, currentIconMode);
           
           //get root node for tree:
           var root = tree.getRoot();

           var databaseItems =  new Array();
           
		<%
		    String rowClass = "";
		    int size = dataset.size();
		    SimpleRow row = null;
		    Object name = null;
		    for (int i = 1; i < size; i++) {
		        row = dataset.getDataRow(i);
		        name = row.get(1);
		        out.println("\t databaseItems["+(i-1)+"] = \"" + name + "\";"); 
    		}
		%>
		
           //add child nodes for tree; our top level nodes are
           var dboCategoryItems =  new Array();
           <%
               for (int i = 0; i < dboCategories.length; i++) {
                   out.println("\t dboCategoryItems["+i+"] = \"" + dboCategories[i] + "\";"); 
               }
          %>
           
			for (var i=0; i<databaseItems.length;i++ ) {
			    var catNode = new YAHOO.widget.TextNode(databaseItems[i], root, false);
           		for (var j=0; j < dboCategoryItems.length;j++) {
				  var o = {
                        label:dboCategoryItems[j],
						title:databaseItems[i]
                    };
                	var dboNode = new YAHOO.widget.TextNode(o, catNode, false);
					dboNode.setDynamicLoad(loadNodeData, currentIconMode);
           		}
			    var lastNode = new YAHOO.widget.TextNode('', catNode, false);
			    lastNode.isLeaf = true;
			 }

           // Use the isLeaf property to force the leaf node presentation for a given node.
           // This disables dynamic loading for the node.
           var tempNode = new YAHOO.widget.TextNode('', root, false);
           tempNode.isLeaf = true;
           
           //render tree with these toplevel nodes; all descendants of these nodes
           //will be generated as needed by the dynamic loader.
           tree.draw();
        }


    return {
        init: function() {
            buildTree();
        }

    }
} ();

//once the DOM has loaded, we can go ahead and set up our tree:
YAHOO.util.Event.onDOMReady(YAHOO.example.treeExample.init, YAHOO.example.treeExample,true)

</script>
</body>
</html>
