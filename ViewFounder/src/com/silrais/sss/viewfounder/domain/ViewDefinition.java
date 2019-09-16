package com.silrais.sss.viewfounder.domain;

import java.sql.Types;

import org.w3c.dom.Node;

import com.silrais.sss.xml.SimpleXPathNode;
import com.silrais.sss.xml.SimpleXPathNodeList;
import com.silrais.toolkit.util.SimpleParameter;
import com.silrais.toolkit.util.SimpleUtil;

public class ViewDefinition extends SimpleXPathNode {
	
	public ViewDefinition(SimpleXPathNode node) {
		super(node.getW3CNode());
	}

	public ViewDefinition(Node node) {
		super(node);
	}

	public String getViewType() throws Exception {
		return getString("@type");
	}

	public String getViewSubType() throws Exception {
		return getString("@subtype");
	}

	protected SimpleParameter prepareParameter(SimpleXPathNode input)
			throws Exception {

		String type = input.getString("@type");
		String id = input.getString("@id");
		int sqlidx = input.getInt("@sqlparamidx");
		String defVal = input.getString("@default");
		SimpleParameter param = null;
		if (SimpleUtil.isnull(type) || type.equalsIgnoreCase("text")) {
			param = new SimpleParameter(id, defVal, Types.VARCHAR, sqlidx);
		} else if (type.equalsIgnoreCase("date")) {
			param = new SimpleParameter(id, defVal, Types.DATE, sqlidx);
		} else if (type.equalsIgnoreCase("options")) {
			param = new SimpleParameter(id, defVal, Types.ARRAY, sqlidx);
			param.put("OPTIONS", prepareOptions(input));
		} else {
			param = new SimpleParameter(id, defVal, Types.VARCHAR, sqlidx);
		}
		param.setCardinality(input.getString("@cardinality"));
		param.put("LABEL", input.getString("@label"));
		param.put("WHERE-CONDITION", input.getString("where-condition"));
		return param;
	}

	protected SimpleParameter[] prepareOptions(SimpleXPathNode input)
			throws Exception {

		SimpleXPathNodeList options = null;
		options = input.getSimpleXPathNodeList("options/option");
		SimpleParameter[] params = null;
		SimpleXPathNode option = null;
		if (options != null && options.getLength() > 0) {
			params = new SimpleParameter[options.getLength()];
			for (int i = 0; i < params.length; i++) {
				option = options.item(i);
				params[i] = SimpleParameter.newStringParameter(option
						.getString("@id"), option.getString("@value"), i);
				params[i].put("LABEL", option.getString("@label"));
			}
		}
		return params;
	}

}
