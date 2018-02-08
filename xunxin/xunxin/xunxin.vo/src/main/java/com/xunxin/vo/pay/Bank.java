package com.xunxin.vo.pay;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年10月2日 -- 下午11:22:59
 * @Version 1.0
 * @Description		银行pojo
 */
public class Bank {

	private int id; 			//主键+自增
	private int bank_id;		//银行id
	private String bank_name;	//银行名称
	private String bank_code;	//银行代码
	private String bank_url;	//银行网关
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBank_id() {
		return bank_id;
	}
	public void setBank_id(int bank_id) {
		this.bank_id = bank_id;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getBank_code() {
		return bank_code;
	}
	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}
	public String getBank_url() {
		return bank_url;
	}
	public void setBank_url(String bank_url) {
		this.bank_url = bank_url;
	}
	
	
}
