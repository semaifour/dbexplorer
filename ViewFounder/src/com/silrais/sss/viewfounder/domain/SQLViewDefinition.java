package com.silrais.sss.viewfounder.domain;


import com.silrais.sss.xml.SimpleXPathNode;
import com.silrais.sss.xml.SimpleXPathNodeList;
import com.silrais.toolkit.util.SimpleParameter;

public class SQLViewDefinition extends ViewDefinition {

    public SQLViewDefinition(SimpleXPathNode node) {
        super(node);
    }

    public Query getSelectQuery() throws  Exception {
        Query query = new Query();
        query.setQuery(getString("sql"));
        query.setType(getString("sql/@type"));
        query.setIfWhereClauseIncluded(getBoolean("sql/@includesWhereCluase"));
        return query;
    }

    public SimpleParameter getQueryParameter(String paramId) 
        throws  Exception {
        SimpleXPathNode     input = null;
        input = getSimpleXPathNode("parameters/input[@id=\""+paramId+"\"]");
        return prepareParameter(input);
    }
    
    public SimpleParameter[] getQueryParameters() throws  Exception {
        SimpleXPathNodeList inputs = null;
        SimpleXPathNode     input = null;
        SimpleParameter[]   params = null;
        String type = null;

        Query query = getSelectQuery();

        if (query.getType() == Query.QRY_TYPE_STATIC) return null;

        inputs = getSimpleXPathNodeList("parameters/input");

        if (inputs != null) {
            int length = inputs.getLength();
            if (length > 0) {
                params = new SimpleParameter[length];
                for (int i = 0; i < length ; i++) {
                    input = inputs.item(i);
                    params[i] = prepareParameter(input);
                }
            }
        }
        return params;
    }
}
