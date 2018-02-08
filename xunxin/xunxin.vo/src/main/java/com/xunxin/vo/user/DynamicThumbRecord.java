package com.xunxin.vo.user;

import org.mongodb.framework.pojo.GeneralBean;

public class DynamicThumbRecord extends GeneralBean{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    
    public DynamicThumbRecord(String dynamicId, int thumbState, int thumbId, int userId) {
        super();
        this.dynamicId = dynamicId;
        this.thumbState = thumbState;
        this.thumbId = thumbId;
        this.userId = userId;
    }
    public DynamicThumbRecord() {
        super();
    }
    private String                           dynamicId;         //动态id
    private int                              thumbState;        //已赞/未赞
    private int                              thumbId;           //被赞用户id
    private int                              userId;            //用户id

    public int getThumbState() {
        return thumbState;
    }
    public String getDynamicId() {
        return dynamicId;
    }
    public void setDynamicId(String dynamicId) {
        this.dynamicId = dynamicId;
    }
    public void setThumbState(int thumbState) {
        this.thumbState = thumbState;
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
    public int getThumbId() {
        return thumbId;
    }
    public void setThumbId(int thumbId) {
        this.thumbId = thumbId;
    }
    
    
    

}
