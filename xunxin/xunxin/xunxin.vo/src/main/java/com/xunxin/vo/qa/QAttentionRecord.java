package com.xunxin.vo.qa;

import org.mongodb.framework.pojo.GeneralBean;

/**
 * 
 * Copyright © 2017 Xunxin Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2018年1月2日 -- 下午3:55:12
 * @Version 1.0
 * @Description     Q&A关注记录
 */
public class QAttentionRecord extends GeneralBean{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private int                         attentionId;                //被关注用户
    private int                         userId;                     //用户
    
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getAttentionId() {
        return attentionId;
    }
    public void setAttentionId(int attentionId) {
        this.attentionId = attentionId;
    }
    
    
    
    
    
    
    

}
