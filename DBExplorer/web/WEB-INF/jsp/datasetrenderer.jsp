<%@page import="java.util.Map"%>
<%@ page import="com.silrais.toolkit.dataset.SimpleDataSet"%>
<%@page import="com.silrais.toolkit.dataset.DataSetColumn"%>
<%@page import="java.lang.Integer"%>
<%@page import="com.silrais.toolkit.dataset.SimpleRow"%>
<%@page import="com.silrais.webtoolkit.util.HttpUtil"%>
<%@page import="s3tool.jdog.web.util.DBOContext"%>
<%@page import="s3tool.jdog.web.util.DataSetContext"%>
<%@page import="com.silrais.toolkit.util.SimpleMap"%>
<%@page import="java.text.MessageFormat"%>
<%@page import="com.silrais.toolkit.util.SimpleUtil"%>
<%@ page import="com.silrais.webtoolkit.util.SimpleRowAction"%>

<% 
	String dataSetKey   = (String) request.getAttribute("dskey");
    int[] cdFilter      = (int[]) request.getAttribute("cdfilter");
    Map model           = (Map) request.getAttribute("model");
    String rowClass = "lineitemA";
    int mrpp            = 25;

    try {
        mrpp = HttpUtil.getIntAttr(request,"mrpp");
    } catch (Exception e) { }

    boolean dspaginated = HttpUtil.getBooleanAttr(request, "dspaginated"); 
    boolean dssortable  = HttpUtil.getBooleanAttr(request, "dssortable"); 
    boolean dsexpand    = HttpUtil.getBooleanAttr(request, "dsexpand");
    boolean dsfilter    = HttpUtil.getBooleanAttr(request, "dsfilter");
    boolean dsbrowse    = HttpUtil.getBooleanAttr(request, "dsbrowse");
    boolean dsrowview   = HttpUtil.getBooleanAttr(request, "dsrowview");
    SimpleMap whereClauseMap = (SimpleMap) request.getAttribute("flr.whereClauseMap");
    String callBackURL   = (String)request.getAttribute("callBackURL");
    
    SimpleDataSet ds    = (SimpleDataSet) model.get(dataSetKey);

    DataSetColumn[] cols = ds.getColumns();
    String title = HttpUtil.getStrParamB4Attr(request, "title");
    

    SimpleRow row = null;
    

    int dsSize = ds.size();
    mrpp   = dsSize;

    
    int tmpIdx = 0;
    int rowSize = 0;
    int startRowIdx = 1;

    int idColIdx = HttpUtil.getIntAttr(request, "idcolidx");
    String idParamName = HttpUtil.getStrAttr(request, "idparamname");
    if (SimpleUtil.isnull(idParamName)) {
    	idParamName = "rowid";
    }

    DBOContext dboctx = new DBOContext();
    dboctx.setContext(request);

    //prepare dataset context
    DataSetContext dsctx = new DataSetContext();
    dsctx.setContext(request);

    int curFirstRowId = dsctx.getcfrid(); 
    if (curFirstRowId < 0) {
        curFirstRowId = 1;
        dsctx.setcfrid(curFirstRowId);
    }
    
    String cdmodeSym = ">>";

    if (">>".equals(dsctx.getdmode()) && dsexpand ) {
        cdmodeSym = "<<";
        //set cdFilter to have all columns

        cdFilter = new int[cols.length];
        for (int i = 0; i < cols.length ; i++) {
            cdFilter[i] = i; 
        }
    }

    if (cdFilter == null) {
        cdFilter = new int[] {0,1,2,3,4,5,6,7,8,9};
    }

    
    //add dbo context - cat,sch and tab
    callBackURL = callBackURL +"&" + dboctx.getHttpParamString();

    //add dataset context if any: scol, cfrid 
    String url2Paginate = callBackURL +"&" + dsctx.getHttpParamString(true); 

    String url2Sort = callBackURL + "&dsaxn=srt&dmode="+dsctx.getdmode();
    
    String curSortCol = dsctx.getscol();
    if (SimpleUtil.isnull(curSortCol)) {
        curSortCol = null;
    }
    String curSortOdr = dsctx.getsodr();
    String tobSortOdr = null;
    String curSortSym = null;
