package s3tool.jdog.biz.datasource;

import java.util.logging.Level;
import java.util.logging.Logger;

import s3tool.jdog.domain.JDDataSource;

import com.silrais.sss.configuration.biz.ConfigurationManager;
import com.silrais.sss.configuration.domain.Configuration;
import com.silrais.sss.configuration.domain.ConfigurationContext;
import com.silrais.toolkit.dataset.SimpleRow;
import com.silrais.toolkit.namespace.Namespace;
import com.silrais.toolkit.util.SimpleMap;
import com.silrais.toolkit.util.SimpleStringUtil;
import com.silrais.toolkit.util.SimpleUtil;

public class DataSourceConfiguration {

	private static Logger log = Logger.getLogger(DataSourceConfiguration.class.getName());
	
	private static final String BASE = "jdog.datasource";
	private static final String DOT = ".";
	private static final String DRIVER_CLASS = "jdbc.driverClassName";
	
	private ConfigurationManager configurationManager = null;
	
	private Configuration dataSourceConfigCache = null;
	private Configuration jdbcConfigCache = null;
	
	public SimpleMap<String, JDDataSource> getDataSources(String groupName) {
		SimpleMap<String, JDDataSource> map = new SimpleMap<String, JDDataSource>();
		
		String[] dss = getDataSourceNames(groupName);
		if (!SimpleUtil.isSize0(dss)) {
			JDDataSource dataSource = null;
			for(String dsName : dss) {
				dataSource = getDataSource(groupName, dsName);
				map.put(dsName, dataSource);	
			}
		}
		return map;
	}
	
	public JDDataSource getDataSource(String groupName, String dsName) {
		JDDataSource dataSource = new JDDataSource();
		
		try {
			dataSource.setName(dsName);
			dataSource.setGroupName(groupName);
			String jdbcURL = getJDDataSourceConfiguration().getStringValue(SimpleStringUtil.join(DOT, BASE, groupName, dsName, "jdbcURL"));
			dataSource.setServer(getJDDataSourceConfiguration().getStringValue(SimpleStringUtil.join(DOT, BASE, groupName, dsName, "server")));
			dataSource.setPort(getJDDataSourceConfiguration().getStringValue(SimpleStringUtil.join(DOT, BASE, groupName, dsName, "port")));
			dataSource.setDatabase(getJDDataSourceConfiguration().getStringValue(SimpleStringUtil.join(DOT, BASE, groupName, dsName, "database")));
			dataSource.setUsername(getJDDataSourceConfiguration().getStringValue(SimpleStringUtil.join(DOT, BASE, groupName, dsName, "username")));
			dataSource.setPassword(getJDDataSourceConfiguration().getStringValue(SimpleStringUtil.join(DOT, BASE, groupName, dsName, "password")));
			dataSource.setDBVendor(getJDDataSourceConfiguration().getStringValue(SimpleStringUtil.join(DOT, BASE, groupName, dsName, "make")));
			dataSource.setSchemaPermission(getJDDataSourceConfiguration().getStringValue(SimpleStringUtil.join(DOT, BASE, groupName, dsName, "permission.schema")));
			dataSource.setDriverClassName(getJDBCConfiguration().getStringValue(SimpleStringUtil.join(DOT, dataSource.getDBVendor().toString(), DRIVER_CLASS)));
		} catch (Exception e) {
			log.log(Level.INFO, "Error reading datasource:  " + groupName + DOT + dsName, e);
			dataSource.setRemark("Missing or Invalid datasource properties");
		}
		
		return dataSource;
	}

	public DataSourceDataSet getDataSourceDataSet() {
		DataSourceDataSet configDS = new DataSourceDataSet();
		configDS.initialize();
		String[] groups = getDatabaseGroups();
		SimpleRow dsRow = null;
		if (groups != null) {
			for (String group : groups) {
				String[] dss = getDataSourceNames(group);
				if (dss != null) {
					for (String dsName : dss) {
						dsRow = new SimpleRow();
						JDDataSource ds = getDataSource(group, dsName);
						dsRow.add(ds.getID());
						dsRow.add(group);
						dsRow.add(dsName);
						dsRow.add(ds.getServer());
						dsRow.add(ds.getPort());
						dsRow.add(ds.getDatabase());
						dsRow.add(ds.getUsername());
						dsRow.add(ds.getPassword());
						dsRow.add(ds.getDBVendor());
						//dsRow.add(ds.isRestricted());
						dsRow.add(ds.getRemark());
						configDS.addDataRow(dsRow);
					}
				}
			}
		}
		return configDS;
	}
	
	public String[] getDatabaseGroups() {
		String val = (String) getJDDataSourceConfiguration().getValue(SimpleStringUtil.join(DOT, BASE, "groups"));
		if (val != null) {
			return val.split(",");
		}
		return null;
	}
	
	public String[] getDataSourceNames(String groupName) {
		String val = (String) getJDDataSourceConfiguration().getValue(SimpleStringUtil.join(DOT, BASE, groupName, "databases"));
		if (val != null) {
			return val.split(",");
		}
		return null;
	}

	public Configuration getJDDataSourceConfiguration() {
		if (dataSourceConfigCache == null) {
			Namespace namespace = new Namespace();
			namespace.setName("jdog-datasource");
			namespace.setType("properties");
			
			dataSourceConfigCache = getConfigurationManager().getConfiguration(namespace, new ConfigurationContext());
		}
		return dataSourceConfigCache;
	}
	
	public Configuration getJDBCConfiguration() {
		if (jdbcConfigCache == null) {
			Namespace namespace = new Namespace();
			namespace.setName("jdbc");
			namespace.setType("properties");
			
			jdbcConfigCache = getConfigurationManager().getConfiguration(namespace, new ConfigurationContext());
		}
		return jdbcConfigCache;
	}
	
	public void setConfigurationManager(ConfigurationManager configurationManager) {
		this.configurationManager = configurationManager;
	}

	public ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}
}
