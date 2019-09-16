package com.silrais.toolkit.dataset.io;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

import com.silrais.toolkit.dataset.SimpleColumn;
import com.silrais.toolkit.dataset.SimpleDataSet;
import com.silrais.toolkit.dataset.SimpleRow;
import com.silrais.toolkit.util.SimpleUtil;

public class SimpleCSVDataSetWriter implements IDataSetWriter {
	private static final Character DELIMITER = '\t';
	private static final String NULL_VALUE = "";
	
	private Character delimiter = DELIMITER;
	private String nullValue = NULL_VALUE;
	private String lineSeparator = System.getProperty("line.separator");
	
	public SimpleCSVDataSetWriter() {
	}
	
	public SimpleCSVDataSetWriter(Character delimiter, String nullValue, String lineSeparator) {
		this.delimiter = delimiter;
		this.nullValue = nullValue;
		this.lineSeparator = lineSeparator;
	}
	
	public Character getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(Character delimiter) {
		this.delimiter = delimiter;
	}
	
	public void setDelimiter(String delimiter) {
		if (SimpleUtil.isSizeNot0(delimiter)) {
			setDelimiter(delimiter.charAt(0));
		}
	}

	public String getNullValue() {
		return nullValue;
	}

	public void setNullValue(String nullValue) {
		this.nullValue = nullValue;
	}

	public String getLineSeparator() {
		return lineSeparator;
	}

	public void setLineSeparator(String lineSeparator) {
		this.lineSeparator = lineSeparator;
	}

	@SuppressWarnings("unchecked")
	public void write(SimpleDataSet dataSet, Writer destination) throws DataSetIOException {
		Iterator iterator = dataSet.iterator();
		while (iterator.hasNext()) {
			write((SimpleRow)iterator.next(), destination);
		}
	}

	@SuppressWarnings("unchecked")
	public void write(SimpleRow row, Writer destination) throws DataSetIOException {
		try {
			if (row != null) {
				Iterator iterator = row.iterator();
				while(iterator.hasNext()) {
					write(iterator.next(), destination);
				}
			}
			destination.write(lineSeparator);
		} catch (IOException e) {
			throw new DataSetIOException(e);
		}
	}
	
	
	public void write(Object object, Writer destination) throws DataSetIOException {
		try {
			if (SimpleUtil.isSize0(object)) {
				destination.write(nullValue);
			} else {
				if (object instanceof SimpleColumn) {
					destination.write(((SimpleColumn)object).getColumnName());
				} else {
					destination.write(object.toString());
				}
			}
			destination.write(delimiter);
		} catch (IOException e) {
			throw new DataSetIOException(e);
		}
	}

}
