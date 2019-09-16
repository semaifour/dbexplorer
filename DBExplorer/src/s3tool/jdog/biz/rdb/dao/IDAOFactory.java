package s3tool.jdog.biz.rdb.dao;

public interface IDAOFactory {
	public IMetaDataDAO getMetaDataDAO();
	public ITableDataDAO getTableDataDAO();
}
