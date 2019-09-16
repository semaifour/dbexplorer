package s3tool.jdog.web.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import s3tool.jdog.domain.ReferentialKeyList;
import s3tool.jdog.web.util.DBOContext;


public class ReferentialKeyDetailController extends JDOGBaseActionController {

    public ModelAndView handleRequest(HttpServletRequest req,
                                      HttpServletResponse res) {

        String catalog = getStrParam(req,"cat");
        String schema = getStrParam(req, "sch");
        String table = getStrParam(req, "oname");

        DBOContext dboCtx = DBOContext.getContext(req);

        String actionName = dboCtx.getContextName() + "> Referential Keys";  
        //push current action to history
        pushAction2History(actionName, req, res); 
        
        ReferentialKeyList impKeysList = getJDOGService().getImportedKeys(getDataSourceName(req),catalog,schema, table); 
        ReferentialKeyList expKeysList = getJDOGService().getExportedKeys(getDataSourceName(req),catalog,schema, table); 
        Map model = new HashMap();
        model.put("ImportedKeysList", impKeysList);
        model.put("ExportedKeysList", expKeysList);
        req.getSession().setAttribute(impKeysList.getId(), impKeysList);
        req.getSession().setAttribute(impKeysList.getId(), expKeysList);
        return new ModelAndView("refkeysview", "model", model);
    }

}
