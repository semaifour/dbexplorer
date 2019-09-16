package com.silrais.toolkit.util;

import java.util.HashMap;
import java.util.Map;

public class SimpleMap<K extends Object, V extends Object> extends HashMap<K, V> {

	private static final long serialVersionUID = -6281487588058459749L;

	public SimpleMap() {
        super();
    }

    public V getValue(K key) {
        return get(key);
    }

    public V setValue(K key, V value) {
        return put(key, value);
    }
 
    public String getStringValue(K key) {
    	return (String) getValue(key);
    }
    public String getValue(K key, String defaultValue) {
    	String val = (String) getValue(key);
    	return val != null ? val : defaultValue;
    }
 
    public Integer getValue(K key, Integer defaultValue) {
    	Integer val = (Integer) getValue(key);
    	return val != null ? val : defaultValue;
    }
    
    public Long getValue(K key, Long defaultValue) {
    	Long val = (Long) getValue(key);
    	return val != null ? val : defaultValue;
    }

    public Map getValue(K key, Map defaultValue) {
    	Map val = (Map) getValue(key);
    	return val != null ? val : defaultValue;
    }
    
}
