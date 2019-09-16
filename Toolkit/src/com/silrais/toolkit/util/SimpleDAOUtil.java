package com.silrais.toolkit.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.silrais.toolkit.dataset.DataSetColumn;
import com.silrais.toolkit.dataset.SimpleDataSet;
import com.silrais.toolkit.dataset.SimpleRow;

public class SimpleDAOUtil {

    /**
      * Populates the supplied SimpleDataSet with data elements from the
      * supplied ResultSet for the DataSetColumn[] found in the supplied
      * SimpleDataSet.
      *
      *@return number row SimpleRow objects added to SimpleDataSet
      *
      */
    public static int populateSimpleDataSet(ResultSet rs,
                                     SimpleDataSet sds) throws SQLException {

        DataSetColumn[] columns = sds.getColumns();
        int rowCount = 0;
        SimpleRow tmpRow = null;
			while (rs.next()) {
				tmpRow = new SimpleRow();
				tmpRow.add(++rowCount); // add the row-id to the 0-th index
				for (int i = 1; i < columns.length; i++) {
					tmpRow.add(columns[i].transform(rs.getString(columns[i]
							.getColumnIndex())));
				}
				sds.addDataRow(tmpRow);
			}
        return rowCount;
    }

    /**
     * Builds an array of SimplColumn object for the given resultset
     */
    public static DataSetColumn[] buildDataSetColumns(ResultSetMetaData rsmd, 
                                                    String dataSetName) 
        throws SQLException {

        int colCount = rsmd.getColumnCount() + 1; 
        //+1 for JD_REC_ID at 0-th index
        DataSetColumn[] columns = new DataSetColumn[colCount]; 
        DataSetColumn column = null;
        column = new DataSetColumn();
        column.setColumnName("REC_NUM");
        column.setColumnType(java.sql.Types.INTEGER);
        column.setColumnTypeName("INTEGER");
        column.setDataSetName(dataSetName);
        column.setColumnIndex(0);
        columns[0] = column;
        for (int i = 1; i < colCount; i++) {
            column = new DataSetColumn();
            column.setColumnName(rsmd.getColumnName(i));
            column.setColumnType(rsmd.getColumnType(i));
            column.setColumnTypeName(rsmd.getColumnTypeName(i));
            column.setDataSetName(dataSetName);
            column.setColumnIndex(i);
            column.setRSColumnIndex(i-1);
            columns[i] = column;
        }
        
        return  columns;
    }
    
    /*
     * Converts ResultSet content into an instance of SimpleDataSet.
     *
     *  @param ResultSet - JDBC result set contains the data
     *  @param dataSetName - unique name to the dataset; often the table name
     *  class
     *  @return SimpleDataSet  
     *  @throws Exception 
     *  @see convertResultSet2SimpleDataSet 
     **/
   
    public static SimpleDataSet convertResultSet2SimpleDataSet(ResultSet rs,
                                                               String dataSetName) 
        throws SQLException {
        return populateResultSet2XXXDataSet(rs, dataSetName,
                                            new SimpleDataSet());
    }


    /*
     * populate ResultSet content into the supplied instance of SimpleDataSet.
     * Any given sub class of SimpleDataSet. 
     *
     *  @param ResultSet - JDBC result set contains the data
     *  @param dataSetName - unique name to the dataset; often the table name
     *  @param xxxDataSet - an instance of SimpleDataSet or any of it's sub
     *  class
     *  @return filled up xxxDataSet 
     *  @throws Exception 
     *  @see convertResultSet2SimpleDataSet 
     **/
    public static SimpleDataSet populateResultSet2XXXDataSet(ResultSet rs,
                                                             String dataSetName,
                                                             SimpleDataSet xxxDataSet) 
        throws SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        DataSetColumn[] cols = null;
        cols = SimpleDAOUtil.buildDataSetColumns(rsmd, dataSetName);
        xxxDataSet.setColumns(cols);
        SimpleDAOUtil.populateSimpleDataSet(rs, xxxDataSet);
        return xxxDataSet;
    }

}
