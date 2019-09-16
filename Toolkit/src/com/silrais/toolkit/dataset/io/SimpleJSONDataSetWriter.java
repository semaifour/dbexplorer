/**
 * 
 */
package com.silrais.toolkit.dataset.io;

import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.silrais.toolkit.dataset.DataSetColumn;
import com.silrais.toolkit.dataset.SimpleDataSet;
import com.silrais.toolkit.dataset.SimpleRow;
import com.silrais.toolkit.util.SimpleUtil;

/**
 * @author mahi
 * 
 */
public class SimpleJSONDataSetWriter implements IDataSetWriter {

	/**
	 * 
	 */
	public SimpleJSONDataSetWriter() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.silrais.toolkit.dataset.io.IDataSetWriter#write(com.silrais.toolkit
	 * .dataset.SimpleDataSet, java.io.Writer)
	 */
	public void write(SimpleDataSet dataSet, Writer destination)
			throws DataSetIOException {
		try {
			destination.write("{");
			destination.write("\n\"Columns\":");
			write(dataSet.getColumns(), null, false, destination);
			destination.write(",");
			destination.write("\n\"Rows\":");
			destination.write("[");
			
			Iterator iterator = dataSet.iterator();
			iterator.next(); //skip the columns 
			while(iterator.hasNext()) {
				write((SimpleRow)iterator.next(), destination);
				destination.write(",");
			}
			destination.write("]");
			destination.write("}");
		} catch (Exception e) {
			throw new DataSetIOException("Error occurred while writing SimpleDataSet as JSON", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.silrais.toolkit.dataset.io.IDataSetWriter#write(com.silrais.toolkit
	 * .dataset.SimpleRow, java.io.Writer)
	 */
	public void write(SimpleRow row, Writer destination)
			throws DataSetIOException {
		try {
			write(row,  null, false, false, destination);
		} catch (Exception e) {
			throw new DataSetIOException("Error occurred while writing SimpleRow as JSON", e);
		}
	}

	private void write(DataSetColumn[] dsCols, int filter[], 
					  boolean isExcludeFilter, Writer destination) throws Exception {
		if (filter == null) {
			write(dsCols, destination, "");
		} else if (isExcludeFilter) {
			destination.write("[");
			for (int i = 0; i < dsCols.length; i++) {
				if (!SimpleUtil.contains(filter, i)) {
					write(dsCols[i], destination, "");
					destination.write(",");
				}
			}
			destination.write("]");
		} else {
			destination.write("[");
			for (int i = 0; i < filter.length; i++) {
				if (filter[i] < dsCols.length) {
					write(dsCols[filter[i]], destination, "");
				}
			}
			destination.write("]");
		}
		
	}
	
	private void write(SimpleRow sdr,
					  int[] filter, boolean isExcludeFilter, 
					  boolean exclColName, Writer destination) throws Exception {

		if (filter == null) {
			write(sdr, destination, "");
		} else if (isExcludeFilter) {
			destination.write("[");
			for (int i = 0; i < sdr.size(); i++) {
				if (!SimpleUtil.contains(filter, i)) {
					write(sdr.get(i), destination, "");
					destination.write(",");
				}
			}
			destination.write("]");
		} else {
			destination.write("[");
			for (int i = 0; i < filter.length; i++) {
				if (filter[i] < sdr.size()) {
					write(sdr.get(filter[i]), destination, "");
				}
			}
			destination.write("]");
		}
	}

	private void write(Object obj, Writer destination, String nullValue) throws Exception {
		if (obj instanceof Map) {
			destination.write("{");
			Map map = (Map) obj;
			Collection keys = map.keySet();
			for (Object key:keys) {
				destination.write("\"");
				destination.write(key.toString());
				destination.write("\"");
				destination.write(":");
				write(map.get(key), destination, nullValue);
				destination.write(",");
			}
			destination.write("}");
		} else if (obj instanceof Collection) {
			destination.write("\n[");
			Collection collection = (Collection)obj;
			for(Object item : collection) {
				write(item, destination, nullValue);
				destination.write(",");
			}
			destination.write("]");
		} else if (obj instanceof DataSetColumn) { 
			write(((DataSetColumn)obj).getColumnName(), destination, nullValue);
		} else if (obj instanceof DataSetColumn[]) {
			DataSetColumn[] dsCols = (DataSetColumn[]) obj;
			destination.write("[");
			for (int i = 0; i < dsCols.length; i++) {
				write(dsCols[i], destination, nullValue);
				destination.write(",");
			}
			destination.write("]");
		} else { 
			destination.write("\"");
			if (obj == null) {
				destination.write(nullValue == null ? "null" : nullValue);
			} else {
				destination.write(obj.toString());
			}
			destination.write("\"");
		}
	}
	
}
