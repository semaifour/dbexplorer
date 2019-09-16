package s3tool.jdog.web.util;


import javax.servlet.http.HttpServletRequest;

import com.silrais.toolkit.util.SimpleUtil;
import com.silrais.webtoolkit.util.HttpUtil;

public class DBOContext {

	protected String dataSource;
	protected String dataSourceGroupName;

	protected String catalog;
    protected String schema;
    protected String otype;
    protected String oname;


    protected String httpParamStr;
    protected String contextName;

    public DBOContext() {
    }


    public String getCatalog() {
        return this.catalog;
    }

    public String getSchema() {
        return this.schema;
    }

    public String getOname() {
        return this.oname;
    }
    
    public String getDataSource() {
    	return this.dataSource;
    }

    public String getDataSourceGroupName() {
		return dataSourceGroupName;
	}

    public static DBOContext getContext(HttpServletRequest req) {
        DBOContext dboctx = new DBOContext();
        dboctx.setContext(req);
        return dboctx;
    }


    public void setContext(HttpServletRequest req) {

        StringBuffer params = new StringBuffer();
        
        this.catalog = HttpUtil.getStrParam(req, "cat");
        this.schema  = HttpUtil.getStrParam(req, "sch");
        this.oname   = HttpUtil.getStrParam(req, "oname");
        this.otype   = HttpUtil.getStrParam(req, "otype");
        this.dataSource = HttpUtil.getStrParam(req, "ds");
        this.dataSourceGroupName = HttpUtil.getStrParam(req, "dsg");
               

        //DONT REMOVE
        //http param string must start without '&'
        params.append("dboctx=y");

        if (!SimpleUtil.isnull(catalog)) params.append("&cat=").append(catalog);
        if (!SimpleUtil.isnull(schema)) params.append("&sch=").append(schema);
        if (!SimpleUtil.isnull(oname)) params.append("&oname=").append(oname);
        if (!SimpleUtil.isnull(otype)) params.append("&otype=").append(otype);
        if (!SimpleUtil.isnull(dataSource)) params.append("&ds=").append(dataSource);
        if (!SimpleUtil.isnull(dataSourceGroupName)) params.append("&dsg=").append(dataSourceGroupName);


        httpParamStr = params.toString();
        if (otype == null) {
            otype = "";
        }
        StringBuffer tmp = new StringBuffer(otype).append(" : ")
                                    .append(SimpleUtil.isnull(catalog) ? "CATALOG" : catalog)
                                    .append(" > ").append(schema);
        if (SimpleUtil.isnotnull(oname)) {
            tmp.append(" > ").append(oname);
        }
        contextName = tmp.toString();
    }

    public String getHttpParamString() {
        return this.httpParamStr;
    }
    
    /**
     * Get contextName.
     *
     * @return contextName as String.
     */
    public String getContextName()
    {
        return contextName;
    }
    
    /**
     * Set contextName.
     *
     * @param contextName the value to set.
     */
    public void setContextName(String contextName)
    {
        this.contextName = contextName;
    }
    
    /**
     * Get otype.
     *
     * @return otype as String.
     */
    public String getOtype()
    {
        return otype;
    }
    
    /**
     * Set otype.
     *
     * @param otype the value to set.
     */
    public void setOtype(String otype)
    {
        this.otype = otype;
    }
}
