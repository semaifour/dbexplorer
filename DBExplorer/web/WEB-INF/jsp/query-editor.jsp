<%@ page import="com.silrais.webtoolkit.util.HttpUtil"%>
<html>
<%
	String ds = request.getParameter("ds");
	String params = HttpUtil.toHttpQueryString("ds", ds);
%>

<head>
    <link rel="stylesheet" type="text/css" href="css/jdog.css">
</head>
<body>
    
    <form method=post action="exeqry.do?axn=exec" target="queryresult">
        <input type="hidden" name="ds" value="<%=ds%>"/>
        <table width="100%" border=0>
            <tr class="header"><th> Query Editor </th></tr>
            
         	<tr class="lineitemA"><td align=center><textarea name="qry" cols="80" rows=5></textarea></td></tr>
        	<tr class="lineitemA"><td align=center><input type="submit" value =" Execute Query "></td></tr>
    	</table>
        <input type="hidden" name="qrytype" value="usrqry">
    </form>
</body>
</html>
