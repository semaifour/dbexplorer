package s3tool.jdog.biz.rdb.dao.jdbc;

import s3tool.jdog.biz.rdb.dao.IDAO;

import com.silrais.toolkit.dataset.SimpleDataSet;

public class OracleMetaDataDAOImpl extends BaseMetaDataDAOImpl {

	@Override
	public SimpleDataSet getDatabaseList() throws Exception {
		return super.getSchemaList();
	}

	@Override
	public IDAO newInstance() {
		return new OracleMetaDataDAOImpl();
	}

}
