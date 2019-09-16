<%@ include file="include.jsp" %>
<%@page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="s3tool.jdog.web.history.ActionHistory" %>
<%@ page import="s3tool.jdog.web.history.Action" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/jdog.css">
</head>
<body>
    <%
    	Map model = (Map) request.getAttribute("model");
            ArrayList actions = (ArrayList) model.get("ActionHistoryList");
            int size = 0;
            if (actions != null) {
                size = actions.size();
            }
    %>
<table>
    <tr><th colspan=2> Action History </th></tr>
    <%
    	Action action = null;
            for (int i = 0; i < size ; i++) {
                action = (Action) actions.get(i);
    %>
     <tr><td><%=(i+1)%></td><td width="500" nowrap><a href="loadaxnurl.do?axn=la&ahidx=<%=i%>" onClick="javascript:window.close(); return true;" target="contentframe"><%=action.getActionName()%></a></td></tr>
     <%
        }
    %>
</table>
</body>
