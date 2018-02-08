package com.xunxin.vo.user;

/**
 * 
 * Copyright © 2017 Xunxin Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2018年1月16日 -- 上午10:16:20
 * @Version 1.0
 * @Description   机器人
 */
public class UserRobot {

    private int                                 pid;
    private String                              name;     
    private String                              nickName;     
    private String                              ID;     
    private String                              gender;
    private int                                 ishiden;
    
    
    public UserRobot() {
        super();
    }
    
    public UserRobot(String name, String nickName, String iD, String gender, int ishiden) {
        super();
        this.name = name;
        this.nickName = nickName;
        ID = iD;
        this.gender = gender;
        this.ishiden = ishiden;
    }
    public int getPid() {
        return pid;
    }
    public void setPid(int pid) {
        this.pid = pid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getID() {
        return ID;
    }
    public void setID(String iD) {
        ID = iD;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public int getIshiden() {
        return ishiden;
    }
    public void setIshiden(int ishiden) {
        this.ishiden = ishiden;
    }     
    
    
    
}
