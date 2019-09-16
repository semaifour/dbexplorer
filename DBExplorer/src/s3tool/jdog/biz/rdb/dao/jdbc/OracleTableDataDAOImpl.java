package s3tool.jdog.biz.rdb.dao.jdbc;

import java.text.MessageFormat;

import s3tool.jdog.biz.rdb.dao.IDAO;



public class OracleTableDataDAOImpl extends BaseTableDataDAOImpl {

	
    public static final String TOP_N_QUERY_TEMPLATE = "select * from (select rownum row_num, a.* from ({0}) a where rownum < {2,number,#}) where row_num >= {1,number,#}";

    
    public OracleTableDataDAOImpl() {
    	
    }
    
    public String buildTopNQuery(String selectQuery,
                                 int beginRowId,
                                 int count) {

        return MessageFormat.format(TOP_N_QUERY_TEMPLATE,
                                    new Object[]{selectQuery,
                                                 new Integer(beginRowId),
                                                 new Integer(beginRowId+count)
                                                 }
                                    );
    }

	public IDAO newInstance() {
		return new OracleTableDataDAOImpl();
	}

}
