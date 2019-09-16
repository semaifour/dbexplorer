package s3tool.jdog.web.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import s3tool.jdog.web.util.DBOContext;

import com.silrais.toolkit.dataset.SimpleDataSet;

public class ColumnDetailController extends JDOGBaseActionController {

    public ModelAndView handleRequest(HttpServletRequest req,
                                      HttpServletResponse res) {

        String catalog = getStrParam(req,"cat");
        String schema = getStrParam(req, "sch");
        String otype = getStrParam(req, "otype");
        String oname = getStrParam(req, "oname");
        String col = getStrParam(req, "col");
        String clsid = getStrParam(req, "dsid");
        SimpleDataSet columnList= null;

        DBOContext dboCtx = new DBOContext();
        dboCtx.setContext(req);
        String actionName = dboCtx.getContextName() + "> Columns";  

        //push current action to history
        pushAction2History(actionName, req, res); 


        if (otype.equalsIgnoreCase("TABLE") || otype.equalsIgnoreCase("VIEW")) {
            columnList = getJDOGService().getColumnList(getDataSourceName(req), catalog,schema, oname, col); 
        } else if (otype.equalsIgnoreCase("PROC")) {
            columnList = getJDOGService().getProcedureColumns(getDataSourceName(req),catalog,
                                                         schema, 
                                                         oname,
                                                         null); 
        } 
        req.getSession().setAttribute(columnList.getId(), columnList);
        Map model = new HashMap();
        model.put("ColumnList", columnList);
        return new ModelAndView("columndetail", "model", model);
    }

}
