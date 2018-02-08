package com.xunxin.vo.im;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class UserMatch implements Comparable<UserMatch>{
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
    private Integer nearby;
    //是否在线
    private Integer isOnline;
    //是否已读
    private Integer isRead=0;
    
    public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

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

	public Integer getNearby() {
		return nearby;
	}

	public void setNearby(Integer nearby) {
		this.nearby = nearby;
	}

	public int compareTo(UserMatch o) {
		int nameComp = this.nearby.compareTo(o.nearby);
        return (nameComp != 0 ? nameComp : (this.nearby - o.nearby));
	}
	
    @Override
	public String toString() {
		return "UserMatch [nearby=" + nearby + "]";
	}

	public static void main(String[] args) {
		List<UserMatch> list = new ArrayList();
    	UserMatch m1 = new UserMatch();
    	m1.setNearby(5);
    	list.add(m1);
    	UserMatch m2 = new UserMatch();
    	m2.setNearby(3);
    	list.add(m2);
    	UserMatch m3 = new UserMatch();
    	m3.setNearby(1);
    	list.add(m3);
    	UserMatch m4 = new UserMatch();
    	m4.setNearby(8);
    	list.add(m4);
    	UserMatch m5 = new UserMatch();
    	m5.setNearby(10);
    	list.add(m5);
    	UserMatch m6 = new UserMatch();
    	m6.setNearby(77);
    	list.add(m6);
    	Collections.sort(list);
    	for (UserMatch object : list) {
			System.out.println(object);
		}
    	
	}
    
}