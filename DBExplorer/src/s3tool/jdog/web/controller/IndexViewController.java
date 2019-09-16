package s3tool.jdog.web.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import s3tool.jdog.domain.IndexList;

import com.silrais.toolkit.util.SimpleUtil;

public class IndexViewController extends JDOGBaseActionController {

    public ModelAndView handleRequest(HttpServletRequest req,
                                      HttpServletResponse res) {

        String catalog = getStrParam(req,"cat");
        String schema = getStrParam(req, "sch");
        String table = getStrParam(req, "oname");

        IndexList indexList= null;

        if (!SimpleUtil.isnull(table)) {
            indexList = getJDOGService().getIndexList(getDataSourceName(req), 
            									 catalog,schema, table, 
                                                  true, false); 
        } 
        req.getSession().setAttribute(indexList.getId(), indexList);
        Map model = new HashMap();
        model.put("IndexList", indexList);
        return new ModelAndView("indexlist", "model", model);
    }
}
