package s3tool.jdog.web.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.silrais.sss.viewfounder.domain.ViewDefinition;


public class CustomViewController extends JDOGBaseActionController {

    public ModelAndView handleRequest(HttpServletRequest req,
                                      HttpServletResponse res) 
        throws  Exception {

        String cvid = getStrParam(req, "cvid");
        ViewDefinition viewDef = null;

        viewDef = (ViewDefinition) getViewConfigurationService().searchViewDefinition(cvid);  
        if ("sql".equalsIgnoreCase(viewDef.getViewType())) {
        	//return new ModelAndView(new RedirectView("showsqlcv.do"));
        	return new CustomSQLViewController().handleRequest(req, res);
        } else if ("http".equalsIgnoreCase(viewDef.getViewType())) {
        	//return new ModelAndView(new RedirectView("showhttpcv.do"));
        	return new CustomHTTPViewController().handleRequest(req, res);
        } else {
        	throw new Exception("invalid custom view type:" + viewDef.getViewSubType());
        }
    }

}
