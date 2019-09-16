package com.silrais.webtoolkit.util;

import java.util.Map.Entry;

import com.silrais.toolkit.dataset.SimpleRow;
import com.silrais.toolkit.util.SimpleMap;


public class SimpleRowAction {
	protected String label;
	protected String actionURL;
	protected String target;
	protected SimpleMap<String, Object> parameters = new SimpleMap<String, Object>();
	protected String[] variableNames;
	protected int[]   valueIndices;
	private SimpleRow currentRow;
	
	public SimpleRowAction() {
		
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getActionURL() {
		return actionURL;
	}

	public void setActionURL(String actionURL) {
		this.actionURL = actionURL;
	}

	public SimpleMap<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(SimpleMap<String, Object> parameters) {
		this.parameters = parameters;
	}
	
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	public String[] getVariableNames() {
		return variableNames;
	}

	public void setVariableNames(String... variableNames) {
		this.variableNames = variableNames;
	}

	public int[] getValueIndices() {
		return valueIndices;
	}

	public void setValueIndices(int... valueIndices) {
		this.valueIndices = valueIndices;
	}

	public String compileActionURL() {
		StringBuilder builder = new StringBuilder(getActionURL());
		if (getActionURL().indexOf("?") <= 0) {
			builder.append("?1=2");
		}
		prepareParameters();
		for(Entry<String, Object> entry: getParameters().entrySet()) {
			builder.append("&").append(entry.getKey()).append("=").append(String.valueOf(entry.getValue()));
		}
		return builder.toString();
	}

	public void prepareParameters() {
		for (int i = 0; i < getValueIndices().length; i++) {
			getParameters().put(getVariableNames()[i], getCurrentRow().get(getValueIndices()[i]));
		}
	}

	public void setCurrentRow(SimpleRow currentRow) {
		this.currentRow = currentRow;
	}

	public SimpleRow getCurrentRow() {
		return currentRow;
	}
	
}
