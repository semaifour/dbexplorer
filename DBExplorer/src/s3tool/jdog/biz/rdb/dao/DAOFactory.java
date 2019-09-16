package s3tool.jdog.biz.rdb.dao;

import java.util.List;

import javax.sql.DataSource;

import com.silrais.toolkit.util.SimpleMap;

public class DAOFactory {
	
	private SimpleMap<Class<IDAO>, IDAO> DAOTypeMap = new SimpleMap<Class<IDAO>, IDAO>();
	
	private List<IDAO> DAOList = null;
	
	public DAOFactory() {
		
	}
	
	public void setDAOTypeList(List<IDAO> list) {
		DAOList = list;
	}
	
	protected IDAO findDAO(Class<IDAO> clazz) {
		for(IDAO dao : DAOList) {
			if (clazz.isInstance(dao)) {
				return dao;
			}
		}
		return null;
	}	
	
	@SuppressWarnings("unchecked")
	public IDAO getDAO(Class clazz) {
		IDAO dao = DAOTypeMap.get(clazz);
		if (dao == null) {
			dao = findDAO(clazz);
			if (dao != null) {
				DAOTypeMap.put(clazz, dao);
			}
		}
		return dao;
	}

}
