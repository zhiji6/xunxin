package com.xunxin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunxin.dao.sys.RoleDao;
import com.xunxin.vo.sys.Role;

@Service("roleService")
public class RoleService {
	
	@Autowired
	private RoleDao roleDao;

    public Role findByRoleId(int roleId) {
        return roleDao.findByRoleId(roleId);
    }


}
