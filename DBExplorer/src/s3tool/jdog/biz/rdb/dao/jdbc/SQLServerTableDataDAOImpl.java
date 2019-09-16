package s3tool.jdog.biz.rdb.dao.jdbc;

import java.text.MessageFormat;

import com.silrais.toolkit.util.SQLFilter;

import s3tool.jdog.biz.rdb.dao.IDAO;

public class SQLServerTableDataDAOImpl extends BaseTableDataDAOImpl {

	//public static final String FAKE_COL_WRAPPER = "SELECT *, 0 as JDOG_FAKE_RANK from ({0}) as JDOG_FC_WRAPPER";
	public static final String ROW_NUM_WRAPPER = "SELECT *, ROW_NUMBER() OVER (ORDER BY JDOG_RANK) AS JDOG_ROW_NUM FROM ({0}) as JDOG_RN_WRAPPER";
	public static final String TOP_N_QUERY_TEMPLATE = "SELECT * FROM ({0}) as JDOG_TN_WRAPPER WHERE JDOG_ROW_NUM BETWEEN {1,number,#} AND {2,number,#}";
	
	public SQLServerTableDataDAOImpl() {
	}

	public String buildTopNQuery1(String selectQuery, int beginRowId, int count) {
		return selectQuery;
	}

	
	public String buildTopNQuery(String selectQuery, int beginRowId, int count) {

		//String fakeColWrapper = MessageFormat.format(FAKE_COL_WRAPPER, new Object[] {selectQuery});
		String rowNumWrapper = MessageFormat.format(ROW_NUM_WRAPPER, new Object[] {selectQuery});
		String topNQuery = MessageFormat.format(TOP_N_QUERY_TEMPLATE, new Object[] {
				rowNumWrapper, new Integer(beginRowId), new Integer(beginRowId + count - 1) });
		return topNQuery;
	}

	protected String selectClauseTemplate = "select *, 0 as JDOG_RANK from dbo.{1}";
	protected String selectClauseTemplate_withOrderBy = "select *, RANK() OVER ({2}) as JDOG_RANK from dbo.{1}";

	public void setSelectClauseTemplate(String selectClauseTemplate) {
		this.selectClauseTemplate = selectClauseTemplate;
	}

	@Override
	public String getSelectClauseTemplate() {
		return selectClauseTemplate;
	}
	
    /**
     * Builds SELECT clause of a select query. This method will be optionally 
     * overridden by the subclasses specfic to the database impl.
     */ 
	
    public String buildSelectClause(String catalogName,
                                    String schemaName,
                                    String tableName,
                                    SQLFilter criteria) {
        String query = MessageFormat.format(getSelectClauseTemplate() , 
                                     new Object[] {schemaName, tableName});

        return query;
    }

	/**
	 * Builds SELECT query for the given table using given criteria
	 */
	public String buildSelectQuery(String catalogName, String schemaName,
			String tableName, SQLFilter criteria) {
		
		String select = null;
		
		String order = buildOrderByClause(criteria);
		
		if (order == null) {
			select = buildSelectClause(catalogName, schemaName, tableName, criteria);
		} else {
			select = MessageFormat.format(selectClauseTemplate_withOrderBy, new Object[] {schemaName, tableName, order});
		}
		
		String where = buildWhereClause(catalogName, schemaName, tableName, criteria);
		
	
		StringBuffer selectQry = new StringBuffer(select);

		if (where != null) {
			selectQry.append(where);
		}

		//if (order != null) {
		//	selectQry.append(order);
		//}

		return selectQry.toString();
	}
    
	public IDAO newInstance() {
		return new SQLServerTableDataDAOImpl();
	}

}
