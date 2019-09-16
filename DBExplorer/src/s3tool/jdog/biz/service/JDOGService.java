package s3tool.jdog.biz.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import s3tool.jdog.biz.datasource.DataSourceConfiguration;
import s3tool.jdog.biz.datasource.DataSourceDataSet;
import s3tool.jdog.biz.rdb.dao.AbstractDAOFactory;
import s3tool.jdog.biz.rdb.dao.DAOFactory;
import s3tool.jdog.biz.rdb.dao.IMetaDataDAO;
import s3tool.jdog.biz.rdb.dao.ITableDataDAO;
import s3tool.jdog.domain.ColumnList;
import s3tool.jdog.domain.DBMetaDataList;
import s3tool.jdog.domain.IndexList;
import s3tool.jdog.domain.JDDataSource;
import s3tool.jdog.domain.ProcedureColumnList;
import s3tool.jdog.domain.ProcedureList;
import s3tool.jdog.domain.QueryResultDataSet;
import s3tool.jdog.domain.ReferentialKeyList;
import s3tool.jdog.domain.SchemaList;
import s3tool.jdog.domain.TableList;
import s3tool.jdog.domain.TablePrivileges;

import com.silrais.toolkit.dataset.SimpleDataSet;
import com.silrais.toolkit.dataset.TableColumn;
import com.silrais.toolkit.permission.VisibilityPermission;
import com.silrais.toolkit.util.SQLFilter;
import com.silrais.toolkit.util.SimpleDataSetUtil;
import com.silrais.toolkit.util.SimpleMap;

public class JDOGService {

	Logger log = Logger.getLogger(JDOGService.class.getName());

	private DataSourceConfiguration dataSourceConfiguration = null;
	
	private DAOFactory DAOFactory = null;

	private SimpleMap<String, DataSource> connectedDSMap = new SimpleMap<String, DataSource>();

	private SimpleMap<String, JDDataSource> configuredDSCache = new SimpleMap<String, JDDataSource>();

	private AbstractDAOFactory abstractDAOFactory = null;
	
	private DataSource  defaultDataSource = null;
	
	public JDOGService() {
	}

	public void initialize() {

		DriverManager.setLoginTimeout(5);

		log.info("JDOG Service module starting up....");
		for (String group : getDataSourceConfiguration().getDatabaseGroups()) {
			log.info("Connecting to datasoures for group: " + group);
			SimpleMap<String, JDDataSource> dataSources = getDataSourceConfiguration()
					.getDataSources(group);
			for (JDDataSource ds : dataSources.values()) {
				try {
					log.info("Connecting datasource named :" + ds.getName());
					// add the datasource object to local cache
					configuredDSCache.put(ds.getID(), ds);
					connect2DataSource(ds);
					ds.setRemark(ds.getRemark() + ": Connected.");
				} catch (Exception e) {
					ds.setRemark(ds.getRemark() + ": Not Connected.");
					log.log(Level.INFO, "Failed to connect to datasource : "
							+ ds.getName(), e);
				}
			}
		}
		log.info("JDOG Service module started...");
	}

