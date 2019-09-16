package com.silrais.toolkit.util;

public class SQLFilter extends Filter {

    public static final String WHERE_CLAUSE_MAP     = "WCM";
    public static final String ORDER_BY_CLAUSE_LIST = "OBCL";
    public static final String MAX_ROW_COUNT        = "MRC";
    public static final String BEGIN_ROW_ID         = "BRI";
    public static final String CATALOG = "cat";
    public static final String SCHEMA  = "sch";
    public static final String TABLE   = "tab";
    public static final String SORT_ORDER = "SO";

    public static final String SORT_ORDER_ASC  = "ASC";
    public static final String SORT_ORDER_DESC = "DESC";

    public static final int DEFAULT_MAX_ROW_COUNT = 25;
    public static final int DEFAULT_BEGIN_ROW_ID  = 1;

    public SQLFilter() {
        super();
    }


    public void setCatalog(String catalog) {
        this.put(CATALOG, catalog);
    }

    
    public String getCatalog() {
        return (String) this.get(CATALOG);
    }


    public void setSchema(String schema) {
        this.put(SCHEMA , schema);
    }


    public String getSchema() {
        return (String) this.get(SCHEMA);
    }

    public void setTable(String table) {
        this.put(TABLE, table);
    }

    
    public String getTable() {
        return (String) this.get(TABLE);
    }

    public void setWhereClauseMap(SimpleMap where) {
        this.put(WHERE_CLAUSE_MAP, where);
    }

    public void setOrderByClauseList(String[] orderBy) {
        this.put(ORDER_BY_CLAUSE_LIST, orderBy);
    }

    public void setSortOrder(String order) {
        this.put(SORT_ORDER, order);
    }

    public SimpleMap getWhereClauseMap() {
        Object obj = this.get(WHERE_CLAUSE_MAP);
        return obj != null ? (SimpleMap) obj : new SimpleMap();
    }

    public String[] getOrderByClauseList() {
        Object obj = this.get(ORDER_BY_CLAUSE_LIST);
        return obj != null ? (String[]) obj : new String[0];
    }

    public String getSortOrder() {
        Object obj = this.get(SORT_ORDER);
        return obj != null ? (String) obj : SORT_ORDER_ASC;
    }

    public void setBeginRowId(int beginRowId) {
        this.put(BEGIN_ROW_ID, new Integer(beginRowId));
    }

    public int getBeginRowId() {
        Integer intObj = (Integer) this.get(BEGIN_ROW_ID);
        if (intObj != null) {
            return intObj.intValue();
        } else {
            return DEFAULT_BEGIN_ROW_ID;
        }
    }

    public void setMaxRowCount(int maxRowCount) {
        this.put(MAX_ROW_COUNT, new Integer(maxRowCount));
    }

    public int getMaxRowCount() {
        Integer intObj = (Integer) this.get(MAX_ROW_COUNT);
        if (intObj != null) {
            return intObj.intValue();
        } else {
            return DEFAULT_MAX_ROW_COUNT;
        }
    }
}
