package com.xunxin.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xunxin.dao.admin.ResourceDao;

@Service("resourceService")
public class ResourceService {

	@Resource
	private ResourceDao resourceDao;

	public List<com.xunxin.vo.admin.Resource> getAll() {
		return resourceDao.findAll();
	}
	
	
	
	
	
	
	
}
