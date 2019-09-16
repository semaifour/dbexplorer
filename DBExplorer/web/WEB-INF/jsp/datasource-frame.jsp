<%@ page import="com.silrais.webtoolkit.util.HttpUtil"%>
<%
	String ds = request.getParameter("ds");
	String params = HttpUtil.toHttpQueryString("ds", ds);
%>
<frameset cols="300,*">
    <frame name="dsleftframe" title="dsleftframe" src="showcustviewmenu.do?cvrsc=jdog-datasource-navigation.xml&targetframe=dscontentframe&title=Data Sources&<%=params%>" />
    <frame name="dscontentframe" title="dscontentframe" src="exejsp.do?jsp2fwd2=jdog-footer" />
</frameset>