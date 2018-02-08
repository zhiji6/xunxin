package com.xunxin.vo.im;

import java.util.Date;

public class UserReports {
	 public static final String REPORT = "report";  //举报
	 public static final String NOINTEREST = "noInterest";  //
	 public static final String SHIELD = "shield";  //屏蔽
    private Integer id;
    //举报人id
    private Integer userId;
    //被举报人Id
    private Integer reportId;
    //是否删除-0删除-1未删除
    private Integer isDelete;
    //举报举报原因code
    private Integer value;
   //创建日期
    private Date createdate;
    //更新日期
    private Date updatedate;
    //举报原因
    private String content;
    //举报对象id
    private String reportObjectId;
    //举报模块 IM  SY  GQ PL ZP
    private String reportType;
    //被举报人名字
    private String reportName;
    //类型 noInterest-不感兴趣
    private String type;
    
    public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReportObjectId() {
		return reportObjectId;
	}

	public void setReportObjectId(String reportObjectId) {
		this.reportObjectId = reportObjectId;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}