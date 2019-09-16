<%@ include file="include.jsp" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/jdog.css">
</head>
<body>
    <%
        request.setAttribute("dskey", "ColumnList");
        int[] colIdDispFilter = new int[] {0,4,6,7,17,18};
        request.setAttribute("cdfilter", colIdDispFilter);
        request.setAttribute("mrpp", "100"); //max recs per page
        request.setAttribute("actvtab", "col");
        request.setAttribute("idcolidx","0");
        //for navstatusbar
        request.setAttribute("elmnt3", "Column");

        request.setAttribute("dspaginated", "false");
        request.setAttribute("dssortable", "false");
        request.setAttribute("dsfilter", "false");
        request.setAttribute("dsexpand", "true");
        request.setAttribute("dsrowview", "true");
        String thisPageURL = "showcoldtl.do?dummy=dummy";
        request.setAttribute("callBackURL", thisPageURL);
    %>
    
    <%@ include file="navstatusbar.jsp" %>
    <%@ include file="dbotabs.jsp" %>
    <%@ include file="datasetrenderer.jsp" %>
</body>
