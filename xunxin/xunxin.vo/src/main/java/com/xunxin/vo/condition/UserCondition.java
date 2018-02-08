package com.xunxin.vo.condition;

/**
 * 
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月10日 -- 上午11:04:02
 * @Version 1.0
 * @Description		用户条件封装
 */
public class UserCondition {

	private int 			userId;
	private String 			name;					//姓名
	private String 			nickname;				//昵称
	private int 			sex;					//初始性别
	private String 			gender; 				//性别
	private int 			age;					//年龄
	private String 			sexualOrientation;		//性取向
	private String 			address;				//地址
	private int 			height; 				//身高
	private String 			profession; 			//职业
	private String 			trade;					//行业
	private String 			position;				//职位
	private String 			income;					//收入
	private String 			education;				//学历
	private String 			williamsCollege;		//毕业院校
	private String 			relationshipStatus;		//感情状态
	private String 			makeFriendWay;			//交友方式
	private int 			isCompleteBasic;			//交友方式
	
/**************   --- 无参构造  ---   *************** */
	public UserCondition() {
		super();
	}
	
/**************   --- 有参构造  ---   *************** */
	public UserCondition(int userId, String name,String nickname, int sex, String gender, int age, String sexualOrientation,
			String address, int height, String profession, String trade, String position, String income,
			String education, String williamsCollege, String relationshipStatus, String makeFriendWay,int isCompleteBasic) {
		super();
		this.userId = userId;
		this.name = name;
		this.nickname = nickname;
		this.sex = sex;
		this.gender = gender;
		this.age = age;
		this.sexualOrientation = sexualOrientation;
		this.address = address;
		this.height = height;
		this.profession = profession;
		this.trade = trade;
		this.position = position;
		this.income = income;
		this.education = education;
		this.williamsCollege = williamsCollege;
		this.relationshipStatus = relationshipStatus;
		this.makeFriendWay = makeFriendWay;
		this.isCompleteBasic = isCompleteBasic;
	}


	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getSexualOrientation() {
		return sexualOrientation;
	}
	public void setSexualOrientation(String sexualOrientation) {
		this.sexualOrientation = sexualOrientation;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getTrade() {
		return trade;
	}
	public void setTrade(String trade) {
		this.trade = trade;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getIncome() {
		return income;
	}
	public void setIncome(String income) {
		this.income = income;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getWilliamsCollege() {
		return williamsCollege;
	}
	public void setWilliamsCollege(String williamsCollege) {
		this.williamsCollege = williamsCollege;
	}
	public String getRelationshipStatus() {
		return relationshipStatus;
	}
	public void setRelationshipStatus(String relationshipStatus) {
		this.relationshipStatus = relationshipStatus;
	}
	public String getMakeFriendWay() {
		return makeFriendWay;
	}
	public void setMakeFriendWay(String makeFriendWay) {
		this.makeFriendWay = makeFriendWay;
	}

	public int getIsCompleteBasic() {
		return isCompleteBasic;
	}

	public void setIsCompleteBasic(int isCompleteBasic) {
		this.isCompleteBasic = isCompleteBasic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
