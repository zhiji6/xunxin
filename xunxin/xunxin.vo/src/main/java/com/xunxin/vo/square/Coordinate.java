package com.xunxin.vo.square;

import org.mongodb.framework.pojo.GeneralBean;

/**
 * 
 * Copyright © 2017 Xunxin Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2018年2月1日 -- 下午2:23:05
 * @Version 1.0
 * @Description     坐标
 */
public class Coordinate extends GeneralBean{

    /**
     * 
     */
    private static final long serialVersionUID = 888405181208183550L;
    
    private double                              longitude;          //所处经度
    private double                              latitude;           //所处纬度
    
    
    public Coordinate() {
        super();
    }

    public Coordinate(double longitude, double latitude) {
        super();
        this.longitude = longitude;
        this.latitude = latitude;
    }
    
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Coordinate [longitude=" + longitude + ", latitude=" + latitude + "]";
    }



    
}
