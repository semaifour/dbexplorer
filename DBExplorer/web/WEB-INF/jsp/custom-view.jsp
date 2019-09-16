<%@ page import="com.silrais.webtoolkit.util.HttpUtil"%>
<%
	String ds = request.getParameter("ds");
	String params = HttpUtil.toHttpQueryString("ds", ds);
%>
<frameset cols="300,*">
    <frame name="custleftframe" title="custleftframe" src="showcustviewmenu.do?cvrsc=jdog-custom-views.xml&targetframe=custcontentframe&title=Custom Views&&<%=params%>" />
    <frame name="custcontentframe" title="custcontentframe" src="exejsp.do?jsp2fwd2=jdog-footer" />
</frameset>
