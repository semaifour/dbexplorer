package com.silrais.toolkit.dataset;


public class DataSetColumn extends SimpleColumn {
    public DataSetColumn () {
        super();
    }

    public DataSetColumn(String columnName, int columnType,
                        String columnTypeName, int columnIndex,
                        int rsColumnIndex) {
         super(columnName, columnType, columnTypeName, 
               columnIndex, rsColumnIndex);
    }


    public static DataSetColumn newIntegerColumn(String columnName,
                                                int    columnIndex,
                                                int    rsColumnIndex) {
        return new DataSetColumn(columnName,
                                java.sql.Types.INTEGER,
                                "INTEGER",
                                columnIndex,
                                rsColumnIndex);
    }

    public static DataSetColumn newStringColumn(String columnName,
                                                int    columnIndex,
                                                int    rsColumnIndex) {
        return new DataSetColumn(columnName,
                                java.sql.Types.VARCHAR,
                                "VARCHAR",
                                columnIndex,
                                rsColumnIndex);
    }

    public static DataSetColumn newDateColumn(String columnName,
                                                int    columnIndex,
                                                int    rsColumnIndex) {
        return new DataSetColumn(columnName,
                                java.sql.Types.DATE,
                                "DATE",
                                columnIndex,
                                rsColumnIndex);
    }

    public static DataSetColumn newObjectColumn(String columnName,
                                                int    columnIndex,
                                                int    rsColumnIndex) {
        return new DataSetColumn(columnName,
                                java.sql.Types.JAVA_OBJECT,
                                "JAVA_OBJECT",
                                columnIndex,
                                rsColumnIndex);
    }
}
