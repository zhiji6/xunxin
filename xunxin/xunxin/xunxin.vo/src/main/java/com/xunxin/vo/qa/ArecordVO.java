package com.xunxin.vo.qa;

import java.util.Date;

import org.mongodb.framework.pojo.GeneralBean;
/**
 * 用户答题记录，保存于mongodb数据库
 * @author rambo
 *
 */
public class ArecordVO extends GeneralBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7683212268472110833L;
	
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



	public int getAnswerID() {
		return answerID;
	}

	public void setAnswerID(int answerID) {
		this.answerID = answerID;
	}

	@Override
	public String toString() {
		return "ArecordVO [questionID=" + questionID + ", answerID=" + answerID + ", answerTime=" + answerTime
				+ ", answers=" + answers + ", type=" + type + "]";
	}
	
	
	
}
