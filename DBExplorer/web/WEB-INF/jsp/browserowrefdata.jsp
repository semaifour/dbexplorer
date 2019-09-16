<%@ include file="include.jsp" %>
<%@ page import="s3tool.jdog.web.util.DBOContext"%>
<%@ page import="com.silrais.webtoolkit.util.HttpUtil"%>
<%
    
    DBOContext dboctx = new DBOContext().getContext(request);
    int idcolidx = HttpUtil.getIntParam(request, "idcolidx");
    String dsid = HttpUtil.getStrParam(request, "dsid");
    int    rowid = HttpUtil.getIntParam(request, "rowid");

%>
<frameset rows="10%,93%">
    <frame name="contentframehdr" scrolling=no marginwidth=1 marginheight=1 frameborder=0 noresize src="showcntfrmhdr.do?jsp2fwd2=contentframeheader&dsid=<%=dsid%>&idcolidx=<%=idcolidx%>&rowid=<%=rowid%>&<%=dboctx.getHttpParamString()%>&actvtab=dat"></frame>
    <frameset cols="30%,70%" >
        <frame name="rowvertdetails" src="showrowdtl.do?dsid=<%=dsid%>&idcolidx=<%=idcolidx%>&rowid=<%=rowid%>&<%=dboctx.getHttpParamString()%>&dsbrowse=true" marginwidth=1 marginheight=1 frameborder=1></frame>
        <frame name="refdataview" src="showrefdatalists.do?dsid=<%=dsid%>&idcolidx=<%=idcolidx%>&rowid=<%=rowid%>&<%=dboctx.getHttpParamString()%>" marginwidth=1 marginheight=1 frameborder=1></frame>
    </frameset>
</frameset>
