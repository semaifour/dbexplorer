<%@ page import="com.silrais.webtoolkit.util.HttpUtil"%>
<%
	String ds = request.getParameter("ds");
	String params = HttpUtil.toHttpQueryString("ds", ds);
%>
<frameset cols="300,*">
    <frame name="dbaleftframe" title="dbaleftframe" src="showcustviewmenu.do?cvrsc=jdog-dba-navigation-setup.xml&targetframe=dbacontentframe&title=DBA Tools&<%=params%>" />
    <frame name="dbacontentframe" title="dbacontentframe" src="exejsp.do?jsp2fwd2=jdog-footer" />
</frameset>
