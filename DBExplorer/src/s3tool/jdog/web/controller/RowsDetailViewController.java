package s3tool.jdog.web.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import s3tool.jdog.web.util.TableDataUtil;

import com.silrais.toolkit.dataset.SimpleDataSet;
import com.silrais.toolkit.dataset.SimpleRow;

public class RowsDetailViewController extends JDOGBaseActionController {

    public ModelAndView handleRequest(HttpServletRequest req,
                                      HttpServletResponse res) {
        String dsid = getStrParam(req, "dsid");
        int idColIdx = getIntParam(req,"idcolidx");
        String[] rowIds = req.getParameterValues("rowid");

        SimpleDataSet ds = (SimpleDataSet) req.getSession().getAttribute(dsid);

        SimpleRow[] rows = TableDataUtil.filter(ds, rowIds); 

        Map model = new HashMap();
        model.put("cols", ds.getColumns());
        model.put("rows", rows); 
        return new ModelAndView("rowvertview", "model", model);
    }
}
