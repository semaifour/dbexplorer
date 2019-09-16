<%@ include file="include.jsp" %>
    <%
        request.setAttribute("title", request.getParameter("sch") + " Objects");
        request.setAttribute("targetframe", "dbbcontentframe");
        request.setAttribute("cat", request.getParameter("cat"));
        request.setAttribute("sch", request.getParameter("sch"));
        String[] navTopItems = {"Table", "View", "Proc"};
        request.setAttribute("navtopitems", navTopItems);

    %>
    <%@ include file="custom-view-navigation.jsp" %>
