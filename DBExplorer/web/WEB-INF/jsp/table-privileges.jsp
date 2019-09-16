<%@ include file="include.jsp" %>
<%@ page import="s3tool.jdog.component.privilege.TablePrivilegesComponent"%>
<%@ page import="s3tool.jdog.domain.TablePrivileges"%>

<html>
<head>
<link rel="stylesheet" type="text/css" href="css/jdog.css">
</head>
<body>
    <%
    
    	TablePrivilegesComponent tablePrivileges = new TablePrivilegesComponent();
    	Map<String, ?> mymodel = tablePrivileges.handleRequest(request,response);

        request.setAttribute("model", mymodel);
        request.setAttribute("dskey", "TablePrivileges");
        //int[] colIdDispFilter = new int[] {0,1,2};
        //request.setAttribute("cdfilter", colIdDispFilter);
        request.setAttribute("mrpp", "50"); //max recs per page
        request.setAttribute("idcolidx","0");
        request.setAttribute("dsrowview", "false");
    %>
    <%@ include file="datasetrenderer.jsp" %>
</body>