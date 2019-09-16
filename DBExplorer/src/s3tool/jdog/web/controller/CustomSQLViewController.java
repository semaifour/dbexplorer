package s3tool.jdog.web.controller;


import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.silrais.sss.viewfounder.domain.Query;
import com.silrais.sss.viewfounder.domain.SQLViewDefinition;
import com.silrais.toolkit.util.SimpleParameter;
import com.silrais.toolkit.util.SimpleUtil;


public class CustomSQLViewController extends JDOGBaseActionController {

    public ModelAndView handleRequest(HttpServletRequest req,
                                      HttpServletResponse res) 
        throws  Exception {

        SQLViewDefinition viewDef = null;
        String qry = null;
        SimpleParameter[] params = null;
        Query queryobj = null;

        String cvid = getStrParam(req, "cvid");
        String cvname = getStrParam(req, "cvname");
        String formid = getStrParam(req, "formid");
        String axn = getStrParam(req, "axn");
        String ds = getDataSourceName(req);

        viewDef = new SQLViewDefinition(getViewConfigurationService().searchViewDefinition(cvid));  
        
        if (SimpleUtil.isnull(axn)) {
            //unparameterized customview is being loaded by user 
            params = viewDef.getQueryParameters();
            if (params != null && params.length > 0) {    
                return new ModelAndView("cvexecutor-frame");
            } else {
                queryobj = viewDef.getSelectQuery();
                qry = queryobj.getQuery();
                res.sendRedirect("exeqry.do?exe=true&axn=exec&qry="+URLEncoder.encode(qry)+"&qryname="+URLEncoder.encode(cvname)+"&ds="+ds);
                return null;
            }
        } else if (axn.equalsIgnoreCase("showform")) {
            //parameterized customview input form is being loaded in a frame 
            params = viewDef.getQueryParameters();
            if (params != null && params.length > 0){
                Map model = new HashMap();
                model.put("params", viewDef.getQueryParameters());
                model.put("cvid", cvid);
                return new ModelAndView("customviewqryinput", "model", model);
            }
            return null;
        } else if (axn.equalsIgnoreCase("exeqry")) {
            //parameterized customview input form is being submitted
            cvid = formid;
            String[] pnames = req.getParameterValues("pmlst"); 
            String pName = null, subqry = null;
            String pVal  = null;
            int pIdx     = 0;
            int pTyp     = 0; 
            int pCar     = 0;
            SimpleParameter tmpParam = null;
            queryobj = viewDef.getSelectQuery();
            qry = queryobj.getQuery();
            StringBuffer subWhere = null; 
            int type = queryobj.getType();
            Object[] inParams = new Object[pnames.length];
            for (int i = 0; i < pnames.length ; i++) {
                pName = pnames[i];
                pVal = getStrParam(req, pName);
                pTyp = getIntParam(req, pName+".typ");
                pIdx = getIntParam(req, pName+".idx");
                pCar = getIntParam(req, pName+".crd");
                //tmpParam = SimpleParameter.newStringParameter(pName, pVal,
                //                                              pIdx);
                inParams[i] = pVal;
                if (type == Query.QRY_TYPE_DYNAMIC) {
                    if (SimpleUtil.isnotnull(pVal)) {
                        tmpParam = viewDef.getQueryParameter(pName);
                        subqry = (String) tmpParam.get("WHERE-CONDITION");
                        subqry = MessageFormat.format(subqry, 
                                                      new Object[]{pVal});
                        if (subWhere == null) {
                            subWhere = new StringBuffer();
                        } else {
                            subWhere.append(" AND ");
                        }
                        subWhere.append(subqry);
                    }
                }
            }
            switch (type) {
                default:
                case Query.QRY_TYPE_PARAMETERIZED:
                    qry = MessageFormat.format(qry, inParams);
                    break;

                case Query.QRY_TYPE_DYNAMIC:
                    qry = qry + " " + subWhere.toString();
                    break;

            }
            qry = qry.trim();
            System.out.println("Query = " + qry);
            res.sendRedirect("exeqry.do?exe=true&axn=exec&qry="+URLEncoder.encode(qry)+"&ds="+ds);
            return null;
        }
        return null;
    }

}
