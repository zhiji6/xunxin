package com.xunxin.vo.qa;

import java.util.Date;
/**
 * 志愿者审核资格
 */
public class XunxinUserAudit {
    private Integer id;
	/**
	 * 用户id
	 */
    private Integer userId;

	/**
	 * 答题比例
	 */
    private Double answerProportion;

	/**
	 * 是否删除
	 */
    private String isDelete;

	/**
	 * 模块类型
	 */
    private Short type;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getAnswerProportion() {
        return answerProportion;
    }

    public void setAnswerProportion(Double answerProportion) {
        this.answerProportion = answerProportion;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete == null ? null : isDelete.trim();
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}


}