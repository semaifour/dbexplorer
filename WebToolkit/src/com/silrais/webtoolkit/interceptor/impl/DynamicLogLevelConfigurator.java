package com.silrais.webtoolkit.interceptor.impl;

import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.silrais.toolkit.util.SimpleUtil;
import com.silrais.webtoolkit.interceptor.InterceptorException;
import com.silrais.webtoolkit.interceptor.SimpleDispatcherInterceptor;
import com.silrais.webtoolkit.util.HttpUtil;

public class DynamicLogLevelConfigurator extends HttpUtil implements SimpleDispatcherInterceptor {
	
	Logger log = Logger.getLogger(DynamicLogLevelConfigurator.class.getName());
	
	public static final String PARENT_LOGGER_NAME = "logging";

	public ModelAndView fire(HttpServletRequest req, HttpServletResponse res) throws InterceptorException {

		boolean debugEnabled = getBooleanParam(req, "debug.enabled");
		
		if (debugEnabled) {
			Level loggerLevel = Level.FINE;
			loggerLevel = Level.parse(getStrParamB4Attr(req, "logger.level"));

			String[] loggerNames = getStrParams(req, "logger.name");
			Enumeration<String> loggers = null;
			if (SimpleUtil.isSize0(loggerNames)) {
				loggers = LogManager.getLogManager().getLoggerNames();
				while (loggers.hasMoreElements()) {
					setLevel(loggers.nextElement(), loggerLevel);
				}
			} else {
				for (String loggerName : loggerNames) {
					setLevel(loggerName, loggerLevel);
				}
			}
		}
		return null;
	}
	
	private void setLevel(String loggerName, Level level) {
		if (loggerName != null && level != null) {
			Logger logger = LogManager.getLogManager().getLogger(loggerName);
			if (logger != null) {
				log.info("Logger.level changing from : " + log.getLevel() + " -> " + level);
				logger.setLevel(level);
			}
		}
	}

}
