package com.xunxin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunxin.dao.admin.AdminDao;
import com.xunxin.vo.admin.Admin;

@Service("adminService")
public class AdminService {

	@Autowired
	private AdminDao adminDao; 
	
	public Admin findUserById(int id) {
		return adminDao.findUserById(id);
	}

	public Admin findUserByAccountName(String username) {
		return adminDao.findAdminByAdminName(username);
	}


}
