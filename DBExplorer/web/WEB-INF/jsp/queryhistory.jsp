<%@page import="java.util.Map" %>
<%@ page import="com.silrais.toolkit.dataset.SimpleDataSet"%>
<%@ page import="com.silrais.toolkit.dataset.SimpleRow"%>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/jdog.css">
<script language="javascript">

	function handleAction(actname) {
		document.forms[0].action = document.forms[0].action + '?actname=' + actname;
		document.forms[0].submit();
		return true;
    }
    
    function deleteQry(qryidx) {
    	document.forms[0].action = document.forms[0].action + '?actname=Delete&qryidx=' + qryidx;
		document.forms[0].submit();
		return true;
    }

    function copyQry(qryid) {
    	var obj = document.getElementById(qryid);
    	for (i=0; i<window.parent.frames.length; i++) {
	    	if (window.parent.frames(i).name == "queryeditor") { 
	    		window.parent.frames(i).document.forms[0].qry.innerText = obj.value;
	    	}
    	}
    	return false;
    }  
      
    function showQry(obj, qryid) {
    	var tmp = document.getElementById(qryid);
    	obj.innerText = tmp.value; 
    	return false;
    }    
</script>    
</head>

<body>
<form method=post action="qryhist.do">
        
    <table width="100%" border=0>
        <tr class="header"><th colspan="4"> Query History </th></tr>
       	<tr class="lineitemA">
       		<td align=center><input type="button" value =" Refresh " onClick="javascript:handleAction('Refresh')"></td>
       		<td align=center><input type="button" value =" Clear All " onClick="javascript:handleAction('Clear')"></td>
       		<td align=center><input type="button" value =" Save " onClick="javascript:handleAction('Save')"></td>
       		<td align=center><input type="button" value =" Upload " onClick="javascript:handleAction('Upload')"></td>
       	</tr>
<%
    
    Map model           = (Map) request.getAttribute("model");
    SimpleDataSet ds    = (SimpleDataSet) model.get("TableData");
    int dsSize = ds.size();
%>

    <!-- Column section begin -->
	    <tr class="header">
	    	<th>Query</th>
	    	<th>Exec Time</th>
	    	<th>Time Taken (ms)</th>
	    	<th>Execute</th>
	    </tr>
    <!-- Column section end -->
    
<%  
    if (dsSize <= 0) {
%>    
    	<tr class="lineitemA"><td align="center" colspan="4">No history found</td></tr>
<%
    }
%>

<!-- Row section begin -->
<% 
	SimpleRow row = null;
	String qryid = null;
	String strqryid = null;
	String qry = null;
	String strqry = null;
    String rowClass = "lineitemA";
    for (int i = 0; i < dsSize; i++) {
        row = ds.getDataRow(i);
        qryid = "histqry"+i;
        strqryid = "histstrqry"+i;
        qry = row.get(0).toString();
        strqry = (qry.length() > 50) ? qry.substring(0, 50) : qry;
        if (i%2 == 1) {
            rowClass = "lineitemA";
        } else {
            rowClass = "lineitemB";
        }
%>   
    <tr class="<%=rowClass%>">
        	<td id="tmp" onMouseOver="javascript:showQry(this, '<%=qryid%>')" 
        				onMouseOut="javascript:showQry(this, '<%=strqryid%>')"><%=strqry%></td>
        	<td><%=row.get(1)%></td>
        	<td align="right"><%=row.get(2)%></td>
            <td align="center"><a class="highlight2" href="#" onClick="javascript:copyQry('<%=qryid%>')"> Copy&nbsp;>> </a><br>
            	<a class="highlight2" href="#" onClick="javascript:deleteQry('<%=i%>')"> Delete </a>
            </td>
        </tr>
        <input type="hidden" id="<%=qryid%>" value="<%=qry%>">
        <input type="hidden" id="<%=strqryid%>" value="<%=strqry%>">
<%
    }
%>
<!-- Row section end -->	
  	</table>

</form>
</body>
</html>
