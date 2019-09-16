package s3tool.jdog.biz.rdb.dao;

import javax.sql.DataSource;

public interface IDAO {
	public IDAO newInstance();
	
	public DataSource getDataSource();
	
	public void setDataSource(DataSource dataSource);
	
}
