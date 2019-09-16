package com.silrais.toolkit.json;

import org.json.JSONArray;
import org.json.JSONObject;

import com.silrais.toolkit.dataset.DataSetColumn;
import com.silrais.toolkit.dataset.SimpleDataSet;
import com.silrais.toolkit.dataset.SimpleRow;
import com.silrais.toolkit.util.SimpleMap;

public class SimpleJSONWriter {

    public static String toJSONFormatString(SimpleDataSet sds,
                                            int[] filter, 
                                            boolean isExcludeFilter,
                                            boolean exclColName) 
        throws SimpleJSONException {

        JSONArray jsArray = new JSONArray();
        JSONObject jsds = new JSONObject();
		try {
			DataSetColumn[] dsCols = sds.getColumns();
			int size  = sds.size();
			int index = 1;
			while (index < size) {
                if (exclColName) {
				    jsArray.put(new JSONObject().put("RowArray", toJSONObject(dsCols, sds.getDataRow(index), filter, isExcludeFilter, exclColName))); 
                } else {
				    jsArray.put(new JSONObject().put("RowObject", new JSONObject((SimpleMap)toJSONObject(dsCols, sds.getDataRow(index), filter, isExcludeFilter, exclColName)))); 
                }
                index++;
			}

			jsds.put("ResultSet",jsArray);
		} catch (Exception e) {
			throw new SimpleJSONException("Error thrown when converting", e);
		}
		return jsds.toString();
	}

    public static Object toJSONObject(DataSetColumn[] dsCols,
                                          SimpleRow   sdr,
                                          int[] filter,
                                          boolean isExcludeFilter,
                                          boolean exclColName) {
        SimpleMap sMap = new SimpleMap();
        JSONArray jsArr = new JSONArray();
        Object retObject = null;

        if (filter == null || isExcludeFilter) {
            for (int i = 0; i < dsCols.length; i++) {
                if (!isFound(i, filter)) {
                    if (exclColName) {
                        jsArr.put(sdr.get(i));
                        retObject = jsArr;
                    } else {
                        sMap.put(dsCols[i].getColumnName(), sdr.get(i));
                        retObject = sMap;
                    }
                }
            }
        } else {
            for (int i = 0; i < filter.length; i++) {
                if (filter[i] < dsCols.length) {
                    if (exclColName) {
                        jsArr.put(sdr.get(filter[i]));
                        retObject = jsArr;
                    } else {
                        sMap.put(dsCols[filter[i]].getColumnName(), sdr.get(filter[i]));
                        retObject = sMap;
                    }
                }
            }
        }
        return retObject;
    }

    private static boolean isFound(int colIdx, int[] filter) {
        if (filter != null ) { 
            for (int i = 0; i < filter.length; i++) {
                return (colIdx == filter[i]);
            }
        }
        return false;
    }


}
