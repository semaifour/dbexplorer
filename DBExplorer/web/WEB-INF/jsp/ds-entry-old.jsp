<html>
<body>
<form name="ds" action="connect.do" method="post">
<table>
	<tr>
		<td colspan=2>DataSource</td>
	</tr>
	<tr>
		<td>User Name</td>
		<td><input name="user" type=textbox size=10></td>
	</tr>
	<tr>
		<td>Password</td>
		<td><input name="pwd" type=password size=10></td>
	</tr>
	<tr>
		<td>Database Type</td>
		<td><select name="ver">
			<option id="Oracle">Oracle</option>
			<option id="MySQL">MySQL</option>
		</select></td>
	</tr>
	<tr>
		<td>Database Server</td>
		<td><input name="server" type=texbox size=15></td>
	</tr>
	<tr>
		<td>Database Port</td>
		<td><input name="port" type=texbox size=5></td>
	</tr>
	<tr>
		<td>Database Name</td>
		<td><input name="db" type=textbox size=10></td>
	</tr>
	<tr>
		<td><input name=clear value=" Clear " type=reset></td>
		<td><input name=submit value=" Connect " type=submit></td>
	</tr>
</table>
</form>
</body>
</html>