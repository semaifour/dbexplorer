package s3tool.jdog.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import s3tool.jdog.biz.datasource.DataSourceDataSet;

public class DataSourceListController extends JDOGBaseActionController {

	public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {

		DataSourceDataSet dbmdList = getJDOGService().getConfiguredDataSourceDataSet(); 
	    Map model = new HashMap();
	    model.put("DSList", dbmdList);
	    return new ModelAndView("datasourcelist", "model", model);
	}

}