%>
<table width=100%>

	<%
    	if (SimpleUtil.isnotnull(title)) {
    %>
	<tr>
		<th class="leftalign" colspan="<%=cdFilter.length+1%>"><%=title%></th>
	</tr>
	<%
        }
    %>
	<%
    if (dspaginated) {
%>
	<tr class="lineitemA">
		<td>&nbsp;</td>
		<td colspan="<%=cdFilter.length-1%>" align=center>
		<%
            if (curFirstRowId == 1) {
        %> &lt;&lt;Previous <%    
            } else {
        %> <a class="highlight2"
			href="<%=callBackURL%>&scol=<%=dsctx.getscol()%>&scolidx=<%=dsctx.getscolidx()%>&cfrid=1&dmode=<%=dsctx.getdmode()%>&dsid=<%=dsctx.getdsid()%>&title=<%=title%>">|&lt;&lt;</a>&nbsp;&nbsp;
		<a class="highlight2"
			href="<%=url2Paginate%>&dsaxn=prv&dsid=<%=dsctx.getdsid()%>&title=<%=title%>">&lt;&lt;Previous</a>
		<%
            }
         %> | <%
            if (dsSize < mrpp) {
         %> Next&gt;&gt; <%
            } else {
         %> <a class="highlight2"
			href="<%=url2Paginate%>&dsaxn=nxt&dsid=<%=dsctx.getdsid()%>&title=<%=title%>">Next&gt;&gt;
		</a> <%
            }
        %>
		</td>

		<td><a
			href="<%=url2Paginate%>&dsaxn=dwnld&dsid=<%=dsctx.getdsid()%>&name=<%=title%>&ct=application/csv&ctw=csv-tab">Download</a></td>

	</tr>
	<%
    } //if dspaginated
