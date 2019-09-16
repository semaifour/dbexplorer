<%@ include file="include.jsp" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.silrais.toolkit.dataset.SimpleRow" %>
<%@ page import="s3tool.jdog.domain.ReferentialKeyList" %>

<html>
<head>
<link rel="stylesheet" type="text/css" href="css/jdog.css">
</head>
<body>
    <%
        Map ermodel = (Map) request.getAttribute("model");
        ReferentialKeyList impKeyList = (ReferentialKeyList) ermodel.get("ImportedKeysList");
        ReferentialKeyList expKeyList = (ReferentialKeyList) ermodel.get("ExportedKeysList");
        
        request.setAttribute("actvtab", "ber"); //browse ER

        //for nav status bar
        request.setAttribute("elmnt3", "Referential Keys");
       
       String ds = (String) request.getParameter("ds");
        
    %>
<!--- ER table section begin -->
<!-- Note: Outer table, tr, th tags are added for each table to give dark
background to get a outer line arround the table -->

<%@ include file="navstatusbar.jsp" %>

<table width=100%>
<tr><td>
    <%@ include file="dbotabs.jsp" %>
</td><tr>
</table>
<br>
<table width=100%><tr><th>
    <table width=100%>
        <tr>
            <!-- imported key details begin -->
            <td valign=top>
                <table width=100%>
		<tr class="header"><th colspan=3>Keys imported by table "<%=request.getParameter("oname")%>"</th></tr>
                <tr class="header2"><th>PK Table</th><th>PK Column</th><th>Table.FK Column</th> </tr>
                <%
                    int iSize = impKeyList.size();
                    SimpleRow krow = null;
                    for (int i = 1; i < iSize ; i++) {
                        krow = impKeyList.getDataRow(i);
                %>
                        <tr class="lineitemA"><td><a href="showrefkeylists.do?ds=<%=ds%>&sch=<%=krow.get(2)%>&oname=<%=krow.get(3)%>"><%=krow.get(3)%></a></td><td><%=krow.get(4)%></td><td><%=krow.get(8)%> </td></tr>
               <%
                    }
                %>
                </table>
            </td>
           <!-- imported key details end -->
            <!--- exported key details debgin -->
            <td valign=top>
              <table width=100%>
   	        <tr class="header"><th colspan=3>Keys exported from table "<%=request.getParameter("oname")%>" </th></tr>
                <tr class="header2"><th>Table.PK Column</th><th>FK Column</th><th>FK Table</th></tr>
                <%
                    iSize = expKeyList.size();
                    krow = null;
                    for (int i = 1; i < iSize ; i++) {
                        krow = expKeyList.getDataRow(i);
                %>
                        <tr class="lineitemA"><td><%=krow.get(4)%></td><td><%=krow.get(8)%></td><td><a href="showrefkeylists.do?ds=<%=ds%>&sch=<%=krow.get(6)%>&oname=<%=krow.get(7)%>"><%=krow.get(7)%></a></td></tr>
               <%
                    }
                %>
                </table>
 
            </td>
            <!--- exported key details debgin -->
        </tr>
    </table>
</th></tr></table>
<!-- ER table section end -->
<br>
<br>
<!-- imported key detail section begin -->

<table><tr><th>
<table>
<tr class="header"><th align=left> Details of imported keys </th><tr>
<tr><td align=left valign=top>
    <%
        request.setAttribute("dskey", "ImportedKeysList");
        int[] colIdDispFilter1 = new int[] {0,2,3,4,6,7,8,12,13};
        request.setAttribute("cdfilter", colIdDispFilter1);
        request.setAttribute("mrpp", "50");
        request.setAttribute("idcolidx","0");
        request.setAttribute("dspaginated", "false");
        request.setAttribute("dsexpand", "false");
        request.setAttribute("dssortable", "false");
        request.setAttribute("dsrowview", "true");
    %>
    <jsp:include page="datasetrenderer.jsp" />
</td></tr>
</table>
</th></tr><table>
<!-- imported key detail section end -->

<br>
<br>
<!-- exported key detail section begin -->

<table><tr><th>
<table>
<tr class="header"><th align=left> Details of exported keys </th><tr>
<tr><td align=left valign=top>
    <%
        request.setAttribute("dskey", "ExportedKeysList");
        request.setAttribute("cdfilter", colIdDispFilter1);
        request.setAttribute("mrpp", "50");
        request.setAttribute("idcolidx","0");
        request.setAttribute("dspaginated", "false");
        request.setAttribute("dsexpand", "false");
        request.setAttribute("dssortable", "false");
        request.setAttribute("dsrowview", "true");

    %>
    <jsp:include page="datasetrenderer.jsp" />
</td></tr>
</table>
</th></tr></table>
<!-- exported key detail section begin -->
</body>
