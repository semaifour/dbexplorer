package s3tool.jdog.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import com.silrais.toolkit.util.SimpleUtil;

import s3tool.jdog.domain.JDDataSource;

public class DataSourceConnectionController extends JDOGBaseActionController {

    public ModelAndView handleRequest(HttpServletRequest req,
                                      HttpServletResponse res) {
    	
    	Log log = LogFactory.getLog(DataSourceConnectionController.class);
    	JDDataSource dataSource = new JDDataSource();
    	String ds = getDataSourceName(req);
    	String dsName = getStrParam(req, "dsn");
    	String dsGroup = getStrParam(req, "dsg");
    	boolean activeDataSource = false;
    	
    	Map model = new HashMap();

    	if (SimpleUtil.isnotnull(dsName)) {
			try {
				activeDataSource = getJDOGService().pingConnectedDataSource(ds);
			} catch (Exception e) {
			}
			if (!activeDataSource) {
				dataSource = getJDOGService().getConfiguredDataSource(dsGroup,dsName);
			}
			
		} else {
			String username = getStrParam(req, "user");
			String password = getStrParam(req, "pwd");
			String server = getStrParam(req, "server");
			String port = getStrParam(req, "port");
			String db = getStrParam(req, "db");
			String make = getStrParam(req, "make");

			dataSource.setUsername(username);
			dataSource.setPassword(password);
			dataSource.setServer(server);
			dataSource.setPort(port);
			dataSource.setDatabase(db);
			dataSource.setDBVendor(make);
			dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
			dataSource.setName(username);
			dataSource.setGroupName(db);
			
			try {
	    		ds = getJDOGService().connect2DataSource(dataSource);
	    		activeDataSource = true;
	    	} catch (Exception e) {
	    		log.error("Error when connecting to data-source" + dataSource, e);
	    		model.put("Error", e);
	    	}
		}
   	    model.put("datasource", dataSource);
       	if (activeDataSource) {
    		try {
				res.sendRedirect("explore.do?ds="+ds);
			} catch (IOException e) {
				e.printStackTrace();
			}
    		return null;
       	} else {
       		return new ModelAndView("ds-entry", "model", model);
       	}
	    
    }
}
