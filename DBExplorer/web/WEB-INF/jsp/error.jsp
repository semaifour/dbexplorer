<%@ page import="java.util.*" %>
<%@ page import="com.silrais.toolkit.util.SimpleMap" %>

<html>
<head>
<link rel="stylesheet" type="text/css" href="css/jdog.css">
</head>
<body>
   	<table width="100%" border=0>
   	<tr><td>
			<font size="-1" color="red"><b>Error reported while processing your request </b></font>
   	</td></tr>
   	<tr><td>
   	<OL>
<%
	SimpleMap errmap = (SimpleMap) request.getAttribute("err");
	if (errmap != null && errmap.size() > 0) {
		Collection errs = errmap.values();
		String msg = null;
		for (Iterator it = errs.iterator(); it.hasNext(); ) {
	msg = (String) it.next();
%>
            <LI><%=msg%> </LI>
<%
		}
%>
	</OL>
<%
	}
%>
	
	</td></tr>
	
	<tr><td> Exception Thrown </td></tr>
	
	<tr><td>
	
	<% 
	Object e = request.getAttribute("exception");
	if (e != null) {
	    out.write(e.toString());
	}
	%>
	
	</td></tr>
   	
	<tr><td>
	You may continue to use jdog database object browser; use web browser's "Back" button to reset context. Contact your system administrator if this problem persists.
	</td></tr>
   	</table>
</body>
</html>
