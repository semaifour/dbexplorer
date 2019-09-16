<%@ page import="com.silrais.webtoolkit.util.HttpUtil"%>
<%
	String ds = request.getParameter("ds");
	String params = HttpUtil.toHttpQueryString("ds", ds);
%>

<frameset cols="325,*">
    <frame name="queryhistory" title="queryhistory" src="qryhist.do?<%=params%>"/>
	
	<frameset rows="200,*">
	    <frame name="queryeditor" title="queryeditor" src="exejsp.do?jsp2fwd2=query-editor&<%=params%>"/>
	    <frame name="queryresult" title="queryresult" src="">
	</frameset>
</frameset>	
