package com.xunxin.vo.sys;

import org.mongodb.framework.pojo.GeneralBean;

public class XDocumentAPI extends GeneralBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String 						api_name;	 				//接口名称
	private String 						api_model;	 				//接口模块
	private String 						api_url;	 				//API链接
	private String 						api_source;	 				//API来源
	private String 						api_prefix;	 				//API前缀
	private int 						api_state;	 				//API状态    0:不可用 | 1:可用 | 2:废弃
	
	
	public String getApi_name() {
		return api_name;
	}
	public void setApi_name(String api_name) {
		this.api_name = api_name;
	}
	public String getApi_model() {
		return api_model;
	}
	public void setApi_model(String api_model) {
		this.api_model = api_model;
	}
	public String getApi_url() {
		return api_url;
	}
	public void setApi_url(String api_url) {
		this.api_url = api_url;
	}
	public String getApi_source() {
		return api_source;
	}
	public void setApi_source(String api_source) {
		this.api_source = api_source;
	}
	public String getApi_prefix() {
		return api_prefix;
	}
	public void setApi_prefix(String api_prefix) {
		this.api_prefix = api_prefix;
	}
	public int getApi_state() {
		return api_state;
	}
	public void setApi_state(int api_state) {
		this.api_state = api_state;
	}

	
	
	

}
