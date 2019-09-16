package s3tool.jdog.web.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.silrais.toolkit.dataset.SimpleDataSet;

public class DatabaseObjectListController extends JDOGBaseActionController {

    public ModelAndView handleRequest(HttpServletRequest req,
                                      HttpServletResponse res) 
        throws  Exception {

        String catalog = getStrParam(req,"cat");
        String schema = getStrParam(req, "sch");
        String oname = getStrParam(req, "oname");
        
        String[] tabType = req.getParameterValues("otype");

        SimpleDataSet dataSet = null;

        if (tabType != null && tabType.length > 0 && tabType[0] != null) {
            if (tabType[0].equalsIgnoreCase("SCHEMA")) {
                dataSet = getJDOGService().getGrantedDatabaseList(getDataSourceName(req));     
            } else if (tabType[0].equalsIgnoreCase("PROC")) {
                dataSet = getJDOGService().getProcedureList(getDataSourceName(req),catalog, schema, null);     
            } else if (tabType[0].equalsIgnoreCase("TABLE") 
                    || tabType[0].equalsIgnoreCase("VIEW")) {
                dataSet = getJDOGService().getTableList(getDataSourceName(req),catalog, schema,
                                                   oname, tabType);
            }
        }

        Map model = new HashMap();
        model.put("DBOList", dataSet);
        return new ModelAndView("dbolist", "model", model);
    }

}
