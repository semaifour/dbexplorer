package s3tool.jdog.web.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;


import com.silrais.sss.viewfounder.domain.SQLViewDefinition;
import com.silrais.sss.viewfounder.domain.ViewDefinition;
import com.silrais.toolkit.dataset.SimpleDataSet;
import com.silrais.toolkit.json.SimpleJSONWriter;

public class DBOJSONListController extends JDOGBaseActionController {

    private String[] custDBOList = null;
    private String   cvcFile = null;

    public void setCustomDBOList(String[] dboList) {
        this.custDBOList = dboList;
    }

    public String[] getCustomDBOList() {
        return this.custDBOList;
    }

    public void setViewConfig4CustomDBOList(String cvcFile) {
        this.cvcFile = cvcFile;
    }

    public ModelAndView handleRequest(HttpServletRequest req,
                                      HttpServletResponse res) 
        throws  Exception {

        String catalog = getStrParam(req,"cat");
        String schema = getStrParam(req, "sch");
        String oname = getStrParam(req, "oname");
        String dataSource = getStrParam(req, "ds");
        String[] otypes = req.getParameterValues("otype");

        SimpleDataSet dataSet = null;

        if (otypes != null && otypes.length > 0 && otypes[0] != null) {
            otypes[0] = otypes[0].toUpperCase();
            if (otypes[0].equals("SCHEMA")) {
                dataSet = getJDOGService().getGrantedDatabaseList(dataSource);     
            } else if (otypes[0].equals("PROC")) {
                dataSet = getJDOGService().getProcedureList(getDataSourceName(req),catalog, schema, null);
            } else if (otypes[0].equals("TABLE") 
                    || otypes[0].equalsIgnoreCase("VIEW")) {
                dataSet = getJDOGService().getTableList(getDataSourceName(req),catalog, schema,
                                                   oname, otypes);
            } else {
            	SQLViewDefinition viewDef = null;
                viewDef = (SQLViewDefinition)getViewConfigurationService().searchViewDefinition(otypes[0]);   
                String query = viewDef.getSelectQuery().getQuery(); 
                dataSet = getJDOGService().executeQuery(getDataSourceName(req),query, null); 
            }
        }

        Map model = new HashMap();
        int[] filter = {1,2,3};
        model.put("oResponse", SimpleJSONWriter.toJSONFormatString(dataSet,filter, false, true));
        return new ModelAndView("text-response", "model", model);
    }
}
