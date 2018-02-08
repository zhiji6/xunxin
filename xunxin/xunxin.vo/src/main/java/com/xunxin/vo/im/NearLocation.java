package com.xunxin.vo.im;


import java.util.Date;

import org.mongodb.framework.pojo.GeneralBean;

public class NearLocation extends GeneralBean{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -215448190111903703L;

	//用户
	private Integer userId;
	
	//地区
	private String cityCode;
	
	//经度
	private Double longitude;
	
	//纬度
	private Double latitude;

	//GeoHash code
	private String geoHashCode;
	
	//跟新时间
	private Date updateTime;
	
	//是否在线  是0 否 1
	private Integer isOnLine;
	
	//相间的距离
	private Double distance;

	private String $or;
	
	private String $regex;
	
	private String $ne;

	private String $gte;
	
	private String $lte;
	
	public String get$gte() {
		return $gte;
	}

	public void set$gte(String $gte) {
		this.$gte = $gte;
	}

	public String get$lte() {
		return $lte;
	}

	public void set$lte(String $lte) {
		this.$lte = $lte;
	}

	public String get$ne() {
		return $ne;
	}

	public void set$ne(String $ne) {
		this.$ne = $ne;
	}

	public String get$or() {
		return $or;
	}

	public void set$or(String $or) {
		this.$or = $or;
	}

	public String get$regex() {
		return $regex;
	}

	public void set$regex(String $regex) {
		this.$regex = $regex;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}



	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getGeoHashCode() {
		return geoHashCode;
	}

	public void setGeoHashCode(String geoHashCode) {
		this.geoHashCode = geoHashCode;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getIsOnLine() {
		return isOnLine;
	}

	public void setIsOnLine(Integer isOnLine) {
		this.isOnLine = isOnLine;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	
}
