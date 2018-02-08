package com.xunxin.vo.user;

import java.util.List;

import org.mongodb.framework.pojo.GeneralBean;

import com.xunxin.vo.qa.SysDict;

/**
 * Copyright © 
 * @Author gyf
 * @Compile 2018-1-8 
 * @Version 1.0
 * @Description	我的福利
 */
public class UserWelfare extends GeneralBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//用户id
	private Integer userId;
	//是否签到
	private Integer isSign;
	//上个月在线时长
	private Integer lastMonthOnTime=0;
	//上个月人均在线时长
	private Integer perCapitaLastMonthOnTime=0;
	//自画像福利
	private List<SysDict> selfPortraitList;
	//共情圈照片福利
	private List<SysDict> circleList;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getIsSign() {
		return isSign;
	}
	public void setIsSign(Integer isSign) {
		this.isSign = isSign;
	}
	public Integer getLastMonthOnTime() {
		return lastMonthOnTime;
	}
	public void setLastMonthOnTime(Integer lastMonthOnTime) {
		this.lastMonthOnTime = lastMonthOnTime;
	}
	public List<SysDict> getSelfPortraitList() {
		return selfPortraitList;
	}
	public void setSelfPortraitList(List<SysDict> selfPortraitList) {
		this.selfPortraitList = selfPortraitList;
	}
	public List<SysDict> getCircleList() {
		return circleList;
	}
	public void setCircleList(List<SysDict> circleList) {
		this.circleList = circleList;
	}
	public Integer getPerCapitaLastMonthOnTime() {
		return perCapitaLastMonthOnTime;
	}
	public void setPerCapitaLastMonthOnTime(Integer perCapitaLastMonthOnTime) {
		this.perCapitaLastMonthOnTime = perCapitaLastMonthOnTime;
	}
	
}
