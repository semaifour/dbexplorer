package s3tool.jdog.component.privilege;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import s3tool.jdog.biz.service.JDOGContext;
import s3tool.jdog.component.JDOGComponent;
import s3tool.jdog.domain.TablePrivileges;

import com.silrais.toolkit.util.SimpleUtil;

public class TablePrivilegesComponent extends JDOGComponent {

	Logger log = Logger.getLogger(TablePrivilegesComponent.class.getName());
	
	public TablePrivilegesComponent() {
		super();
	}
	
	@Override
	public Map<String, ?> handleRequest(HttpServletRequest req, HttpServletResponse res) {

		String catalog = SimpleUtil.getNNRD(getCatalog(req), "%");
		String schema = SimpleUtil.getNNRD(getSchema(req), "%");
		String table = SimpleUtil.getNNRD(getDBOName(req), "%");
		TablePrivileges sds = JDOGContext.getJDOGService().getTablePrivileges(getDataSourceName(req), catalog, schema, table);
		Map<String, TablePrivileges> m = new HashMap<String, TablePrivileges>();
		m.put("TablePrivileges", sds);
		return m;
	}

}
