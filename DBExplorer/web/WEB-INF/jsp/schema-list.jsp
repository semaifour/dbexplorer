<%@ include file="/WEB-INF/jsp/include.jsp" %>

<%@ page import="java.util.Map" %>
<%@ page import="com.silrais.toolkit.dataset.SimpleDataSet"%>
<%@ page import="com.silrais.toolkit.dataset.SimpleRow"%>
<%@ page import="com.silrais.toolkit.util.SimpleUtil"%>

<%
    Map model = (Map) request.getAttribute("model");
    SimpleDataSet ds = (SimpleDataSet) model.get("SchemaList");
    String catalog = request.getParameter("cat");
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/jdog.css">

<script language="javascript">
    function loadobjectframe(index) {
        document.forms[0].submit();
    }
</script>
</head>
<body>
<form name="schemaform" action="exejsp.do" target="dbbleftobjframe">
<input type="hidden" name="cat" value="<%=catalog%>">
<input type="hidden" name="jsp2fwd2" value="dbo-list">
<table width="100%">
    <tr class="header">
        <th> Schema </th>
    </tr>
    <tr class="lineitemA">
        <td>
            <select name="sch" onChange="javascript:loadobjectframe(this.selectedIndex);">
<%
    String rowClass = "";
    int size = ds.size();
    SimpleRow row = null;
   Object name = null;
    for (int i = 1; i < size; i++) {
        row = ds.getDataRow(i);
        name = row.get(1);
%>
            <option name="<%=name%>" value="<%=name%>"> <%=name%></option>   
<%
    }
%>
        </select>
        </td>
    </tr>
</table>
</form>
<script language="javascript">
    loadobjectframe(0);
</script>
</body>
</html>

