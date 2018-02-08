package com.xunxin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunxin.dao.admin.MenuDao;
import com.xunxin.vo.admin.Menu;
import com.xunxin.web.api.bean.PageData;

@Service
public class MenuService {

	@Autowired
	private MenuDao menuDao;
	
	public List<Menu> getAll() {
		return menuDao.getAll();
	}

	public List<Menu> findByParentId(String parentId) {
		return menuDao.findByParentId(parentId);
	}

	public List<PageData> listUserMenu(PageData pd) {
		return null;
	}

	public List<PageData> listAllMenu(PageData pd) {
		return null;
	}

}
