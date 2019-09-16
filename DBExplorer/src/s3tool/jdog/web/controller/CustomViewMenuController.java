package s3tool.jdog.web.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.silrais.toolkit.dataset.SimpleDataSet;

public class CustomViewMenuController extends JDOGBaseActionController {

    public ModelAndView handleRequest(HttpServletRequest req,
                                      HttpServletResponse res) 
        throws  Exception {
        String viewConfigRes = getStrParam(req, "cvrsc");
        SimpleDataSet dataSet = null;
        dataSet = getViewConfigurationService().getCustomViewGroupList(viewConfigRes);
        Map model = new HashMap();
        model.put("cvnavlist", dataSet);
        return new ModelAndView("custom-view-navigation", "model", model);
    }

}
