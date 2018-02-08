package com.xunxin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunxin.dao.sys.SystemAreaDao;
import com.xunxin.dao.sys.SystemCountryEntityDao;
import com.xunxin.vo.sys.SystemArea;
import com.xunxin.vo.sys.SystemCountryEntity;

@Service("systemEntityService")
public class SystemEntityService {

	@Autowired
	private SystemCountryEntityDao systemCountryEntityDao;
	@Autowired
	private SystemAreaDao systemAreaDao;
	
	public List<SystemCountryEntity> getAll() {
		return systemCountryEntityDao.getAll();
	}

	public void update(SystemCountryEntity entity) {
		systemCountryEntityDao.update(entity);
	}

	public void save(SystemArea province) {
		systemAreaDao.save(province);
	}

	public List<SystemArea> getAllArea() {
		return systemAreaDao.getAllArea();
	}

	public void update(int preId,int newId) {
		systemAreaDao.update(preId,newId);
	}

	public SystemCountryEntity findByChinaName(String name) {
		return systemCountryEntityDao.findByChinaName(name);
	}

    public SystemArea findNameByCode(String code) {
        return systemAreaDao.findByCode(code);
    }

    public SystemCountryEntity findCountryById(int id) {
        return systemCountryEntityDao.findCountryById(id);
    }

    
	
	
}
