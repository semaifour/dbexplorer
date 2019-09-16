package s3tool.jdog.domain;

import com.silrais.toolkit.dataset.SimpleDataSet;

public class TableList extends SimpleDataSet {
    public TableList(String id) {
        super(id);
    }
    
    public void init() {
    	defineColumns("");
    }
}
