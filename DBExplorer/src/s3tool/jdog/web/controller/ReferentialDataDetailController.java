package s3tool.jdog.web.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import s3tool.jdog.domain.ReferentialKeyList;
import s3tool.jdog.web.util.DBOContext;
import s3tool.jdog.web.util.TableDataUtil;

import com.silrais.toolkit.dataset.SimpleDataSet;
import com.silrais.toolkit.dataset.SimpleRow;
import com.silrais.toolkit.util.SimpleUtil;

public class ReferentialDataDetailController extends JDOGBaseActionController {

    public ModelAndView handleRequest(HttpServletRequest req,
                                      HttpServletResponse res) {
        
        DBOContext dboctx = DBOContext.getContext(req);

        String dsid = getStrParam(req, "dsid");
        int idColIdx = getIntParam(req,"idcolidx");
        String[] rowIds = req.getParameterValues("rowid");

        String catalog = dboctx.getCatalog();
        String schema  = dboctx.getSchema();
        String table   = dboctx.getOname();

        String actionName = dboctx.getContextName() + " > Browse > Rec #" + rowIds[0];  
        //push current action to history
        pushAction2History(actionName, req, res); 


        SimpleDataSet ds = (SimpleDataSet) req.getSession().getAttribute(dsid);

        ReferentialKeyList impKeysList = null;
        ReferentialKeyList expKeysList = null;
        if (SimpleUtil.isnotnull(table) && SimpleUtil.isSizeNot0(rowIds)) {
            impKeysList = getJDOGService().getImportedKeys(getDataSourceName(req),catalog,schema, table); 
            expKeysList = getJDOGService().getExportedKeys(getDataSourceName(req),catalog,schema, table);
        }

        SimpleRow[] rows = TableDataUtil.filter(ds, rowIds);

        Map model = new HashMap();
        model.put("ImportedKeysList", impKeysList);
        model.put("ExportedKeysList", expKeysList);
        model.put("cols", ds.getColumns());
        model.put("row", rows[0]); 
        req.getSession().setAttribute(impKeysList.getId(), impKeysList);
        req.getSession().setAttribute(impKeysList.getId(), expKeysList);

        return new ModelAndView("referentialdataview", "model", model);
    }

}
