package s3tool.jdog.biz.rdb.dao.jdbc;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.ArrayList;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import s3tool.jdog.biz.rdb.dao.IDAO;
import s3tool.jdog.biz.rdb.dao.IMetaDataDAO;
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

import com.silrais.toolkit.dataset.DataSetColumn;
import com.silrais.toolkit.dataset.SimpleDataSet;
import com.silrais.toolkit.dataset.SimpleRow;
import com.silrais.toolkit.dataset.TableColumn;
import com.silrais.toolkit.util.SimpleDAOUtil;


public abstract class BaseMetaDataDAOImpl extends JdbcDaoSupport implements IMetaDataDAO {
	
	public BaseMetaDataDAOImpl() {	
	}
	
	public abstract SimpleDataSet getDatabaseList() throws Exception;
	
	public abstract IDAO newInstance();
	
    public CatalogList getCatalogList() throws Exception {
		Connection conn = getConnection();
		DatabaseMetaData dbmd = conn.getMetaData();
   		ResultSet rs = dbmd.getCatalogs();    		
		ResultSetMetaData rsmd = rs.getMetaData();
		String dsName = "DatabaseCatListList";
		CatalogList catList = new CatalogList(dsName);
		SimpleDAOUtil.populateResultSet2XXXDataSet(rs, dsName, catList);
		rs.close();
		releaseConnection(conn);
		return catList;
    }

    public SchemaList getSchemaList() throws  Exception {
    		Connection conn = getConnection();
    		DatabaseMetaData dbmd = conn.getMetaData();
    		//ResultSet rs = dbmd.getSchemas();
       		ResultSet rs = dbmd.getCatalogs();    		
    		ResultSetMetaData rsmd = rs.getMetaData();
    		String dsName = "DatabaseSchemaListList";
    		SchemaList schemaLst = new SchemaList(dsName);
    		SimpleDAOUtil.populateResultSet2XXXDataSet(rs, dsName, schemaLst);
    		rs.close();
			releaseConnection(conn);
        return schemaLst;
    }
    
    
    public TableList getTableList(String catalogName,
                                  String schemaName,
                                  String tableNamePattern,
                                  String[] tableTypes)
        throws Exception {

	        Connection conn = getConnection();
	        DatabaseMetaData dbmd = conn.getMetaData();
        	ResultSet rs = dbmd.getTables(catalogName,
        								 schemaName,
        								 tableNamePattern,
        								 tableTypes);
        	ResultSetMetaData rsmd = rs.getMetaData();
        	String dsName = schemaName+"-TableList";
        	TableList tableList = new TableList(dsName);
        	SimpleDAOUtil.populateResultSet2XXXDataSet(rs, dsName, tableList);
        	rs.close();
			releaseConnection(conn);
        return tableList;
    }

    public ColumnList getColumnList(String catalogName,
    								String schemaName, 
                                    String tableName,
                                    String columnNamePattern) 
        throws Exception {

        Connection conn = getConnection();
        DatabaseMetaData dbmd = conn.getMetaData();
        ResultSet rs = dbmd.getColumns(catalogName,
                                      schemaName,
                                      tableName,
                                      columnNamePattern);
        ResultSetMetaData rsmd = rs.getMetaData();
        String dsName = schemaName+"."+tableName+"-TableColumnList";
        ColumnList columnList = new ColumnList(dsName);
        SimpleDAOUtil.populateResultSet2XXXDataSet(rs, dsName, columnList);
        rs.close();
        releaseConnection(conn);
        return columnList;
    }


    public ReferentialKeyList getImportedKeys(String catalogName,
                                              String schemaName, 
                                              String tableName) 
        throws Exception {

        Connection conn = getConnection();
        DatabaseMetaData dbmd = conn.getMetaData();
        ResultSet rs = dbmd.getImportedKeys(catalogName, schemaName, tableName);
        ReferentialKeyList impKeyList = null;
        String name = schemaName+"."+tableName+"-ImportedKeys";
        impKeyList = (ReferentialKeyList) 
                      SimpleDAOUtil .populateResultSet2XXXDataSet(rs, name, 
                                                 new ReferentialKeyList(name));
        rs.close();
	    releaseConnection(conn);
        return impKeyList;
    }


    public ReferentialKeyList getExportedKeys(String catalogName,
                                              String schemaName, 
                                              String tableName) 
        throws Exception {
        Connection conn = getConnection();
        DatabaseMetaData dbmd = conn.getMetaData();
        ResultSet rs = dbmd.getExportedKeys(catalogName, schemaName, tableName);
        ReferentialKeyList expKeyList = null;
        String name = schemaName+"."+tableName+"-ImportedKeys"; 
        expKeyList = (ReferentialKeyList) 
                      SimpleDAOUtil.populateResultSet2XXXDataSet(rs, name, 
                                                new ReferentialKeyList(name));
        rs.close();
	    releaseConnection(conn);
        return expKeyList;
    }


    public TableColumn[] getTableColumns(String catalogName,
                                          String schemaName,
                                          String tableName) throws  Exception {
        Connection conn = getConnection();
        DatabaseMetaData dbmd = conn.getMetaData();
        ResultSet rs = dbmd.getColumns(catalogName,
                                       schemaName,
                                       tableName,
                                       null);
        TableColumn[] cols = null;
        ArrayList list = new ArrayList();
        TableColumn tmpCol = null;
        String dsName = schemaName + "." + tableName;
        while (rs.next()) {
            tmpCol = new TableColumn();
            tmpCol.setColumnName(rs.getString(4));
            tmpCol.setColumnType(rs.getInt(5));
            tmpCol.setColumnTypeName(rs.getString(6));
            tmpCol.setColumnIndex(rs.getInt(17));
            tmpCol.setDataSetName(dsName);
            list.add(tmpCol);
        }
        if (list.size() > 0) {
            cols = new TableColumn[list.size()];
            list.toArray(cols);
        }
        rs.close();
	    releaseConnection(conn);
        return  cols;
    }


