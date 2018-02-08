package com.xunxin.vo.sys;

/**
 * 
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月6日 -- 下午3:45:14
 * @Version 1.0
 * @Description	国籍
 */
public class SystemCountryEntity {

	private int country_id;
	private String code;
	private String name_Chinese;
	private String name_English;

	public int getCountry_id() {
		return country_id;
	}
	public void setCountry_id(int country_id) {
		this.country_id = country_id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName_Chinese() {
		return name_Chinese;
	}
	public void setName_Chinese(String name_Chinese) {
		this.name_Chinese = name_Chinese;
	}
	public String getName_English() {
		return name_English;
	}
	public void setName_English(String name_English) {
		this.name_English = name_English;
	}
	
	
}
