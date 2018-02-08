package com.xunxin.vo.square;

import java.util.Date;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年10月19日 -- 下午1:55:37
 * @Version 1.0
 * @Description	社区活动
 */
public class XunXinSQHD {

	private int recordID;		//记录id
	private String content;		//信息内容
	private Date recordTime;	//记录时间
	public int getRecordID() {
		return recordID;
	}
	public void setRecordID(int recordID) {
		this.recordID = recordID;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	
	
}
