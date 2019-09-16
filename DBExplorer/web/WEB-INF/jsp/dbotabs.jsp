<%@ page import="com.silrais.toolkit.util.SimpleUtil"%>
<%@ page import="s3tool.jdog.web.util.DBOContext"%>

<%
    String actvtab = (String) request.getAttribute("actvtab");
    DBOContext dbctx = new DBOContext();
    dbctx.setContext(request);
    //String dbStr = "cat="+catalog+"&sch="+schema+"&tab="+table;
    String dbStr = dbctx.getHttpParamString();

    String otyp   = dbctx.getOtype();


    
    if (SimpleUtil.isnull(actvtab)) {
        actvtab = "NA";
    }

%>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="navstatusbar">
<tr><td>
<table align="center"> 
<tr>
  <%
    if (actvtab.equals("col")) {
  %>   
    <th class="selectedtab"> <a class="highlight1" target="dbbcontentframe" href="showcoldtl.do?<%=dbStr%>"> Column </a></th>
  <% 
    } else { 
  %>
    <th class="tabs"> <a class="highlight1" target="dbbcontentframe" href="showcoldtl.do?<%=dbStr%>"> Column </a></th>
  <%
     }
  %>
 
 <%
    if (!"PROC".equals(otyp)) {
 %>

  <%
    if (actvtab.equals("dat")) {
  %>   
    <th class="selectedtab"> <a class="highlight1" target="dbbcontentframe" href="showtabdata.do?<%=dbStr%>"> Data </a></th>
  <% 
    } else { 
  %>
    <th class="tabs"> <a class="highlight1" target="dbbcontentframe" href="showtabdata.do?<%=dbStr%>"> Data </a></th>
  <%
     }
  %>

  <%
    if (actvtab.equals("ber")) {
  %>   
    <th class="selectedtab"> <a class="highlight1" target="dbbcontentframe" href="showrefkeylists.do?<%=dbStr%>"> Browse ER </a></th>
  <% 
    } else { 
  %>
    <th class="tabs"> <a class="highlight1" target="dbbcontentframe" href="showrefkeylists.do?<%=dbStr%>"> Browse ER </a></th>
  <%
     }
  %>

    <%
    if (actvtab.equals("idx")) {
  %>   
    <th class="selectedtab"> <a class="highlight1" target="dbbcontentframe" href="showidxlist.do?<%=dbStr%>"> Index </a></th>
  <% 
    } else { 
  %>
    <th class="tabs"> <a class="highlight1" target="dbbcontentframe" href="showidxlist.do?<%=dbStr%>"> Index </a></th>
  <%
     }
    }
  %>
    </tr> 
</table>
</td></tr>
<tr><td valign="top"><hr/></td></tr>
</table>
