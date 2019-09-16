package s3tool.jdog.biz.rdb.dao;

import s3tool.jdog.domain.CatalogList;
import s3tool.jdog.domain.ColumnList;
import s3tool.jdog.domain.DBMetaDataList;
import s3tool.jdog.domain.IndexList;
import s3tool.jdog.domain.ProcedureColumnList;
import s3tool.jdog.domain.ProcedureList;
import s3tool.jdog.domain.ReferentialKeyList;
import s3tool.jdog.domain.SchemaList;
import s3tool.jdog.domain.TableList;
import s3tool.jdog.domain.TablePrivileges;

import com.silrais.toolkit.dataset.SimpleDataSet;
import com.silrais.toolkit.dataset.TableColumn;


public interface IMetaDataDAO extends IDAO {
	
	public SimpleDataSet getDatabaseList() throws Exception;
	
    public CatalogList getCatalogList() throws  Exception;
    
    public SchemaList getSchemaList() throws  Exception;
    
    public TableList getTableList(String catalogName,
                                  String schemaName,
                                  String tableNamePattern,
                                  String[] tableTypes) throws  Exception;

    public TablePrivileges getTablePrivileges(String catalogName,
            								  String schemaName,
            								  String tableNamePattern) throws Exception;
    
    public ColumnList getColumnList(String catalogName,
                                    String schemaName, 
                                    String tableName,
                                    String columnNamePattern) throws  Exception;

    public ReferentialKeyList getImportedKeys(String catalogName,
                                              String schemaName, 
                                              String tableName) 
        throws Exception;

    public ReferentialKeyList getExportedKeys(String catalogName,
                                              String schemaName, 
                                              String tableName) 
        throws Exception;


    public TableColumn[] getTableColumns(String catalogName,
                                          String schemaName,
                                          String tableName) throws  Exception;

    public ProcedureList getProcedureList(String catalogName,
                                       String schemaName,
                                       String procNamePattern)
        throws  Exception;

    public ProcedureColumnList getProcedureColumns(String catalogName,
                                       String schemaName,
                                       String procNamePattern,
                                       String columnNamePattern)
        throws  Exception;
            
    public IndexList getIndexList(String catalogName,
                                  String schemaName,
                                  String tableName,
                                  boolean unique,
                                  boolean approximate) throws  Exception;

    public DBMetaDataList getDBMetaDataList() throws  Exception;
}
