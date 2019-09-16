package s3tool.jdog.web.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import s3tool.jdog.biz.service.JDOGContext;
import s3tool.jdog.biz.service.JDOGService;
import s3tool.jdog.web.history.ActionHistory;

import com.silrais.sss.viewfounder.biz.service.ViewConfigurationService;
import com.silrais.webtoolkit.controller.SimpleSpringActionController;

public abstract class JDOGBaseActionController extends SimpleSpringActionController {


    public JDOGService getJDOGService() {
    	return JDOGContext.getJDOGService();
    }


    public ViewConfigurationService getViewConfigurationService() {
        return JDOGContext.getViewConfigurationService();
    }


    public void pushAction2History(String actionName,
                                   HttpServletRequest req,
                                   HttpServletResponse res) {
        ActionHistory.pushContextURL(actionName, req, res);
    }

    
    protected String getDataSourceName(HttpServletRequest req) {
    	return getStrParam(req, "ds");
    }
    
    protected String getDataSourceGroupName(HttpServletRequest req) {
    	return getStrParam(req, "dsg");
    }
    
    protected String getSchema(HttpServletRequest req) {
    	return getStrParam(req, "sch");
    }
    
    protected String getCatalog(HttpServletRequest req) {
    	return getStrParam(req, "cat");
    }
    
    protected String getDBOName(HttpServletRequest req) {
    	return getStrParam(req, "oname");
    }
    protected String getDBOType(HttpServletRequest req) {
    	return getStrParam(req, "otype");
    }
}
