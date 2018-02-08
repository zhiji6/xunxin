package com.xunxin.vo.user;

import org.mongodb.framework.pojo.GeneralBean;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年12月7日 -- 上午10:03:55
 * @Version 1.0
 * @Description
 */
public class UserInterestPoint extends GeneralBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String 							pointId;
	private int 							userId;
	
	public String getPointId() {
		return pointId;
	}
	public void setPointId(String pointId) {
		this.pointId = pointId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
	
	
	

}
