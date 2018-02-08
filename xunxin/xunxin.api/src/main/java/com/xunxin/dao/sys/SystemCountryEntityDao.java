package com.xunxin.dao.sys;

import java.util.List;

import com.xunxin.vo.sys.SystemCountryEntity;

/**
 * 
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月6日 -- 下午3:49:20
 * @Version 1.0
 * @Description	国籍
 */
public interface SystemCountryEntityDao {

	List<SystemCountryEntity> getAll();

	void update(SystemCountryEntity entity);

	SystemCountryEntity findByChinaName(String name);

    SystemCountryEntity findCountryById(Integer id);
}
