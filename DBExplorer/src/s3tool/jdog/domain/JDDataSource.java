package s3tool.jdog.domain;

import java.util.ArrayList;

import com.silrais.toolkit.datasource.JDBCDataSource;
import com.silrais.toolkit.permission.VisibilityPermission;
import com.silrais.toolkit.util.SimpleUtil;

public class JDDataSource extends JDBCDataSource {
	
	private String schemaPermission;
	private String remark;

	private VisibilityPermission schemaPermissionType = VisibilityPermission.ShowAll; 
	private ArrayList<String> schemaList = null;

	public void setSchemaPermission(String schemaPermission) {
		this.schemaPermission = schemaPermission;
		prepareSchemaPermission();
	}
	
	public String getSchemaPermission() {
		return schemaPermission;
	}
	
	public ArrayList<String> getSchemaList() {
		if (schemaList == null) {
			prepareSchemaPermission();
		}
		return schemaList;
	}
	
	public VisibilityPermission getSchemaPermissionType() {
		return schemaPermissionType;
	}
	
	private void prepareSchemaPermission() {
		schemaList = new ArrayList<String>();

		String perm = getSchemaPermission();
		if (SimpleUtil.isnull(perm)) {
			schemaPermissionType = VisibilityPermission.ShowAll;
		} else {
			schemaPermissionType = VisibilityPermission.valueOf(perm.substring(perm.indexOf("[")+1, perm.indexOf("]")));
			String[] perms = perm.split(":");
			if (perms.length == 2) {
				perms = perms[1].split(",");
				for(String p : perms) {
					schemaList.add(p);
				}
			}
		}
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}

}
