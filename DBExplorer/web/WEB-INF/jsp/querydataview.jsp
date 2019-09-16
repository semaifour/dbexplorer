<%@ include file="include.jsp" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/jdog.css">
</head>
<body class="jdogbody">
    
    <%
        Map model1 = (Map)request.getAttribute("model");

        request.setAttribute("dskey", "TableData");
        int[] colIdDispFilter = new int[] {1,2,3,4,5};
        request.setAttribute("cdfilter", colIdDispFilter);
        request.setAttribute("mrpp", model1.get("mxrecperpage"));
        request.setAttribute("actvtab", "tab");
        request.setAttribute("idcolidx","0");
        request.setAttribute("dspaginated", "false");
        request.setAttribute("dssortable", "false");
        request.setAttribute("dsexpand", "true");
        request.setAttribute("dsfilter", "false");
        request.setAttribute("dsrowview", "true");
        
        String thisPageURL = "exeqry.do?axn=pagn";
        request.setAttribute("callBackURL", thisPageURL);

        request.setAttribute("cfrid", model1.get("cfrid"));
        request.setAttribute("dsid", model1.get("dsid"));
        request.setAttribute("elmnt3", "Data");
        request.setAttribute("title", model1.get("qryname"));
    %>
    <%@ include file="datasetrenderer.jsp" %>
</body>    
