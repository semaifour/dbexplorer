<%@ include file="/WEB-INF/jsp/include.jsp" %>

<%@ page import="java.util.Map" %>
<%@ page import="com.silrais.toolkit.dataset.SimpleDataSet"%>
<%@ page import="com.silrais.toolkit.dataset.SimpleRow"%>
<%@ page import="com.silrais.toolkit.util.SimpleUtil"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/jdog.css">
</head>
<body>
<%
    Map model = (Map) request.getAttribute("model");
    SimpleDataSet ds = (SimpleDataSet) model.get("DBOList");
    String objType = request.getParameter("otype");

    String catalog = request.getParameter("cat");
    String schema = request.getParameter("sch");
    String hdrTable = "Table";
    String hdrView = "View";
    String hdrSchema = "Schema";
    String hdrProc = "Proc";
    String hdrCV = "Cust.View";
    int dbotype = 2;
    String thclass_schema = "tabs";
    String thclass_table = "tabs";
    String thclass_view = "tabs";
    String thclass_proc = "tabs";
    String thclass_cv = "tabs";

    if (objType.equalsIgnoreCase("SCHEMA")) {
        //hdrSchema = "[Schema]";
        dbotype = 1;
	thclass_schema = "selectedtab";
    } else if (objType.equalsIgnoreCase("TABLE")) {
        //hdrTable = "[Table]";
        dbotype = 2;
	thclass_table = "selectedtab";
    } else if (objType.equalsIgnoreCase("VIEW")) {
        //hdrView = "[View]";
        dbotype = 3;
	thclass_view = "selectedtab";
    } else if (objType.equalsIgnoreCase("PROC")) {
        dbotype = 4;
        //hdrProc = "[Proc]";
	thclass_proc = "selectedtab";
    } else if (objType.equalsIgnoreCase("CV")) {
        dbotype = 5;
        //hdrCV = "[Cust.View]";
	thclass_cv = "selectedtab";
    }

   
%>
<table width="100%">
    <tr>
        <th class="<%=thclass_schema%>"><a class="highlight1" href="showdbolist.do?cat=<%=catalog%>&sch=<%=schema%>&otype=SCHEMA&"><%=hdrSchema%></a></th>
        <th class="<%=thclass_table%>"><a class="highlight1" href="showdbolist.do?cat=<%=catalog%>&sch=<%=schema%>&otype=TABLE&"><%=hdrTable%></a></th>
        <th class="<%=thclass_view%>"><a class="highlight1" href="showdbolist.do?cat=<%=catalog%>&sch=<%=schema%>&otype=VIEW"><%=hdrView%></a></th>
        <th class="<%=thclass_proc%>"><a class="highlight1" href="showdbolist.do?cat=<%=catalog%>&sch=<%=schema%>&otype=PROC"><%=hdrProc%></a></th>
    </tr>
<%
    String rowClass = "";
    int size = ds.size();
    SimpleRow row = null;
    for (int i = 1; i < size; i++) {
        row = ds.getDataRow(i);
        if (i%2 == 1) {
            rowClass = "lineitemA";
        } else {
            rowClass = "lineitemB";
        }
%>
    <tr class="lineitemB">
            <td colspan=5>
            <% 
                switch (dbotype) {

                case 1: //schema
            %>
                <a href="showdbolist.do?sch=<%=row.get(1)%>&otype=TABLE"> <%=row.get(1)%> </a> 
            <% 
                    break;

                case 2: //table
                case 3: //view
            %>
                <a href="showcoldtl.do?cat=<%=row.get(1)%>&sch=<%=row.get(2)%>&oname=<%=row.get(3)%>&otype=<%=objType%>" target="contentframe"> <%=row.get(3)%> </a> 

            <% 
                    break;

                case 4://proc
            %>
                <a href="showcoldtl.do?cat=<%=row.get(1)%>&sch=<%=row.get(2)%>&oname=<%=row.get(3)%>&otype=<%=objType%>" target="contentframe"> <%=row.get(3)%> </a> 

            <% 
                    break;
                case 5: //custom view
            %>
                <a href="showcv.do?cvid=<%=row.get(1)%>&cvname=<%=row.get(2)%>&otype=<%=objType%>" target="contentframe"> <%=row.get(2)%> </a> 
            <%
                }
            %>
            
            </td> 
        </tr>
<%
    }
%>
</table>
</body>
</html>

