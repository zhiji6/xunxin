package com.xunxin.vo.qa;

import org.mongodb.framework.pojo.GeneralBean;

/**
 * 
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月14日 -- 下午1:57:16
 * @Version 1.0
 * @Description		答案/观点库
 */
public class AnswerVO extends GeneralBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5741850371111440736L;


	private String 						answer;					//答案内容
	private int 						type;					//答案内容    	1:常用|2:热门|3：其他	
	private int 						count;					//答案内容


	public String getAnswer() {
		return answer;
	}


	public void setAnswer(String answer) {
		this.answer = answer;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	
}
