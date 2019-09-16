package com.silrais.sss.viewfounder.domain;

import com.silrais.sss.xml.SimpleXPathNode;

public class HTTPViewDefinition extends ViewDefinition {

    public HTTPViewDefinition(SimpleXPathNode node) {
        super(node);
    }

    public String getURL() throws  Exception {
        return getString("url");
    }
    
    public String getMethod() throws Exception {
    	return getString("url/@method");
    }

 }
