package com.xunxin.vo.account;

import java.util.Date;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月3日 -- 下午3:17:40
 * @Version 1.0
 * @Description	提现记录
 */
public class TransferRecord {

	public TransferRecord() {
		super();
	}
	
	public TransferRecord(int userId, String orderNo, int thirdPayId, Double tansferBefore,
			Double tansferAmount, Double tansferEnd, Date tansferTime, int orderState, String remark) {
		super();
		this.userId = userId;
		this.orderNo = orderNo;
		this.thirdPayId = thirdPayId;
		this.tansferBefore = tansferBefore;
		this.tansferAmount = tansferAmount;
		this.tansferEnd = tansferEnd;
		this.tansferTime = tansferTime;
		this.orderState = orderState;
		this.remark = remark;
	}

	private int id;                    //主键+自增
	private int userId; 				//用户ID	
	private String orderNo;				//订单编号
	private int thirdPayId;				//第三方支付ID
	private Double tansferBefore;			//订单交易前
	private Double tansferAmount;			//交易金额
	private Double tansferEnd;			//订单交易后
	private Date tansferTime;				//提现时间
	private int orderState;				//订单状态
	private String remark;				//备注
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public int getThirdPayId() {
		return thirdPayId;
	}
	public void setThirdPayId(int thirdPayId) {
		this.thirdPayId = thirdPayId;
	}
	public Double getTansferBefore() {
		return tansferBefore;
	}
	public void setTansferBefore(Double tansferBefore) {
		this.tansferBefore = tansferBefore;
	}
	public Double getTansferAmount() {
		return tansferAmount;
	}
	public void setTansferAmount(Double tansferAmount) {
		this.tansferAmount = tansferAmount;
	}
	public Double getTansferEnd() {
		return tansferEnd;
	}
	public void setTansferEnd(Double tansferEnd) {
		this.tansferEnd = tansferEnd;
	}
	public Date getTansferTime() {
		return tansferTime;
	}
	public void setTansferTime(Date tansferTime) {
		this.tansferTime = tansferTime;
	}
	public int getOrderState() {
		return orderState;
	}
	public void setOrderState(int orderState) {
		this.orderState = orderState;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	
}
