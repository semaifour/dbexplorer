<%@page import="java.util.Map" %>
<%@page import="java.io.OutputStreamWriter" %>
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
<%@page import="com.silrais.toolkit.dataset.io.SimpleCSVDataSetWriter"%>
<% 
	String dataSetKey   = (String) request.getAttribute("dskey");
    int[] cdFilter      = (int[]) request.getAttribute("cdfilter");
    Map model           = (Map) request.getAttribute("model");
	SimpleDataSet ds    = (SimpleDataSet) model.get(dataSetKey);
	//OutputStreamWriter  osw = new OutputStreamWriter(out);
	SimpleCSVDataSetWriter cvsDSWriter = new SimpleCSVDataSetWriter();
	try {
		cvsDSWriter.write(ds, out);
	} catch(Exception e) {
		//e.printStackTrace(out);
	}
%>

	