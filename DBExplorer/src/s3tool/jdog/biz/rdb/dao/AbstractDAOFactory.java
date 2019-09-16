package s3tool.jdog.biz.rdb.dao;

import com.silrais.toolkit.datasource.JDBCDataSource.DBVendor;

public class AbstractDAOFactory {
	
	private IDAOFactory oracleDAOFactory;
	private IDAOFactory MySQLDAOFactory;
	private IDAOFactory SQLServerDAOFactory;
	
	public IDAOFactory getOracleDAOFactory() {
		return oracleDAOFactory;
	}
	public void setOracleDAOFactory(IDAOFactory oracleDAOFactory) {
		this.oracleDAOFactory = oracleDAOFactory;
	}
	public IDAOFactory getMYSQLDAOFactory() {
		return MySQLDAOFactory;
	}
	public void setMYSQLDAOFactory(IDAOFactory mySQLDAOFactory) {
		this.MySQLDAOFactory = mySQLDAOFactory;
	}
	public IDAOFactory getSQLServerDAOFactory() {
		return SQLServerDAOFactory;
	}
	public void setSQLServerDAOFactory(IDAOFactory sQLServerDAOFactory) {
		SQLServerDAOFactory = sQLServerDAOFactory;
	}
	
	public IDAOFactory getDAOFactory(DBVendor vendor) {
		IDAOFactory dao = null;
		switch(vendor) {
			case Oracle:
				dao = getOracleDAOFactory();
				break;
			case MySQL:
				dao = getMYSQLDAOFactory();
				break;
			case SQLServer:
				dao = getSQLServerDAOFactory();
				break;
			default:
				break;
		}
		return dao;
	}
	
	public IMetaDataDAO getMetaDataDAO(DBVendor vendor) {
		return getDAOFactory(vendor).getMetaDataDAO();
	}
	
	public ITableDataDAO getTableDataDAO(DBVendor vendor) {
		return getDAOFactory(vendor).getTableDataDAO();
	}
	
}
