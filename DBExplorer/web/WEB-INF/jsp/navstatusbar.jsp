<%@ page import="com.silrais.toolkit.util.SimpleUtil"%>
<%
	String elmnt0 = (String) request.getParameter("cat");
	String elmnt1 = (String) request.getParameter("sch");
	String elmnt2 = (String) request.getParameter("oname");
	String elmnt3 = (String) request.getAttribute("elmnt3");
	String elmnt4 = (String) request.getAttribute("elmnt4");
	if (SimpleUtil.isnull(elmnt0)) {
		elmnt0 = "CATALOG";
	}

	if (SimpleUtil.isnull(elmnt1)) {
		elmnt1 = "SCHEMA";
	}

	if (SimpleUtil.isnull(elmnt2)) {
		elmnt2 = "TABLE";
	}
%>
<table width=100%>
	<tr>
		<th class="navstatusbar"><b> <%=elmnt0%> &nbsp;&gt;&gt;&nbsp;
		<%=elmnt1%> &nbsp;&gt;&gt;&nbsp; <%=elmnt2%> <%
 	if (!SimpleUtil.isSize0(elmnt3)) {
 %> &nbsp;&gt;&gt;&nbsp;<%=elmnt3%> <%
 	}

 	if (!SimpleUtil.isSize0(elmnt4)) {
 %> &nbsp;&gt;&gt;&nbsp;<%=elmnt4%> <%
 	}
 %> </b></th>
		<!--
    <td>
        <a href="#" onClick="HistoryWindow=window.open('showaxnhistlist.do?axn=sahl','history','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=300,height=500,left=300'); HistoryWindow.focus(); return false;">History</a>
    </td>
    -->
	</tr>
</table>
