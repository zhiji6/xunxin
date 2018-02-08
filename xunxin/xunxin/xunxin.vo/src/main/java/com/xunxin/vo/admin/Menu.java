package com.xunxin.vo.admin;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年10月6日 -- 上午11:44:02
 * @Version 1.0
 * @Description  菜单
 */
public class Menu {
	
	public static final int NORMAL = 0;		//可用
	public static final int UNUSUAL = 1;	//不可用

	private String id;
	private String name;
	private String parentId;
	private String menuType;//菜单类型   0父节点  1子菜单   2链接
	private String url;
	private String icon;
	private int order;
	private int isVisible = NORMAL;
	
	public String getMenuType() {
		return menuType;
	}
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public int getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(int isVisible) {
		this.isVisible = isVisible;
	}
	@Override
	public String toString() {
		return "Menu [id=" + id + ", name=" + name + ", parentId=" + parentId + ", menuType=" + menuType + ", url="
				+ url + ", icon=" + icon + ", order=" + order + ", isVisible=" + isVisible + "]";
	}
	
	
}
