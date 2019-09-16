package s3tool.jdog.web.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.silrais.sss.viewfounder.domain.HTTPViewDefinition;


public class CustomHTTPViewController extends JDOGBaseActionController {

    public ModelAndView handleRequest(HttpServletRequest req,
                                      HttpServletResponse res) 
        throws  Exception {

        HTTPViewDefinition viewDef = null;
 
        String cvid = getStrParam(req, "cvid");
 /*
        String cvname = getStrParam(req, "cvname");
        String formid = getStrParam(req, "formid");
        String axn = getStrParam(req, "axn");
        String ds = getDataSourceName(req);
*/
        viewDef = new HTTPViewDefinition(getViewConfigurationService().searchViewDefinition(cvid));   
        res.sendRedirect(viewDef.getURL());
        return null;
     }

}
