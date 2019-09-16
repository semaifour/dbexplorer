
package s3tool.jdog.web.history;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Action {

    protected String actionName;
    protected String actionURI;
    protected String queryString;

    public Action(String actionName,
                  HttpServletRequest req,
                  HttpServletResponse res) {

        this.actionName  = actionName;
        this.actionURI   = req.getRequestURI();
        this.queryString = req.getQueryString();
    }
                  
    
    /**
     * Get actionName.
     *
     * @return actionName as String.
     */
    public String getActionName()
    {
        return actionName;
    }
    
    /**
     * Set actionName.
     *
     * @param actionName the value to set.
     */
    public void setActionName(String actionName)
    {
        this.actionName = actionName;
    }
    
    /**
     * Get actionURI.
     *
     * @return actionURI as String.
     */
    public String getActionURI()
    {
        return actionURI;
    }
    
    /**
     * Set actionURI.
     *
     * @param actionURI the value to set.
     */
    public void setActionURI(String actionURI)
    {
        this.actionURI = actionURI;
    }
    
    /**
     * Get queryString.
     *
     * @return queryString as String.
     */
    public String getQueryString()
    {
        return queryString;
    }
    
    /**
     * Set queryString.
     *
     * @param queryString the value to set.
     */
    public void setQueryString(String queryString)
    {
        this.queryString = queryString;
    }


    /**
     * Returns the action URL (URI?QueryString)
     */
    public String getActionURL() {
        return new StringBuffer(getActionURI())
                            .append("?")
                            .append(getQueryString())
                            .toString();
        
    }
}
