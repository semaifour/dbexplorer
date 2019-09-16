package com.silrais.toolkit.dataset.io;

import java.io.Reader;

import com.silrais.toolkit.dataset.SimpleDataSet;

public interface IDataSetReader {

	/**
	 * Reads values from the given read (source) and constructs a SimpleDataSet object.
	 * @param reader source to read data elements
	 * @return SimpleDataSet created from values found in input source (reader)
	 * @throws DataSetIOException
	 */
	public SimpleDataSet read(Reader reader) throws DataSetIOException;
	
}
