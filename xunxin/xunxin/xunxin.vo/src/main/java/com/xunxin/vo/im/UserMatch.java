package com.xunxin.vo.im;

import java.math.BigDecimal;
import java.util.Date;

public class UserMatch {
    private Integer id;

    private Integer userId;

    private Integer consonanceId;

    private Integer isDelete;

    private Date createdate;

    private Date updatedate;

    private BigDecimal matchValue;
    
    //昵称
    private String nickName;
    //性取向
    private String sexualOrientation;
    //距离
    private String distance;
    //是否在线
    private Integer isOnline;
    
    //性别
    private String gender;

    public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Integer isOnline) {
		this.isOnline = isOnline;
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

    public Integer getConsonanceId() {
        return consonanceId;
    }

    public void setConsonanceId(Integer consonanceId) {
        this.consonanceId = consonanceId;
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

    public BigDecimal getMatchValue() {
        return matchValue;
    }

    public void setMatchValue(BigDecimal matchValue) {
        this.matchValue = matchValue;
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

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}
    
    
}