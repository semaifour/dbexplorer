package s3tool.jdog.biz.rdb.dao;

import s3tool.jdog.domain.QueryResultDataSet;
import s3tool.jdog.exception.JDOGException;

import com.silrais.toolkit.dataset.SimpleDataSet;
import com.silrais.toolkit.util.SQLFilter;

public interface ITableDataDAO extends IDAO {
    
    public SimpleDataSet queryTableData(String catalogName,
                                        String schemaName,
                                        String tableName, 
                                        SQLFilter criteria) throws  JDOGException;

    public QueryResultDataSet executeQuery(String selectQuery,
                                           SQLFilter criteria) throws JDOGException;

}
