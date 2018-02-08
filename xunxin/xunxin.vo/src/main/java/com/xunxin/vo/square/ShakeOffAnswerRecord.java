package com.xunxin.vo.square;

import java.util.Date;

import org.mongodb.framework.pojo.GeneralBean;

/**
 * 
 * Copyright © 2017 Xunxin Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2017年12月18日 -- 下午1:25:53
 * @Version 1.0
 * @Description		摇一摇用户答题记录
 */
public class ShakeOffAnswerRecord extends GeneralBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 问题id  */
	private String questionID;

	/** 答题人  */
	private int answerID;

	/** 答题时间  */
	private Date answerTime;

	/** 答题内容(a:0000001_01,0000001表示问题id,01表示答案id 用于快速匹配出答题相同的人)  */
	private String answers;
	
	/** 
	 * 问题类型: 
	 * 	0:模块0 1:模块1 2:模块2 ...
	 */
	private short type;

	public String getQuestionID() {
		return questionID;
	}

	public void setQuestionID(String questionID) {
		this.questionID = questionID;
	}

	public int getAnswerID() {
		return answerID;
	}

	public void setAnswerID(int answerID) {
		this.answerID = answerID;
	}

	public Date getAnswerTime() {
		return answerTime;
	}

	public void setAnswerTime(Date answerTime) {
		this.answerTime = answerTime;
	}

	public String getAnswers() {
		return answers;
	}

	public void setAnswers(String answers) {
		this.answers = answers;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	
	
	
}
