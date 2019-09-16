package com.silrais.toolkit.util;

import java.util.ArrayList;
import java.util.HashSet;

import com.silrais.toolkit.dataset.SimpleDataSet;
import com.silrais.toolkit.dataset.SimpleRow;

public class SimpleDataSetUtil {

	/**
	 * Returns an vertical Array of data for the given column from given data-set.
	 * 
	 * @param dataSet
	 * @param columnIndex
	 * @return
	 */
	public static ArrayList<Object> getVerticalDataRow(SimpleDataSet dataSet, int columnIndex, boolean unique) {
		ArrayList<Object> array = new ArrayList<Object>();
		for (int i = 1; i < dataSet.size(); i++) {
			SimpleRow row = dataSet.getDataRow(i);
			Object value = row.get(columnIndex);
			if (unique && !array.contains(row.get(columnIndex))) {
				array.add(value);
			} else {
				array.add(value);
			}
		}
		return array;
	}

	/**
	 * Returns an array of matching SimpleRows from the given dataSet for the given criteria.
	 * 
	 * @param dataSet
	 * @param matchingValues
	 * @param matchColumnIndex
	 * @return
	 */
	public static ArrayList<SimpleRow> getMatchingDataRow(SimpleDataSet dataSet, HashSet<?> matchingValues, int matchColumnIndex) {
		ArrayList<SimpleRow> matched = new ArrayList<SimpleRow>();
		for(int i = 1; i < dataSet.size(); i++) {
			SimpleRow row = dataSet.getDataRow(i);
			if (matchingValues.contains(row.get(matchColumnIndex))) {
				matched.add(row);
			}
		}
		return matched;
	}
	
	/**
	 * 
	 * Retains only the matching data-rows in the given data-set.
	 * 
	 * @param dataSet
	 * @param matchingValues
	 * @param matchColumnIndex
	 * @return
	 */
	public static SimpleDataSet retainAll(SimpleDataSet dataSet, HashSet<?> matchValueSet, int matchColumnIndex) {
		ArrayList<SimpleRow> rows2retain = getMatchingDataRow(dataSet, matchValueSet, matchColumnIndex);
		rows2retain.add(dataSet.getDataRow(0)); //add the data-row (of columns) to the items to retain.
		dataSet.retainAll(rows2retain);
		return dataSet;
	}

	/**
	 * 
	 * Removes only the matching data-rows in the given data-set.
	 * 
	 * @param dataSet
	 * @param matchingValues
	 * @param matchColumnIndex
	 * @return
	 */
	public static SimpleDataSet removeAll(SimpleDataSet dataSet, HashSet<?> matchValueSet, int matchColumnIndex) {
		ArrayList<SimpleRow> rows2remove = getMatchingDataRow(dataSet, matchValueSet, matchColumnIndex);
		dataSet.removeAll(rows2remove);
		return dataSet;
	}

	
}
