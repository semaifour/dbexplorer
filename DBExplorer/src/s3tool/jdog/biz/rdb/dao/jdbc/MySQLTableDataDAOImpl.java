package s3tool.jdog.biz.rdb.dao.jdbc;

import java.text.MessageFormat;

import s3tool.jdog.biz.rdb.dao.IDAO;

import com.silrais.toolkit.util.SQLFilter;


public class MySQLTableDataDAOImpl extends BaseTableDataDAOImpl {

    public static final String TOP_N_QUERY_TEMPLATE = "{0} LIMIT {1,number,#}, {2,number,#}";

    public MySQLTableDataDAOImpl() {
    }
       
    public String buildTopNQuery(String selectQuery,
                                 int beginRowId,
                                 int count) {

        return MessageFormat.format(TOP_N_QUERY_TEMPLATE,
                                    new Object[]{selectQuery,
                                                 new Integer(beginRowId),
                                                 new Integer(count)
                                                 }
                                    );
    }

    public String prepareSelectClause(String catalogName,
                                      String schemaName,
                                      String tableName,
                                      SQLFilter criteria) {
        String query = MessageFormat.format(getSelectClauseTemplate(), 
                                     new Object[] {catalogName, tableName});
        return query;
    }

	public IDAO newInstance() {
		return new MySQLTableDataDAOImpl();
	}

}
