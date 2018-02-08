package com.xunxin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunxin.dao.sys.RoleDao;

@Service()
public class RoleService {
	
	@Autowired
	private RoleDao roleDao;


}