	public SimpleDataSet getGrantedDatabaseList (String dataSource) {
		SimpleDataSet schemaList = null;
		try {
			schemaList = getMetaDataDAO(dataSource).getDatabaseList();
			
			JDDataSource jds = configuredDSCache.get(dataSource);

			if (jds != null) {
				HashSet<Object> matchSet = new HashSet<Object>();

				matchSet.addAll(jds.getSchemaList());
				if (jds.getSchemaPermissionType() != VisibilityPermission.HideAll) {
					switch (jds.getSchemaPermissionType()) {
					case ShowOnly:
						schemaList =  SimpleDataSetUtil.retainAll(
								schemaList, matchSet, 1);
						break;
					case HideOnly:
						schemaList = SimpleDataSetUtil.removeAll(
								schemaList, matchSet, 1);
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return schemaList;
	}

	public TablePrivileges getTablePrivileges(String dataSource,
			String catalogName, String schemaName, String tableNamePattern) {
		try {
			return getMetaDataDAO(dataSource).getTablePrivileges(catalogName,
					schemaName, tableNamePattern);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public TableList getTableList(String dataSource, String catalogName,
			String schemaName, String tableNamePattern, String[] tableTypes) {
		try {
			return getMetaDataDAO(dataSource).getTableList(catalogName,
					schemaName, tableNamePattern, tableTypes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ColumnList getColumnList(String dataSource, String catalogName,
			String schemaName, String tableName, String columnNamePattern) {
		try {

			return getMetaDataDAO(dataSource).getColumnList(catalogName,
					schemaName, tableName, columnNamePattern);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ReferentialKeyList getImportedKeys(String dataSource,
			String catalogName, String schemaName, String tableName) {
		try {
			return getMetaDataDAO(dataSource).getImportedKeys(catalogName,
					schemaName, tableName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ReferentialKeyList getExportedKeys(String dataSource,
			String catalogName, String schemaName, String tableName) {
		try {
			return getMetaDataDAO(dataSource).getExportedKeys(catalogName,
					schemaName, tableName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public SimpleDataSet getDataSet(String dataSource, String catalogName,
			String schemaName, String tableName, SQLFilter criteria) {
		try {

			return getTableDataDAO(dataSource).queryTableData(catalogName,
					schemaName, tableName, criteria);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public QueryResultDataSet executeQuery(String dataSource, String query,
			SQLFilter criteria) {
		QueryResultDataSet dataSet = null;
		try {

			return getTableDataDAO(dataSource).executeQuery(query, criteria);
		} catch (Exception e) {
			e.printStackTrace();
			dataSet = (dataSet == null) ? new QueryResultDataSet() : dataSet;
			dataSet.setErrorCode(e.getMessage());
		}
		return dataSet;
	}

	public TableColumn[] getTableColumns(String dataSource, String catalogName,
			String schemaName, String tableName) {
		try {
			return getMetaDataDAO(dataSource).getTableColumns(catalogName,
					schemaName, tableName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public DBMetaDataList getDBMetaDataList(String dataSource) {
		try {
			return getMetaDataDAO(dataSource).getDBMetaDataList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ProcedureList getProcedureList(String dataSource,
			String catalogName, String schemaName, String procNamePattern) {
		try {
			return getMetaDataDAO(dataSource).getProcedureList(catalogName,
					schemaName, procNamePattern);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ProcedureColumnList getProcedureColumns(String dataSource,
			String catalogName, String schemaName, String procNamePattern,
			String columnNamePattern) {
		try {
			return getMetaDataDAO(dataSource).getProcedureColumns(catalogName,
					schemaName, procNamePattern, columnNamePattern);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public IndexList getIndexList(String dataSource, String catalogName,
			String schemaName, String tableName, boolean unique,
			boolean approximate) {
		try {
			return getMetaDataDAO(dataSource).getIndexList(catalogName,
					schemaName, tableName, unique, approximate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public IMetaDataDAO getMetaDataDAO(String dataSource) {
		JDDataSource jds = configuredDSCache.get(dataSource);
		DataSource ds = getConnectedDataSource(dataSource);
		
		IMetaDataDAO dao = null;
		//(IMetaDataDAO) getDAOFactory().getDAO(IMetaDataDAO.class);
		dao = getAbstractDAOFactory().getMetaDataDAO(jds.getDBVendor());
		dao.setDataSource(ds);
		return dao;
	}

	public ITableDataDAO getTableDataDAO(String dataSource) {
		JDDataSource jds = configuredDSCache.get(dataSource);
		DataSource ds = getConnectedDataSource(dataSource);
		ITableDataDAO dao = null;
		//(ITableDataDAO) getDAOFactory().getDAO(ITableDataDAO.class);
		dao = getAbstractDAOFactory().getTableDataDAO(jds.getDBVendor());
		dao.setDataSource(ds);
		return dao;
	}

	public void setDAOFactory(DAOFactory dAOFactory) {
		DAOFactory = dAOFactory;
		//getConnectedDataSourceMap().put("default",
		//		DAOFactory.getDAO(IMetaDataDAO.class).getDataSource());
		log.info("DataSource 'default' added from TableDataDAO");

	}

	public DAOFactory getDAOFactory() {
		return DAOFactory;
	}

	protected DataSource getConnectedDataSource(String dataSource) {
		return getConnectedDataSourceMap().get(dataSource);
	}
	
	public void setConnectedDataSourceMap(
			SimpleMap<String, DataSource> dataSourceMap) {
		this.connectedDSMap = dataSourceMap;
	}

	public SimpleMap<String, DataSource> getConnectedDataSourceMap() {
		return connectedDSMap;
	}

	public String connect2DataSource(JDDataSource dataSource) throws Exception {
		DriverManagerDataSource dmds = constructDataSource(dataSource);
		Connection con = dmds.getConnection();
		con.close();
		// successfully connected, so add it to connected DS map
		getConnectedDataSourceMap().put(dataSource.getID(), dmds);
		return dataSource.getID();
	}

	private DriverManagerDataSource constructDataSource(JDDataSource dataSource) {
		DriverManagerDataSource dmds = new DriverManagerDataSource();
		dmds.setUsername(dataSource.getUsername());
		dmds.setPassword(dataSource.getPassword());
		dmds.setUrl(JDDataSource.getJDBCUrl(dataSource));
		dmds.setDriverClassName(dataSource.getDriverClassName());
		return dmds;
	}

	public boolean pingConnectedDataSource(String id) throws Exception {
		DriverManagerDataSource dmds = (DriverManagerDataSource) getConnectedDataSourceMap()
				.get(id);
		if (dmds != null) {
			dmds.getConnection().close();
			return true;
		} else {
			return false;
		}
	}

	public void setDataSourceConfiguration(
			DataSourceConfiguration dataSourceConfiguration) {
		this.dataSourceConfiguration = dataSourceConfiguration;
	}

	public DataSourceConfiguration getDataSourceConfiguration() {
		return dataSourceConfiguration;
	}

	public DataSourceDataSet getConfiguredDataSourceDataSet() {
		return getDataSourceConfiguration().getDataSourceDataSet();
	}

	public JDDataSource getConfiguredDataSource(String groupName, String dsName) {
		return getDataSourceConfiguration().getDataSource(groupName, dsName);
	}

	public boolean isConnectedDataSource(String id) {
		return getConnectedDataSourceMap().containsKey(id);
	}

	public AbstractDAOFactory getAbstractDAOFactory() {
		return abstractDAOFactory;
	}

	public void setAbstractDAOFactory(AbstractDAOFactory abstractDAOFactory) {
		this.abstractDAOFactory = abstractDAOFactory;
	}

	public DataSource getDefaultDataSource() {
		return defaultDataSource;
	}

	public void setDefaultDataSource(DataSource defaultDataSource) {
		this.defaultDataSource = defaultDataSource;
	}
}
