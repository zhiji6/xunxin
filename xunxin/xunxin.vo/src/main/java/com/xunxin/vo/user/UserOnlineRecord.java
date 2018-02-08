package com.xunxin.vo.user;

import org.mongodb.framework.pojo.GeneralBean;

public class UserOnlineRecord extends GeneralBean{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private int                         onlineTime;         //在线时长
    private int                         userId;             //用户
    public int getOnlineTime() {
        return onlineTime;
    }
    public void setOnlineTime(int onlineTime) {
        this.onlineTime = onlineTime;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    
    

}
