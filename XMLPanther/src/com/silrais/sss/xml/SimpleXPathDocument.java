package com.silrais.sss.xml;


import java.io.File;
import java.io.InputStream;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SimpleXPathDocument extends SimpleXPathReader {

    public SimpleXPathDocument(String ccfile) throws  SimpleXPathException {
        super(ccfile);
    }

    public SimpleXPathDocument(File xmlFile) throws  SimpleXPathException {
        super(xmlFile);
    }
    
    public SimpleXPathDocument(InputStream instream) throws SimpleXPathException {
    	super(instream);
    }

    public SimpleXPathNode getSimpleXPathNode(String expr) 
        throws SimpleXPathException {

        Node w3cNode = getNode(expr);
        if (w3cNode != null) {
            return new SimpleXPathNode(w3cNode);
        }
        return null;
    }

    public SimpleXPathNodeList getSimpleXPathNodeList(String expr) throws
        SimpleXPathException {

        NodeList w3cNodeList = getNodeList(expr);
        if (w3cNodeList != null) {
            return new SimpleXPathNodeList(w3cNodeList);
        }
        return null;
    }

}
