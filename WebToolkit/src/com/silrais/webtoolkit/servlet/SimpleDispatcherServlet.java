package com.silrais.webtoolkit.servlet;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;

import com.silrais.sss.license.SimpleLicenseContext;
import com.silrais.sss.license.SimpleLicenseManager;
import com.silrais.sss.license.exception.LicenseException;
import com.silrais.webtoolkit.interceptor.InterceptorException;
import com.silrais.webtoolkit.interceptor.SimpleDispatcherInterceptor;


public class SimpleDispatcherServlet extends DispatcherServlet {
	
	private static Logger log = Logger.getLogger(SimpleDispatcherServlet.class.getName());
	
    private static SimpleLicenseManager licenseMgr;
    private static SimpleLicenseContext lctx;

    private static ModelAndView errMV = new ModelAndView("error");
    private static ModelAndView licMV = new ModelAndView("nolicense");
    
    private static ArrayList<SimpleDispatcherInterceptor> preProcessInterceptors = new ArrayList<SimpleDispatcherInterceptor>();
    private static ArrayList<SimpleDispatcherInterceptor> postProcessInterceptors = new ArrayList<SimpleDispatcherInterceptor>();

    static {
        try {
            lctx = new SimpleLicenseContext("license");
            licenseMgr = new SimpleLicenseManager(lctx);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        System.out.println(" Simple Dispatcher Serlvet is open for business...");
    }

    @Override
	protected void service(HttpServletRequest req, HttpServletResponse res) 
        throws ServletException, java.io.IOException {

        try {
            licenseMgr.checkLicense(req);
            fireInterceptors(req, res, getPreProcessInterceptors());
            super.service(req, res);
        } catch (LicenseException le) {
            _render(licMV, req, res);
            le.printStackTrace();
        } catch (Exception e) {
            _render(errMV, req, res);
            e.printStackTrace();
        }
        fireInterceptors(req, res, getPostProcessInterceptors());
    }

    private void _render(ModelAndView mv, 
                    HttpServletRequest req,
                    HttpServletResponse res) throws ServletException {
        try {
            render(mv, req, res);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    
    public static void addPreProcessInterceptor(SimpleDispatcherInterceptor interceptor) {
    	preProcessInterceptors.add(interceptor);
    }
    
    public static void addPostProcessInterceptor(SimpleDispatcherInterceptor interceptor) {
    	postProcessInterceptors.add(interceptor);
    }
    
    public static ArrayList<SimpleDispatcherInterceptor> getPreProcessInterceptors() {
    	return preProcessInterceptors;
    }

    public static ArrayList<SimpleDispatcherInterceptor> getPostProcessInterceptors() {
    	return postProcessInterceptors;
    }
    
    /**
     * 
     * Fires of the interceptors one by one. Ignores failed interceptors.
     * 
     * @param req
     * @param res
     * @param interceptors
     * @return
     */
    private ModelAndView fireInterceptors(HttpServletRequest req, HttpServletResponse res, ArrayList<SimpleDispatcherInterceptor> interceptors) {
    	for(SimpleDispatcherInterceptor interceptor : interceptors) {
    		ModelAndView mv = null;
			try {
				mv = interceptor.fire(req, res);
			} catch (InterceptorException e) {
				log.log(Level.SEVERE, "Error occurred when firing interceptor", e);
			}
    		if (mv != null) {
    			return mv;
    		}
    	}
    	return null;
    }

}
