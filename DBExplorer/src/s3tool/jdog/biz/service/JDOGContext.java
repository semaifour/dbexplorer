package s3tool.jdog.biz.service;

import com.silrais.sss.viewfounder.biz.service.ViewConfigurationService;

public class JDOGContext {

	private static JDOGService jdogService = null;
	private static ViewConfigurationService viewConfigurationService = null;
	
	public void setJDOGService(JDOGService jdogService) {
		JDOGContext.jdogService = jdogService;
	}
	
	public static JDOGService getJDOGService() {
		return jdogService;
	}

	public void setViewConfigurationService(ViewConfigurationService viewConfigurationService) {
		JDOGContext.viewConfigurationService = viewConfigurationService;
	}

	public static ViewConfigurationService getViewConfigurationService() {
		return viewConfigurationService;
	}
}
