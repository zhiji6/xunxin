package com.xunxin.dao.admin;

import java.util.List;


import org.apache.ibatis.annotations.Param;

import com.xunxin.vo.admin.Admin;
import com.xunxin.vo.sys.PageData;

public interface AdminDao {

	Admin findUserById(Integer id);

	Admin findAdminByAdminName(@Param("username") String username);

    List<Admin> findAll();

    void insert(PageData pd);

    void delete(PageData pd);

}
