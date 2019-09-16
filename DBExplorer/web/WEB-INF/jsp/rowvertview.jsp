<%@ include file="include.jsp"%>
<%@page import="java.util.Map"%>
<%@ page import="com.silrais.toolkit.dataset.SimpleDataSet"%>
<%@ page import="com.silrais.toolkit.dataset.SimpleColumn"%>
<%@ page import="java.lang.Integer"%>

<%@page import="com.silrais.toolkit.dataset.SimpleRow"%>
<%@page import="com.silrais.toolkit.util.SimpleUtil"%>
<%@page import="com.silrais.webtoolkit.util.HttpUtil"%>
<%@page import="s3tool.jdog.web.util.DBOContext"%>
<%@page import="java.util.Stack"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/jdog.css">
</head>
<body>


<%
	Map model = (Map) request.getAttribute("model");
	SimpleColumn[] cols = (SimpleColumn[]) model.get("cols");
	SimpleRow[] rows = (SimpleRow[]) model.get("rows");
	SimpleRow row = null;
	if (rows == null) {
		row = (SimpleRow) model.get("row");
		rows = new SimpleRow[] { row };
	}

	String dsid = HttpUtil.getStrParam(request, "dsid");
	String idcolidx = HttpUtil.getStrParam(request, "idcolidx");
	int rowid = HttpUtil.getIntParam(request, "rowid");
	int idColIdx = HttpUtil.getIntParam(request, "idcolidx");
	boolean dsbrowse = HttpUtil.getBooleanParam(request, "dsbrowse");
	SimpleDataSet ds = (SimpleDataSet) request.getSession()
			.getAttribute(dsid);
	int dsSize = 25;
	if (ds != null) {
		dsSize = ds.size() - 1; //-1 to exclude 0index
	}

	DBOContext dboctx = DBOContext.getContext(request);

	String pagenateURL = "showrowdtl.do?dsid=" + dsid + "&idcolidx="
			+ idcolidx;

	pagenateURL = pagenateURL + "&" + dboctx.getHttpParamString()
			+ "&dsbrowse=" + dsbrowse;

	boolean ispaginated = true;
	int prowid = rowid - 1;
	int nrowid = rowid + 1;

	//for nav status bar
	request.setAttribute("elmnt4", "Item# " + rowid);
%>

<%@ include file="navstatusbar.jsp"%>

<table align=center>

	<%
		if (ispaginated) {
	%>

	<tr class="lineitemA">
		<td colspan="2" align=center>
		<%
			if (rowid == 1) {
		%> &lt;&lt;Previous <%
 	} else {
 %> <a href="<%=pagenateURL%>&rowid=1">|&lt;&lt;</a>&nbsp;&nbsp; <a
			href="<%=pagenateURL%>&rowid=<%=prowid%>">&lt;&lt;Previous</a> <%
 	}
 %> | <%
 	if (rowid == dsSize) {
 %> Next&gt;&gt; <%
 	} else {
 %> <a href="<%=pagenateURL%>&rowid=<%=nrowid%>">Next&gt;&gt; </a>
		&nbsp;&nbsp; <a href="<%=pagenateURL%>&rowid=<%=dsSize%>">&gt;&gt;|
		</a> <%
 	}
 %>
		</td>
	</tr>
	<%
		}
	%>
	<tr class="header">
		<th>Column Name</th>
		<%
			for (int i = 0; i < rows.length; i++) {
		%>
		<TH>Rec ID: <%=rows[i].get(idColIdx)%> <br>
		<%
			if (dsbrowse) {
		%> <a class="highlight2"
			href="browserowrefdata.do?jsp2fwd2=browserowrefdata&dsid=<%=dsid%>&idcolidx=<%=idColIdx%>&rowid=<%=rows[i].get(idColIdx)%>&<%=dboctx.getHttpParamString()%>&dsbrowse=true"
			target="dbbcontentframe"> Browse </a> <%
 	}
 %>
		</TH>
		<%
			}
		%>
	</tr>
	<%
		for (int i = 0; i < cols.length; i++) {
	%>

	<TR class="lineitemB">
		<TD><%=cols[i].getColumnName()%></TD>
		<%
			for (int j = 0; j < rows.length; j++) {
		%>
		<TD><%=rows[j].get(i)%></TD>
		<%
			}
		%>

	</TR>
	<%
		}
	%>
</table>
</body>