    public ProcedureList getProcedureList(String catalogName,
                                       String schemaName,
                                       String procNamePattern) 
        throws  Exception {
        Connection conn = getConnection();
        DatabaseMetaData dbmd = conn.getMetaData();
        ResultSet rs = dbmd.getProcedures(catalogName, 
                                          schemaName,
                                          procNamePattern);
        ResultSetMetaData rsmd = rs.getMetaData();
        String dsName = schemaName+"-ProcedureList";
        ProcedureList procList = new ProcedureList(dsName);
        SimpleDAOUtil.populateResultSet2XXXDataSet(rs, dsName, procList);
        rs.close();
	    releaseConnection(conn);
        return procList;

    }

    public ProcedureColumnList getProcedureColumns(String catalogName,
                                       String schemaName,
                                       String procNamePattern,
                                       String columnNamePattern)
        throws  Exception {
        Connection conn = getConnection();
        DatabaseMetaData dbmd = conn.getMetaData();
        ResultSet rs = dbmd.getProcedureColumns(catalogName, 
                                          schemaName,
                                          procNamePattern,
                                          columnNamePattern);
        ResultSetMetaData rsmd = rs.getMetaData();
        String dsName = schemaName+"."+procNamePattern+"-ProcedureColumnList";
        ProcedureColumnList procColList = new ProcedureColumnList(dsName);
        SimpleDAOUtil.populateResultSet2XXXDataSet(rs, dsName, procColList);
        rs.close();
	    releaseConnection(conn);
        return procColList;

    }
            
    public IndexList getIndexList(String catalogName,
                                  String schemaName,
                                  String tableName,
                                  boolean unique,
                                  boolean approximate) throws  Exception {
        Connection conn = getConnection();
        DatabaseMetaData dbmd = conn.getMetaData();
        System.out.println("getting index....");
        ResultSet rs = dbmd.getIndexInfo(catalogName, 
                                         schemaName,
                                         tableName,
                                         unique,
                                         approximate);
        System.out.println("getting index....done");
        ResultSetMetaData rsmd = rs.getMetaData();
        String dsName = schemaName+"."+tableName+"-IndexList";
        IndexList indList = new IndexList(dsName);
        SimpleDAOUtil.populateResultSet2XXXDataSet(rs, dsName, indList);
        rs.close();
	    releaseConnection(conn);
        return indList;
    }

    public DBMetaDataList getDBMetaDataList() throws  Exception {
        Connection conn = getConnection();
        DatabaseMetaData dbmd = conn.getMetaData();
   
        Class dbmaClass = DatabaseMetaData.class;
        Method[] methods = dbmaClass.getMethods();

        String mName = null;
        Class mRetType = null;
        Class[] mParamTypes = null;
        Object mRetVal = null;
        DBMetaDataList dbmdList = prepareMetaDataListDS();
        int propRowId = 1;
        SimpleRow row = null;
        for (int i = 0; i < methods.length; i++) {
            mName = methods[i].getName();
            mRetType = methods[i].getReturnType();
            mParamTypes = methods[i].getParameterTypes();
            if (mParamTypes.length == 0 && (mRetType == Integer.TYPE ||
                                     mRetType == String.class || 
                                     mRetType == Boolean.TYPE)) {
                row = new SimpleRow();
                row.add(propRowId++);
                row.add(mName);
                try {
                    mRetVal = methods[i].invoke(dbmd, (Object[])null);
                    row.add(mRetVal.toString());
                } catch (Throwable e) {
                    row.add("Info Not Available");
                }
                dbmdList.addDataRow(row);
            }
        }
	    releaseConnection(conn);
        return dbmdList;
    }

    protected DBMetaDataList prepareMetaDataListDS() {
        String dsName = "DBMetaDataList";
        DataSetColumn col1 = new DataSetColumn();
        col1.setColumnName("J_ROW_ID");
        col1.setColumnIndex(0);
        col1.setColumnType(Types.NUMERIC);
        col1.setColumnTypeName("NUMERIC");
        col1.setDataSetName(dsName);
        

        DataSetColumn col2 = new DataSetColumn();

        col2.setColumnName("PROPERTY NAME");
        col2.setColumnIndex(1);
        col2.setColumnType(Types.VARCHAR);
        col2.setColumnTypeName("VARCHAR2");
        col2.setDataSetName(dsName);

        DataSetColumn col3 = new DataSetColumn();

        col3.setColumnName("PROPERTY VALUE");
        col3.setColumnIndex(2);
        col3.setColumnType(Types.VARCHAR);
        col3.setColumnTypeName("VARCHAR2");
        col3.setDataSetName(dsName);
        
        DBMetaDataList dbmdList = new DBMetaDataList(dsName);
        dbmdList.setColumns(new DataSetColumn[]{col1, col2, col3});

        return dbmdList;
    }


	public TablePrivileges getTablePrivileges(String catalogName,
										      String schemaName, 
										      String tableNamePattern) throws Exception {
		  	Connection conn = getConnection();
	        DatabaseMetaData dbmd = conn.getMetaData();
	        ResultSet rs = dbmd.getTablePrivileges(catalogName, schemaName, tableNamePattern);
	        TablePrivileges privileges = new TablePrivileges("TablePrivileges");
	        SimpleDAOUtil.populateResultSet2XXXDataSet(rs, "TablePrivileges", privileges);
	        return privileges;
	}

}
