package com.silrais.toolkit.dataset;

import java.util.ArrayList;

import com.silrais.toolkit.util.SimpleUtil;

/**
  * SimpleRow represents a row in a SimpleDataSet. Data elements are stored and
  * retrieved using an index. Index starts at 0. It also provider convenient
  * getter and setter methods to access data elements.
  * 
  * Index 0 is often reserved for serial number of the record.
  * @see SimpleColumn
  * @see SimpleDataSet
  */
public class SimpleRow extends ArrayList {

    public SimpleRow() {
        super();
    }

    public void add(Object[] objArray) {
        for (int i = 0; i < objArray.length; i++) {
            add(objArray[i]);
        }
    }

    public int getInt(int index) {
        return SimpleUtil.parseInt(get(index));
    }

    public boolean getBoolean(int index) {
    	return SimpleUtil.parseBoolean(get(index));
    }
    
    public String getString(int index) {
    	return String.valueOf(get(index));
    }
}
