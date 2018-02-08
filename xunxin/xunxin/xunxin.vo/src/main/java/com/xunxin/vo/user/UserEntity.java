package com.xunxin.vo.user;

import java.util.Date;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年10月2日 -- 下午4:19:36
 * @Version 1.0
 * @Description	 用户资料 pojo
 */
public class UserEntity {
	
	public static final int 						NORMAL = 0;	
	public static final int 						UNUSUAL = 1;
	public static final int 						COMPLETE = 2;
	
	/** constructor empty  */
	
	public UserEntity() {
		super();
	}
	
	/** constructor register  */
	public UserEntity(String phone, String iD, int grade, String password, Date createTime) {
		super();
		this.phone = phone;
		this.ID = iD;	
		this.grade = grade;
		this.password = password;
		this.createTime = createTime;
	}
	
	
/** #########   基本资料      #########  */
	public int 										uid;      			//主键ID        
	public String 									phone;				//手机号
	public String 									ID;					//系统ID
	public int 										grade;				//用户等级
	public String 									name;				//姓名
	public String 									certNo;				//身份证号码
	public String 									password;			//密码
	public Date 									createTime;    		//注册时间
	public Double 									amount;				//账户余额
	public int 										userExp;			//用户积分
	public int 										sex;				//初始性别
	public String 									gender;				//性别
	public String 									sexualOrientation;	//性取向
	public int 										age;				//年龄
	public String 									nickName;			//昵称
	public String 									address;			//常住地
	public int 										height;				//身高
	public String 									profession;			//职业
	public String 									trade;				//行业
	public String 									position;			//职位
	public String 									income;				//收入
	public String 									education;			//学历
	public String 									williamsCollege;	//毕业院校
	public String 									relationshipStatus;	//情感状态
	public String 									makeFriendWay;		//交友方式
	

/** #########   详细资料      #########  */
	public String 									introduce;			//自我介绍(独白)
	public String 									housingConditions;	//住房情况
	public String 									trafficTools;		//交通工具
	public int 										weight;				//体重
	public String 									nation;				//民族
	public String 									nationality;		//国籍
	public String 									nativePlace;		//籍贯
	public String 									censusRegister;		//户籍
	public String 									familyOrder;		//家中排行
	public String 									haveChild;			//有无子女
	public String 									religiousBelief;	//宗教信仰
	public String 									constellation;		//星座
	public String 									zodiac;				//生肖
	public String 									blood;				//血型
	
	
/** #########   用户的所有状态      #########  */
	public int 										certificationDegree;	//认证程度
	public int 										isCompleteBasic;		//是否填写基本资料
	public int 										isAuthentication;		//是否实名认证
	public int										isAuditor;             //是否是志愿者审核员 
	public int										isLogin;               //是否登录
	
/** #########   基本资料      #########  */
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public void setRelationshipStatus(String relationshipStatus) {
		this.relationshipStatus = relationshipStatus;
	}

	public void setMakeFriendWay(String makeFriendWay) {
		this.makeFriendWay = makeFriendWay;
	}

	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
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
	public int getUserExp() {
		return userExp;
	}

	public void setUserExp(int userExp) {
		this.userExp = userExp;
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
	
	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getRelationshipStatus() {
		return relationshipStatus;
	}

	public String getMakeFriendWay() {
		return makeFriendWay;
	}

	public int getCertificationDegree() {
		return certificationDegree;
	}
	public void setCertificationDegree(int certificationDegree) {
		this.certificationDegree = certificationDegree;
	}
	
	
	/** #########   详细资料      #########  */
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getHousingConditions() {
		return housingConditions;
	}

	public void setHousingConditions(String housingConditions) {
		this.housingConditions = housingConditions;
	}

	public String getTrafficTools() {
		return trafficTools;
	}

	public void setTrafficTools(String trafficTools) {
		this.trafficTools = trafficTools;
	}

	public void setHaveChild(String haveChild) {
		this.haveChild = haveChild;
	}

	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getNativePlace() {
		return nativePlace;
	}
	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}
	public String getCensusRegister() {
		return censusRegister;
	}
	public void setCensusRegister(String censusRegister) {
		this.censusRegister = censusRegister;
	}
	public String getFamilyOrder() {
		return familyOrder;
	}
	public void setFamilyOrder(String familyOrder) {
		this.familyOrder = familyOrder;
	}
	public String getReligiousBelief() {
		return religiousBelief;
	}
	public void setReligiousBelief(String religiousBelief) {
		this.religiousBelief = religiousBelief;
	}
	public String getConstellation() {
		return constellation;
	}
	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}
	public String getZodiac() {
		return zodiac;
	}
	public void setZodiac(String zodiac) {
		this.zodiac = zodiac;
	}
	public String getBlood() {
		return blood;
	}
	public void setBlood(String blood) {
		this.blood = blood;
	}

	public int getIsCompleteBasic() {
		return isCompleteBasic;
	}

	public void setIsCompleteBasic(int isCompleteBasic) {
		this.isCompleteBasic = isCompleteBasic;
	}
	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public int getIsAuthentication() {
		return isAuthentication;
	}

	public void setIsAuthentication(int isAuthentication) {
		this.isAuthentication = isAuthentication;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public int getIsAuditor() {
		return isAuditor;
	}

	public void setIsAuditor(int isAuditor) {
		this.isAuditor = isAuditor;
	}

	public int getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(int isLogin) {
		this.isLogin = isLogin;
	}

	public String getHaveChild() {
		return haveChild;
	}

	public String getSexualOrientation() {
		return sexualOrientation;
	}

	public void setSexualOrientation(String sexualOrientation) {
		this.sexualOrientation = sexualOrientation;
	}

	

	
	
}
