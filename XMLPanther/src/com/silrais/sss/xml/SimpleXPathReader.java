package com.silrais.sss.xml;


import java.io.File;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


public class SimpleXPathReader {

    protected static XPath xpath = null;
    protected Object xmlSource = null;

    static {
        xpath = XPathFactory.newInstance().newXPath();
    }

    private SimpleXPathReader() {
    }

    public SimpleXPathReader(String xmlString) throws SimpleXPathException {
        try {
            StringReader strReader = new StringReader(xmlString);
            InputSource insource = new InputSource(strReader);
            DocumentBuilder parser = null;
            parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = parser.parse(insource);
            xmlSource = doc;
        } catch (Exception e) {
            throw new SimpleXPathException(e);
        }
    } 

    public SimpleXPathReader(InputStream instream) throws  SimpleXPathException {
    	try {
    		DocumentBuilder parser = null;
    		parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    		Document doc = parser.parse(instream);
    		xmlSource = doc;
        } catch (Exception e) {
            throw new SimpleXPathException(e);
        }
    }

    public SimpleXPathReader(File xmlFile) throws  SimpleXPathException {
        try {
            DocumentBuilder parser = null;
            parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = parser.parse(xmlFile);
            xmlSource = doc;
        } catch (Exception e) {
                throw new SimpleXPathException(e);
        }
    }

    public SimpleXPathReader(Node w3cNode) {
        xmlSource = w3cNode;
    }

    public SimpleXPathReader(NodeList w3cNodeList) {
        xmlSource = w3cNodeList;
    }

    public Object getXMLSource() {
        return xmlSource;
    }

    public Node getNode(String expr)throws  SimpleXPathException {
        try {
            return (Node) xpath.evaluate(expr,
                                         xmlSource,
                                         XPathConstants.NODE);
        }catch (Exception e) {
            throw new SimpleXPathException(e);
        }
    }

    public NodeList getNodeList(String expr) throws  SimpleXPathException {
        try {
            return (NodeList) xpath.evaluate(expr,
                                             xmlSource,
                                             XPathConstants.NODESET);
        } catch (Exception e) {
            throw new SimpleXPathException(e);
        }
    }

    public Boolean getBoolean(String expr) throws  SimpleXPathException {
        try {
            return (Boolean)xpath.evaluate(expr,
                                           xmlSource,
                                           XPathConstants.BOOLEAN);
        } catch(Exception e) {
            throw new SimpleXPathException(e);
        }
    }

    public Double getDouble(String expr) throws  SimpleXPathException {
        try {
            return (Double) xpath.evaluate(expr,
                                           xmlSource,
                                           XPathConstants.NUMBER);
        } catch (Exception e) {
            throw new SimpleXPathException(e);
        }
    }

    public String getString(String expr) throws  SimpleXPathException {
        try {
            return (String) xpath.evaluate(expr,
                                           xmlSource,
                                           XPathConstants.STRING);
        } catch (Exception e) {
            throw new SimpleXPathException(e);
        }
    }

    public int getInt(String expr) throws SimpleXPathException {
        try {
            Double d = getDouble(expr);
            return d.intValue();
        } catch (Exception e) { 
                throw new SimpleXPathException(e);
        }
    }

    public int getInt(String expr, int defval) throws SimpleXPathException {
        try {
            Double d = getDouble(expr);
            return d.intValue();
        } catch (Exception e) { 
                throw new SimpleXPathException(e);
        }
    }

}
