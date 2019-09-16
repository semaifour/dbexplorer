package s3tool.jdog.web.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import s3tool.jdog.domain.DBMetaDataList;


public class MetaDataListController extends JDOGBaseActionController {

    public ModelAndView handleRequest(HttpServletRequest req,
                                      HttpServletResponse res) {
        DBMetaDataList dbmdList = getJDOGService().getDBMetaDataList(getDataSourceName(req)); 
        Map model = new HashMap();
        model.put("DBMDList", dbmdList);
        return new ModelAndView("dbmetadatalist", "model", model);
    }

}
