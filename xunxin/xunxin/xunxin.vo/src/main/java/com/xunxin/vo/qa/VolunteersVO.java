package com.xunxin.vo.qa;
/**
 * 审题志愿者列表，每周更新数据,用于快速把问题推送到志愿者手中
 * @author rambo
 *
 */
public class VolunteersVO {
	/*
	 * 用户id
	 */
	private String id;
	/*
	 * 志愿者擅长板块,可以擅长多个模块(如：001,001_002,003_005_007)
	 */
	private String type;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}	
}
