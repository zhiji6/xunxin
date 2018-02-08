package com.xunxin.vo.square;

import org.mongodb.framework.pojo.GeneralBean;

/**
 * 
 * Copyright © 2017 Xunxin Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2017年12月18日 -- 下午12:51:04
 * @Version 1.0
 * @Description			摇一摇用户记录
 */
public class ShakeItOffRecord extends GeneralBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int 						userId;						//用户
	private int 						otherId;					//匹配用户

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getOtherId() {
		return otherId;
	}

	public void setOtherId(int otherId) {
		this.otherId = otherId;
	}
	
	
	
	
	
	
}