%>
	<!-- column section begin -->
	<tr class="header">
		<%
        StringBuffer filterRow = new StringBuffer("<TR class=\"lineitemB\">");
        String inputTemp = "<input name=\"{0}\" value=\"{1}\" size=\"{2}\">";
        String colName = null;
        String colVal  = null;
        Object[] objArray = null;
        boolean sortColFound = false;
        for (int i = 0; i < cdFilter.length; i++) {
            tmpIdx = cdFilter[i];
            colVal = null;
            if (tmpIdx < cols.length) {
                colName = cols[tmpIdx].getColumnName();
                if (dssortable && tmpIdx > 1) {
                    //determine sort order, symbol
                    if (curSortCol != null && !sortColFound 
                            && curSortCol.equalsIgnoreCase(colName)) {

                        sortColFound = true;
                        if ("ASC".equalsIgnoreCase(curSortOdr)) {
                            //curSortSym = "&#33";
                            //curSortSym = "\\/";
                            curSortSym = "image/down.gif";
                            tobSortOdr = "DESC";
                         } else {
                            //curSortSym = "&#161";
                            //curSortSym = "/\\";
                            curSortSym = "image/up.gif";
                            tobSortOdr = "ASC";
                        }
                    } else {
                        curSortSym = "";
                        tobSortOdr  = "ASC";
                    }

    %>
		<TH nowrap="nowrap"><a class="highlight1"
			href="<%=url2Sort%>&scol=<%=cols[tmpIdx].getColumnName()%>&scolidx=<%=cols[tmpIdx].getRSColumnIndex()%>&dsid=<%=dsctx.getdsid()%>&sodr=<%=tobSortOdr%>&title=<%=title%>"><%=colName%></a>
		<%
    				if (SimpleUtil.isnotnull(curSortSym)) {
    			%> &nbsp;<img height="12" width="12" src="<%=curSortSym%>" />&nbsp;
		<% 
    				}
    			%>
		</TH>
		<%
                } else {
    %>
		<TH><%=colName%></TH>
		<%
                }
                //<input> not required for first two cols that is PHY_ROW_NUM &
                //J_REC_ID
                if (tmpIdx > 1) {
                    if (whereClauseMap != null) {
                        objArray = (Object[]) whereClauseMap.get(colName);
                        if (objArray != null && objArray.length >=2) {
                            colVal = (String)objArray[1];
                        } else {
                            colVal = "";
                        }
                    }
                    filterRow.append("<TD>")
                            .append(MessageFormat.format(inputTemp,
                                                         new Object[] {colName,
                                                                       colVal,
                                                                       "10"
                                                                       }))
                            .append("</TD>");
                } else {
					if (tmpIdx == 1) {
						filterRow.append("<TD align=\"center\"><input type=\"submit\" value=\" Filter \"></TD>");
					} else {
                    	filterRow.append("<TD>&nbsp;</TD>");
                    }
                }
            }
        }
        filterRow.append("<TD align=\"center\"><input type=\"submit\" value=\" Filter \"></TD></TR>");
    %>

		<TH>
		<%
        if (dsexpand) {
    %> <a class="highlight1"
			href="<%=callBackURL%>&dmode=<%=cdmodeSym%>&dsaxn=cdm&scol=<%=dsctx.getscol()%>&scolidx=<%=dsctx.getscolidx()%>&cfrid=<%=dsctx.getcfrid()%>&dsid=<%=dsctx.getdsid()%>&title=<%=title%>"><%=cdmodeSym%></a>
		<%
        } else {
    %> <%=cdmodeSym%> <%
        }
    %>
		</TH>
	</tr>

	<%
        if (dsfilter) {
     %>
	<form name="dsfilter" method="post"
		action="<%=callBackURL%>&scol=<%=dsctx.getscol()%>&scolidx=<%=dsctx.getscolidx()%>&cfrid=1&dmode=<%=dsctx.getdmode()%>&dsid=<%=dsctx.getdsid()%>&dsaxn=flr&title=<%=title%>">
	<%=filterRow.toString()%></form>
	<%
        }
    %>
	<%  
        if (dsSize <= 1) {
    %>
	<tr class="lineitemA">
		<td colspan="<%=cdFilter.length+1%>">No records found</td>
	</tr>
	<%
        }
    %>
	<% 
        for (int i = startRowIdx; (i < startRowIdx+mrpp) && (i < dsSize); i++) {
            row = ds.getDataRow(i);
            rowSize = row.size();
            if (i%2 == 1) {
                rowClass = "lineitemA";
            } else {
                rowClass = "lineitemB";
            }
    %>
	<tr class="<%=rowClass%>">

		<% 
            String val, text1, more, text2;

            for (int j = 0; j < cdFilter.length; j++) {
                tmpIdx = cdFilter[j];
                if (tmpIdx < rowSize) {
					if (row.get(tmpIdx) instanceof java.lang.String && ((String)row.get(tmpIdx)).length() > 30) {
						val = (String) row.get(tmpIdx);
						text1 = val;
						text2 = ""; 
						more  = "";
						if (val != null && val.length() > 30) {
							text1 = val.substring(0, 30);
							text2 = val.substring(30, val.length());
						} 
						
		%>
		<TD class="popup"><%=text1%><span class="comment"><%=text2%></span></TD>
		<%		 	
				    } else { 
        %>
		<TD><%=row.get(tmpIdx)%></TD>
		<%
            		}
				}
            }
        %>
		<TD>
		<% 
                    if (dsrowview) { 
                 %> <a class="highlight2"
			href="showrowdtl.do?dsid=<%=ds.getId()%>&idcolidx=<%=idColIdx%>&<%=idParamName%>=<%=row.get(idColIdx)%>&<%=dboctx.getHttpParamString()%>&dsbrowse=<%=dsbrowse%>">
		View <a> <%
                    }

                    if (dsbrowse) {
                %> | <a class="highlight2"
			href="browserowrefdata.do?jsp2fwd2=browserowrefdata&dsid=<%=ds.getId()%>&idcolidx=<%=idColIdx%>&<%=idParamName%>=<%=row.get(idColIdx)%>&<%=dboctx.getHttpParamString()%>&dsbrowse=true"
			target="dbbcontentframe"> Browse </a> <%
                    }
                %> <% 
                	SimpleRowAction[] actions = (SimpleRowAction[]) request.getAttribute("rowactions");
	    			String seperator = "";
                	if (actions != null) {
                		for(SimpleRowAction action : actions) {
                			action.setCurrentRow(row);
                			String url = action.compileActionURL();
                %> <a class="highlight2"
			target="<%=action.getTarget()%>" href="<%=url%>"> <%=action.getLabel()%></a>
		<%=seperator%> <% 			
                			seperator = "|";
                		}
                	}
                %>
		</TD>
	</tr>
	<%
        }
    %>
	<!--- data section end -->
</table>
