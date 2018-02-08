package com.xunxin.vo.qa;

import org.mongodb.framework.pojo.GeneralBean;

/**
 * 
 * Copyright © 2017 Xunxin Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2017年12月20日 -- 下午3:35:05
 * @Version 1.0
 * @Description		Q&A标签库
 */
public class XTagLibrary extends GeneralBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String 								tagName;				//标签名
	private short 								type;					//板块类型
	
	
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public short getType() {
		return type;
	}
	public void setType(short type) {
		this.type = type;
	}
	

}
