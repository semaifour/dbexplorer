package com.silrais.toolkit.dataset.io;

import java.io.Writer;

import com.silrais.toolkit.dataset.SimpleDataSet;
import com.silrais.toolkit.dataset.SimpleRow;

public interface IDataSetWriter {
	/**
	 * Writes a given SimpleDataSet to the given destination.
	 * @param destination Writer object to write the given SimpleDataSet to
	 * @param dataSet Input Simple Data Set
	 * @throws DataSetIOException
	 */
	public void write(SimpleDataSet dataSet, Writer destination) throws DataSetIOException;
	
	/**
	 * Writes the given SimleRow into the given destination.
	 * @param row Input SimpleRow to be written
	 * @param destination Write object to write the given SimpleRow to
	 * @throws DataSetIOException
	 */
	public void write(SimpleRow row, Writer destination) throws DataSetIOException;
}
