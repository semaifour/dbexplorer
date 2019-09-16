package s3tool.jdog.web.util;

import s3tool.jdog.biz.service.JDOGContext;

import com.silrais.webtoolkit.util.SimpleRowAction;

public class DataSourceRowAction extends SimpleRowAction {

	@Override
	public String getLabel() {
		if (isAuthenticated()) {
			return "Explore";
		} else {
			return "Login";
		}
		
	}
	
	@Override
	public String getTarget() {
		if (isAuthenticated()) {
			return "_top";
		} else {
			return "_self";
		}
	}
	
	public boolean isAuthenticated() {
		return JDOGContext.getJDOGService().isConnectedDataSource(getCurrentRow().getString(0));
	}
}
