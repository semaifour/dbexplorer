package com.silrais.webtoolkit.controller.io;

import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.silrais.webtoolkit.controller.SimpleSpringActionController;

/**
 * Abstract Download controller define a "template method" pattern handleRequest function. 
 * Abstract methods will be implemented by sub-classes.
 * 
 * @author mahi
 *
 */
public abstract class DownloadController extends SimpleSpringActionController {

	/**
	 * handles download requests.
	 */
	public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Writer out = res.getWriter();
		
		/* Step 1 */
		res.setContentType(getContentType(req));
		
		/* Step 2 */
		writeContent(req, new MyWriter(out));
		
		/* Step 3 */
		out.flush();
		
		return null;
	}

	/**
	 * Returns the content-type such as text/plain, text/html, text/xml, etc..
	 * @return
	 */
	public abstract String getContentType(HttpServletRequest req) throws Exception;
	
	/**
	 * Returns the default file name to be used when client saves the downloaded content.
	 * @return
	 */
	public abstract String getDownloadFileName(HttpServletRequest req) throws Exception;

	/**
	 * Writes the content into the writer.
	 * @param out
	 */
	public abstract void writeContent(HttpServletRequest req, Writer out) throws Exception;
}

/**
 * Convenient non-closeable Writer.		
 * @author mahi
 *
 */
class MyWriter extends PrintWriter {

	public MyWriter(Writer out) {
		super(out, true);
	}

	@Deprecated
	@Override
	public void close() {
		throw new RuntimeException("Close method is blocked from sub-classes, should not be used by sub-classes.");
	}
}