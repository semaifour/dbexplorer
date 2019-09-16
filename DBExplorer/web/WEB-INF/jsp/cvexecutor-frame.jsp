<%@ page import="com.silrais.webtoolkit.util.HttpUtil"%>
<%
		String ds = request.getParameter("ds");
        String cvid = request.getParameter("cvid");
        String cvname = request.getParameter("cvname");
    	
        String params = HttpUtil.toHttpQueryString("ds", ds, "cvid", cvid, "cvname", cvname);

%>
	<frameset rows="175,*" border=0>
	    <frame name="inputformframe" title="inputformframe" src="showcv.do?axn=showform&<%=params%>"/>
	    <frame name="cvqryresultframe" title="cvqryresultframe" src="">
	</frameset>
