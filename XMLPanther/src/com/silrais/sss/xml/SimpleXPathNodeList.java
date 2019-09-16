package com.silrais.sss.xml;


import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SimpleXPathNodeList extends SimpleXPathReader {

    public SimpleXPathNodeList(NodeList w3cNodeList) {
        super(w3cNodeList);
    }

    public NodeList getW3CNodeList() {
        return (NodeList) getXMLSource();
    }
    
    public SimpleXPathNode getSimpleXPathNode(String expr) throws
        SimpleXPathException {
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

    public int getLength() {
        return getW3CNodeList().getLength();
    }

    public SimpleXPathNode item(int index) {
        return new SimpleXPathNode(getW3CNodeList().item(index));
    }

}
