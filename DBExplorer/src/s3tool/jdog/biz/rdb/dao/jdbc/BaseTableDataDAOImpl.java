package s3tool.jdog.biz.rdb.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.Iterator;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import s3tool.jdog.biz.rdb.dao.ITableDataDAO;
import s3tool.jdog.domain.QueryResultDataSet;
import s3tool.jdog.exception.JDOGException;

import com.silrais.toolkit.dataset.DataSetColumn;
import com.silrais.toolkit.dataset.SimpleColumn;
import com.silrais.toolkit.dataset.SimpleDataSet;
import com.silrais.toolkit.dataset.SimpleRow;
import com.silrais.toolkit.util.SQLFilter;
import com.silrais.toolkit.util.SimpleDAOUtil;
import com.silrais.toolkit.util.SimpleMap;
import com.silrais.toolkit.util.SimpleUtil;



public abstract class BaseTableDataDAOImpl 
    extends JdbcDaoSupport 
    implements ITableDataDAO  {

        public static String varchar_condition  = " {0}.{1} = ''{2}'' ";
        public static String varchar_like_condition  = " {0}.{1} LIKE ''{2}'' ";
        public static String number_condition   = " {0}.{1} = {2} ";
        public static String datetime_condition = " {0}.{1} = {2,date, date}";
        public static String generic_condition  = varchar_condition;


    
    //----- Query Data from given Table ------//
    public SimpleDataSet queryTableData(String catalogName,
                                        String schemaName,
                                        String tableName,
                                        SQLFilter criteria) 
        throws  JDOGException {

        SimpleDataSet dataSet = null;
        Connection conn = null;
        ResultSet  rs = null;
        Statement stmt = null;

        try { 
            String query = buildSelectQuery(catalogName, schemaName, 
                                            tableName, criteria);
            if (criteria.getMaxRowCount() != -1) {
				query = buildTopNQuery(query, criteria.getBeginRowId(),
						criteria.getMaxRowCount());
            }
//            System.out.println("qry = " + query );
//            conn = getConnection();
//            stmt =  conn.createStatement();
//            rs = stmt.executeQuery(query);
//            String dsName = schemaName+"."+tableName;
//            dataSet = SimpleDAOUtil.convertResultSet2SimpleDataSet(rs, dsName);
            dataSet = executeQuery(query, criteria);
        } catch (Exception e) {
            throw new JDOGException("Error quering data from table " + tableName, e); 
        } finally {
            releaseAll(rs, stmt, conn);
        }
        return dataSet;
    }


    //------- SQL util functions ---------//


    protected String selectClauseTemplate = "select * from {0}.{1} ";

    public void setSelectClauseTemplate(String selectClauseTemplate) {
        this.selectClauseTemplate = selectClauseTemplate;
    }

    public String getSelectClauseTemplate() {
        return selectClauseTemplate;
    }

    /**
     * Builds SELECT clause of a select query. This method will be optionally 
     * overridden by the subclasses specfic to the database impl.
     */ 

    public String buildSelectClause(String catalogName,
                                    String schemaName,
                                    String tableName,
                                    SQLFilter criteria) {
        String query = MessageFormat.format(getSelectClauseTemplate() , 
                                     new Object[] {schemaName, tableName});

        return query;
    }

    /**
     * Builds Where Clause for the given table using the given criteria
     */ 
    public String buildWhereClause(String catalogName,
                                   String schemaName,
                                   String tableName,
                                   SQLFilter criteria) {

        int          loopCounter        = 0;
        Object[]     tmp                = null;
        String       condition          = null;
        String       colName            = null;
        SimpleColumn column             = null;
        Object       colValue           = null;

        SimpleMap whereClauseMap  = criteria.getWhereClauseMap(); 
        Iterator  iterator        = whereClauseMap.keySet().iterator();
        StringBuffer whereClause  = new StringBuffer(" where ");


        while (iterator.hasNext()) {
            colName = (String) iterator.next();
            tmp =  (Object[])whereClauseMap.get(colName);
            column  = (SimpleColumn)tmp[0]; 
            colValue = (String) tmp[1];
            if (loopCounter > 0) {
                whereClause.append(" and ");
            }

            switch(column.getColumnType()) {
                case java.sql.Types.BIT:
                case java.sql.Types.TINYINT:
                case java.sql.Types.SMALLINT:
                case java.sql.Types.INTEGER:
                case java.sql.Types.BIGINT:
                case java.sql.Types.FLOAT:
                case java.sql.Types.REAL:
                case java.sql.Types.DOUBLE:
                case java.sql.Types.NUMERIC:
                case java.sql.Types.DECIMAL:
                    condition = number_condition;
                    break;
                case java.sql.Types.DATE:
                case java.sql.Types.TIME:
                case java.sql.Types.TIMESTAMP:
                    condition = datetime_condition;
                    break;
                case java.sql.Types.VARCHAR:
                    if (colValue.toString().indexOf("%") >= 0) {
                        condition = varchar_like_condition;
                    } else {
                        condition = varchar_condition;
                    }
                    break;
                default:
                    condition = varchar_condition;
                    break;
            }
            whereClause.append(MessageFormat.format(condition,
                                                    new Object[] { tableName,
                                                                   colName,
                                                                   colValue
                                                                  }
                                                      ));
            loopCounter++;
        }

        if (loopCounter > 0 ) {
            return whereClause.toString();
        } else {
            return null;
        }
    }


    /**
     * Builds Order By Clause for using given criteria
     */ 
    public String buildOrderByClause(SQLFilter criteria) {

		String orderByCols[] = criteria.getOrderByClauseList();
		StringBuffer orderBy = new StringBuffer(" order by ");
		boolean orderByClauseFound = false;
		for (int i = 0; i < orderByCols.length; i++) {
			if (i > 0) {
				orderBy.append(" , ");
			}
			orderBy.append(orderByCols[i]);
			orderByClauseFound = true;
		}
		
		if (orderByClauseFound) {
			String sortOrder = criteria.getSortOrder();
			if (SQLFilter.SORT_ORDER_DESC.equalsIgnoreCase(sortOrder)) {
				sortOrder = " desc ";
			} else {
				sortOrder = " asc ";
			}

			orderBy.append(sortOrder);

            return orderBy.toString();
		} else {
            return null;
        }
    }

    /**
     * Builds SELECT query for the given table using given criteria
     */ 
    public String buildSelectQuery(String catalogName,
                                   String schemaName, 
                                   String tableName,
                                   SQLFilter criteria) {

        String select = buildSelectClause(catalogName, schemaName,
                                          tableName, criteria);
        String where = buildWhereClause(catalogName, schemaName, 
                                        tableName, criteria);
        String order = buildOrderByClause(criteria);
        StringBuffer selectQry = new StringBuffer(select);

        if (where != null) {
            selectQry.append(where);
        }

        if (order != null) {
            selectQry.append(order);
        }

        return selectQry.toString();
    }

    /**
     * Appends order by clause to a given query using given criteria
     */
    public String buildGenericOrderByWrapper(String selectQuery, 
                                      SQLFilter criteria) {

        String orderby = buildOrderByClause(criteria);
        if (orderby != null) {
		    StringBuffer qry = new StringBuffer("select * from (");
            qry.append(selectQuery);
            qry.append(")");
            qry.append(orderby);
		    return qry.toString();
        } else {
            return selectQuery;
        }
	}

    private int getQueryType(String query) {
        String tmp =  query.trim().substring(0, 6);
        if (tmp.equalsIgnoreCase("SELECT")) {
            return 0;
        } else if (tmp.equalsIgnoreCase("INSERT")) {
            return 1;
        } else if (tmp.equalsIgnoreCase("UPDATE")) {
            return 2;
        } else {
            return 3;
        }
    }

    
    /**
     * Executes the given SQL (SELECT, INSERT, UPDATE) after formatting it for the given SQLFilter criteria.
     */
    public QueryResultDataSet executeQuery(String query, SQLFilter criteria) 
        throws JDOGException {

		QueryResultDataSet dataSet = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		long startTime = 0;
        long endTime = 0;
		
		try {
            int qtype = getQueryType(query);
			conn = getConnection();
			stmt = conn.createStatement();
            dataSet = new QueryResultDataSet(query);
            if (qtype == 0 || qtype == 3) {
			    startTime = System.currentTimeMillis();
                rs = stmt.executeQuery(query);
			    endTime = System.currentTimeMillis();
			    SimpleDAOUtil.populateResultSet2XXXDataSet(rs, 
                                                           query,
                                                           dataSet); 
            } else if (qtype == 1 || qtype == 2) {
			    startTime = System.currentTimeMillis();
                stmt.executeUpdate(query);
			    endTime = System.currentTimeMillis();
                DataSetColumn tmpCol = DataSetColumn.newIntegerColumn("Update Count", 0, 1);
                dataSet.setColumns(new DataSetColumn[]{tmpCol});
                SimpleRow tmp = new SimpleRow();
                tmp.add(stmt.getUpdateCount());
                dataSet.add(tmp);
            }
			Timestamp ts = new Timestamp(startTime);
			dataSet.setQryExecTime(ts);
			dataSet.setTimeTaken(endTime - startTime);
            System.out.println(">> Info : Query :" + query);
		} catch (Exception e) {
            System.out.println(">> Error : Query ...");
            System.out.println(query); 
            throw new JDOGException(e.getMessage());
        } finally {
            releaseAll(rs, stmt, conn);
		}
		return dataSet;
    }

    /**
     * Executes the given SQL (SELECT, INSERT, UPDATE) after formatting it for the given SQLFilter criteria.
     */
    public QueryResultDataSet executeQueryCopy (String query, SQLFilter criteria) 
        throws JDOGException {

		QueryResultDataSet dataSet = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		long startTime = 0;
        long endTime = 0;
		
		try {
            int qtype = getQueryType(query);
			conn = getConnection();
			stmt = conn.createStatement();
            dataSet = new QueryResultDataSet(query);
            if (qtype == 0 || qtype == 3) {
                query = buildGenericOrderByWrapper(query, criteria);
                if (criteria.getMaxRowCount() != -1) {
                	query = buildTopNQuery(query,
                                       criteria.getBeginRowId(), 
                                       criteria.getMaxRowCount());
                }
			    startTime = System.currentTimeMillis();
                rs = stmt.executeQuery(query);
			    endTime = System.currentTimeMillis();
			    SimpleDAOUtil.populateResultSet2XXXDataSet(rs, 
                                                           query,
                                                           dataSet); 
            } else if (qtype == 1 || qtype == 2) {
			    startTime = System.currentTimeMillis();
                stmt.executeUpdate(query);
			    endTime = System.currentTimeMillis();
                DataSetColumn tmpCol = DataSetColumn.newIntegerColumn("Update Count", 0, 1);
                dataSet.setColumns(new DataSetColumn[]{tmpCol});
                SimpleRow tmp = new SimpleRow();
                tmp.add(stmt.getUpdateCount());
                dataSet.add(tmp);
            }
			Timestamp ts = new Timestamp(startTime);
			dataSet.setQryExecTime(ts);
			dataSet.setTimeTaken(endTime - startTime);
            System.out.println(">> Info : Query :" + query);
		} catch (Exception e) {
            System.out.println(">> Error : Query ...");
            System.out.println(query); 
            throw new JDOGException(e.getMessage());
        } finally {
            releaseAll(rs, stmt, conn);
		}
		return dataSet;
    }

    
    //--------- Top N Query ---------------//

    protected String topNQryTmplate = "";

    public void setTopNQueryTemplate(String topNQryTmplate) {
	    this.topNQryTmplate = topNQryTmplate;
    }

    public String getTopNQueryTemplate() {
	    return topNQryTmplate;
    }


    /**
     * Wraps the given query with database specific syntax to get only the
     * TOP-N-RECORDS from the given rowId.
     * <br>
     * This method should be overridden in subclasses that database specific.
     */ 
    public String buildTopNQuery(String selectQuery, 
                                 int beginRowId,
                                 int count) {
    	
		String tmp = getTopNQueryTemplate();
		if (SimpleUtil.isnotnull(tmp) && count > 0) {
        	return MessageFormat.format(tmp, 
                                    new Object[]{selectQuery,
                                                 new Integer(beginRowId),
                                                 new Integer(beginRowId+count)
                                                 }
                                    );
		} else {
			return selectQuery;
		}
    }

    // -------------jdbc util classes ---------//

    public void release(ResultSet rs) throws JDOGException {
        try {
            if (rs != null ) rs.close();
        } catch (Exception e) {
            throw new JDOGException("Error closing resultset", e);
        }
    }

    public void release(Statement stmt) throws JDOGException {
        try {
            if (stmt != null ) stmt.close();
        } catch (Exception e) {
            throw new JDOGException("Error closing statement", e);
        }
    }

    public void release(Connection conn) throws JDOGException {
        try {
            if (conn != null ) releaseConnection(conn);
        } catch (Exception e) {
            throw new JDOGException("Error releasing connection", e);
        }
    }

    public void releaseAll(ResultSet rs, Statement stmt, Connection conn) {
        try {
            release(rs);
        } catch(Exception e) {
            //TODO: logging
        }
        try {
            release(stmt);
        } catch(Exception e) {
            //TODO: logging
        }
        try {
            release(conn);
        } catch(Exception e) {
            //TODO: logging
        }
    }

}
