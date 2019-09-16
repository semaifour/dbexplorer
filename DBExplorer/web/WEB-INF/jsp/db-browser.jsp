<%@ page import="com.silrais.webtoolkit.util.HttpUtil"%>

<%
	String ds = request.getParameter("ds");
	String params = HttpUtil.toHttpQueryString("ds", ds);
%>
<frameset cols="200,*">
    <frame name="dbbleftschframe" src="showschemalist.do?otype=SCHEMA&<%=params%>" />
    <frame name="dbbcontentframe"  src="exejsp.do?jsp2fwd2=jdog-footer" />
</frameset>
