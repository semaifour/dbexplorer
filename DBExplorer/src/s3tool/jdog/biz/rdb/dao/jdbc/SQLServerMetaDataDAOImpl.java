package s3tool.jdog.biz.rdb.dao.jdbc;

import s3tool.jdog.biz.rdb.dao.IDAO;

import com.silrais.toolkit.dataset.SimpleDataSet;

public class SQLServerMetaDataDAOImpl extends BaseMetaDataDAOImpl {

	@Override
	public SimpleDataSet getDatabaseList() throws Exception {
		return super.getCatalogList();
	}

	@Override
	public IDAO newInstance() {
		return new SQLServerMetaDataDAOImpl();
	}

}
