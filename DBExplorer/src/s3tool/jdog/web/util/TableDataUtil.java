package s3tool.jdog.web.util;


import java.util.ArrayList;

import com.silrais.toolkit.dataset.SimpleDataSet;
import com.silrais.toolkit.dataset.SimpleRow;

public class TableDataUtil {

    public static SimpleRow[] filter(SimpleDataSet ds, String[] rowIds) {
        SimpleRow row = null;
        SimpleRow[] rows = null;
        int foundCount = 0;
        ArrayList arrList = new ArrayList();
        int size = ds.size();
        for (int i = 1; i < size; i++) {
            row = ds.getDataRow(i);
            for (int j = 0; (j < rowIds.length) 
                            && (foundCount <= rowIds.length); j++) {
                if (rowIds[j].equals(row.get(0).toString())) {
                    arrList.add(row);
                    foundCount++;
                }
            }
        }
        rows = (SimpleRow[]) arrList.toArray(new SimpleRow[0]);
        return rows;
    }
}
