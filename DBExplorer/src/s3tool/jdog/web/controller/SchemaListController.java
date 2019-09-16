package s3tool.jdog.web.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import s3tool.jdog.biz.service.JDOGContext;

import com.silrais.toolkit.dataset.SimpleDataSet;

public class SchemaListController extends JDOGBaseActionController {

    public ModelAndView handleRequest(HttpServletRequest req,
                                      HttpServletResponse res) 
        throws  Exception {


        SimpleDataSet dataSet = null;
        //dataSet = getJDOGService().getSchemaList(getDataSourceName(req));     
        dataSet = JDOGContext.getJDOGService().getGrantedDatabaseList(getDataSourceName(req));
        Map model = new HashMap();
        model.put("dblist", dataSet);
        return new ModelAndView("browser-navigation", "model", model);
    }

}
