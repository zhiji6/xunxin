package com.xunxin.vo.user;

import org.mongodb.framework.pojo.GeneralBean;

/**
 * 
 * Copyright © 2017 Xunxin Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2017年12月17日 -- 下午4:48:55
 * @Version 1.0
 * @Description		用户动态记录
 */
public class UserDynamicRecordVO extends GeneralBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 行为记录项目
          Q&A发布（审核上线后）                                              覆盖
          Q&A答题                                                                      覆盖
                  相册照片增删                                                              不覆盖
                  个人资料修改，基本资料和详细资料（审核上线后）    不覆盖
                  资料认证项目新增、变更（审核上线后）                     不覆盖
                  共情圈发布的消息                                                       不覆盖
                  对共情圈他人的评论                                                    不覆盖
                  使用了广场的摇一摇                                                    覆盖
                  使用了广场的丢绣球                                                    覆盖
                  登上了广场的 排行榜                                                   不覆盖
                  回复了广场的答疑解惑                                                 不覆盖
                  使用了广场的转盘游戏                                                 覆盖
                  使用了广场的爱情速配                                                 覆盖
                  评论被置顶为固定选项                                                 不覆盖
                  覆盖项目记录用户第一次使用为展示时间，重复操作项目更改动态数字。
                  动态可删除，但不删除源数据
	 */
	
	public UserDynamicRecordVO(int thumbCount, String dynamicUrls, String content, String dynamicSource,
	        String dynamicExtend, int userId, int ishiden) {
	    super();
	    this.thumbCount = thumbCount;
	    this.dynamicUrls = dynamicUrls;
	    this.content = content;
	    this.dynamicSource = dynamicSource;
	    this.dynamicExtend = dynamicExtend;
	    this.userId = userId;
	    this.ishiden = ishiden;
	}
	
	public UserDynamicRecordVO() {
        super();
    }

    private int 							thumbCount; 		//点赞数
    private String							dynamicUrls;		//图片地址
	private String 							content;			//动态内容
	private String 							dynamicSource;		//动态来源
	private String 							time;		//动态附加信息
	private String 							dynamicExtend;		//动态附加信息
	private int 							userId;				//用户
	private int                             ishiden;            //是否隐藏   0:正常|1:隐藏
	
	public int getThumbCount() {
		return thumbCount;
	}
	public void setThumbCount(int thumbCount) {
		this.thumbCount = thumbCount;
	}
	public String getDynamicUrls() {
		return dynamicUrls;
	}
	public void setDynamicUrls(String dynamicUrls) {
		this.dynamicUrls = dynamicUrls;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getDynamicSource() {
		return dynamicSource;
	}
	public void setDynamicSource(String dynamicSource) {
		this.dynamicSource = dynamicSource;
	}
    public String getDynamicExtend() {
        return dynamicExtend;
    }
    public void setDynamicExtend(String dynamicExtend) {
        this.dynamicExtend = dynamicExtend;
    }
    public int getIshiden() {
        return ishiden;
    }
    public void setIshiden(int ishiden) {
        this.ishiden = ishiden;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
	
	
	
	

}
