package com.silrais.webtoolkit.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;


public class SimpleSpringJSPActionController extends SimpleSpringActionController {

    public ModelAndView handleRequest(HttpServletRequest req,
                                      HttpServletResponse res) {
        String forward = getStrParam(req,"jsp2fwd2");
        return new ModelAndView(forward);
    }
}
