<%@ include file="include.jsp" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.silrais.toolkit.dataset.SimpleRow" %>
<%@ page import="s3tool.jdog.web.util.DBOContext" %>
<%@ page import="s3tool.jdog.domain.ReferentialKeyList" %>
<%@ page import="com.silrais.toolkit.dataset.DataSetColumn" %>
<%@ page import="com.silrais.toolkit.util.SimpleMap" %>
<%@ page import="com.silrais.toolkit.util.SimpleUtil" %>

<html>
<head>
<link rel="stylesheet" type="text/css" href="css/jdog.css"></link>

<SCRIPT language="JavaScript">

function hidediv(id) {
	//safe function to hide an element with a specified id
	if (document.getElementById) { // DOM3 = IE5, NS6
		document.getElementById(id).style.display = 'none';
	}
	else {
		if (document.layers) { // Netscape 4
			document.id.display = 'none';
		}
		else { // IE 4
			document.all.id.style.display = 'none';
		}
	}
}

function showdiv(id) {
	//safe function to show an element with a specified id
		  
	if (document.getElementById) { // DOM3 = IE5, NS6
		document.getElementById(id).style.display = 'block';
	}
	else {
		if (document.layers) { // Netscape 4
			document.id.display = 'block';
		}
		else { // IE 4
			document.all.id.style.display = 'block';
		}
	}
}
</SCRIPT>

</head>
<body>
    <%
        Map refmodel = (Map) request.getAttribute("model");
        ReferentialKeyList impKeyList = (ReferentialKeyList) refmodel.get("ImportedKeysList");
        ReferentialKeyList expKeyList = (ReferentialKeyList) refmodel.get("ExportedKeysList");
        DataSetColumn[] dsCols = (DataSetColumn[]) refmodel.get("cols");
        SimpleRow row = (SimpleRow) refmodel.get("row");
        int iSize = impKeyList.size();
        SimpleRow krow = null;
        String rColName = null; //ref col name
        int rColIdx  = 1;
        String rColVal = null;
		String ds = request.getParameter("ds");
%>
    <table width=100%>
        <tr> <th class="tabs" width="80%"> Parent Tables & Rows </th><th class="tabs"  width="20%"><a class="highlight1" href="javascript:showdiv('parent_rec_sec');">Show </a> | <a class="highlight1" href="javascript:hidediv('parent_rec_sec');">Hide </a> </th></tr> 
    </table>
    
    <div id="parent_rec_sec" style="display:block;">

<%
        for (int i = 1; i < iSize ; i++) {
                krow = impKeyList.getDataRow(i);
                //get col name that refers to the parent table
                rColName = (String) krow.get(8);
                //find index of the column that imports/refs parent table 
                for (int j = 0; j < dsCols.length ; j++) {
                    if (rColName.equalsIgnoreCase(dsCols[j].getColumnName())) {
                        rColIdx = dsCols[j].getColumnIndex();
                        rColVal = (String) row.get(rColIdx); 
                        break; //exit when find value of ref col 
                    }
                }
                
                %>
                <table width="100%">
                <tr class="header"> <TH  class="leftalign"><%=krow.get(3)%></TH> 
                <%
                    if (SimpleUtil.isnull(rColVal)) {
                %>
                <TH width="100"> No Ref Value </TH>
                <% } else  { %>
                <TH width="100"><a class="highlight1" href="showtabdata.do?isdov=true&dsaxn=flr&ds=<%=ds%>&cat=<%=krow.get(1)%>&sch=<%=krow.get(2)%>&oname=<%=krow.get(3)%>&<%=krow.get(4)%>=<%=rColVal%>" target="impifid<%=i%>" onClick="javascript:showdiv('impdid<%=i%>');">Show </a>| <a  class="highlight1" href="javascript:hidediv('impdid<%=i%>');"> Hide </a></TH> 
                <% } %>
                </tr>
                </table>
                <div id="impdid<%=i%>" style="display:none;">
       			<!--
                <table width="100%"><tr class="lineitemB"><TD class="leftalign"><%=krow.get(8)%> <b>==&gt;</b> <%=krow.get(4)%></TD> </tr></table>
                -->
                <iframe width=100% name="impifid<%=i%>">
                </iframe>
                </div>
     <%
            }

            if (iSize <= 1) {
     %>
            <table width="100%"><tr class="lineitemA"><td align=center> No foreign key references found from the selected record/table. </td></tr></table>
     <%
            }
     %>
     </div>
    
    <BR/>
    <HR/>
     
    <table width=100%>
        <tr> <th class="tabs" align=center width="80%"> Child Tables & Rows </th><th class="tabs" width="20%"><a class="highlight1" href="javascript:showdiv('child_rec_sec');">Show </a> | <a class="highlight1" href="javascript:hidediv('child_rec_sec');">Hide </a></th></tr> 
    </table>

    <div id="child_rec_sec" style="display:block;">
     <%
            iSize = expKeyList.size();
            krow = null;
            SimpleMap map = new SimpleMap();
            for (int i = 1; i < iSize ; i++) {
                krow = expKeyList.getDataRow(i);
                //get col name that is being refered by a child table
                rColName = (String) krow.get(4);
                //find index of the column that imports/refs parent table 
                rColVal = (String) map.get(rColName);
                for (int j = 0; rColVal == null && j < dsCols.length ; j++) {
                    if (rColName.equalsIgnoreCase(dsCols[j].getColumnName())) {
                        rColIdx = dsCols[j].getColumnIndex();
                        rColVal = (String) row.get(rColIdx); 
                        map.put(rColName, rColVal);
                        break; //exit when find value of ref col 
                    }
                }

                %>
                <table width="100%">
                <tr class="header"> <TH class="leftalign"> <%=krow.get(7)%> </TH>
                
                <%
                    if (SimpleUtil.isnull(rColVal)) {
                %>
                <TH width="100"> No Ref Value </TH>
                <% } else  { %>
                <TH width="100"><a class="highlight1" href="showtabdata.do?isdov=true&dsaxn=flr&ds=<%=ds%>&cat=<%=krow.get(5)%>&sch=<%=krow.get(6)%>&oname=<%=krow.get(7)%>&<%=krow.get(8)%>=<%=rColVal%>" target="expifid<%=i%>" onClick="javascript:showdiv('expdid<%=i%>');">Show </a>| <a class="highlight1" href="javascript:hidediv('expdid<%=i%>');"> Hide </a></TH>
                 <% } %> 
                
                </tr>
                </table>
                <div id="expdid<%=i%>" style="display:none;">
                <!--   
                	<table  width="100%"><tr class="lineitemB"><TD class="leftalign"><%=krow.get(4)%> <b>&lt;==</b> <%=krow.get(8)%></TD></tr></table> 
                -->
                <iframe width=100% name="expifid<%=i%>">
                </iframe>
                </div>
     <%
            }

            if (iSize <= 1) {
     %>
            <table width="100%"><tr class="lineitemA"><td align=center> No foreign key references found to the selected record/table </td></tr></table>
     <%
            }
     %>
     </div>
</body>
</html>
