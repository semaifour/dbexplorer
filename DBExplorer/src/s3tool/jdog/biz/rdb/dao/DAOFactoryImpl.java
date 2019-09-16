package s3tool.jdog.biz.rdb.dao;

public class DAOFactoryImpl implements IDAOFactory {
	
	private IMetaDataDAO metaDataDAO;
	private ITableDataDAO tableDataDAO;
	
	public ITableDataDAO getTableDataDAO() {
		return tableDataDAO;
	}
	public void setTableDataDAO(ITableDataDAO tableDataDAO) {
		this.tableDataDAO = tableDataDAO;
	}
	public IMetaDataDAO getMetaDataDAO() {
		return metaDataDAO;
	}
	public void setMetaDataDAO(IMetaDataDAO metaDataDAO) {
		this.metaDataDAO = metaDataDAO;
	}
	
}
