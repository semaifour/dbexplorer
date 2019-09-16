package s3tool.jdog.web.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.Ostermiller.util.CSVPrinter;
import com.silrais.toolkit.dataset.SimpleDataSet;
import com.silrais.toolkit.dataset.SimpleRow;


public class QueryHistoryController extends JDOGBaseActionController {

    public ModelAndView handleRequest(HttpServletRequest req,
                                      HttpServletResponse res) {

      String actname = getStrParam(req, "actname");
      	
      SimpleDataSet histDataSet = null;
		if ("Clear".equals(actname)) {
			req.getSession().setAttribute("qryhist", null);
		} else if ("Save".equals(actname)) {
			saveQryHistory(req, res);
		} else if ("Delete".equals(actname)) {
			deleteQryFromHistory(req);
		}
		

		// Get the value from session for all actions.	
		histDataSet = (SimpleDataSet) req.getSession().getAttribute("qryhist");
		
    	if (histDataSet == null) {
    		histDataSet = new SimpleDataSet();
    	}
    	req.getSession().setAttribute("qryhist", histDataSet);

    	Map model = new HashMap();
        model.put("TableData", histDataSet);
        return new ModelAndView("queryhistory", "model", model);

    }
    
    private void deleteQryFromHistory(HttpServletRequest req) {
    	SimpleDataSet histDataSet = (SimpleDataSet) req.getSession().getAttribute("qryhist");
    	int qryidx = getIntParam(req, "qryidx");
    	histDataSet.remove(qryidx);
    	req.getSession().setAttribute("qryhist", histDataSet);
    }

    private void saveQryHistory(HttpServletRequest req, HttpServletResponse res) {
		PrintWriter writer = null;
		 
		try {
			writer = (PrintWriter) res.getWriter();
/*			res.setContentType ("application/x-msexcel");
			res.addHeader ("Content-Disposition"
								 , "attachment;filename=Query_History_"
								 + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new java.util.Date())
								 + ".xls");
*/
			res.setContentType ("text/csv");
			res.addHeader ("Content-Disposition"
								 , "attachment;filename=Test"
								 + ".csv");
			CSVPrinter csvPrinter = new CSVPrinter(writer);
			
			SimpleDataSet histDataSet = (SimpleDataSet) req.getSession().getAttribute("qryhist");
			
			if (histDataSet == null || histDataSet.size() > 0) {
				SimpleRow row = null;
				String qry = null;
				//String time = null;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				csvPrinter.println(new String[] {"Query", "Exec Time", "Time Taken (ms)"});
		        
		        
				for (int i = 0; i < histDataSet.size(); i++) {
			        row = histDataSet.getDataRow(i);
			        qry = row.get(0).toString();
			        qry = qry.replace('\t', ' ');
			        qry = qry.replace('\n', ' ');
			        qry = qry.replace('\r', ' ');
			        java.sql.Timestamp time = (java.sql.Timestamp) row.get(1);
			        
			        csvPrinter.println(new String[] {qry, sdf.format(time), row.get(2).toString()});
			    }
			}    
    	} catch (IOException ioe) {
    		ioe.printStackTrace();
    	} finally {
			if (writer != null) writer.close();
    	}	
	}
    
/*    private void saveQryHistory(HttpServletRequest req, HttpServletResponse res) {
    	ServletOutputStream out = null;
    	try {
	    	out = res.getOutputStream();
			res.setContentType ("application/x-msexcel");
			res.addHeader ("Content-Disposition"
								 , "attachment;filename=Query_History_"
								 + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new java.util.Date())
								 + ".xls");

			SimpleDataSet histDataSet = (SimpleDataSet) req.getSession().getAttribute("qryhist");
			
			if (histDataSet == null || histDataSet.size() > 0) {
				SimpleRow row = null;
				String qry = null;
				out.print(" Query \t Exec Time \t Time Taken (ms) ");
			    for (int i = 0; i < histDataSet.size(); i++) {
			        row = histDataSet.getDataRow(i);
			        qry = (String) row.get(0);
			        qry = qry.replace('\t', ' ');
			        qry = qry.replace('\n', ' ');
			        out.print(qry+"\t"+row.get(1)+"\t"+row.get(2));
			        out.print("\n");
			    }
			}    
    	} catch (IOException ioe) {
    		ioe.printStackTrace();
    	} finally {
    		try {
    			if (out != null) out.close();
    		} catch (IOException e) {
    			//Ignore
    		}
    	}	
	}
*/    
}
