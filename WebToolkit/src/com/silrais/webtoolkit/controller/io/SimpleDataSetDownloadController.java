package com.silrais.webtoolkit.controller.io;

import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.silrais.toolkit.dataset.SimpleDataSet;
import com.silrais.toolkit.dataset.io.IDataSetWriter;

public class SimpleDataSetDownloadController extends DownloadController {
	
	private Map dsWritersMap = null;
	
	@Override
	public String getContentType(HttpServletRequest req) throws Exception {
		return getStrParamB4Attr(req, "ct");
	}

	@Override
	public String getDownloadFileName(HttpServletRequest req) throws Exception {
		return "downloaded-file";
	}

	/**
	 * Reads all datasets using "$datasetkey.*" and writes all of them into give writer.
	 */
	@Override
	public void writeContent(HttpServletRequest req, Writer out) throws Exception {
		
		Map model = (Map) req.getAttribute("model");
		
		String dskey = (String) model.get("datasetkey");
		
		SimpleDataSet ds = (SimpleDataSet) model.get(dskey);
		
		IDataSetWriter writer = resolveWriter(getStrParamB4Attr(req, "ctw"));
		
		writer.write(ds, out);
	}
	
	/**
	 * Sets a Map of DataSetWriter objects from config.
	 * @param map
	 */
	public void setDataSetWriters(Map map) {
		this.dsWritersMap = map;
	}
	
	/**
	 * Returns map of dataset writers
	 * @return
	 */
	public Map getDataSetWriters() {
		return this.dsWritersMap;
	}

	/**
	 * Returns a non-null writer from the configuration. If no writer configured for the given key, it tries to return 'default'
	 *
	 * @param id
	 * @return configured dataset writer fromthe configuration.
	 * @throws Exception if no dataset writer is configured.
	 */
	public IDataSetWriter resolveWriter (String id) throws Exception {
		Map writers = getDataSetWriters();
		IDataSetWriter writer = null;
		if (writers != null) {
			writer = (IDataSetWriter) writers.get(id);
			if (writer == null) {
				writer = (IDataSetWriter) writers.get("default");
			}
		}
		if (writer == null) {
			throw new RuntimeException("No valid DataSetWriter has been configured. Please confiure at one IDataSetWriter for 'default' key");
		}
		
		return writer;
	}
}
