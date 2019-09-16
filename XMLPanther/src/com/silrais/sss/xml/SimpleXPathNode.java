package com.silrais.sss.xml;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SimpleXPathNode extends SimpleXPathReader {

    public SimpleXPathNode(Node w3cNode) {
        super(w3cNode);
    }

    public Node getW3CNode() {
        return (Node) getXMLSource();
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
}
