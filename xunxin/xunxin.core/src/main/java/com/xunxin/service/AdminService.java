package com.xunxin.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunxin.dao.admin.AdminDao;
import com.xunxin.vo.admin.Admin;
import com.xunxin.vo.sys.PageData;

@Service("adminService")
public class AdminService {

	@Resource
	private AdminDao adminDao; 
	
	public Admin findUserById(int id) {
		return adminDao.findUserById(id);
	}

	public Admin findUserByAccountName(String username) {
		return adminDao.findAdminByAdminName(username);
	}

    public List<Admin> admin_list() {
        return adminDao.findAll();
    }

    public void insert(PageData pd) {
        adminDao.insert(pd);
    }

    public void delete(PageData pd) {
        adminDao.delete(pd);
    }


}
