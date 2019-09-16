package com.silrais.sss.xml;

import com.silrais.toolkit.util.ClasspathResourceLoader;

public class XMLResourceFileReader {
	
	public XMLResourceFileReader() {
	}
	
	/**
	 * Constructs an instance of SimpleXPathDocument for the given xml resource file in classpath.
	 * 
	 * @param xmlResourceName - resource name of the xml file/resource; ex: "com/mydomain/myapp/config/myappconfig.xml"
	 */
	public  SimpleXPathDocument getXMLResourceAsXPathDocument(String xmlResourceName) throws SimpleXPathException {
		try {
			ClasspathResourceLoader rscsloader = new ClasspathResourceLoader(this);
			return new SimpleXPathDocument(rscsloader.getResourceAsStream(xmlResourceName));
		} catch (SimpleXPathException e) {
			throw e;
		} catch (Exception e) {
			throw new SimpleXPathException(e);
		}
	}

}
