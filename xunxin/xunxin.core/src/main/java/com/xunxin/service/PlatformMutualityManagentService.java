package com.xunxin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunxin.dao.sys.PlatformMutualityManagentDao;
import com.xunxin.vo.sys.PageData;
import com.xunxin.vo.sys.PlatformMutualityManagent;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年10月20日 -- 下午1:52:10
 * @Version 1.0
 * @Description
 */
@Service("platformMutualityManagentService")
@SuppressWarnings("rawtypes")
public class PlatformMutualityManagentService {

	@Autowired
	private PlatformMutualityManagentDao platformMutualityManagentDao;
	
	public List<PageData> findAll(PageData pd) {
		return platformMutualityManagentDao.findAll(pd);
	}
	
	public PlatformMutualityManagent findOne(int platform_id) {
		return platformMutualityManagentDao.findOne(platform_id);
	}
	
	public void insert(PageData pd) {
		platformMutualityManagentDao.insert(pd);
	}
	
	public void update(PageData pd) {
		platformMutualityManagentDao.insert(pd);
	}
	
	public void delete(PageData pd) {
		platformMutualityManagentDao.insert(pd);
	}
	
}
