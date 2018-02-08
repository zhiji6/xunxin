package com.xunxin.dao.admin;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xunxin.vo.admin.Menu;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年10月6日 -- 上午11:52:04
 * @Version 1.0
 * @Description
 */
public interface MenuDao{

	List<Menu> findAll();
	
	List<Menu> getAll();

	List<Menu> findByParentId(@Param(value="parentId") String parentId);
}
