package com.xunxin.vo.user;

import org.mongodb.framework.pojo.GeneralBean;

/**
 * 
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月15日 -- 上午11:13:59
 * @Version 1.0
 * @Description	标签库VO
 */
public class InterestPointVO extends GeneralBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1539300610332881608L;
	
	private String 								pointName;			//兴趣点
	private int 								is_display;			//是否显示
	
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	public int getIs_display() {
		return is_display;
	}
	public void setIs_display(int is_display) {
		this.is_display = is_display;
	}
	
	
	
	
	
}
