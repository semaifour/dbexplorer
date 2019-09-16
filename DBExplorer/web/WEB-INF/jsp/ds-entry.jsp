<%@ include file="include.jsp"%>
<%@page import="java.util.Map"%>
<%@ page import="com.silrais.toolkit.dataset.SimpleDataSet"%>
<%@page import="com.silrais.toolkit.dataset.DataSetColumn"%>
<%@page import="java.lang.Integer"%>
<%@page import="com.silrais.toolkit.dataset.SimpleRow"%>
<%@page import="com.silrais.webtoolkit.util.HttpUtil"%>
<%@page import="s3tool.jdog.domain.JDDataSource"%>
<%@page import="s3tool.jdog.web.util.DataSetContext"%>
<%@page import="com.silrais.toolkit.util.SimpleUtil"%>
<%@page import="com.silrais.toolkit.datasource.JDBCDataSource.DBVendor"%>

<html>
<head>
<link rel="stylesheet" type="text/css" href="css/jdog.css">
</head>


<%
	Map model = (Map) request.getAttribute("model");
	JDDataSource dataSource = (JDDataSource) model.get("datasource");

	String user = SimpleUtil.getNNStr(dataSource.getUsername());
	String server = SimpleUtil.getNNStr(dataSource.getServer());
	String port = SimpleUtil.getNNStr(dataSource.getPort());
	String db = SimpleUtil.getNNStr(dataSource.getDatabase());
	DBVendor make = dataSource.getDBVendor();
%>
<body>
<center>
<form name="ds" action="connect.do" method="post" target="_top">
<table>
	<tr class="header">
		<th colspan="2">Connect to Data Source</th>
	</tr>
	<tr class="lineitemA">
		<td>User Name</td>
		<td><input name="user" type="textbox" value="<%=user%>" size="15"></td>
	</tr>
	<tr class="lineitemB">
		<td>Password</td>
		<td><input name="pwd" type="password" size="15"></td>
	</tr>
	<tr class="lineitemA">
		<td>Database Type</td>
		<td><select name="make">
		<% 
			String mkname = null;
			String mkselected = null;
			for(DBVendor mk : DBVendor.values()) {
				mkname = mk.toString();
				mkselected = mk == make ? "SELECTED" : "";
				
		%>
			<option id="<%=mkname%>" <%=mkselected%> ><%=mkname%></option>
		<% 		
			}
		%>
		</select></td>
	</tr>
	<tr class="lineitemB">
		<td>Database Server</td>
		<td><input name="server" type="textbox" value="<%=server%>"
			size="15"></td>
	</tr>
	<tr class="lineitemA">
		<td>Database Port</td>
		<td><input name="port" type="textbox" value="<%=port%>" size="5"></td>
	</tr>
	<tr class="lineitemB">
		<td>Database Name</td>
		<td><input name="db" type="textbox" value="<%=db%>" size="15"></td>
	</tr>
	<tr class="lineitemA">
		<td align="right"><input name=clear value=" Clear " type=reset></td>
		<td><input name=submit value=" Connect " type=submit></td>
	</tr>
</table>
</form>
</center>
</body>
</html>