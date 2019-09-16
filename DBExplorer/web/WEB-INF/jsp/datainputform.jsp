<%@ page import="java.sql.Types" %>
<%@ page import="java.text.MessageFormat" %>
<%@ page import="com.silrais.toolkit.util.SimpleParameter" %>
<%@ page import="com.silrais.toolkit.util.SimpleUtil" %>

<%
    String paramkey = (String) request.getAttribute("paramkey");
    SimpleParameter[] params = (SimpleParameter[]) request.getAttribute(paramkey);
    String formid = (String) request.getAttribute("formid");
    String actionURL = (String) request.getAttribute("actionURL");
    String actionTarget = (String) request.getAttribute("actionTarget");
%>
<form name="form1" method="post" action="<%=actionURL%>" target="<%=actionTarget%>">

<table width=50%>

    <tr class="header"><th colspan=2> <%=request.getAttribute("formtitle")%> </th></tr>
<%
    String rowClass = "";
    String pname = null;
    for (int i = 0; i < params.length; i++) {
        pname = params[i].getName();

        if (i%2 == 0) {
            rowClass = "lineitemA";
        } else {
            rowClass = "lineitemB";
        }

%>
<TR class="<%=rowClass%>">
    <TD>
    <input name="pmlst" type="hidden" value="<%=pname%>">
    <input name="<%=pname%>.idx" type="hidden" value="<%=params[i].getIndex()%>">
    <input name="<%=pname%>.typ" type="hidden" value="<%=params[i].getType()%>">
    <input name="<%=pname%>.crd" type="hidden" value="<%=params[i].getCardinality()%>">
    <%=params[i].get("LABEL")%> 
    </TD>
    <TD>
<%

        switch (params[i].getType()) {

            default:
            case Types.VARCHAR:
%>
    <input name="<%=pname%>" type="text" size="20">
<%
            break;

            case Types.ARRAY:
%>
    <select name="<%=pname%>">
    <%
        SimpleParameter[] options = (SimpleParameter[])params[i].get("OPTIONS");
        for (int j = 0; j < options.length; j++) {
    %>
        <option value="<%=options[j].getValue()%>"><%=options[j].get("LABEL")%> </option>
    <%
        }
    %>
    </select>
<%
            break;
        }

 %>
    </TD>
    </TR>


<%
    }
%>
    <TR class="lineitemA">
    <TD colspan=2 align=center>
    <input type="submit" value=" Execute ">
    </TD>
    </TR>
    <input type="hidden" name="formid" value="<%=formid%>">
</table>
</form>
