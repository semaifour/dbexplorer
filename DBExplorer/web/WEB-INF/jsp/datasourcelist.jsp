<%@ include file="include.jsp" %>
<%@ page import="s3tool.jdog.web.util.DataSourceRowAction"%>

<html>
<head>
<link rel="stylesheet" type="text/css" href="css/jdog.css">
</head>
<body>
    <%
        request.setAttribute("dskey", "DSList");
        int[] colIdDispFilter = new int[] {0,1,2,3,5,6,8};
        request.setAttribute("cdfilter", colIdDispFilter);
        request.setAttribute("mrpp", "500"); //max recs per page
        request.setAttribute("dsrowview", "false");
        request.setAttribute("title", "Configured Data Sources");
        DataSourceRowAction axn = new DataSourceRowAction();
        axn.setLabel("Explore");
        axn.setTarget("_top");
        axn.setActionURL("connect.do");
        axn.setVariableNames("ds","dsg", "dsn");
        axn.setValueIndices(0,1,2);
        request.setAttribute("rowactions", new DataSourceRowAction[] { axn });

    %>
    <%@ include file="datasetrenderer.jsp" %>
</body>
