package com.silrais.sss.configuration.biz.dac;

import com.silrais.sss.configuration.domain.ConfigurationBatch;
import com.silrais.toolkit.dac.SimpleDataAccessController;
import com.silrais.toolkit.dataset.SimpleDataSet;
import com.silrais.toolkit.namespace.Namespace;

public interface ConfigurationDataAccessController extends SimpleDataAccessController<SimpleDataSet> {

	public ConfigurationBatch load(Namespace namespace);
	
}
