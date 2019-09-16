package com.silrais.toolkit.dataset;


import java.util.ArrayList;


/**
  * SimpleDataSet is a basic ResultSet. DataSetColumn[] is the header of the
  * data set; stored at the 0 index; very first items in the dataset.
  * 
  * SimpleDataSet provides getter setter methods to return SimpleRow object
  * based on the index. Data rows start from index 1. Index 0 is reserved for
  * columns; stored and return as DataSetColumn[]
  *
  */
public class SimpleDataSet extends ArrayList {

    String id = "SimpleDataSet";

    public SimpleDataSet() {
        super();
        id = Long.toString(System.currentTimeMillis());
    }

    public SimpleDataSet(String id) {
        super();
        this.id = id;
    }


    public void setColumns(DataSetColumn[] columns) {
        SimpleRow rowOfCols = new SimpleRow();
        rowOfCols.add(columns); //add array of columns
        setColumns(rowOfCols);
    }
    
    private void setColumns(SimpleRow columnRow) {
    	//add simple row of columns to 0-th index.
    	add(0, columnRow);
    }
    
    public DataSetColumn[] getColumns() {
    	
    	if (super.size() == 0) return null;
    	
        SimpleRow row = (SimpleRow) get(0);
        if (row != null) {
            return (DataSetColumn[]) row.toArray(new DataSetColumn[0]);
        } else {
            return null;
        }
    }

    /**
      * Return SimpleRow of the given index.
      *
      *@see get(index)
      */
    public SimpleRow getDataRow(int index) {
        return (SimpleRow) get(index);
    }

    public SimpleRow getRow(int index) {
        return getDataRow(index);
    }

    /**
      * Adds the given SimpleRow to the next index
      *
      *@see add(index), add(index, obj), set(index, obj)
      *
      */

    public void addDataRow(SimpleRow row) {
        add(row);
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
    
    public void defineColumns(String... columnNames)  {
    	SimpleRow columnRow = null;
    	if (super.size() == 0) {
    		columnRow = new SimpleRow();
    		setColumns(columnRow);	
    	} else { 
    		columnRow = (SimpleRow) get(0);
    	}
    	int index= columnRow.size();
    	for (String columnName : columnNames) { 
    	   	DataSetColumn column = new DataSetColumn();
        	column.setColumnName(columnName);
            column.setColumnType(java.sql.Types.VARCHAR);
            column.setColumnTypeName("VARCHAR2");
            column.setDataSetName(getId());
            column.setColumnIndex(index);
            column.setRSColumnIndex(index);
            
            columnRow.add(column);
            
            index++;
    	}
    }

}
