package com.xunxin.vo.account;

import java.util.Date;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年10月2日 -- 下午11:34:27
 * @Version 1.0
 * @Description		充值记录
 */
public class RechargeRecord {

	
	
	public RechargeRecord() {
		super();
	}
	public RechargeRecord(int userId, String orderNo, int thirdPayId, Double orderBefore, Date orderTime,
			int orderState) {
		super();
		this.userId = userId;
		this.orderNo = orderNo;
		this.thirdPayId = thirdPayId;
		this.orderBefore = orderBefore;
		this.orderTime = orderTime;
		this.orderState = orderState;
	}
	
	private int id;                     //主键+自增
	private int userId; 				//用户ID	
	private String orderNo;				//订单编号
	private int thirdPayId;				//第三方支付ID
	private Double orderBefore;			//订单交易前
	private Double tradeAmount;			//交易金额
	private Double orderEnd;			//订单交易后
	private Date orderTime;				//下单时间
	private Date orderAccountingTime;	//到账时间
	private int orderState;				//订单状态    0:已到账 | 1:未到账  
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
	public Double getOrderBefore() {
		return orderBefore;
	}
	public void setOrderBefore(Double orderBefore) {
		this.orderBefore = orderBefore;
	}
	public Double getTradeAmount() {
		return tradeAmount;
	}
	public void setTradeAmount(Double tradeAmount) {
		this.tradeAmount = tradeAmount;
	}
	public Double getOrderEnd() {
		return orderEnd;
	}
	public void setOrderEnd(Double orderEnd) {
		this.orderEnd = orderEnd;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public Date getOrderAccountingTime() {
		return orderAccountingTime;
	}
	public void setOrderAccountingTime(Date date) {
		this.orderAccountingTime = date;
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
	@Override
	public String toString() {
		return "RechargeRecord [id=" + id + ", userId=" + userId + ", orderNo=" + orderNo + ", thirdPayId=" + thirdPayId
				+ ", orderBefore=" + orderBefore + ", tradeAmount=" + tradeAmount + ", orderEnd=" + orderEnd
				+ ", orderTime=" + orderTime + ", orderAccountingTime=" + orderAccountingTime + ", orderState="
				+ orderState + ", remark=" + remark + "]";
	}
	
	
	
}
