package s3tool.jdog.web.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import s3tool.jdog.domain.QueryResultDataSet;

import com.silrais.toolkit.dataset.SimpleDataSet;
import com.silrais.toolkit.dataset.SimpleRow;
import com.silrais.toolkit.util.SQLFilter;
import com.silrais.toolkit.util.SimpleMap;
import com.silrais.toolkit.util.SimpleUtil;

public class ExecuteQueryController extends JDOGBaseActionController {

    public static final String RS_AXN_NEXT   = "nxt";
    public static final String RS_AXN_PREV   = "prv";
    public static final String RS_AXN_SORT   = "srt";
    public static final String RS_AXN_FILTER = "flr";
    public static final String RS_AXN_CH_DISP_MODE = "cdm";


    protected int maxRecordCountPerPage = 25;

    @SuppressWarnings("unchecked")
	public ModelAndView handleRequest(HttpServletRequest req,
                                      HttpServletResponse res) {

        String query = null;
        String action = getStrParamB4Attr(req, "axn");
        String qryName   = getStrParamB4Attr(req, "qryname");
        String qryType   = getStrParamB4Attr(req, "qrytype");
        //if axn = exec -> execute supplied query
        if (SimpleUtil.isnotnull(action) && action.equalsIgnoreCase("exec")) {
            query = getStrParamB4Attr(req, "qry");
        } else {
            //else it's dataset operation such as sort, pagination,  etc - so use last query from session
        	query = (String) req.getSession().getAttribute("qry");
        }

        if (SimpleUtil.isnull(query)) {
           	SimpleMap map = new SimpleMap();
        	map.put("0", "Enter the query");
        	req.setAttribute("err", map);
        	return new ModelAndView("error");        	
        }
        
        boolean refreshData = true;
        boolean isNewFilter = false;
        String dsaxn = getStrParam(req, "dsaxn");
        String sortCol = getStrParam(req, "scolidx");
        String sortOdr = getStrParam(req, "sodr");
        String dsid = getStrParam(req, "dsid");
        int  curFirstRowId = getIntParam(req, "cfrid");
        
        //set request context
        if (curFirstRowId < 1) {
            curFirstRowId = 1;
        }
        
        if (RS_AXN_NEXT.equalsIgnoreCase(dsaxn)) {
            curFirstRowId += maxRecordCountPerPage;
        } else if (RS_AXN_PREV.equalsIgnoreCase(dsaxn)) {
            curFirstRowId -= maxRecordCountPerPage;
        } else if (RS_AXN_SORT.equalsIgnoreCase(dsaxn)) {
            curFirstRowId = 1;
        } else if (RS_AXN_FILTER.equalsIgnoreCase(dsaxn)) {
            isNewFilter = true;
        } else if (RS_AXN_CH_DISP_MODE.equalsIgnoreCase(dsaxn)) {
            //it's wide or normal display request
            //make refersh false to avoid executing query to load data
            refreshData = false;
        }

        SQLFilter filter = new SQLFilter();
        
        if (!SimpleUtil.isnull(sortCol)) {
            filter.setOrderByClauseList(new String[] {sortCol});
        }

        if (!SimpleUtil.isnull(sortOdr)) {
            filter.setSortOrder(sortOdr);
        }

        filter.setBeginRowId(curFirstRowId);
        filter.setMaxRowCount(getMaxRecordCountPerPage());

        //set filter columns
//        System.out.println(" -- is new filter = " +isNewFilter);
//        if (isNewFilter && dsid != null) {
//            
//            SimpleDataSet dataSet = (SimpleDataSet) req.getSession().getAttribute(dsid);
//            DataSetColumn[] cols = dataSet.getColumns();
//            
//            if (cols != null) {
//                SimpleMap whereClauseMap = new SimpleMap();
//                String pname = null, pval = null;
//                                 
//                for (int i = 0; i < cols.length ; i++) {
//                    pname = cols[i].getColumnName(); 
//                    pval = getStrParam(req, pname);
//                    if (!SimpleUtil.isnull(pval)) {
//                        System.out.println("filter " + pname + " == " + pval);
//                        whereClauseMap.put(pname, new Object[]{cols[i], pval});
//                    }
//                }
//                filter.setWhereClauseMap(whereClauseMap);
//            } else {
//                System.out.println("Error, no columns found for the query");
//            }
//        } else {
//            System.out.println("filter load from session");
//            filter.setWhereClauseMap((SimpleMap)(req.getSession()).getAttribute(dsid+"-WhereClauseMap"));
//        }

        
        QueryResultDataSet dataSet = null;

        if (refreshData) {
            dataSet = getJDOGService().executeQuery(getDataSourceName(req),query, filter); 
        } else {
            dataSet = (QueryResultDataSet) req.getSession().getAttribute(dsid);
        }
        
        if (dataSet.getErrorCode() == null) {
            //if no error - cache the resultset
	        req.getSession().setAttribute(dataSet.getId(), dataSet);

	        if ("exec".equals(action) && "usrqry".equals(qryType)) {
                //if action is 'exec' add query to history
	        	SimpleDataSet histDataSet = (SimpleDataSet) req.getSession().getAttribute("qryhist");
	        	if (histDataSet == null) {
	        		histDataSet = new SimpleDataSet();
	        	}
	        	SimpleRow tmpRow = new SimpleRow();
	        	tmpRow.add(query.trim());
	        	tmpRow.add(dataSet.getQryExecTime());
	        	tmpRow.add(dataSet.getTimeTaken());
	        	histDataSet.addDataRow(tmpRow);
	        	req.getSession().setAttribute("qryhist", histDataSet);
	        }
            
           //save current query in session for dataset operations 
	       req.getSession().setAttribute("qry", query.trim());
	        
	        Map model = new HashMap();
	        model.put("TableData", dataSet);
	        model.put("cfrid", curFirstRowId);
	        model.put("dsid", dataSet.getId());
	        model.put("qryname", qryName);
            model.put("mxrecperpage", getMaxRecordCountPerPage());
	        model.put("flr.whereClauseMap", filter.getWhereClauseMap());
	        return new ModelAndView("querydataview", "model", model);
        } else {
        	SimpleMap map = new SimpleMap();
        	map.put("0", dataSet.getErrorCode());
        	req.setAttribute("err", map);
        	return new ModelAndView("error");
        }
    }

    public void setMaxRecordCountPerPage(int mrcpp) {
        this.maxRecordCountPerPage = mrcpp;
    }


    public int getMaxRecordCountPerPage() {
        return this.maxRecordCountPerPage;
    }

}
