package com.silrais.sss.viewfounder.biz.service;

import java.util.HashMap;

import com.silrais.sss.viewfounder.biz.dao.ICustomViewDAO;
import com.silrais.sss.viewfounder.biz.dao.xml.CustomConfigDAOImpl;
import com.silrais.sss.viewfounder.domain.ViewDefinition;
import com.silrais.sss.viewfounder.domain.ViewGroupList;

public class ViewConfigurationService {

    protected HashMap  cvDAOMap = new HashMap();

    public ViewConfigurationService() {
    }

    public void setCustomViewDAO(ICustomViewDAO[] cvDAOList) {
        for(ICustomViewDAO cvDAO : cvDAOList) {
            cvDAOMap.put(cvDAO.getResourceName(), cvDAO);
        }
    }
    
    public ICustomViewDAO getCustomViewDAO(String resourceName) {
    	ICustomViewDAO dao = (ICustomViewDAO) cvDAOMap.get(resourceName);
    	if (dao == null) {
    		dao = new CustomConfigDAOImpl(resourceName);
    		cvDAOMap.put(resourceName, dao);
    	}
    	return dao;
    }

    public ViewGroupList getCustomViewGroupList(String resourceName) 
        throws  Exception {
        ICustomViewDAO dao = getCustomViewDAO(resourceName);
        return dao.getCustomViewGroups();
    }

    public ViewDefinition searchViewDefinition(String cvId) throws  Exception {
        Object obj = null;
        for (Object dao : cvDAOMap.values()) {
            if (obj == null) {
                obj = ((ICustomViewDAO)dao).getCustomViewDefinition(cvId);
            }
        }
        return (ViewDefinition) obj;
    }

    public ViewDefinition getCustomViewDefinition(String resourceName, String cvId) 
        throws  Exception {
        ICustomViewDAO dao = (ICustomViewDAO) cvDAOMap.get(resourceName);
        return dao.getCustomViewDefinition(cvId);
    }

}
