package com.silrais.webtoolkit.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public interface SimpleDispatcherInterceptor {

	public ModelAndView fire(HttpServletRequest req, HttpServletResponse res) throws InterceptorException;
}
