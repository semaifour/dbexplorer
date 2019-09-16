package s3tool.jdog.biz.datasource;

import com.silrais.toolkit.dataset.SimpleDataSet;

public class DataSourceDataSet extends SimpleDataSet {
	
	public DataSourceDataSet() {
		super();
	}
	
	public void initialize() {
		defineColumns("Id", "Group", "Name", "Server", "Port", "Database", "Username", "Password", "DB Make", "Secured?");
	}
	

}
