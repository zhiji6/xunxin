package com.xunxin.vo.qa;

import org.mongodb.framework.pojo.GeneralBean;

/**
 * 
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月14日 -- 下午1:52:36
 * @Version 1.0
 * @Description	  主页板块
 */
public class QASection extends GeneralBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 563884710006507291L;

	private String 								sectionName;			//板块名称
	private short 								sectionType;			//板块类型
	private int 								is_display;				//是否显示
	
	
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public short getSectionType() {
		return sectionType;
	}
	public void setSectionType(short sectionType) {
		this.sectionType = sectionType;
	}
	public int getIs_display() {
		return is_display;
	}
	public void setIs_display(int is_display) {
		this.is_display = is_display;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
