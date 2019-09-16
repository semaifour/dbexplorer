package com.silrais.sss.configuration.biz.dac;

import java.util.Enumeration;
import java.util.ResourceBundle;

import com.silrais.sss.configuration.domain.Configuration;
import com.silrais.sss.configuration.domain.ConfigurationBatch;
import com.silrais.toolkit.namespace.Namespace;

public class PropertiesConfigurationDACImpl implements 	ConfigurationDataAccessController {

	public ConfigurationBatch load(Namespace namespace) {
		ResourceBundle bundle = ResourceBundle.getBundle(namespace.getName());
		ConfigurationBatch batch = new ConfigurationBatch();
		batch.addConfiguration(newConfiguration(bundle));
		return batch;
	}

	private Configuration newConfiguration(ResourceBundle bundle) {
		Configuration config = new Configuration();
		Enumeration<String> keys = bundle.getKeys();
		String key = null;
		while(keys.hasMoreElements()) {
			key = keys.nextElement();
			config.put(key, bundle.getObject(key));
		}
		return config;
	}

}