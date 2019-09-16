package s3tool.jdog.web.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import s3tool.jdog.web.history.Action;
import s3tool.jdog.web.history.ActionHistory;


public class ActionHistoryController extends JDOGBaseActionController {

    public ModelAndView handleRequest(HttpServletRequest req,
                                      HttpServletResponse res) 
        throws  Exception {

        String axn = getStrParam(req,"axn");

        if ("sahl".equalsIgnoreCase(axn)) {
            //show action history list 
            ArrayList actions = ActionHistory.getActionHistory(req, res);
            Map model = new HashMap();
            model.put("ActionHistoryList", actions);
            return new ModelAndView("actionhistory", "model", model);

        } else if ("la".equalsIgnoreCase(axn)) {
            //load action
            int ahidx = getIntParam(req,"ahidx");
            Action action = ActionHistory.getAction(ahidx, req, res);
            String url = action.getActionURL(); 
            System.out.println("History Action = " + url);
            res.sendRedirect(url);
        }

        return null;
    }

}
