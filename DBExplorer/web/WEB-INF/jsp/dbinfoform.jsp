<%@ include file="include.jsp"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/jdog.css">
</head>
<body>
<form name="ds" action="connect.do" method="post">
<table>
	<tr>
		<th colspan=2>DataSource</th>
	</tr>
	<tr class="lineitemA">
		<td>User Name</td>
		<td><input name="user" type=textbox size=10></td>
	</tr>
	<tr class="lineitemB">
		<td>Password</td>
		<td><input name="pwd" type=password size=10></td>
	</tr>
	<tr class="lineitemA">
		<td>Database Type</td>
		<td><select name="ver">
			<option id="Oracle">Oracle</option>
			<option id="MySQL">MySQL</option>
		</select></td>
	</tr>
	<tr class="lineitemB">
		<td>Database Server</td>
		<td><input name="server" type=texbox size=15></td>
	</tr>
	<tr class="lineitemA">
		<td>Database Port</td>
		<td><input name="port" type=texbox size=5></td>
	</tr>
	<tr class="lineitemB">
		<td>Database Name</td>
		<td><input name="db" type=textbox size=10></td>
	</tr>
	<tr class="lineitemA">
		<td><input name="clear" value=" Clear " type=reset></td>
		<td><input name="submit" value=" Connect " type=submit></td>
	</tr>
</table>
</form>
</body>
</html>