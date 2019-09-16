package com.silrais.sss.viewfounder.domain;

import com.silrais.toolkit.util.SimpleParameter;



public class Query {

    public static final String STATIC = "static";
    public static final String DYNAMIC =  "dynamic";
    public static final String PARAMETERIZED = "parameterized";

    public static final int QRY_TYPE_STATIC        = 1;
    public static final int QRY_TYPE_DYNAMIC       = 2;
    public static final int QRY_TYPE_PARAMETERIZED = 3;

    protected String query;
    protected String type;
    protected boolean includesWhereCluase;
    protected SimpleParameter[] params;

    public Query() {
    }

    public String getQuery() {
        return query;
    }

    public SimpleParameter[] getParameters() {
        return params;
    }

    public String getTypeName() {
        return type;
    }

    public int getType() {
        if (STATIC.equalsIgnoreCase(type)) {
           return QRY_TYPE_STATIC;
        } else if (DYNAMIC.equalsIgnoreCase(type)) {
           return QRY_TYPE_DYNAMIC;
        } else if (PARAMETERIZED.equalsIgnoreCase(type)) {
           return QRY_TYPE_PARAMETERIZED;
        }
        return QRY_TYPE_STATIC;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIfWhereClauseIncluded(boolean includesWhereCluase) {
        this.includesWhereCluase = includesWhereCluase;
    }

    public boolean isWhereClauseIncluded() {
        return this.includesWhereCluase;
    }


    public String toString() {
        return new StringBuffer("Query:sql->").append(query)
                        .append("|type->").append(type)
                        .append("|includesWhereCluase->")
                        .append(includesWhereCluase)
                        .toString();
                                        
    }
    
}
