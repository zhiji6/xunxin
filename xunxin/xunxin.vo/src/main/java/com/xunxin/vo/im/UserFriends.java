package com.xunxin.vo.im;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserFriends {
    private Integer id;
    //用户id
    
    private Integer userId;
    //好友的用户id
    private Integer friendId;
    //是否删除-0删除-1未删除
    @JsonIgnore
    private Integer isDelete;
    //创建日期
    private Date createdate;
    //更新日期
    @JsonIgnore
    private Date updatedate;
    //是否屏蔽 0 屏蔽 1取消屏蔽
    @JsonIgnore
    private Integer isShield;
    //是否举报 0 举报 1非举报
    @JsonIgnore
    private Integer isReport;
    //昵称
    private String nickName;
    //性取向
    private String sexualOrientation;
    //备注
    private String openRemark;
    //性别
    private String gender;
    //类型
    private String type;
    //在线时长
    private Integer onLineTine;
    //ID
    private String userID;
    //是否登录
    private Integer isLogin;
    //独白
    private String introduce;
    
    private String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public Integer getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(Integer isLogin) {
		this.isLogin = isLogin;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
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

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSexualOrientation() {
		return sexualOrientation;
	}

	public void setSexualOrientation(String sexualOrientation) {
		this.sexualOrientation = sexualOrientation;
	}

	public String getOpenRemark() {
		return openRemark;
	}

	public void setOpenRemark(String openRemark) {
		this.openRemark = openRemark;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getIsShield() {
		return isShield;
	}

	public void setIsShield(Integer isShield) {
		this.isShield = isShield;
	}

	public Integer getIsReport() {
		return isReport;
	}

	public void setIsReport(Integer isReport) {
		this.isReport = isReport;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getOnLineTine() {
		return onLineTine;
	}

	public void setOnLineTine(Integer onLineTine) {
		this.onLineTine = onLineTine;
	}


    
    
}