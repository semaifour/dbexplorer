<%@page import="java.util.Map" %>
<%@ page import="com.silrais.webtoolkit.util.HttpUtil"%>
<%@ include file="include.jsp" %>
<%

%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/jdog.css">
</head>
<body>
    <%
		String ds = request.getParameter("ds");
    	String cvid = request.getParameter("cvid");
    	String cvname = request.getParameter("cvname");
	
    	String urlParams = HttpUtil.toHttpQueryString("ds", ds, "cvid", cvid, "cvname", cvname);

	    Map model = (Map) request.getAttribute("model");
        request.setAttribute("params", model.get("params"));
        request.setAttribute("paramkey", "params");
        request.setAttribute("formid", cvid);
        request.setAttribute("formtitle", cvname);
        request.setAttribute("actionURL", "showcv.do?axn=exeqry&"+urlParams);
        request.setAttribute("actionTarget", "cvqryresultframe");
    %>

    <center>
    
    <%@ include file="datainputform.jsp" %>

    </center>
</body>
