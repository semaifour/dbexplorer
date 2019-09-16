package s3tool.jdog.domain;

public class DatabaseInfoForm {

	protected String dbType;
	protected String dbServer;
	protected String dbPort;
	protected String dbName;
	protected String dbUserName;
	protected String dbPassword;
	
	public String getDBType() {
		return dbType;
	}
	public void setDBType(String dbType) {
		this.dbType = dbType;
	}
	public String getDBServer() {
		return dbServer;
	}
	public void setDBServer(String dbServer) {
		this.dbServer = dbServer;
	}
	public String getDBPort() {
		return dbPort;
	}
	public void setDBPort(String dbPort) {
		this.dbPort = dbPort;
	}
	public String getDBName() {
		return dbName;
	}
	public void setDBName(String dbName) {
		this.dbName = dbName;
	}
	public String getDBUserName() {
		return dbUserName;
	}
	public void setDBUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}
	public String getDBPassword() {
		return dbPassword;
	}
	public void setDBPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	
}
