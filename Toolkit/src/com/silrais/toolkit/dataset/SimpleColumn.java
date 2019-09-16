package com.silrais.toolkit.dataset;

import com.silrais.toolkit.util.SimpleMap;

public class SimpleColumn extends SimpleMap {

	protected String 	columnName;
	protected int    	columnIndex;
	protected int    	rsColumnIndex;
	protected int    	columnType;
	protected String 	columnTypeName;
	protected String 	dataSetName; //table name

    public SimpleColumn() {
    }

    public SimpleColumn(String columnName, int columnType,
                        String columnTypeName, int columnIndex,
                        int rsColumnIndex) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.columnTypeName = columnTypeName;
        this.columnIndex = columnIndex;
        this.rsColumnIndex = rsColumnIndex;
    }

	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public int getColumnIndex() {
		return columnIndex;
	}
	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}
	public int getRSColumnIndex() {
		return rsColumnIndex;
	}
	public void setRSColumnIndex(int columnIndex) {
		rsColumnIndex = columnIndex;
	}
	public int getColumnType() {
		return columnType;
	}
	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}
	public String getColumnTypeName() {
		return columnTypeName;
	}
	public void setColumnTypeName(String columnTypeName) {
		this.columnTypeName = columnTypeName;
	}
	public String getDataSetName() {
		return dataSetName;
	}
	public void setDataSetName(String dataSetName) {
		this.dataSetName = dataSetName;
	}


    @Override
	public String toString() {
        return new StringBuffer(dataSetName).append(":")
                    .append(columnName).append(",col.typ.name=")
                    .append(columnTypeName).append(",col.typ=")
                    .append(columnType).append(",col.idx=")
                    .append(columnIndex).append(",col.rsidx=")
        			.append(rsColumnIndex)
                    .append("col.attrs="+super.toString()).toString();
    }


    
    /**
     * Transfors the given the string value to the type of the column.
     * By default no transformation is done.
     * Sub classes may override and do special transformation
     */
    public String transform(String str) {
        //by default no transformation required
        return str;
    }




}
