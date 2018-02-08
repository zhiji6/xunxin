package com.xunxin.vo.circle;

import java.util.Date;
import java.util.List;

import org.mongodb.framework.pojo.GeneralBean;
import org.springframework.data.annotation.Id;
/**
 * 共情圈，保存于mongodb数据库
 * @author gyf
 *
 */
public class EmpathyCircle extends GeneralBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4494296490771893350L;
	//内容
	private String content;

	//用户id
	private Integer userId;
	//地址
	private String address;
	//性别
	private String gender;
	//昵称
	private String nickName;
	//性取向
	private String sexualOrientation;
	//评论
	private List<CircleComment> circleComment;
	//图片
	private List<UserCirclePhoto> photos;
	//点赞数
	private Integer likeNum = 0;
	//照片是否模糊
	private Integer isVague =1 ;
	//更新时间
	private Date updateDate;
	//时间
	private String time;
	//是否点赞
	private Integer giveUp = 1;
	//查询工具
	private String $gte;
	//查询工具
	private String $date;
	private String $set;
	
	public String get$set() {
		return $set;
	}
	public void set$set(String $set) {
		this.$set = $set;
	}
	private String $in;
	
	public String get$in() {
		return $in;
	}
	public void set$in(String $in) {
		this.$in = $in;
	}
	public String get$date() {
		return $date;
	}
	public void set$date(String $date) {
		this.$date = $date;
	}
	public String get$gte() {
		return $gte;
	}
	public void set$gte(String $gte) {
		this.$gte = $gte;
	}
	public EmpathyCircle() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public List<CircleComment> getCircleComment() {
		return circleComment;
	}
	public void setCircleComment(List<CircleComment> circleComment) {
		this.circleComment = circleComment;
	}
	public Integer getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(Integer likeNum) {
		this.likeNum = likeNum;
	}

	public Integer getIsVague() {
		return isVague;
	}

	public void setIsVague(Integer isVague) {
		this.isVague = isVague;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	public List<UserCirclePhoto> getPhotos() {
		return photos;
	}
	public void setPhotos(List<UserCirclePhoto> photos) {
		this.photos = photos;
	}
	public Integer getGiveUp() {
		return giveUp;
	}
	public void setGiveUp(Integer giveUp) {
		this.giveUp = giveUp;
	}



}
