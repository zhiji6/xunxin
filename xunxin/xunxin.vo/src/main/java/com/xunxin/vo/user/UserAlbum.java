package com.xunxin.vo.user;

import java.util.Date;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月21日 -- 上午10:29:11
 * @Version 1.0
 * @Description		用户相册
 */
public class UserAlbum {

	private int 								id;							//
	private int 								userId;						//用户
	private String 								name;						//文件名
	private String 								url;						//文件地址
	private Date 								createTime;					//上传时间
	
	
	public UserAlbum() {
		super();
	}

	public UserAlbum(int userId, String name, String url, Date createTime) {
		super();
		this.userId = userId;
		this.name = name;
		this.url = url;
		this.createTime = createTime;
	}
	
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
	
}
