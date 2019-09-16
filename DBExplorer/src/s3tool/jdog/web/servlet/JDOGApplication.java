package s3tool.jdog.web.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;

import com.silrais.sss.license.SimpleLicenseContext;
import com.silrais.sss.license.SimpleLicenseManager;
import com.silrais.sss.license.exception.LicenseException;


public class JDOGApplication extends DispatcherServlet {

	private static Log log = LogFactory.getLog(JDOGApplication.class);
	
    static SimpleLicenseManager licenseMgr;
    static SimpleLicenseContext lctx;

    static ModelAndView errMV = new ModelAndView("error");
    static ModelAndView licMV = new ModelAndView("nolicense");

    static {
        try {

            lctx = new SimpleLicenseContext("jdog-license");
            licenseMgr = new SimpleLicenseManager(lctx);
            log.info("jdog is open for business ...");

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
    
    protected void service(HttpServletRequest req, HttpServletResponse res) 
        throws ServletException, java.io.IOException {
        try {
            licenseMgr.checkLicense(req);
            super.service(req, res);
      } catch (LicenseException le) {
            _render(licMV, req, res);
            le.printStackTrace();
        } catch (Exception e) {
            _render(errMV, req, res);
            e.printStackTrace();
        }

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
}
