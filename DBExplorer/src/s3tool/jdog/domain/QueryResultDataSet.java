package s3tool.jdog.domain;


import java.sql.Timestamp;

import com.silrais.toolkit.dataset.SimpleDataSet;


public class QueryResultDataSet extends SimpleDataSet {

    protected String    queryString = null;
    protected String    errorCode   = null;
    protected Timestamp qryExecTime = null;
    protected long      timeTaken   = 0;

    public QueryResultDataSet() {
        super();
    }

    public QueryResultDataSet(String queryString) {
        super();
        this.queryString = queryString;
    }


	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Timestamp getQryExecTime() {
		return qryExecTime;
	}

	public void setQryExecTime(Timestamp qryExecTime) {
		this.qryExecTime = qryExecTime;
	}

	public long getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(long timeTaken) {
		this.timeTaken = timeTaken;
	}

    public String getQueryString() {
        return queryString;
    }
}
