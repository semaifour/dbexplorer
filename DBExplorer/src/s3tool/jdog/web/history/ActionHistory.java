package s3tool.jdog.web.history; 

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ActionHistory {

    public static void pushContextURL(String name,
                                      HttpServletRequest req, 
                                      HttpServletResponse res) {
        HttpSession session = req.getSession(true);
        ArrayList actions = (ArrayList) session.getAttribute("ACTION_HISTORY");
        if (actions == null) {
            actions = new ArrayList();
            session.setAttribute("ACTION_HISTORY", actions);
        }
        actions.add(new Action(name, req, res));
    }

    public static  Action getAction(int index,
                                    HttpServletRequest req, 
                                    HttpServletResponse res) {
        HttpSession session = req.getSession(true);
        ArrayList actions = (ArrayList) session.getAttribute("ACTION_HISTORY");
        if (actions != null) {
            return  (Action) actions.get(index);
        }
        return null;
    }

    public static  ArrayList getActionHistory(HttpServletRequest req, 
                                              HttpServletResponse res) {
        HttpSession session = req.getSession();
        return (ArrayList) session.getAttribute("ACTION_HISTORY");
    }
}
