package s3tool.jdog.web.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import s3tool.jdog.web.util.DBOContext;

import com.silrais.toolkit.dataset.SimpleColumn;
import com.silrais.toolkit.dataset.SimpleDataSet;
import com.silrais.toolkit.util.SQLFilter;
import com.silrais.toolkit.util.SimpleMap;
import com.silrais.toolkit.util.SimpleUtil;
import com.silrais.webtoolkit.controller.io.DownloadController;

public class TableDataViewController extends JDOGBaseActionController {
	
	DownloadController downloadController = null;

    public static final String RS_AXN_NEXT   = "nxt";
    public static final String RS_AXN_PREV   = "prv";
    public static final String RS_AXN_SORT   = "srt";
    public static final String RS_AXN_FILTER = "flr";
    public static final String RS_AXN_CH_DISP_MODE = "cdm";
    public static final String RS_AXN_DOWNLOAD = "dwnld";

    protected int maxRecordCountPerPage = 25;

    @SuppressWarnings("unchecked")
	public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {

        String catalog = null;
        String schema  = null;
        String table   = null;
        String orderBy = null;
        int  curFirstRowId = 1;
        int  curMaxRecCnt  = this.getMaxRecordCountPerPage();
        String sortCol = null;
        String sortOdr = null;
        String dsid = null;
        String dsaxn = null;
        SQLFilter filter = null; 
        boolean isNewFilter = false;
        boolean refreshData = true;
        boolean isDataOnlyView = false;
        boolean isDownload = false;
        filter = new SQLFilter();
        catalog = getStrParam(req,"cat");
        schema = getStrParam(req, "sch");
        table = getStrParam(req, "oname");
        orderBy = getStrParam(req, "oby");
        curFirstRowId  = getIntParam(req, "cfrid");
        sortCol = getStrParam(req, "scol");
        sortOdr = getStrParam(req, "sodr");
        dsaxn = getStrParam(req, "dsaxn");
        dsid = getStrParam(req, "dsid");
        isDataOnlyView = getBooleanParam(req, "isdov");

        DBOContext dboCtx = new DBOContext();
        dboCtx.setContext(req);
        String actionName = dboCtx.getContextName() + "> Data";  

        //push current action to history
        pushAction2History(actionName, req, res); 

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
            refreshData = false;
        } else if (RS_AXN_DOWNLOAD.equalsIgnoreCase(dsaxn)) {
            //refreshData = false;
            isDownload = true;
            curFirstRowId = 1;
            curMaxRecCnt = -1;
        }

        if (!SimpleUtil.isnull(sortCol)) {
            filter.setOrderByClauseList(new String[] {sortCol});
        }

        if (!SimpleUtil.isnull(sortOdr)) {
            filter.setSortOrder(sortOdr);
        }

        filter.setBeginRowId(curFirstRowId);
        filter.setMaxRowCount(curMaxRecCnt);

        //set filter columns
        if (isNewFilter) {
            
            SimpleDataSet dataSet = null;
            SimpleColumn[] cols = null;
            if (dsid != null) {
                dataSet = (SimpleDataSet) req.getSession().getAttribute(dsid);
            }
            if (dataSet != null) {
                //DataSet column
                cols = dataSet.getColumns();
            } else {
                //Table Column
                cols = getJDOGService().getTableColumns(getDataSourceName(req),catalog, schema, table); 
            }
            
            if (cols != null) {
                SimpleMap whereClauseMap = new SimpleMap();
                String pname = null, pval = null;
                                 
                for (int i = 0; i < cols.length ; i++) {
                    pname = cols[i].getColumnName(); 
                    pval = getStrParam(req, pname);
                    if (!SimpleUtil.isnull(pval)) {
                        whereClauseMap.put(pname, new Object[]{cols[i], pval});
                    }
                }
                filter.setWhereClauseMap(whereClauseMap);
            } else {
                System.out.println("Error, no columns found for " + catalog +
                        "." + schema + "." + table);
            }
        } else {
            filter.setWhereClauseMap((SimpleMap)(req.getSession()).getAttribute(dsid+"-WhereClauseMap"));
        }

        //load data
        SimpleDataSet dataSet = null;
        if (refreshData) {
            dataSet = getJDOGService().getDataSet(getDataSourceName(req),catalog,schema, table, filter); 
        } else {
            dataSet = (SimpleDataSet) req.getSession().getAttribute(dsid);
        }
        req.getSession().setAttribute(dataSet.getId(), dataSet);
        req.getSession().setAttribute(dataSet.getId()+"-WhereClauseMap",
                                      filter.getWhereClauseMap());
        Map model = new HashMap();
        model.put("TableData", dataSet);
        model.put("cfrid", curFirstRowId);
        model.put("dsid", dataSet.getId());
        model.put("flr.whereClauseMap", filter.getWhereClauseMap());
        if (isDownload) {
        	model.put("datasetkey", "TableData");
        	req.setAttribute("model", model);
        	downloadController.handleRequest(req, res);
        	return null;
        } else {
        	
        	if (isDataOnlyView) {
        		return new ModelAndView("dataonlyview", "model", model);
        	} else {
        		return new ModelAndView("tabledataview", "model", model);
        	}
        }
    }


    public void setMaxRecordCountPerPage(int mrcpp) {
        this.maxRecordCountPerPage = mrcpp;
    }


    public int getMaxRecordCountPerPage() {
        return this.maxRecordCountPerPage;
    }


	public DownloadController getDownloadController() {
		return downloadController;
	}


	public void setDownloadController(DownloadController downloadController) {
		this.downloadController = downloadController;
	}

}
