<%@ include file="include.jsp" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/jdog.css">
</head>
<body>
    
    <%
        request.setAttribute("dskey", "TableData");
        int[] colIdDispFilter = new int[] {1,2,3,4,5};
        request.setAttribute("cdfilter", colIdDispFilter);
        request.setAttribute("mrpp", "50");
        request.setAttribute("actvtab", "tab");
        request.setAttribute("idcolidx","0");
        request.setAttribute("dspaginated", "true");
        request.setAttribute("dssortable", "true");
        request.setAttribute("dsexpand", "true");
        request.setAttribute("dsfilter", "true");
        request.setAttribute("dsbrowse", "true");
        request.setAttribute("dsrowview", "true");
        String thisPageURL = "showtabdata.do?isdov=true";
        request.setAttribute("callBackURL", thisPageURL);

        Map model1 = (Map)request.getAttribute("model");
        request.setAttribute("cfrid", model1.get("cfrid"));
        request.setAttribute("dsid", model1.get("dsid"));
        request.setAttribute("flr.whereClauseMap",
                             model1.get("flr.whereClauseMap"));
        request.setAttribute("elmnt3", "Data");
    %>
    <%@ include file="datasetrenderer.jsp" %>
</body>    
