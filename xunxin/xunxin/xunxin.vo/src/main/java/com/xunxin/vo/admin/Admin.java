package com.xunxin.vo.admin;

import java.util.List;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年10月8日 -- 上午9:22:53
 * @Version 1.0
 * @Description		
 */
public class Admin {

	private int                    id;
	private String                 adminName;		        //用户姓名
	private String                 gender;		            //性别
	private String                 nickName;		        //昵称
	private String                 password;		        //密码
	private int                    roleId;			        //角色
	private int                    ishiden;      	        //是否禁用
	private String                 lastLoginTime;           //登录时间
	private String                 lastLoginIp;		        //上次登录Ip
	private String                 photograph;		        //用户头像
	
	public List<Resource> getResourceList() {
		return resourceList;
	}
	public void setResourceList(List<Resource> resourceList) {
		this.resourceList = resourceList;
	}
	private List<Resource> resourceList;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getAdminName() {
        return adminName;
    }
    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
    public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getIshiden() {
        return ishiden;
    }
    public void setIshiden(int ishiden) {
        this.ishiden = ishiden;
    }
    public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public String getPhotograph() {
		return photograph;
	}
	public void setPhotograph(String photograph) {
		this.photograph = photograph;
	}
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
	
	
	
	
}
