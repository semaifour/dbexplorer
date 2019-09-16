package com.silrais.toolkit.util;

import java.sql.Types;
import java.util.Date;

public class SimpleParameter extends SimpleMap {

    public static final int CORDINALITY_SINGLE   = 1;
    public static final int CORDINALITY_MULTIPLE = 2;

    protected String name;
    protected Object value;
    protected int    type; //java.sql.Types
    protected int    index;
    protected int    cardinality;

    private SimpleParameter() {
    }

    public SimpleParameter(String name, Object value, int type, int index) {
        this.name = name;
        this.value = value;
        this.type = type;
        this.index = index;
    }
    
    public static SimpleParameter newIntParameter(String name, 
                                                  int value, 
                                                  int index) {
        return new SimpleParameter(name, new Integer(value), 
                                   Types.INTEGER,
                                   index);
    }


    public static SimpleParameter newStringParameter(String name,
                                                     String value,
                                                     int index) {
        return new SimpleParameter(name, value, Types.VARCHAR,index);
    }


    public static SimpleParameter newDateParameter(String name,
                                                   Date value,
                                                   int index) {
        return new SimpleParameter(name, value, Types.DATE,index);
    }


    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }

    public int getType() {
        return type;
    }


    public int getIntValue() {
        if (type == Types.INTEGER) {
            return ((Integer)value).intValue();
        } else {
            throw new RuntimeException("Parameter type is not integer");
        }
    }

    public String getStringValue() {
        if (type == Types.VARCHAR) {
            return (String) value;
        } else {
            return value.toString();
        }
    }

    public  Date getDateValue() {
        if (type == Types.DATE) {
            return (Date) value;
        } else {
            throw new RuntimeException("Parameter type is not date");
        }
    }

    public void setCardinality(String str) {
        if (SimpleUtil.isnull(str)) {
            cardinality = CORDINALITY_SINGLE;
        } else if ("SINGLE".equalsIgnoreCase(str)) {
            cardinality = CORDINALITY_SINGLE;
        } else if ("MULTIPLE".equalsIgnoreCase(str)) {
            cardinality = CORDINALITY_MULTIPLE;
        } else {
            cardinality = CORDINALITY_SINGLE;
        }
    }

    public int getCardinality() {
        return cardinality;
    }


    @Override
	public String toString() {
        return new StringBuffer("Name=").append(name).append(",")
                            .append("Value=").append(value).append(",")
                            .append("Type=").append(type).append(",")
                            .append("Index=").append(index)
                            .append("Attrs=").append(super.toString())
                            .toString();
    }
}
