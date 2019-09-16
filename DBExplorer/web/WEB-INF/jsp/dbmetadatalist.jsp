<%@ include file="include.jsp" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/jdog.css">
</head>
<body>
    <%
        request.setAttribute("dskey", "DBMDList");
        int[] colIdDispFilter = new int[] {0,1,2};
        request.setAttribute("cdfilter", colIdDispFilter);
        request.setAttribute("mrpp", "500"); //max recs per page
        request.setAttribute("idcolidx","0");
        request.setAttribute("dsrowview", "false");
    %>
    <%@ include file="datasetrenderer.jsp" %>
</body>
