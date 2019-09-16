package com.silrais.sss.configuration.biz;

import java.util.Map;

import com.silrais.sss.configuration.biz.dac.ConfigurationDataAccessController;
import com.silrais.sss.configuration.domain.Configuration;
import com.silrais.sss.configuration.domain.ConfigurationBatch;
import com.silrais.sss.configuration.domain.ConfigurationContext;
import com.silrais.toolkit.namespace.Namespace;
import com.silrais.toolkit.util.SimpleMap;

public class ConfigurationManager {
	
	
	private SimpleMap<String, ConfigurationDataAccessController> configDACMap = new SimpleMap<String, ConfigurationDataAccessController>();
	
	private SimpleMap<Namespace, Configuration> namespaces = new SimpleMap<Namespace, Configuration>();
	
	public Configuration getConfiguration(Namespace namespace, ConfigurationContext context) {
		Configuration config = namespaces.get(namespace);
		if (config == null) {
			config = loadConfiguration(namespace, context);
			namespaces.put(namespace, config);
		}
		return config;
	}
	
	private Configuration loadConfiguration(Namespace namespace, ConfigurationContext context) {
		
		ConfigurationDataAccessController loader = configDACMap.getValue(namespace.getType());
		ConfigurationBatch configBatch = loader.load(namespace);
		
		//TODO:
		//step 1: read all configurations matching the namespace
		//step 2: merge configurations according to scope in context
		
		return configBatch.getConfigurations().get(0);
	}
	
	
	public void setConfigurationDataAccessControllers(Map dacMap) {
		for(Object type : dacMap.keySet()) {
			configDACMap.put((String)type, (ConfigurationDataAccessController)dacMap.get(type));
		}
	}
	
}
