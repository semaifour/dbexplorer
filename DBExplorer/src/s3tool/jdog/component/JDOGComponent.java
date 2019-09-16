package s3tool.jdog.component;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.silrais.webtoolkit.util.HttpUtil;

public abstract class JDOGComponent extends HttpUtil {

	public JDOGComponent() {
		super();
	}

	protected String getDataSourceName(HttpServletRequest req) {
		return getStrParam(req, "ds");
	}

	protected String getDataSourceGroupName(HttpServletRequest req) {
		return getStrParam(req, "dsg");
	}

	protected String getSchema(HttpServletRequest req) {
		return getStrParam(req, "sch");
	}

	protected String getCatalog(HttpServletRequest req) {
		return getStrParam(req, "cat");
	}

	protected String getDBOName(HttpServletRequest req) {
		return getStrParam(req, "oname");
	}

	protected String getDBOType(HttpServletRequest req) {
		return getStrParam(req, "otype");
	}

	public abstract Map<String, ?> handleRequest(HttpServletRequest req, HttpServletResponse res);
}
