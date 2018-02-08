package com.xunxin.vo.square;

import java.util.Date;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月27日 -- 下午5:45:53
 * @Version 1.0
 * @Description 	转盘游戏记录
 */
public class TurnPlateRecord {

	private int 							id;
	private String 							turnName;			  	//游戏结果
	private String							turnRewards;			//游戏奖励
	private Date 							createTime;				//游戏时间
	private int 							userId;					//用户
	
	public TurnPlateRecord(String turnName, String turnRewards, Date createTime, int userId) {
		super();
		this.turnName = turnName;
		this.turnRewards = turnRewards;
		this.createTime = createTime;
		this.userId = userId;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTurnName() {
		return turnName;
	}
	public void setTurnName(String turnName) {
		this.turnName = turnName;
	}
	public String getTurnRewards() {
		return turnRewards;
	}
	public void setTurnRewards(String turnRewards) {
		this.turnRewards = turnRewards;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
	
	
}
