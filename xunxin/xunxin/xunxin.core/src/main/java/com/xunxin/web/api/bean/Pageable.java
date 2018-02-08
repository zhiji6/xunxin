package com.xunxin.web.api.bean;

import java.util.Map;

/**
 * 
 * Copyright © 2017 Xunxin Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2017年12月18日 -- 下午5:26:26
 * @Version 1.0
 * @Description		分页对象
 */
public class Pageable {
	
	Map<Object, Object> map = null;
	
	private int 				pageNo;				//页码 第几页
	private int 				PageSize;			//条数 多少条
	
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return PageSize;
	}
	public void setPageSize(int pageSize) {
		PageSize = pageSize;
	}
	
		
		
	
}
