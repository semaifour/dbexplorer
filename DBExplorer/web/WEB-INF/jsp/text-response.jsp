<%@page import="java.util.Map" %>
<%
    Map model        = (Map) request.getAttribute("model");
    Object oResponse = model.get("oResponse");
    out.print(oResponse.toString());
%>
