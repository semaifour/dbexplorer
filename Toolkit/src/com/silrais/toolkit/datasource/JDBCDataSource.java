package com.silrais.toolkit.datasource;

import java.util.Random;

public class JDBCDataSource {
	
	private static final Random random = new Random();

	public enum DBVendor { Oracle, MySQL, DB2, Derby, SQLServer };

	private String name = String.valueOf(random.nextLong());
	private String groupName = String.valueOf(random.nextLong());
	
	private String username;
	private String password;
	private String server;
	private String port;
	private String database;
	private String driverClassName;
	private DBVendor vendor;
	private String URL;
	
	public String getUsername() {
		return username;
		
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getServer() {
		return server;
	}
	
	public void setServer(String server) {
		this.server = server;
	}
	
	public String getPort() {
		return port;
	}
	
	public void setPort(String port) {
		this.port = port;
	}
	
	public void setDatabase(String database) {
		this.database = database;
	}
	
	public String getDatabase() {
		return database;
	}
	
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	
	public String getDriverClassName() {
		return driverClassName;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setDBVendor(String vendor) {
		this.vendor = DBVendor.valueOf(vendor);
	}
	
	public DBVendor getDBVendor() {
		return vendor;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupName() {
		return groupName;
	}
	
	public String getID() {
		return getGroupName() + "." + getName();
	}
	
	public static String getJDBCUrl(JDBCDataSource dataSource) {
		StringBuilder url =  new StringBuilder("jdbc");
		switch (dataSource.getDBVendor()) {
		case Oracle:
			url.append(":oracle:thin:@").append(dataSource.getServer()).append(":").append(dataSource.getPort()).append(":").append(dataSource.getDatabase());
			break;
		case MySQL:
			url.append(":mysql://").append(dataSource.getServer()).append(":").append(dataSource.getPort()).append("/").append(dataSource.getDatabase());
			url.append("?user=").append(dataSource.getUsername()).append("&password=").append(dataSource.getPassword());
			break;
		case Derby:
			url.append(":derby://").append(dataSource.getServer()).append(":").append(dataSource.getPort()).append("/").append(dataSource.getDatabase());
			break;
		case SQLServer:
			url.append(":jtds:sqlserver://").append(dataSource.getServer()).append(":").append(dataSource.getPort()).append("/").append(dataSource.getDatabase());
			break;
		}
		return url.toString();
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}
}
