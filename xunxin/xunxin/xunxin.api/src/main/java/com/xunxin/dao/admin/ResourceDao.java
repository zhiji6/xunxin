package com.xunxin.dao.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xunxin.vo.admin.Resource;

public interface ResourceDao {

	List<Resource> findAll();


}
