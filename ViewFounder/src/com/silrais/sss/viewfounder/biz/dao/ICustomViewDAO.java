package com.silrais.sss.viewfounder.biz.dao;

import com.silrais.sss.viewfounder.domain.CustomViewList;
import com.silrais.sss.viewfounder.domain.ViewDefinition;
import com.silrais.sss.viewfounder.domain.ViewGroupList;


public interface ICustomViewDAO {
    public String getResourceName();
    public ViewGroupList getCustomViewGroups() throws  Exception;
    public CustomViewList   getCustomViews() throws  Exception;
    public ViewDefinition getCustomViewDefinition(String viewId) throws  Exception;
}
