package s3tool.jdog.domain;

import java.sql.DatabaseMetaData;

import com.silrais.toolkit.dataset.DataSetColumn;
import com.silrais.toolkit.dataset.SimpleDataSet;
import com.silrais.toolkit.util.SimpleUtil;

public class ProcedureColumnList extends SimpleDataSet {
    public ProcedureColumnList(String id) {
        super(id);
    }

    public void setColumns(DataSetColumn[] columns) {
        DataSetColumn colTypNameCol = new ProcParamColTypeNameColumn();
        colTypNameCol.setColumnName("JD_COLUMN_TYPE_NAME");
        colTypNameCol.setColumnType(java.sql.Types.VARCHAR);
        colTypNameCol.setColumnTypeName("VARCHAR2");
        colTypNameCol.setDataSetName(columns[0].getDataSetName());
        colTypNameCol.setColumnIndex(5);
        colTypNameCol.setRSColumnIndex(5);
        DataSetColumn[] newCols = new DataSetColumn[columns.length+1];
        System.arraycopy(columns, 0, newCols, 0, columns.length);
        newCols[columns.length] = colTypNameCol;
        super.setColumns(newCols);
    }
}

class ProcParamColTypeNameColumn extends DataSetColumn {
    public ProcParamColTypeNameColumn() {
    }

    public String transform(String str) {
        int ctype = SimpleUtil.parseInt(str);
        String val = null;
        switch(ctype) {
            case DatabaseMetaData.procedureColumnIn:
                val = " IN ";
                break;
            case DatabaseMetaData.procedureColumnInOut:
                val = " IN-OUT ";
                break;
            case DatabaseMetaData.procedureColumnOut:
                val = " OUT ";
                break;
            case DatabaseMetaData.procedureColumnReturn:
                val = " Procedure Return Value ";
                break;
            case DatabaseMetaData.procedureColumnResult:
                val = " Result Column in ResultSet ";
                break;
            default:
            case DatabaseMetaData.procedureColumnUnknown:
                val = " Unknown ";
                break;
        }
        return val;
    }
}
