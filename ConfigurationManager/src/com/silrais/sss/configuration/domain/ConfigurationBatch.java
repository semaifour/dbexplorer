package com.silrais.sss.configuration.domain;

import java.util.ArrayList;

public class ConfigurationBatch {
	
	private ArrayList<Configuration> configList = new ArrayList<Configuration>();

	public ConfigurationBatch() {
		
	}
	
	public ArrayList<Configuration> getConfigurations() {
		return this.configList;
	}
	
	public void addConfigurations(ArrayList<Configuration> configList) {
		this.configList.addAll(configList);
	}
	
	public void addConfiguration(Configuration configuration) {
		this.configList.add(configuration);
	}
}
