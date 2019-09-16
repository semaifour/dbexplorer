package com.silrais.sss.viewfounder.biz.dao.xml;


import com.silrais.sss.viewfounder.biz.dao.ICustomViewDAO;
import com.silrais.sss.viewfounder.domain.CustomViewList;
import com.silrais.sss.viewfounder.domain.ViewDefinition;
import com.silrais.sss.viewfounder.domain.ViewGroupList;
import com.silrais.sss.xml.SimpleXPathDocument;
import com.silrais.sss.xml.SimpleXPathNode;
import com.silrais.sss.xml.SimpleXPathNodeList;
import com.silrais.sss.xml.XMLResourceFileReader;
import com.silrais.toolkit.dataset.DataSetColumn;
import com.silrais.toolkit.dataset.SimpleRow;

public class CustomConfigDAOImpl implements ICustomViewDAO {

    protected String configFile = null;
    
    protected SimpleXPathDocument ccxmldoc = null;

    public CustomConfigDAOImpl() {
    }

    public CustomConfigDAOImpl(String cfile) {
        configFile = cfile;
    }

    public void setConfigFile(String cfile) {
        configFile = cfile;
    }

    public String getConfigFile() {
        return configFile;
    }

    public String getResourceName() {
        return getConfigFile();
    }

    public ViewGroupList getCustomViewGroups() throws Exception {
        ViewGroupList vglist = newViewGroupList();
        SimpleXPathDocument sxd = getCustomConfigDataSource();
        SimpleXPathNodeList snl = null;
        snl = sxd.getSimpleXPathNodeList("/S3ViewFounder/viewgroup");
        SimpleXPathNode node = null;
        int length = snl.getLength();
        SimpleRow row = null;
        String id = null;
        for (int i = 0; i < length ; i++) {
            node = snl.item(i);
            row = new SimpleRow();
            row.add(i+1);//row id
            id = node.getString("@id");
            row.add(id);
            row.add(node.getString("@name"));
            row.add(getCustomViews(id));
            vglist.addDataRow(row);
        }
        return vglist; 
    } 

    public CustomViewList getCustomViews() throws Exception {
        return getCustomViews("default");
    }

    @SuppressWarnings("unchecked")
	protected CustomViewList getCustomViews(String groupId) throws  Exception {
        CustomViewList cvList = newCustomViewList();
        SimpleXPathDocument sxd = getCustomConfigDataSource();
        SimpleXPathNodeList snl = null;
        snl = sxd.getSimpleXPathNodeList("/S3ViewFounder/viewgroup[@id=\""+groupId+"\"]/view");
        SimpleXPathNode node = null;
        int length = snl.getLength();
        SimpleRow row = null;
        for (int i = 0; i < length ; i++) {
            node = snl.item(i);
            row = new SimpleRow();
            row.add(i+1);//row id
            row.add(node.getString("@id"));
            row.add(node.getString("@label"));
            row.add(node.getString("view-def/sql"));
            row.add(new ViewDefinition(node.getSimpleXPathNode("view-def")));
            cvList.addDataRow(row);
        }
        return cvList;
    }


    private ViewGroupList newViewGroupList() {
        ViewGroupList vgList = new ViewGroupList("DBANavigationConfig");
        DataSetColumn[] cols = new DataSetColumn[4];
        cols[0] = DataSetColumn.newIntegerColumn("JD_REC_ID", 0, 0);
        cols[0].setDataSetName(vgList.getId());
        cols[1] = DataSetColumn.newStringColumn("Id", 1, 1);
        cols[1].setDataSetName(vgList.getId());
        cols[2] = DataSetColumn.newStringColumn("name", 2, 2);
        cols[2].setDataSetName(vgList.getId());
        cols[3] = DataSetColumn.newObjectColumn("Navigation Option List", 3, 3);
        cols[3].setDataSetName(vgList.getId());
        vgList.setColumns(cols);
        return vgList;
    }

    private CustomViewList newCustomViewList() {
        CustomViewList cvList = new CustomViewList("CustomViewList");
        DataSetColumn[] cols = new DataSetColumn[5];
        cols[0] = DataSetColumn.newIntegerColumn("JD_REC_ID", 0, 0);
        cols[0].setDataSetName(cvList.getId());
        cols[1] = DataSetColumn.newStringColumn("Id", 1, 1);
        cols[1].setDataSetName(cvList.getId());
        cols[2] = DataSetColumn.newStringColumn("Label", 2, 2);
        cols[2].setDataSetName(cvList.getId());
        cols[3] = DataSetColumn.newStringColumn("Select Query", 3, 3);
        cols[3].setDataSetName(cvList.getId());
        cols[4] = DataSetColumn.newObjectColumn("View Definition", 4, 4);
        cols[4].setDataSetName(cvList.getId());
        cvList.setColumns(cols);
        return cvList;
    }
    
    public ViewDefinition getCustomViewDefinition(String viewId) 
        throws  Exception {

        String expr = "/S3ViewFounder/viewgroup/view[@id=\""+viewId+"\"]/view-def";
        SimpleXPathDocument cc = getCustomConfigDataSource();
        SimpleXPathNode node = cc.getSimpleXPathNode(expr);
        if (node != null) {
            return new ViewDefinition(node);
        } else {
            return null;
        }
    }

    public SimpleXPathDocument getCustomConfigDataSource() throws  Exception {
        if (ccxmldoc == null) {
        	XMLResourceFileReader xmlrsloader = new XMLResourceFileReader();
        	ccxmldoc = xmlrsloader.getXMLResourceAsXPathDocument(configFile);
        }
        return ccxmldoc;
    }
}
